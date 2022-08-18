package co.com.sergio.bkterrymathmand.security.controller;

import co.com.sergio.bkterrymathmand.entity.Docente;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.security.dto.JwtDto;
import co.com.sergio.bkterrymathmand.security.dto.LoginUsuario;
import co.com.sergio.bkterrymathmand.security.jwt.JwtProvider;
import co.com.sergio.bkterrymathmand.service.DocenteService;
import co.com.sergio.bkterrymathmand.service.EstudianteService;
import co.com.sergio.bkterrymathmand.service.UsuarioService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;


/**
 * @project bk-terry-math-land
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 01/07/2022 16:51
 **/
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EstudianteService estudianteService;

    private final DocenteService docenteService;

    private final UsuarioService usuarioService;

    private final JwtProvider jwtProvider;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EstudianteService estudianteService, DocenteService docenteService, UsuarioService usuarioService, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.estudianteService = estudianteService;
        this.docenteService = docenteService;
        this.usuarioService = usuarioService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<?>> login(@Valid @RequestBody LoginUsuario loginUsuario){

        GeneralResponse<JwtDto> response = new GeneralResponse<>();

        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginUsuario.getContrasenia(), loginUsuario.getContrasenia()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            if (jwtProvider.validateName(authentication, loginUsuario.getNombre())) {
                String jwt = jwtProvider.generateToken(authentication);
                JwtDto jwtDto = new JwtDto(jwt);

                response.setData(jwtDto);
                response.setSuccess(true);
                response.setMessage("Acceso concedido");
            } else {
                response.setData(null);
                response.setSuccess(false);
                response.setMessage("Acceso no concedido, el nombre no es valido");
            }
        }catch (Exception e){
            response.setData(null);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<GeneralResponse<?>> refresh(@RequestBody JwtDto jwtDto){
        GeneralResponse<JwtDto> response = new GeneralResponse<>();

        try {
            String token = jwtProvider.refreshToken(jwtDto);

            JwtDto jwt = new JwtDto(token);

            response.setData(jwt);
            response.setSuccess(true);
            response.setMessage("Acceso concedido");
        }catch (ParseException pe){
            response.setData(null);
            response.setSuccess(true);
            response.setMessage(pe.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/agregarAdministrador")
    public ResponseEntity<GeneralResponse<Usuario>> agregarAdministrador(@RequestBody Usuario admin){

        GeneralResponse<Usuario> response = new GeneralResponse<>();
        Usuario nuevoEstudiante;
        HttpStatus status = HttpStatus.OK;

        admin.setContrasenia(passwordEncoder.encode(admin.getDocumento()));

        nuevoEstudiante = usuarioService.agregarAdministrador(admin);

        if(nuevoEstudiante != null){
            response.setData(nuevoEstudiante);
            response.setSuccess(true);
            response.setMessage("Administrador agregado con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se pudo agregar o ya existe el administrador");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de agregar un estudiante", response = ResponseEntity.class)
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    @PostMapping("/agregarEstudiante")
    public ResponseEntity<GeneralResponse<Estudiante>> agregarEstudiante(@RequestBody Estudiante estudiante){

        GeneralResponse<Estudiante> response = new GeneralResponse<>();
        Estudiante nuevoEstudiante;
        HttpStatus status = HttpStatus.OK;

        estudiante.setContrasenia(passwordEncoder.encode(estudiante.getDocumento()));

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
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCENTE')")
    @PutMapping("/editarEstudiante")
    public ResponseEntity<GeneralResponse<Estudiante>> actualizarEstudiante(@RequestBody Estudiante estudiante){

        GeneralResponse<Estudiante> response = new GeneralResponse<>();
        Estudiante data;
        HttpStatus status = HttpStatus.OK;

        estudiante.setContrasenia(passwordEncoder.encode(estudiante.getDocumento()));

        data = estudianteService.actualizarEstudiante(estudiante);

        if(data != null){
            response.setData(data);
            response.setSuccess(true);
            response.setMessage("Estudiante actualizado con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("No se pudo actualizar el estudiante o ya existe el documento");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de agregar un docente", response = ResponseEntity.class)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/agregarDocente")
    public ResponseEntity<GeneralResponse<Docente>> agregarDocente(@RequestBody Docente docente){

        GeneralResponse<Docente> response = new GeneralResponse<>();
        Docente nuevoDocente;
        HttpStatus status = HttpStatus.OK;

        docente.setContrasenia(passwordEncoder.encode(docente.getDocumento()));

        nuevoDocente = docenteService.agregarDocente(docente);

        if(nuevoDocente != null){
            response.setData(nuevoDocente);
            response.setSuccess(true);
            response.setMessage("Docente agregado con exito");
        }else{
            response.setData(null);
            response.setSuccess(true);
            response.setMessage("Hubo un error al agregar el docente");
        }

        return new ResponseEntity<>(response, status);
    }

    @ApiOperation(value = "Método encargado de editar un docente", response = ResponseEntity.class)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editarDocente")
    public ResponseEntity<GeneralResponse<Docente>> editarDocente(@RequestBody Docente docente){

        GeneralResponse<Docente> response = new GeneralResponse<>();
        Docente nuevoDocente;
        HttpStatus status = HttpStatus.OK;

        docente.setContrasenia(passwordEncoder.encode(docente.getDocumento()));

        nuevoDocente = docenteService.editarDocente(docente);

        if(nuevoDocente != null){
            response.setData(nuevoDocente);
            response.setSuccess(true);
            response.setMessage("Docente agregado con exito");
        }else{
            response.setData(null);
            response.setSuccess(false);
            response.setMessage("Hubo un error al agregar el docente");
        }

        return new ResponseEntity<>(response, status);
    }
}
