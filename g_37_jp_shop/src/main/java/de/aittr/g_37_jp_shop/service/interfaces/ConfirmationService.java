package de.aittr.g_37_jp_shop.service.interfaces;

import de.aittr.g_37_jp_shop.domain.entity.User;

public interface ConfirmationService {

    String generateConfirmationCode(User user);
}