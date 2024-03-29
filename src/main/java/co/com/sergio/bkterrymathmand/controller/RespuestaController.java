package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.dto.IDatosPromedioEstudiante;
import co.com.sergio.bkterrymathmand.dto.IDatosaGraficarDTO;
import co.com.sergio.bkterrymathmand.dto.IRespuestaProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.service.RespuestaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @ApiOperation(value = "Método encargado de obtener la lista de respuestas", response = ResponseEntity.class)
    @GetMapping
    public ResponseEntity<GeneralResponse<List<Respuesta>>> obtenerRespuestas() {

        GeneralResponse<List<Respuesta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Respuesta> data;

        try {
            data = respuestaService.obtenerRespuestas();

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);
                response.setMessage("Lista de respuestas obtenida con éxito");
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("Hubo un error al obtener la lista de respuestas");
            }
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error al obtener la lista de respuestas");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista de respuestas por filtro")
    @GetMapping("/filtrar")
    public ResponseEntity<GeneralResponse<Page<Respuesta>>> obtenerRespuestasPorFiltro(
            @RequestParam(value = "estudiante", required = false) Estudiante estudiante,
            @RequestParam(value = "fecha", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @RequestParam(value = "pagina", defaultValue = "0", required = false) int pagina,
            @RequestParam(value = "cantPagina", defaultValue = "10", required = false) int cantPagina
    ) {

        GeneralResponse<Page<Respuesta>> response = new GeneralResponse<>();
        Page<Respuesta> data;
        HttpStatus status = HttpStatus.OK;

        try {
            Pageable pageable = PageRequest.of(pagina, cantPagina, Sort.by("fecha").descending());

            data = respuestaService.obtenerRespuestasPorFiltro(estudiante, fecha, pageable);

            if (data != null) {

                response.setData(data);
                response.setSuccess(true);

                if (data.getContent().size() > 1) {
                    response.setMessage("Lista de respuestas obtenida con exito");
                } else if (data.getContent().size() == 1) {
                    response.setMessage("Respuesta obtenido con exito");
                } else {
                    response.setSuccess(false);
                    response.setMessage("No se encontro ningun respuesta");
                }
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("Lista de resultados esta vacia");
            }
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error al obtener la lista de respuestas");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de agregar o actualizar una repuesta a un estudiante desde el juego", response = ResponseEntity.class)
    @PutMapping(value = "/guardarRespuestaEstudiante", consumes = "application/json;charset=UTF-8;application/x-www-form-urlencoded")
    public ResponseEntity<GeneralResponse<List<IRespuestaProyeccion>>> guardarRespuesta(@RequestBody Estudiante estudiante) {

        GeneralResponse<List<IRespuestaProyeccion>> response = new GeneralResponse<>();
        List<IRespuestaProyeccion> nuevoEstudiante;
        HttpStatus status = HttpStatus.OK;

        try {
            nuevoEstudiante = respuestaService.guardarRespuestaEstudiante(estudiante);

            if (nuevoEstudiante != null) {
                response.setData(nuevoEstudiante);
                response.setSuccess(true);
                response.setMessage("Se agrego la respuesta con exito");
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("No se pudo aguardar la respuesta del estudiante");
            }
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error al guardar la respuesta del estudiante");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista de respuestas para graficar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE') or hasRole('ESTUDIANTE')")
    @GetMapping("/graficarRespuestas")
    public ResponseEntity<GeneralResponse<IDatosaGraficarDTO>> graficarRespuestas(
            @RequestParam(value = "estudiante", required = false) Estudiante estudiante,
            @RequestParam(value = "fecha", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha
    ) {

        GeneralResponse<IDatosaGraficarDTO> response = new GeneralResponse<>();
        IDatosaGraficarDTO data;
        HttpStatus status = HttpStatus.OK;

        try {
            data = respuestaService.graficarRespuestas(estudiante, fecha);

            if (data != null) {
                if (data.getListaPromedioNotas().size() == 0) {
                    response.setData(data);
                    response.setSuccess(false);
                    response.setMessage("No se encontraron datos de respuestas para graficar");
                } else if (verificarPromedioEstudiantes(data.getListaPromedioEstudiantes())) {
                    response.setData(data);
                    response.setSuccess(true);
                    response.setMessage("No se encontraron datos de respuestas para la grafica de torta");
                } else {
                    response.setData(data);
                    response.setSuccess(true);
                    response.setMessage("Se han obtenido los datos de respuestas a graficar");
                }
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("Lista de resultados esta vacia");
            }
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error al consultar los datos de respuestas");
        }

        return new ResponseEntity<>(response, status);
    }

    private boolean verificarPromedioEstudiantes(List<IDatosPromedioEstudiante> listaPromedioEstudiantes) {

        float total = 0;

        for (IDatosPromedioEstudiante dato : listaPromedioEstudiantes) {
            total += dato.getPromedioestudiantes();
        }

        return total == 0;
    }

}
