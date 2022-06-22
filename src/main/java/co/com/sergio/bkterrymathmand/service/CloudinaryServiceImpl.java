package co.com.sergio.bkterrymathmand.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:application.properties")
public class CloudinaryServiceImpl implements CloudinaryService {

    Cloudinary cloudinary;

    private Map<String, String> valuesMap = new HashMap<>();


    public CloudinaryServiceImpl() {
        valuesMap.put("cloud_name", "dj8sqmb8n");
        valuesMap.put("api_key", "764965299756129");
        valuesMap.put("api_secret", "PN6bdT58d_SJZQbv_vJnt7xjCdY");
        cloudinary = new Cloudinary(valuesMap);
    }

    @Override
    public Map cargarImagen(MultipartFile imagen) throws IOException {

        File file = convertir(imagen);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();

        return result;
    }

    @Override
    public Map eliminarImagen(String idImagen) throws IOException {
        Map result = cloudinary.uploader().destroy(idImagen, ObjectUtils.emptyMap());

        return result;
    }

    private File convertir(MultipartFile imagen) throws IOException {
        File file = new File(imagen.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagen.getBytes());
        fos.close();

        return file;
    }
}
