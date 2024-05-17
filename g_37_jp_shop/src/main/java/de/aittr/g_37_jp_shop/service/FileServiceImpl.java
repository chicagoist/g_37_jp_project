package de.aittr.g_37_jp_shop.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import de.aittr.g_37_jp_shop.service.interfaces.FileService;
import de.aittr.g_37_jp_shop.service.interfaces.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private AmazonS3 client;
    private ProductService productService;

    public FileServiceImpl(AmazonS3 client, ProductService productService) {
        this.client = client;
        this.productService = productService;
    }

    @Override
    public String upload(MultipartFile file, String productTitle) {
        try {

            String uniqueFileName = generateUniqueFileName(file);

/*            Files.copy(
                    file.getInputStream(),
                    Path.of(
                   "C:\\Users\\ThinkPad T430\\IdeaProjects\\g_37_jp_shop" +
                           "\\files\\" + uniqueFileName));
*/
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());

            PutObjectRequest request = new PutObjectRequest(
                    "cohort-37-bucket",
                    uniqueFileName,
                    file.getInputStream(),
                    metadata
            ).withCannedAcl(CannedAccessControlList.PublicRead);

            client.putObject(request);

            String url =
                    client.getUrl("cohort-37-bucket", uniqueFileName).toString();

            productService.attachImage(url,productTitle);

            return uniqueFileName;


        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueFileName(MultipartFile file) {
        String sourceFileName = file.getOriginalFilename();
        assert sourceFileName != null;
        int dotIndex = sourceFileName.lastIndexOf(".");
        String fileName = sourceFileName.substring(0, dotIndex);
        String extention = sourceFileName.substring(dotIndex);

        return String.format("%s--Valerii--%s%s", fileName, UUID.randomUUID(),
                extention);
    }
}
