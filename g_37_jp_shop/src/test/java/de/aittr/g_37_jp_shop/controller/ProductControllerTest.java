package de.aittr.g_37_jp_shop.controller;

import de.aittr.g_37_jp_shop.domain.dto.ProductDto;
import de.aittr.g_37_jp_shop.domain.entity.Product;
import de.aittr.g_37_jp_shop.domain.entity.Role;
import de.aittr.g_37_jp_shop.domain.entity.User;
import de.aittr.g_37_jp_shop.repository.ProductRepository;
import de.aittr.g_37_jp_shop.repository.RoleRepository;
import de.aittr.g_37_jp_shop.repository.UserRepository;
import de.aittr.g_37_jp_shop.security.sec_dto.TokenResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    private TestRestTemplate template;
    private HttpHeaders headers;
    private ProductDto testProduct;

    private final String TEST_PRODUCT_NAME = "Test product";
    private final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(777);
    private final String TEST_ADMIN_NAME = "Test Admin";
    private final String TEST_USER_NAME = "Test User";
    private final String TEST_PASSWORD = "Test password";
    private final String TEST_EMAIL = "test@test.com";
    private final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
    private final String USER_ROLE_NAME = "ROLE_USER";

    private final String AUTH_HEADER_NAME = "Authorization";
    private final String BEARER_PREFIX = "Bearer ";

    private final String HTTP_PREFIX = "http://localhost:";
    private final String AUTH_RESOURCE_NAME = "/auth";
    private final String PRODUCTS_RESOURCE_NAME = "/products";
    private final String LOGIN_ENDPOINT = "/login";
    private final String ALL_ENDPOINT = "/all";

    private String adminAccessToken;
    private String userAccessToken;

    @BeforeEach
    public void setUp() {
        template = new TestRestTemplate();
        headers = new HttpHeaders();

        testProduct = new ProductDto();
        testProduct.setTitle(TEST_PRODUCT_NAME);
        testProduct.setPrice(TEST_PRODUCT_PRICE);

        BCryptPasswordEncoder encoder = null;
        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME);
        User user = userRepository.findByUsername(TEST_USER_NAME);

        if(admin == null) {
            encoder = new BCryptPasswordEncoder();
            roleAdmin = roleRepository.findByTitle(ADMIN_ROLE_NAME);
            roleUser = roleRepository.findByTitle(USER_ROLE_NAME);

            admin = new User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));
            admin.setEmail(TEST_EMAIL);
            admin.setActive(true);

            userRepository.save(admin);
        }

        if(user == null) {
            encoder = encoder == null ? new BCryptPasswordEncoder() : encoder;

            user = new User();
            user.setUsername(TEST_USER_NAME);
            user.setPassword(encoder.encode(TEST_PASSWORD));
            user.setRoles(Set.of(roleUser == null ?
                    roleRepository.findByTitle(USER_ROLE_NAME) : roleUser));
            user.setEmail(TEST_EMAIL);
            user.setActive(true);

            userRepository.save(user);
        }

        admin.setPassword(TEST_PASSWORD);
        admin.setRoles(null);

        user.setPassword(TEST_PASSWORD);
        user.setRoles(null);

        String url = HTTP_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;
        HttpEntity<User> request = new HttpEntity<>(admin, headers);

        ResponseEntity<TokenResponseDto> response = template
                .exchange(url, HttpMethod.POST, request,
                        TokenResponseDto.class);

        assertTrue(response.hasBody(), "Token response does not contain body");

        adminAccessToken = BEARER_PREFIX + response.getBody().getAccesToken();

        request = new HttpEntity<>(user, headers);

        response = template
                .exchange(url, HttpMethod.POST, request,
                        TokenResponseDto.class);

        assertTrue(response.hasBody(), "Token response does not contain body");

        userAccessToken = BEARER_PREFIX + response.getBody().getAccesToken();
    }

    @Test
    @Order(1)
    public void positiveGettingAllProductsWithoutAuthorization() {

        String url = HTTP_PREFIX + port + PRODUCTS_RESOURCE_NAME + ALL_ENDPOINT;
        HttpEntity<Object> request = new HttpEntity<>(headers);

        ResponseEntity<ProductDto[]> response = template
                .exchange(url, HttpMethod.GET, request, ProductDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has " +
                "incorrect http status");
        assertTrue(response.hasBody(), "Response has empty body");
    }

    @Test
    public void negativeSavingProductWithoutAuthorization() {

        String url = HTTP_PREFIX + port + PRODUCTS_RESOURCE_NAME;
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(),
                "Response has incorrect http status");
        assertFalse(response.hasBody(), "Response has unexpected body");

    }

    @Test
    public void negativeSavingProductWithUserAuthorization() {

        String url = HTTP_PREFIX + port + PRODUCTS_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(),
                "Response has incorrect USER ROLE");

    }

    @Test
    public void positiveSavingProductWithAdminAuthorization() {

        String url = HTTP_PREFIX + port + PRODUCTS_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));
        HttpEntity<ProductDto> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDto> response = template
                .exchange(url, HttpMethod.POST, request, ProductDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode(),
                "Response has incorrect http request");

        ProductDto savedProduct = response.getBody();
        assertNotNull(savedProduct, "Response body is empty (but product " +
                "expected");
        assertNotNull(savedProduct.getId(), "Product ID is null");
        assertEquals(TEST_PRODUCT_NAME, savedProduct.getTitle(),
                "Received Product has unexpected title");


    }

    @AfterEach
    @Order(Integer.MAX_VALUE)
    public void removeTestProductFromDatabase() {
        Product product = productRepository.findByTitle(TEST_PRODUCT_NAME);
        productRepository.delete(product);
    }


}