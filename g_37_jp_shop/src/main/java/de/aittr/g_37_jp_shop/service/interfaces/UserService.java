package de.aittr.g_37_jp_shop.service.interfaces;

import de.aittr.g_37_jp_shop.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(User user);
}