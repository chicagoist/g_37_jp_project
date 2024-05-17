package de.aittr.g_37_jp_shop.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(MultipartFile file, String productTitle);
}
