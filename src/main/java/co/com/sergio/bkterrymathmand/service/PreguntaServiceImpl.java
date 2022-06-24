package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Cartilla;
import co.com.sergio.bkterrymathmand.entity.Opcion;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.repository.OpcionRepository;
import co.com.sergio.bkterrymathmand.repository.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 10:31
 **/

@Service
public class PreguntaServiceImpl implements PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private OpcionRepository opcionRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> obtenerPreguntas() {
        return preguntaRepository.findAll(Sort.by(Sort.Direction.ASC, "idpregunta"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPorId(int id) {
        return preguntaRepository.filtrarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPor(String filtro) {
        return preguntaRepository.filtrarPor(filtro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPorIdOEnunciado(int id, String enunciado) {
        return preguntaRepository.filtrarPorIdOEnunciado(id, enunciado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Pregunta> filtrarPregunta(String id, String enunciado) {

        List<Pregunta> listResult;

        if (id != null && enunciado != null) {
            listResult = filtrarPorIdOEnunciado(Integer.parseInt(id), enunciado);
        } else if (id != null) {
            listResult = filtrarPorId(Integer.parseInt(id));
        } else if (enunciado != null) {
            listResult = filtrarPor(enunciado);
        } else {
            listResult = obtenerPreguntas();
        }

        return listResult;
    }

    @Override
    @Transactional
    public Pregunta editarPregunta(Pregunta pregunta, MultipartFile file) {

        List<Opcion> opciones;
        Pregunta preguntaGuardada;
        Pregunta preguntaEditada = null;

        preguntaGuardada = preguntaRepository.findById(pregunta.getIdpregunta()).orElse(null);

        if (preguntaGuardada != null) {
            for (int i = 0; i < pregunta.getOpciones().size(); i++) {
                pregunta.getOpciones().get(i).setPregunta(preguntaGuardada);
            }
            opciones = opcionRepository.saveAll(pregunta.getOpciones());
            pregunta.setOpciones(opciones);

            if (opciones != null) {
                pregunta.setCartillas(preguntaGuardada.obtenerCartillas());
                preguntaEditada = preguntaRepository.save(pregunta);

                if (preguntaEditada != null) {
                    try {
                        if (preguntaEditada.getUrlImg().equals("") && !preguntaEditada.getIdImg().equals("")) {
                            Map borrado = cloudinaryService.eliminarImagen(preguntaEditada.getIdImg());
                            if (!("not found".equalsIgnoreCase(borrado.get("result").toString()))) {
                                preguntaEditada.setUrlImg(preguntaGuardada.getUrlImg());
                                preguntaRepository.save(preguntaEditada);
                                preguntaEditada.setUrlImg("-1");
                                return preguntaEditada;
                            }
                            preguntaEditada.setUrlImg("");
                            preguntaEditada.setNombreImg("");
                            preguntaEditada.setIdImg("");
                        } else if (!preguntaEditada.getUrlImg().equals("") && !preguntaEditada.getIdImg().equals("")) {
                            if (file != null) {
                                Map borrado = cloudinaryService.eliminarImagen(preguntaEditada.getIdImg());
                                if (!("not found".equalsIgnoreCase(borrado.get("result").toString()))) {
                                    preguntaEditada.setUrlImg(preguntaGuardada.getUrlImg());
                                    preguntaRepository.save(preguntaEditada);
                                    preguntaEditada.setUrlImg("-1");
                                    return preguntaEditada;
                                } else {
                                    Map imagen = cloudinaryService.cargarImagen(file);
                                    preguntaEditada.setUrlImg((String) imagen.get("url"));
                                    preguntaEditada.setNombreImg((String) imagen.get("original_filename"));
                                    preguntaEditada.setIdImg((String) imagen.get("public_id"));
                                }
                            }
                        } else if (preguntaEditada.getUrlImg().equals("") && preguntaEditada.getIdImg().equals("")) {
                            if (file != null) {
                                Map imagen = cloudinaryService.cargarImagen(file);
                                preguntaEditada.setUrlImg((String) imagen.get("url"));
                                preguntaEditada.setNombreImg((String) imagen.get("original_filename"));
                                preguntaEditada.setIdImg((String) imagen.get("public_id"));
                            }
                        }
                        preguntaRepository.save(preguntaEditada);
                    } catch (IOException e) {
                        preguntaEditada = null;
                    }
                }
            }
        }
        return preguntaEditada;
    }

    @Override
    @Transactional
    public Pregunta crearPregunta(Pregunta pregunta, MultipartFile file) throws Exception {

        try {
            List<Opcion> opciones;
            Pregunta preguntaGuardada = new Pregunta();

            preguntaGuardada = preguntaRepository.save(pregunta);


            if (preguntaGuardada != null) {
                for (int i = 0; i < pregunta.getOpciones().size(); i++) {
                    pregunta.getOpciones().get(i).setIdopcion(0);
                    pregunta.getOpciones().get(i).setPregunta(preguntaGuardada);
                }
                opciones = opcionRepository.saveAll(pregunta.getOpciones());
                preguntaGuardada.setOpciones(opciones);

                if (opciones != null) {

                    preguntaGuardada = preguntaRepository.save(preguntaGuardada);

                    if (preguntaGuardada != null) {
                        try {
                            if (file != null) {
                                Map imagen = cloudinaryService.cargarImagen(file);
                                preguntaGuardada.setUrlImg((String) imagen.get("url"));
                                preguntaGuardada.setNombreImg((String) imagen.get("original_filename"));
                                preguntaGuardada.setIdImg((String) imagen.get("public_id"));
                            }
                            preguntaRepository.save(preguntaGuardada);
                        } catch (IOException e) {
                            preguntaGuardada = null;
                        }
                    }
                }
            }
            return preguntaGuardada;
        } catch (Exception pe) {
            throw new Exception("Error llave duplicada");
        }
    }

    @Override
    public Boolean eliminarPregunta(Pregunta pregunta) throws Exception {

        try {

            Pregunta pOriginal = preguntaRepository.findById(pregunta.getIdpregunta()).orElse(null);

            if(pOriginal == null){
                return false;
            }

            List<Cartilla> cartillas = pOriginal.obtenerCartillas();
            if(cartillas.size() == 0){
                for (int i = 0; i < pregunta.getOpciones().size(); i++) {
                    pregunta.getOpciones().get(i).setPregunta(null);
                }
                opcionRepository.deleteAll(pregunta.getOpciones());
                preguntaRepository.delete(pregunta);

                return true;
            }else{
                throw new Exception("No se puede eliminar la pregunta, primero debe eliminar la pregunta de las cartillas");
            }
        }catch (Exception e){
            throw new Exception("Error al eliminar la pregunta");
        }
    }
}
