package de.aittr.g_37_jp_shop.service;

import de.aittr.g_37_jp_shop.domain.entity.User;
import de.aittr.g_37_jp_shop.repository.UserRepository;
import de.aittr.g_37_jp_shop.service.interfaces.EmailService;
import de.aittr.g_37_jp_shop.service.interfaces.RoleService;
import de.aittr.g_37_jp_shop.service.interfaces.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private BCryptPasswordEncoder encoder;
    private RoleService roleService;
    private EmailService emailService;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder, RoleService roleService, EmailService emailService) {
        this.repository = repository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public void register(User user) {
        user.setId(null);
        user.setPassword(encoder.encode(user.getPassword()));

        // Эта одна строчка кода делает то же самое,
        // что и четыре закомментированные строчки ниже
        user.setRoles(Set.of(roleService.getRoleUser()));

//        Role roleUser = roleService.getRoleUser();
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleUser);
//        user.setRoles(roles);

        user.setActive(false);

        repository.save(user);
        emailService.sendConfirmationEmail(user);
    }
}