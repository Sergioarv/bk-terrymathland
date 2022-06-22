package co.com.sergio.bkterrymathmand.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    public Map cargarImagen(MultipartFile imagen) throws IOException;

    public Map eliminarImagen(String idImagen) throws IOException;
}
