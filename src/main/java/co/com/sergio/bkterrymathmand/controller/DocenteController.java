package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Docente;
import co.com.sergio.bkterrymathmand.service.DocenteService;
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
 * @Date 22/04/2022 11:27
 **/

@RestController
@RequestMapping("/docente")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @ApiOperation(value = "Método encargado de listar los docentes", response = ResponseEntity.class)
    @GetMapping
    public ResponseEntity<GeneralResponse<List<Docente>>> obtenerDocentes(){

        GeneralResponse<List<Docente>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Docente> data;

        data = docenteService.obtenerDocentes();

        if(data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Lista de docentes obtenida con exito");
        }else{
            response.setData(null);
            response.setSuccess(true);
            response.setMessage("La lista de docentes esta vacia");
        }

        return new ResponseEntity<>(response, status);

    }

    @ApiOperation(value = "Método encargado de buscar un docente por nombre", response = ResponseEntity.class)
    @GetMapping("/docentenombre")
    public ResponseEntity<GeneralResponse<Docente>> docentePorNombre(@RequestParam(value = "nombre") String nombre) {

        GeneralResponse<Docente> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Docente data;

        data = docenteService.docentePorNombre(nombre);

        if(data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Docente obtenido con exito");
        }else{
            response.setData(null);
            response.setSuccess(true);
            response.setMessage("Hubo un error al buscar el docente");
        }

        return new ResponseEntity<>(response, status);
    }
}
