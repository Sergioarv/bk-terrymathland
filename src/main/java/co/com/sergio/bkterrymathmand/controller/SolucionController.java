package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Solucion;
import co.com.sergio.bkterrymathmand.service.SolucionService;
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
 * @Date 22/04/2022 15:08
 **/
@RestController
@RequestMapping("/solucion")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
public class SolucionController {

    @Autowired
    private SolucionService solucionService;

    @ApiOperation(value = "Método encargado de obtener la lista de soluciones", response = ResponseEntity.class)
    @GetMapping
    public ResponseEntity<GeneralResponse<List<Solucion>>> obtenerSoluciones(){

        GeneralResponse<List<Solucion>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Solucion> data;

        data = solucionService.obtenerSoluciones();

        if (data != null) {
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Lista de soluciones obtenida con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error al obtener la lista de soluciones");
        }

        return new ResponseEntity<>(response, status);

    }
}
