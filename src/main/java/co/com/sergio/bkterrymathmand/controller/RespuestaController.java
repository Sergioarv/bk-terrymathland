package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.service.RespuestaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import io.swagger.annotations.ApiOperation;
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
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @ApiOperation(value = "Método encargado de obtener la lista de respuestas", response = ResponseEntity.class)
    @GetMapping
    public ResponseEntity<GeneralResponse<List<Respuesta>>> obtenerRespuestas() {

        GeneralResponse<List<Respuesta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Respuesta> data;

        data = respuestaService.obtenerRespuestas();

        if (data != null) {
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Lista de respuestas obtenida con exito");
        }else {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error al obtener la lista de respuestas");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista de respuestas por fecha", response = ResponseEntity.class)
    @GetMapping("/fecha")
    public ResponseEntity<GeneralResponse<List<Respuesta>>> obtenerRespuestaPorFecha(
            @RequestParam(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {

        GeneralResponse<List<Respuesta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Respuesta> data;

        data = respuestaService.obtenerRespuestaPorFecha(fecha);

        if (data != null) {
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Lista de respuestas obtenida con exito");
        }else {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se encontro respuestas con el parametro de busqueda");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista de respuestas por fecha y estudiante", response = ResponseEntity.class)
    @GetMapping("/fechaUsuario")
    public ResponseEntity<GeneralResponse<Respuesta>> obtenerRespuestaPorFechaYEstudiante(
            @RequestParam(value = "fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @RequestParam(value = "usuario") int idusuario
    ) {
        GeneralResponse<Respuesta> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Respuesta data;

        data = respuestaService.obtenerRespuestaPorFechaYEstudiante(fecha, idusuario);

        if (data != null) {
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Respuesta obtenida con exito");
        }else {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se encontraron respuesta del usuario en las fechas especificadas");
        }

        return new ResponseEntity<>(response, status);
    }
}
