package School.Management.School.Management.service.impl;

import School.Management.School.Management.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map upload=cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder","school_management")
            );
            return upload.get("secure_url").toString();
        }catch (IOException e){
            throw  new RuntimeException("Image upload failed",e);
        }

    }
}
