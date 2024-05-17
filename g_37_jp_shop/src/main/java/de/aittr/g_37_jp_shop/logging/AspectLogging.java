package de.aittr.g_37_jp_shop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// AspectJ framework
@Aspect
@Component
public class AspectLogging {

    private Logger logger = LoggerFactory.getLogger(AspectLogging.class);

    // Lesson 15 HW. A
    @Pointcut("execution(* de.aittr.g_37_jp_shop.service.ProductServiceImpl.*" +
            "(..))")
    public void allProductServiceMethods() {
    }


    // Lesson 15 HW. B
    @Pointcut("execution(* de.aittr.g_37_jp_shop.service.*.*(..))")
    public void allDomainEntityClasses() {
    }


    // Lesson 15 HW. B
    @Around("allDomainEntityClasses()")
    public Object aroundAllClassesEntity(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Метод «{}»  класса «{}», вызываемый с параметрами «{}»",
                methodName, joinPoint.getTarget().getClass().getName(), args);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch(Throwable e) {
            logger.error("Метод «{}» класса «{}» вызвал исключение: '{}'",
                    methodName, joinPoint.getTarget().getClass().getName(),
                    e.getMessage());
            throw e;
        }

        logger.info("Метод «{}» класса «{}» завершил свою работу", methodName,
                joinPoint.getTarget().getClass().getName());
        return result;
    }





/*

    // Lesson 15 HW. A
    @Before("allProductServiceMethods()")
    public void beforeProductServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("Метод {} класса ProductServiceImpl, вызываемый с
        параметрами {}", methodName, args);
    }

    // Lesson 15 HW. A
    @After("allProductServiceMethods()")
    public void afterProductServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("Метод {} класса ProductServiceImpl завершил свою
        работу", methodName);
    }


    //@Pointcut("execution(* de.aittr.g_37_jp_shop.service.ProductServiceImpl
    .save(de.aittr.g_37_jp_shop.domain.dto.ProductDto))")
    @Pointcut("execution(* de.aittr.g_37_jp_shop.service.ProductServiceImpl
    .save(..))")
    public void saveProduct() {}

    @Before("saveProduct()")
    public void beforeSavingProduct(JoinPoint joinPoint) {
        // code injection
        // Advice (адвайс) - тот код, который и будет внедряться в основной код,
        // используя правило, описанное в @Pointcut

        Object[] args = joinPoint.getArgs();
        logger.info("Method save of the class ProductServiceImpl called with
        parameter {}", args[0]);
    }

    @After("saveProduct()")
    public void afterSavingProduct() {
        logger.info("Method save of the class ProductServiceImpl finished its
         work");
    }

    @Pointcut("execution(* de.aittr.g_37_jp_shop.service.ProductServiceImpl
    .getById(..))")
    public void getProductById() {}

//    @AfterReturning("getProductById()")
//    public void afterReturningProductById() {
//        logger.info("Method getById of the class ProductServiceImpl
successfully returned product");
//    }

    @AfterReturning(pointcut = "getProductById()", returning = "result")
    public void afterReturningProductById(Object result) {
        logger.info("Method getById of the class ProductServiceImpl
        successfully returned product {}", result);
    }

    @AfterThrowing(pointcut = "getProductById()", throwing = "e")
    public void afterThrowingAnExceptionWhileGettingProductById(Exception e) {
        logger.warn("Method getById of the class ProductServiceImpl threw an
        exception while getting product: " +
                "message - {}", e.getMessage());
    }

    @Pointcut("execution(* de.aittr.g_37_jp_shop.service.ProductServiceImpl
    .getAll(..))")
    public void getAllProducts() {}

    @Around("getAllProducts()")
    public Object aroundGettingAllProducts(ProceedingJoinPoint joinPoint) {
        logger.info("Method getAll of the class ProductServiceImpl called");

        List<ProductDto> result = null;

        try {
            result = ((List<ProductDto>) joinPoint.proceed())
                    .stream()
                    .filter(x -> x.getPrice().compareTo(new BigDecimal(100))
                    > 0)
                    .toList();
        } catch (Throwable e) {
            logger.error("Method getAll of the class ProductServiceImpl threw
             exception: {}", e.getMessage());
        }

        logger.info("Method getAll of the class ProductServiceImpl finished
        its work");
        return result;
    }*/

}