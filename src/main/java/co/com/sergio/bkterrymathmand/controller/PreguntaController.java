package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.service.PreguntaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    GeneralResponse<List<Pregunta>> response = new GeneralResponse<>();
    List<Pregunta> data;
    HttpStatus status;

    data = preguntaService.findAllPregunta();

    if(data != null){
      status = HttpStatus.OK;
      response.setData(data);
    }else{
      status = HttpStatus.NOT_FOUND;
    }

    return new ResponseEntity<>(response, status);
  }
}
