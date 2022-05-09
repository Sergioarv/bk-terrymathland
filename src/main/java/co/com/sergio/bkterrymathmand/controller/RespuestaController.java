package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.service.RespuestaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 14:17
 **/

@RestController
@RequestMapping("/respuesta")
@CrossOrigin("*")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<Respuesta>>> getAllRespuesta() {

        GeneralResponse<List<Respuesta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        List<Respuesta> data = null;

        data = respuestaService.getAllRespuesta();

        if (data != null) {
            response.setData(data);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/fecha")
    public ResponseEntity<GeneralResponse<List<Respuesta>>> getRespuestaByFecha(@RequestParam(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {

        GeneralResponse<List<Respuesta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        List<Respuesta> data = null;

        data = respuestaService.getRespuestaByFecha(fecha);

        if (data != null) {
            response.setData(data);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, status);
    }

    @PutMapping
    public ResponseEntity<Respuesta> saveRespuesta(@RequestBody Respuesta respuesta){

        Respuesta data = null;
        HttpStatus status = HttpStatus.NOT_FOUND;

        data = respuestaService.saveRespuesta(respuesta);

        if(data != null){
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(data, status);
    }
    @GetMapping("/fechaUsuario")
    public ResponseEntity<Respuesta> getRespuestaByFechaAndUsuario(
            @RequestParam(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @RequestParam(value = "usuario") String usuario
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        Respuesta data = null;

        data = respuestaService.getRespuestaByFechaAndUsuario(fecha, usuario);

        if (data != null) {
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(data, status);
    }
}
