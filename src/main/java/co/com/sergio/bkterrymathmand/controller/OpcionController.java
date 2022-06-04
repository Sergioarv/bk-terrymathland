package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Opcion;
import co.com.sergio.bkterrymathmand.service.OpcionService;
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
 * @Date 4/06/2022 11:22
 **/
@RestController
@RequestMapping("/opcion")
@CrossOrigin("*")
public class OpcionController {

    @Autowired
    private OpcionService opcionService;

    @GetMapping("/pregunta")
    public ResponseEntity<GeneralResponse<List<Opcion>>> obtenerOpcionesDePregunta(@RequestParam(value = "idpregunta") int idpregunta) {

        GeneralResponse<List<Opcion>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Opcion> data;

        data = opcionService.obtenerOpcionesDePregunta(idpregunta);

        if (data != null) {
            response.setData(data);
            response.setMessage("Opciones obtenidos con exito");
            response.setSuccess(true);
        } else {
            response.setData(null);
            response.setMessage("Error al obtener las opciones");
            response.setSuccess(true);
        }

        return new ResponseEntity<>(response, status);
    }
}
