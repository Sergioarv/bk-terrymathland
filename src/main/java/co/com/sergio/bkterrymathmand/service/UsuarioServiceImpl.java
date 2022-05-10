package co.com.sergio.bkterrymathmand.service;

import co.com.sergio.bkterrymathmand.entity.Opcion;
import co.com.sergio.bkterrymathmand.entity.Respuesta;
import co.com.sergio.bkterrymathmand.entity.Solucion;
import co.com.sergio.bkterrymathmand.entity.Usuario;
import co.com.sergio.bkterrymathmand.repository.OpcionRepository;
import co.com.sergio.bkterrymathmand.repository.RespuestaRepository;
import co.com.sergio.bkterrymathmand.repository.SolucionRepository;
import co.com.sergio.bkterrymathmand.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 11:40
 **/

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private SolucionRepository solucionRepository;

    @Autowired
    private OpcionRepository opcionRepository;

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario usuarioByNombre(String nombre) {
        return usuarioRepository.usuarioByNombre(nombre);
    }

    @Override
    public List<Opcion> allOpciones() {
        return opcionRepository.findAll();
    }

    @Override
    public List<Opcion> opcionesQuery(String idpregunta) {
        return opcionRepository.opcionesQuery(idpregunta);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {

        Respuesta data = respuestaRepository.findRespuestaByFechaAndUsuario(usuario.getRespuestas().get(0).getFecha(), String.valueOf(usuario.getIdusuario()));

        if(data != null){
            usuario.getRespuestas().get(0).setIdrespuesta(data.getIdrespuesta());
            usuario.getRespuestas().get(0).setUsuario(usuario);

            respuestaRepository.save(usuario.getRespuestas().get(0));

            for (int i = 0; i < data.getSoluciones().size(); i++) {
                usuario.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(data.getSoluciones().get(i).getIdsolucion());
                usuario.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(data);
            }

            solucionRepository.saveAll(usuario.getRespuestas().get(0).getSoluciones());

        }else{
            usuario.getRespuestas().get(0).setIdrespuesta(0);
            usuario.getRespuestas().get(0).setUsuario(usuario);

            Respuesta res = respuestaRepository.save(usuario.getRespuestas().get(0));

            for (int i = 0; i < usuario.getRespuestas().get(0).getSoluciones().size(); i++) {
                usuario.getRespuestas().get(0).getSoluciones().get(i).setIdsolucion(0);
                usuario.getRespuestas().get(0).getSoluciones().get(i).setRespuesta(res);
            }

            solucionRepository.saveAll(usuario.getRespuestas().get(0).getSoluciones());
        }

        return usuarioRepository.save(usuario);
    }
}
