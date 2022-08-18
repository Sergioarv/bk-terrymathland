package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.dto.ICartillaProyeccion;
import co.com.sergio.bkterrymathmand.entity.Cartilla;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.service.CartillaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 15:08
 **/
@RestController
@RequestMapping("/cartilla")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CartillaController {

    @Autowired
    private CartillaService cartillaService;

    @ApiOperation(value = "Método encargado de obtener la lista de cartillas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    @GetMapping
    public ResponseEntity<GeneralResponse<List<Cartilla>>> obtenerCartillas() {

        GeneralResponse<List<Cartilla>> response = new GeneralResponse<>();
        List<Cartilla> data;
        HttpStatus status = HttpStatus.OK;

        try {
            data = cartillaService.obtenerCartillas();

            if (data != null) {
                if (data.size() > 0) {
                    response.setData(data);
                    response.setSuccess(true);
                    response.setMessage("Lista de cartillas obtenida con exito");
                } else {
                    response.setData(data);
                    response.setSuccess(false);
                    response.setMessage("La lista de cartillas esta vacia");
                }
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("No se obtuvo la lista de cartillas");
            }
        } catch (Exception e) {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se obtuvo la lista de cartillas");
        }

        return new ResponseEntity<>(response, status);
    }

    /**
     * @return
     */
    @ApiOperation(value = "Método encargado de lista una proyeccion de cartillas para mostrar en el juego")
    @GetMapping("/listarCartillas")
    public ResponseEntity<GeneralResponse<List<ICartillaProyeccion>>> listarCartillas() {

        GeneralResponse<List<ICartillaProyeccion>> response = new GeneralResponse<>();
        List<ICartillaProyeccion> data;
        HttpStatus status = HttpStatus.OK;

        data = cartillaService.listarCartillas();

        if (data != null) {
            if (data.size() > 0) {
                response.setData(data);
                response.setSuccess(true);
                response.setMessage("Lista de cartillas obtenida con exito");
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("La lista de cartillas esta vacia");
            }
        } else {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se obtuvo la lista de cartillas");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista preguntas por filtro de cartilla", response = ResponseEntity.class)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    @GetMapping("/filtrarPreguntas")
    public ResponseEntity<GeneralResponse<Page<Pregunta>>> filtrarPreguntas(
            @RequestParam(value = "idcartilla", required = false) String idcartilla,
            @RequestParam(value = "pagina", defaultValue = "0", required = false) int pagina,
            @RequestParam(value = "cantPagina", defaultValue = "10", required = false) int catnPagina
    ) {

        GeneralResponse<Page<Pregunta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        Page<Pregunta> data;

        try {
            Pageable pageable = PageRequest.of(pagina, catnPagina);

            data = cartillaService.filtrarPregunta(idcartilla, pageable);

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);

                if (data.getContent().size() > 1) {
                    response.setMessage("Preguntas de la cartilla obtenida con exito");
                } else if (data.getContent().size() == 1) {
                    response.setMessage("Pregunta de la cartilla obtenida con exito");
                } else {
                    response.setSuccess(false);
                    response.setMessage("No se encontro ninguna pregunta con esta cartilla");
                }
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("La cartilla no tiene preguntas");
            }
        } catch (NumberFormatException nfe) {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error, se solicito un parametro de busca no valido");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de actualizar las preguntas de la cartilla")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    @PutMapping
    public ResponseEntity<GeneralResponse<Boolean>> actualizarCartilla(
            @RequestBody Cartilla cartilla
    ) {
        GeneralResponse<Boolean> response = new GeneralResponse<>();
        Boolean data;
        HttpStatus status = HttpStatus.OK;

        try {
            data = cartillaService.actualizarCartilla(cartilla);

            if (data) {
                response.setData(true);
                response.setSuccess(true);
                response.setMessage("Se ha actualizado la lista de preguntas de la cartilla");
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("No se pudo editar la lista de preguntas de cartilla");
            }
        } catch (Exception e) {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se pudo editar la lista de preguntas de cartilla");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de crear una cartilla con sus preguntas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    @PostMapping
    public ResponseEntity<GeneralResponse<Cartilla>> crearCartilla(
            @RequestBody Cartilla cartilla
    ) {
        GeneralResponse<Cartilla> response = new GeneralResponse<>();
        Cartilla data;
        HttpStatus status = HttpStatus.OK;
        try {
            data = cartillaService.crearCartilla(cartilla);

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);
                response.setMessage("Se ha creado la cartilla con exito con exito");
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("Hubo un error al crear la cartilla");
            }
        } catch (Exception e) {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error al crear la cartilla");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de eliminar una cartilla con sus preguntas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    @DeleteMapping
    public ResponseEntity<GeneralResponse<Boolean>> eliminarCartilla(@RequestBody Cartilla cartilla) {

        GeneralResponse<Boolean> response = new GeneralResponse<>();
        Boolean data;
        HttpStatus status = HttpStatus.OK;

        try {
            data = cartillaService.eliminarCartilla(cartilla);

            if (data) {
                response.setData(true);
                response.setSuccess(true);
                response.setMessage("La cartilla se elimino exitosamente");
            } else {
                response.setData(false);
                response.setSuccess(false);
                response.setMessage("No se a podido eliminar la cartilla");
            }
        } catch (Exception e){
            response.setData(false);
            response.setSuccess(false);
            response.setMessage("No se puede eliminar la cartilla");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista de preguntas de una cartilla para el juego")
    @GetMapping("/obtenerPreguntas")
    public ResponseEntity<GeneralResponse<List<Pregunta>>> obtenerPreguntas(@RequestParam(value = "idcartilla") int idcartilla) {
        GeneralResponse<List<Pregunta>> response = new GeneralResponse<>();
        List<Pregunta> data;
        HttpStatus status = HttpStatus.OK;

        data = cartillaService.obtenerPreguntas(idcartilla);

        if (data != null) {
            response.setData(data);
            response.setSuccess(true);
            if (data.size() > 1) {
                response.setMessage("Lista de preguntas obtenida con exito de la cartilla solicitada");
            } else if (data.size() == 1) {
                response.setMessage("Pregunta de la cartilla obtenida con exito");
            } else {
                response.setSuccess(false);
                response.setMessage("No se encontro ninguna pregunta con esta cartilla");
            }
        } else {
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se pudo obtener la lista de preguntas de la cartilla");
        }

        return new ResponseEntity<>(response, status);
    }
}
