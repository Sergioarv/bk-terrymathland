package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Docente;
import co.com.sergio.bkterrymathmand.service.DocenteService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<GeneralResponse<List<Docente>>> obtenerDocentes(){

        GeneralResponse<List<Docente>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Docente> data;

        try {
            data = docenteService.obtenerDocentes();

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);
                if (data.size() > 1) {
                    response.setMessage("Lista de docentes obtenida con exito");
                } else if (data.size() == 1) {
                    response.setMessage("Docente obtenida con exito");
                } else {
                    response.setSuccess(false);
                    response.setMessage("La lista de docentes esta vacia");
                }
            } else {
                response.setData(null);
                response.setSuccess(true);
                response.setMessage("Hubo un error al obtener la lista de docentes");
            }
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(true);
            response.setMessage("Hubo un error al obtner la lista de docentes");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista de docentes por filtros (nombre, correo)", response = ResponseEntity.class)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/filtrar")
    public ResponseEntity<GeneralResponse<Page<Docente>>> filtrar(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "documento", required = false) String documento,
            @RequestParam(value = "pagina", defaultValue = "0", required = false) int pagina,
            @RequestParam(value = "cantPagina", defaultValue = "10", required = false) int cantPagina
    ) {

        GeneralResponse<Page<Docente>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Page<Docente> data;

        try {
            PageRequest pageable = PageRequest.of(pagina, cantPagina, Sort.by("idusuario"));
            data = docenteService.filtrarDocente(nombre, documento, pageable);

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);

                if (data.getContent().size() > 1) {
                    response.setMessage("Lista de docentes obtenida con exito");
                } else if (data.getContent().size() == 1) {
                    response.setMessage("Docente obtenido con exito");
                } else {
                    response.setSuccess(false);
                    response.setMessage("No se encontro ningun docente");
                }
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("La lista de docentes esta vacia");
            }
        }catch (NumberFormatException nfe){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error, se solicito un parametro de busca no valido");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de eliminar un docente", response = ResponseEntity.class)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<GeneralResponse<Boolean>> eliminarDocente(@RequestBody Docente docente){

        GeneralResponse<Boolean> response = new GeneralResponse<>();
        boolean data;
        HttpStatus status = HttpStatus.OK;

        try{
            data = docenteService.eliminarDocente(docente);

            if (data) {
                response.setData(true);
                response.setSuccess(true);
                response.setMessage("Docente eliminado con exito");
            } else {
                response.setData(false);
                response.setSuccess(false);
                response.setMessage("No se pudo eliminar el docente");
            }
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(true);
            response.setMessage("No se puede eliminar el docentes");
        }
        return new ResponseEntity<>(response, status);
    }
}
