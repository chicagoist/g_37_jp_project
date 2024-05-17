package de.aittr.g_37_jp_shop.repository;

import de.aittr.g_37_jp_shop.domain.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    ConfirmationCode findByCode(String code);
}