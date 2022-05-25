package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Opcion;
import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.service.EstudianteService;
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
@RequestMapping("/estudiante")
@CrossOrigin("*")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<Estudiante>>> getAllEstudiantes(){

        GeneralResponse<List<Estudiante>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Estudiante> data;

        data = estudianteService.getAllEstudiantes();

        if(data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Lista de estudiantes obtenida con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("La lista de estudiantes esta vacia");
        }

        return new ResponseEntity<>(response, status);

    }

    @PutMapping(consumes = "application/json;charset=UTF-8;application/x-www-form-urlencoded")
    public ResponseEntity<GeneralResponse<Estudiante>> saveEstudiante(@RequestBody Estudiante estudiante){

        GeneralResponse response = new GeneralResponse();
        Estudiante nuevoEstudiante;
        HttpStatus status = HttpStatus.OK;

        nuevoEstudiante = estudianteService.saveEstudiante(estudiante);

        if(nuevoEstudiante != null){
            response.setData(nuevoEstudiante);
            response.setSuccess(true);
            response.setMessage("Estudiante actualizado con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estudiante");
        }

        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/estudiantenombre")
    public ResponseEntity<GeneralResponse<Usuario>> estudianteByNombre(@RequestParam(value = "nombre") String nombre) {

        GeneralResponse<Usuario> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Estudiante data;

        data = estudianteService.estudianteByNombre(nombre);

        if(data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Busqueda realizada con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("El parametro buscado no existe en la base de datos, por favor verificar");
        }

        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/opciones")
    public ResponseEntity<GeneralResponse<List<Opcion>>> allOpciones(@RequestParam(value = "idpregunta") String idpregunta){

        GeneralResponse<List<Opcion>> response = new GeneralResponse<>();
        HttpStatus status;
        List<Opcion> data;

        data = estudianteService.opcionesQuery(idpregunta);

        if (data != null) {
            response.setData(data);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(response, status);
    }
}
