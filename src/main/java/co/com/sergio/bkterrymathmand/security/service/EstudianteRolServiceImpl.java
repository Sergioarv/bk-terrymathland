package co.com.sergio.bkterrymathmand.security.service;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.security.entity.EstudiantePrincipal;
import co.com.sergio.bkterrymathmand.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EstudianteRolServiceImpl implements UserDetailsService {

    private EstudianteService estudianteService;

    public EstudianteRolServiceImpl(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Estudiante estudiante = estudianteService.obtenerEstudiantePorNombre(username);
            return EstudiantePrincipal.build(estudiante);
        }catch (UsernameNotFoundException e){
            throw  new UsernameNotFoundException(e.getMessage());
        }
    }
}
