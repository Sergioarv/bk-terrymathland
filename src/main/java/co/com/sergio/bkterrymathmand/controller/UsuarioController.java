package co.com.sergio.bkterrymathmand.controller;

import co.com.sergio.bkterrymathmand.entity.Opcion;
import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.service.UsuarioService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 11:27
 **/

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<Usuario>>> getAllUsuarios(){

        GeneralResponse<List<Usuario>> response = new GeneralResponse<>();
        HttpStatus status = HttpStatus.NOT_FOUND;
        List<Usuario> data = null;

        data = usuarioService.getAllUsuarios();

        if(data != null){
            response.setData(data);
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, status);

    }

    @GetMapping("/usuarionombre")
    public ResponseEntity<Usuario> usuarioByNombre(@RequestParam(value = "nombre") String nombre) {

        HttpStatus status = null;
        Usuario data = null;

        data = usuarioService.usuarioByNombre(nombre);

        if (data != null) {
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(data, status);
    }

    @GetMapping("/opciones")
    public ResponseEntity<GeneralResponse<List<Opcion>>> allOpciones(@RequestParam(value = "idpregunta") String idpregunta){

        GeneralResponse<List<Opcion>> response = new GeneralResponse<>();
        HttpStatus status = null;
        List<Opcion> data = null;

        data = usuarioService.opcionesQuery(idpregunta);

        if (data != null) {
            response.setData(data);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(response, status);
    }
}
