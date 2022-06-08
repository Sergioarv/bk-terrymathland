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

    @Query(value = "select * from respuesta r where r.fecha = :fecha", nativeQuery = true)
    List<Respuesta> obtenerRespuestasPorFecha(Date fecha);

    @Query(value = "select * from respuesta r where r.fecha = :fechaS and r.idusuario = :idusuario", nativeQuery = true)
    Respuesta obtenerRespuestaPorFechaYidUsuario(Date fechaS, int idusuario);

    @Query(value = "select * from (select * from respuesta as r where r.fecha = :fecha) as c2 inner join (select * from estudiante as e where e.idusuario = :idusuario) as c1 on c1.idusuario = c2.idusuario", nativeQuery = true)
    List<Respuesta> respuestasPorEstudianteYFecha(int idusuario, Date fecha);

    @Query(value = "select * from respuesta as r where r.idusuario = :idusuario order by r.fecha", nativeQuery = true)
    List<Respuesta> respuestaPorEstudiante(int idusuario);
}
