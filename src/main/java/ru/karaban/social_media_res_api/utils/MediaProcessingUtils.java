package ru.karaban.social_media_res_api.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MediaProcessingUtils {

    @Value("${media_storage_location}")
    private String mediasDirectory;

    public String getAccountHash(String nickName) {
        try {
            byte[] encodedHash = MessageDigest.getInstance("MD5").digest(nickName.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static String bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }

    public String getHashPath(String fileName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.digest(fileName.getBytes(StandardCharsets.UTF_8));
            md.update(LocalDateTime.now().toString().getBytes(StandardCharsets.UTF_8));
            return bytesToHex(md.digest());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return MessageUtils.BAD_GEN_HASH;
    }

    public Path getFilePath(String fileName, String accountHash) {
        String hashPath = getHashPath(fileName);
        String directoryPath =
                mediasDirectory + File.separator + accountHash.substring(0, 2) + File.separator + accountHash.substring(3, 5) + File.separator + hashPath ;
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory;
    }
}
