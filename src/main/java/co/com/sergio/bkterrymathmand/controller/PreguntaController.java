package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.service.PreguntaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 10:33
 **/

@RestController
@RequestMapping("/pregunta")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class PreguntaController {

    @Autowired
    private PreguntaService preguntaService;

    @ApiOperation(value = "Método encargado de obtener la lista de preguntas", response = ResponseEntity.class)
    @GetMapping
    public ResponseEntity<GeneralResponse<List<Pregunta>>> obtenerPreguntas() {

        GeneralResponse<List<Pregunta>> response = new GeneralResponse<>();
        List<Pregunta> data;
        HttpStatus status = HttpStatus.OK;

        data = preguntaService.obtenerPreguntas();

        if (data != null) {
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Lista de preguntas obtenida con exito");
        } else {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("La lista de preguntas esta vacia");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista de preguntas por filtro (id, enunciado)", response = ResponseEntity.class)
    @GetMapping("/filtrar")
    public ResponseEntity<GeneralResponse<Page<Pregunta>>> filtrar(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "enunciado", required = false) String enunciado,
            @RequestParam(value = "pagina", defaultValue = "0", required = false) int pagina,
            @RequestParam(value = "cantPagina", defaultValue = "10", required = false) int cantPagina
    ) {

        GeneralResponse<Page<Pregunta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Page<Pregunta> data;

        try {

            Pageable pageable = PageRequest.of(pagina, cantPagina, Sort.by("idpregunta").ascending());

            data = preguntaService.filtrarPregunta(id, enunciado, pageable);

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);

                if (data.getContent().size() > 1) {
                    response.setMessage("Lista de preguntas obtenida con exito");
                } else if (data.getContent().size() == 1) {
                    response.setMessage("Pregunta obtenida con exito");
                } else {
                    response.setSuccess(false);
                    response.setMessage("No se encontro ninguna pregunta");
                }
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("La lista de preguntas esta vacia");
            }
        } catch (NumberFormatException nfe) {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error, se solicito un parametro de busca no valido");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de crear y guardar una pregunta nueva")
    @PostMapping
    public ResponseEntity<GeneralResponse<Pregunta>> crearPregunta(
            @RequestPart(value = "file", required = false) MultipartFile file, @RequestParam("pregunta") String pregunta
    ) throws JsonProcessingException {
        GeneralResponse<Pregunta> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Pregunta data;

        Pregunta preguntaJson = new Pregunta();

        ObjectMapper obj = new ObjectMapper();
        preguntaJson = obj.readValue(pregunta, Pregunta.class);

        try {
            if (!verificarImagen(file) && file != null) {
                response.setData(preguntaJson);
                response.setMessage("Por favor selecciona una imagen en .png o .jpg");
                response.setSuccess(false);

                return new ResponseEntity<>(response, status);
            }

            data = preguntaService.crearPregunta(preguntaJson, file);

            if (data == null) {
                response.setData(null);
                response.setMessage("Hubo un error al crear la pregunta");
                response.setSuccess(false);
            } else if (data.getOpciones() == null) {
                response.setData(data);
                response.setMessage("Hubo un error al crear las opciones de la pregunta");
                response.setSuccess(false);
            } else {
                response.setData(data);
                response.setMessage("Se creo la pregunta correctamente");
                response.setSuccess(true);
            }

            return new ResponseEntity<>(response, status);

        } catch (IOException e) {
            response.setData(null);
            response.setMessage("Hubo un error al crear la imagen de la pregunta");
            response.setSuccess(false);

            return new ResponseEntity<>(response, status);
        } catch (Exception e) {
            response.setData(null);
            response.setMessage(e.getLocalizedMessage());
            response.setSuccess(false);

            return new ResponseEntity<>(response, status);
        }
    }

    @ApiOperation(value = "Método encargado de actualizar una pregunnta, sus opciones e imagen", response = ResponseEntity.class)
    @PostMapping("/editarPregunta")
    public ResponseEntity<GeneralResponse<Pregunta>> editarPregunta(
            @RequestPart(value = "file", required = false) MultipartFile file, @RequestParam("pregunta") String pregunta
    ) throws JsonProcessingException {

        GeneralResponse<Pregunta> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Pregunta data;

        Pregunta preguntaJson = new Pregunta();

        ObjectMapper obj = new ObjectMapper();
        preguntaJson = obj.readValue(pregunta, Pregunta.class);

        try {
            if (!verificarImagen(file) && file != null) {
                response.setData(preguntaJson);
                response.setMessage("Por favor selecciona una imagen en .png o .jpg");
                response.setSuccess(false);

                return new ResponseEntity<>(response, status);
            }

            data = preguntaService.editarPregunta(preguntaJson, file);

            if (data == null) {
                response.setData(null);
                response.setMessage("Hubo un error al editar la pregunta");
                response.setSuccess(false);
            } else if (data.getOpciones() == null) {
                response.setData(data);
                response.setMessage("Hubo un error al editar las opciones de la pregunta");
                response.setSuccess(false);
            } else if (data.getUrlImg() == "-1") {
                response.setData(data);
                response.setMessage("Hubo un error al editar la imagen de la pregunta");
                response.setSuccess(false);
            } else {
                response.setData(data);
                response.setMessage("Se edito correctamente");
                response.setSuccess(true);
            }

            return new ResponseEntity<>(response, status);

        } catch (IOException e) {

            response.setData(null);
            response.setMessage("Hubo un error al editar la imagen de la pregunta");
            response.setSuccess(false);

            return new ResponseEntity<>(response, status);
        }
    }

    @ApiOperation(value = "Método encargado de eliminar una pregunta")
    @DeleteMapping
    public ResponseEntity<GeneralResponse<Boolean>> eliminarPregunta(@RequestBody Pregunta pregunta) {

        GeneralResponse<Boolean> response = new GeneralResponse<>();
        Boolean data;
        HttpStatus status = HttpStatus.OK;


        try {
            data = preguntaService.eliminarPregunta(pregunta);

            if (data) {
                response.setData(true);
                response.setSuccess(true);
                response.setMessage("Pregunta eliminada con exito");
            } else {
                response.setData(false);
                response.setSuccess(false);
                response.setMessage("No se pudo eliminar la pregunta");
            }

            return new ResponseEntity<>(response, status);

        } catch (Exception e) {
            response.setData(false);
            response.setSuccess(false);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, status);
        }
    }

    private boolean verificarImagen(MultipartFile file) throws IOException {

        if (file != null) {
            BufferedImage bi = ImageIO.read(file.getInputStream());
            if (bi == null) {
                return false;
            }
        }
        return true;
    }
}
