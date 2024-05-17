package de.aittr.g_37_jp_shop.service;

import de.aittr.g_37_jp_shop.domain.entity.ConfirmationCode;
import de.aittr.g_37_jp_shop.domain.entity.User;
import de.aittr.g_37_jp_shop.repository.ConfirmationCodeRepository;
import de.aittr.g_37_jp_shop.service.interfaces.ConfirmationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private ConfirmationCodeRepository repository;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateConfirmationCode(User user) {
        LocalDateTime expired = LocalDateTime.now().plusMinutes(1);
        String code = UUID.randomUUID().toString();
        ConfirmationCode entity = new ConfirmationCode(code, expired, user);
        repository.save(entity);
        return code;
    }

//    public static void main(String[] args) {
//        // UUID demo
//        System.out.println(UUID.randomUUID());
//        System.out.println(UUID.randomUUID());
//        System.out.println(UUID.randomUUID());
//    }
}