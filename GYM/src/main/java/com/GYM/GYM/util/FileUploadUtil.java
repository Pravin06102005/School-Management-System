package com.GYM.GYM.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadUtil {

    // private final String UPLOAD_DIR = "uploads/";
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";


    public  String saveFile(MultipartFile file)throws IOException{
        if (file==null || file.isEmpty()){
            return null;
        }

        // Create directory if not exists
        Path uploadPath= Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String fileName=System.currentTimeMillis()+"_"+file.getOriginalFilename();
        Path filePath=uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        return fileName;
    }

    public void deleteFile(String fileName) throws IOException{
        if (fileName !=null){
            Path filePath=Paths.get(UPLOAD_DIR ).resolve(fileName);
            Files.deleteIfExists(filePath);
        }
    }
}

