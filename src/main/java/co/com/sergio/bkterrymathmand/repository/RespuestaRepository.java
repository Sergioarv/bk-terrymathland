package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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

    @Query(value = "select * from respuesta r where r.fecha = DATE_FORMAT(:fecha,\"%Y-%m-%d\") and r.idusuario = :idusuario", nativeQuery = true)
    Respuesta findRespuestaByFechaAndUsuario(Date fecha, String idusuario);
}
