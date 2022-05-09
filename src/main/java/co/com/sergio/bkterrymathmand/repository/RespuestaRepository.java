package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 14:18
 **/

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Integer> {
    List<Respuesta> findRespuestaByFecha(Date fecha);

    Respuesta findRespuestaByFechaAndUsuario(Date fecha, String usuario);
}
