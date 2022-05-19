package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Docente;
import co.com.sergio.bkterrymathmand.service.DocenteService;
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
 * @Date 22/04/2022 11:27
 **/

@RestController
@RequestMapping("/docente")
@CrossOrigin("*")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<Docente>>> getAllDocente(){

        GeneralResponse<List<Docente>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        List<Docente> data = null;

        data = docenteService.getAllDocente();

        if(data != null){
            response.setData(data);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, status);

    }

    @PutMapping(consumes = "application/json;charset=UTF-8;application/x-www-form-urlencoded")
    public ResponseEntity<Docente> saveDocente(@RequestBody Docente docente){

        Docente nuevoDocente = null;
        HttpStatus status = HttpStatus.NOT_FOUND;

        nuevoDocente = docenteService.saveDocente(docente);

        if(nuevoDocente != null){
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(nuevoDocente, status);
    }

    @GetMapping("/docentenombre")
    public ResponseEntity<Docente> docenteByNombre(@RequestParam(value = "nombre") String nombre) {

        HttpStatus status = null;
        Docente data = null;

        data = docenteService.docenteByNombre(nombre);

        if (data != null) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(data, status);
    }
}
