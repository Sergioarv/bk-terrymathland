package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Cartilla;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import co.com.sergio.bkterrymathmand.service.CartillaService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartilla")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE})
public class CartillaController {

    @Autowired
    private CartillaService cartillaService;

    @ApiOperation(value = "Método encargado de obtener la lista de cartillas")
    @GetMapping
    public ResponseEntity<GeneralResponse<List<Cartilla>>> obtenerCartillas(){

        GeneralResponse<List<Cartilla>> response = new GeneralResponse<>();
        List<Cartilla> data;
        HttpStatus status = HttpStatus.OK;

        data = cartillaService.obtenerCartillas();

        if( data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Lista de cartillas obtenida con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se obtuvo la lista de cartillas");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de obtener la lista preguntas por filtro de cartilla", response = ResponseEntity.class)
    @GetMapping("/filtrarPreguntas")
    public ResponseEntity<GeneralResponse<List<Pregunta>>> filtrarPreguntas(
            @RequestParam(value = "idcartilla", required = false) String idcartilla) {

        GeneralResponse<List<Pregunta>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.OK;
        List<Pregunta> data;

        try {
            data = cartillaService.filtrarPregunta(idcartilla);

            if (data != null) {
                response.setData(data);
                response.setSuccess(true);

                if (data.size() > 1) {
                    response.setMessage("Preguntas de la cartilla obtenida con exito");
                } else if (data.size() == 1) {
                    response.setMessage("Pregunta de la cartilla obtenida con exito");
                } else {
                    response.setSuccess(false);
                    response.setMessage("No se encontro ninguna pregunta con esta cartilla");
                }
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("La cartilla no tiene preguntas o ");
            }
        }catch (NumberFormatException nfe){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error, se solicito un parametro de busca no valido");
        }
        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de actualizar las preguntas de la cartilla")
    @PutMapping
    public ResponseEntity<GeneralResponse<Boolean>> actualizarCartilla(
            @RequestBody Cartilla cartilla
    ){
        GeneralResponse<Boolean> response = new GeneralResponse<>();
        Boolean data;
        HttpStatus status = HttpStatus.OK;

        data = cartillaService.actualizarCartilla(cartilla);

        if( data){
            response.setData(true);
            response.setSuccess(true);
            response.setMessage("Lista de cartillas obtenida con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se obtuvo la lista de cartillas");
        }

        return new ResponseEntity<>(response, status);
    }


    @ApiOperation(value = "Método encargado de obtener la lista de preguntas de una cartillas")
    @GetMapping("/obtenerPreguntas")
    public ResponseEntity<GeneralResponse<List<Pregunta>>> obtenerPreguntas(@RequestParam(value = "idcartilla") int idcartilla){
        GeneralResponse<List<Pregunta>> response = new GeneralResponse<>();
        List<Pregunta> data;
        HttpStatus status = HttpStatus.OK;

        data = cartillaService.obtenerPreguntas(idcartilla);

        if( data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Lista de preguntas obtenida con exito de la cartilla solicitada");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se obtuvo la lista de preguntas de la cartilla");
        }

        return new ResponseEntity<>(response, status);
    }
}
