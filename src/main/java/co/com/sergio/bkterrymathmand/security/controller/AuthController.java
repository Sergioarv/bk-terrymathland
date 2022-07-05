package co.com.sergio.bkterrymathmand.security.controller;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.security.dto.JwtDto;
import co.com.sergio.bkterrymathmand.security.dto.LoginUsuario;
import co.com.sergio.bkterrymathmand.security.jwt.JwtProvider;
import co.com.sergio.bkterrymathmand.security.service.RolServiceImpl;
import co.com.sergio.bkterrymathmand.service.EstudianteService;
import co.com.sergio.bkterrymathmand.utils.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EstudianteService estudianteService;

    private final RolServiceImpl rolService;

    private final JwtProvider jwtProvider;

    public AuthController(PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, EstudianteService estudianteService, RolServiceImpl rolService, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.estudianteService = estudianteService;
        this.rolService = rolService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<GeneralResponse<?>> login(@Valid @RequestBody LoginUsuario loginUsuario){

        GeneralResponse<JwtDto> response = new GeneralResponse<>();

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginUsuario.getNombre(), loginUsuario.getDocumento()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

        response.setData(jwtDto);
        response.setSuccess(true);
        response.setMessage("Acceso concedido");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/crearEstudiante")
    public ResponseEntity<GeneralResponse<Estudiante>> agregarEstudiante(@RequestBody Estudiante estudiante){

        GeneralResponse<Estudiante> response = new GeneralResponse<>();
        Estudiante nuevoEstudiante;
        HttpStatus status = HttpStatus.OK;

        estudiante.setDocumento(passwordEncoder.encode(estudiante.getDocumento()));

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
}
