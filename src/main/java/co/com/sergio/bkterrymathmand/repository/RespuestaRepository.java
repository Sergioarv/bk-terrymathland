package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.dto.IRespuestaProyeccion;
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

    @Query(value = "select * from respuesta as r where r.idusuario = :idusuario order by r.fecha desc limit 3", nativeQuery = true)
    List<IRespuestaProyeccion> respuestaGuardadaPorEstudiante(int idusuario);

//    @Query(value = "(select count(promedio) as cant from (select cast(avg(nota) as decimal(10,1)) as promedio from respuesta group by idusuario) as c1 where promedio = 5) " +
//            "union all " +
//            "(select count(promedio) as cant from (select cast(avg(nota) as decimal(10,1)) as promedio from respuesta group by idusuario) as c1 where promedio < 5 and promedio >= 4) " +
//            "union all " +
//            "(select count(promedio) as cant from (select cast(avg(nota) as decimal(10,1)) as promedio from respuesta group by idusuario) as c1 where promedio < 4 and promedio >= 3) " +
//            "union all " +
//            "(select count(*) as cant from (select cast(avg(nota) as decimal(10,1)) as promedio from respuesta group by idusuario) as c1 where promedio < 3)", nativeQuery = true)
//    IDatosaGraficar graficarRespuestas();

//    @Query(value = "", nativeQuery = true)
//    IDatosaGraficar graficarRespuestas();
//
//    @Query(value = "select fecha from respuesta where fecha = '2022-06-03' group by fecha", nativeQuery = true)
//    List<Respuesta> respuestasValidas();


    //List<IDatosaGraficar> graficarRespuestasPorEstudiante(int idusuario);

//    @Query(value = "((select count(nota) as cant, fecha from (select nota, fecha from respuesta where fecha <= '2022-06-13') as c1 where nota = 5 group by fecha) " +
//            "union all " +
//            "(select count(nota) as cant, fecha from (select nota, fecha from respuesta where fecha <= '2022-06-13') as c1 where nota < 5 and nota >= 4 group by fecha) " +
//            "union all " +
//            "(select count(nota) as cant, fecha from (select nota, fecha from respuesta where fecha <= '2022-06-13') as c1 where nota < 4 and nota >= 3 group by fecha) " +
//            "union all " +
//            "(select count(nota) as cant, fecha from (select nota, fecha from respuesta where fecha <= '2022-06-13') as c1 where nota < 3 group by fecha)) order by fecha desc limit 7", nativeQuery = true)
//    IDatosaGraficar graficarRespuestasPorFecha(Date fecha);
}
