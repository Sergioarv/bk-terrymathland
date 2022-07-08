package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.dto.IEstudianteProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.service.EstudianteService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<GeneralResponse<List<Estudiante>>> obtenerEstudiantes(){

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

    @ApiOperation(value = "Método encargado de eliminar un estudiante", response = ResponseEntity.class)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
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

    @ApiOperation(value = "Método encargado de ontener un estudiante por el nombre para el juego", response = ResponseEntity.class)
    @GetMapping("/estudiantenombre")
    public ResponseEntity<GeneralResponse<IEstudianteProyeccion>> estudianteByNombre(@RequestParam(value = "nombre") String nombre) {

        GeneralResponse<IEstudianteProyeccion> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        IEstudianteProyeccion data;

        data = estudianteService.estudianteByNombre(nombre);

        if(data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Busqueda realizada con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("El nombre buscado no existe en la base de datos, por favor verificar");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener al lista de estudiantes por filtros (nombre. fecha)", response = ResponseEntity.class)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
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

    @ApiOperation(value = "Método encargado de obtener la lista unicamente id y nombre de estudiantes", response = ResponseEntity.class)
    @GetMapping("/listarEstudiantes")
    public ResponseEntity<GeneralResponse<List<IEstudianteProyeccion>>> obtenerIdyNombreEstudiantes(){

        GeneralResponse<List<IEstudianteProyeccion>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<IEstudianteProyeccion> data;

        data = estudianteService.obtenerIdyNombreEstudiantes();

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
}
