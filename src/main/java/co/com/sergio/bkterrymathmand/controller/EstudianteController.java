package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.service.EstudianteService;
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
 * @Date 22/04/2022 11:27
 **/

@RestController
@RequestMapping("/estudiante")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;

    @ApiOperation(value = "Método encargado de obtener la lista de estudiantes", response = ResponseEntity.class)
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

    @ApiOperation(value = "Método encargado de agregar un nuevo estudiante", response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<GeneralResponse<Estudiante>> agregarEstudiante(@RequestBody Estudiante estudiante){

        GeneralResponse<Estudiante> response = new GeneralResponse<>();
        Estudiante nuevoEstudiante;
        HttpStatus status = HttpStatus.OK;

        nuevoEstudiante = estudianteService.agregarEstudiante(estudiante);

        if(nuevoEstudiante != null){
            response.setData(nuevoEstudiante);
            response.setSuccess(true);
            response.setMessage("Estudiante agregado con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se pudo agregar o ya existe el estudiante");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de actualizar un estudiante", response = ResponseEntity.class)
    @PutMapping
    public ResponseEntity<GeneralResponse<Estudiante>> actualizarEstudiante(@RequestBody Estudiante estudiante){

        GeneralResponse<Estudiante> response = new GeneralResponse<>();
        Estudiante data;
        HttpStatus status = HttpStatus.OK;

        data = estudianteService.actualizarEstudiante(estudiante);

        if(data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Estudiante actualizado con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estudiante");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de eliminar un estudiante", response = ResponseEntity.class)
    @DeleteMapping
    public ResponseEntity<GeneralResponse<Boolean>> eliminarEstudiante(@RequestBody Estudiante estudiante){

        GeneralResponse<Boolean> response = new GeneralResponse<>();
        boolean data;
        HttpStatus status = HttpStatus.OK;

        data = estudianteService.eliminarEstudiante(estudiante);

        if(data){
            response.setData(true);
            response.setSuccess(true);
            response.setMessage("Estudiante eliminado con exito");
        }else{
            response.setData(false);
            response.setSuccess(false);
            response.setMessage("No se pudo eliminar el estudiante");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de agregar o actualizar una repuesta a un estudiante", response = ResponseEntity.class)
    @PutMapping(value = "/guardarRespuesta", consumes = "application/json;charset=UTF-8;application/x-www-form-urlencoded")
    public ResponseEntity<GeneralResponse<Estudiante>> guardarRespuesta(@RequestBody Estudiante estudiante){

        GeneralResponse<Estudiante> response = new GeneralResponse<>();
        Estudiante nuevoEstudiante;
        HttpStatus status = HttpStatus.OK;

        nuevoEstudiante = estudianteService.guardarRespuesta(estudiante);

        if(nuevoEstudiante != null){
            response.setData(nuevoEstudiante);
            response.setSuccess(true);
            response.setMessage("Se agrego la respuesta con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se pudo agregar o actualiza la respuesta del estudiante");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de ontener un estudiante por el nombre", response = ResponseEntity.class)
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

    @ApiOperation(value = "Método encargado de obtener al lista de estudiantes por filtros (nombre. fecha)", response = ResponseEntity.class)
    @GetMapping("/filtrar")
    public ResponseEntity<GeneralResponse<List<Estudiante>>> filtrar(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "fecha", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {

        GeneralResponse<List<Estudiante>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Estudiante> data;

        try {
            data = estudianteService.filtrarEstudiante(nombre, fecha);

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
