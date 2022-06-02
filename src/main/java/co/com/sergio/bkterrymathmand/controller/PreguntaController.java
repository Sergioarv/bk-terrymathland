package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.service.PreguntaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
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
@CrossOrigin("*")
public class PreguntaController {

  @Autowired
  private PreguntaService preguntaService;

  @GetMapping
  public ResponseEntity<GeneralResponse<List<Pregunta>>> getPreguntas(){

    GeneralResponse<List<Pregunta>> response = new GeneralResponse();
    List<Pregunta> data = null;
    HttpStatus status = HttpStatus.OK;

    data = preguntaService.findAllPregunta();


    if(data != null){
      response.setData(data);
      response.setSuccess(true);
      response.setMessage("Lista de preguntas obtenida con exito");
    }else{
      response.setData(null);
      response.setSuccess(false);
      response.setMessage("La lista de preguntas esta vacia");
    }

    return new ResponseEntity<>(response, status);
  }

  @GetMapping("/filtrar")
  public ResponseEntity<GeneralResponse<List<Pregunta>>> filtrar(
          @RequestParam(value = "id", required = false) String id,
          @RequestParam(value = "enunciado", required = false) String enunciado ){

    GeneralResponse<List<Pregunta>> response = new GeneralResponse<>();
    HttpStatus status = HttpStatus.OK;
    List<Pregunta> data = null;

    data = preguntaService.filtrarPregunta(id, enunciado);

    if(data != null){
        response.setData(data);
        response.setSuccess(true);

        if(data.size() > 1){
            response.setMessage("Lista de preguntas obtenida con exito");
        }else if(data.size() == 1){
            response.setMessage("Preguntaa obtenida con exito");
        }else{
            response.setSuccess(false);
            response.setMessage("No se encontro ninguna pregunta");
        }
    }else{
      response.setData(null);
      response.setSuccess(false);
      response.setMessage("La lista de preguntas esta vacia");
    }

    return new ResponseEntity<>(response, status);

  }

}
