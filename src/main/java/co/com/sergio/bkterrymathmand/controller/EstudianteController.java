package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
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

        GeneralResponse<Estudiante> response = new GeneralResponse<>();
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

    @GetMapping("/filtrar")
    public ResponseEntity<GeneralResponse<List<Estudiante>>> filtrar(
            @RequestParam(value = "nombre", required = false) String nombre) {

        GeneralResponse<List<Estudiante>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Estudiante> data;

        try {
            data = estudianteService.filtrarEstudiante(nombre);

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);

                if (data.size() > 1) {
                    response.setMessage("Lista de estudiantes obtenida con exito");
                } else if (data.size() == 1) {
                    response.setMessage("Estudiante obtenido con exito");
                } else {
                    response.setSuccess(false);
                    response.setMessage("No se encontro ningun estudiante");
                }
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("La lista de estudiantes esta vacia");
            }
        }catch (NumberFormatException nfe){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error, se solicito un parametro de busca no valido");
        }
        return new ResponseEntity<>(response, status);
    }
}
