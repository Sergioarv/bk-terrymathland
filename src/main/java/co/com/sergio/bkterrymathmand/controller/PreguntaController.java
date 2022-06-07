package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.service.PreguntaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 10:33
 **/

@RestController
@RequestMapping("/pregunta")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
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
    public ResponseEntity<GeneralResponse<List<Pregunta>>> filtrar(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "enunciado", required = false) String enunciado) {

        GeneralResponse<List<Pregunta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Pregunta> data;

        try {
            data = preguntaService.filtrarPregunta(id, enunciado);

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);

                if (data.size() > 1) {
                    response.setMessage("Lista de preguntas obtenida con exito");
                } else if (data.size() == 1) {
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
        }catch (NumberFormatException nfe){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error, se solicito un parametro de busca no valido");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de actualizar una pregunnta", response = ResponseEntity.class)
    @PutMapping(consumes = { "application/json;charset=UTF-8;application/x-www-form-urlencoded; multipart/form-data"} )
    public ResponseEntity<GeneralResponse<Pregunta>> editarPregunta(@RequestBody Pregunta pregunta){

        GeneralResponse<Pregunta> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Pregunta data;

        data = preguntaService.editarPregunta(pregunta);

        if(data != null){
            if(data.getOpciones() != null ) {
                response.setData(data);
                response.setMessage("Se edito correctamente");
                response.setSuccess(true);
            }else{
                response.setData(data);
                response.setMessage("Hubo un error al editar la opciones");
                response.setSuccess(true);
            }
        } else{
            response.setData(null);
            response.setMessage("Huo un error al editar la pregunta");
            response.setSuccess(false);
        }

        return new ResponseEntity<>(response, status);
    }
}
