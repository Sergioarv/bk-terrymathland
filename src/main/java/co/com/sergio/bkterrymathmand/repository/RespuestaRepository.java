package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.dto.IDatosPromedioEstudiante;
import co.com.sergio.bkterrymathmand.dto.IDatosPromedioNotas;
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

    /** Querys para las graficas **/
    @Query( value = "select cast(avg(nota) as decimal(10,1)) as promedionotas, fecha from respuesta where fecha <= :fecha group by fecha limit 7", nativeQuery = true)
    List<IDatosPromedioNotas> graficarRespuestasNotas(Date fecha);

    @Query( value = "select cast(avg(nota) as decimal(10,1)) as promedionotas, fecha from respuesta where idusuario = :idusuario group by fecha limit 7", nativeQuery = true)
    List<IDatosPromedioNotas> graficarRespuestasNotasPorId(int idusuario);

    @Query(value = "select cast(avg(nota) as decimal(10,1)) as promedionotas, fecha from respuesta where idusuario = :idusuario and fecha <= :fecha group by fecha limit 7", nativeQuery = true)
    List<IDatosPromedioNotas> graficarRespuestasNotasPorIdFecha(int idusuario, Date fecha);

    @Query(value = "select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta group by idusuario) as c1 " +
            "where nota = 5 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta group by idusuario ) as c1 " +
            "where nota < 5 and nota >= 4 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta group by idusuario ) as c1 " +
            "where nota < 4 and nota >= 3 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta group by idusuario ) as c1 " +
            "where nota < 3", nativeQuery = true)
    List<IDatosPromedioEstudiante> graficarRespuestasEstudiantes();

    @Query(value = "select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta where fecha = :fecha group by idusuario) as c1 " +
            "where nota = 5 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta where fecha = :fecha group by idusuario ) as c1 " +
            "where nota < 5 and nota >= 4 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta where fecha = :fecha group by idusuario ) as c1 " +
            "where nota < 4 and nota >= 3 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta where fecha = :fecha group by idusuario ) as c1 " +
            "where nota < 3", nativeQuery = true)
    List<IDatosPromedioEstudiante> graficarRespuestasEstudiantesPorFecha(Date fecha);

    @Query(value = "select count(nota) as promedioestudiantes from " +
            "(select  nota from respuesta where idusuario = :idusuario group by idusuario, nota) as c1 " +
            "where nota = 5  union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select nota from respuesta where idusuario = :idusuario  group by idusuario, nota) as c1 " +
            "where nota < 5 and nota >= 4 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select nota from respuesta where idusuario = :idusuario  group by idusuario, nota) as c1 " +
            "where nota < 4 and nota >= 3 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select nota from respuesta where idusuario = :idusuario group by idusuario, nota) as c1 " +
            "where nota < 3", nativeQuery = true)
    List<IDatosPromedioEstudiante> graficarRespuestasEstudiantesPoId(int idusuario);

    @Query(value = "select count(nota) as promedioestudiantes from " +
            "(select  nota from respuesta where idusuario = :idusuario and fecha = :fecha group by idusuario, nota) as c1 " +
            "where nota = 5 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select nota from respuesta where idusuario = :idusuario and fecha = :fecha  group by idusuario, nota) as c1 " +
            "where nota < 5 and nota >= 4 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select nota from respuesta where idusuario = :idusuario and fecha = :fecha  group by idusuario, nota) as c1 " +
            "where nota < 4 and nota >= 3 union all " +
            "select count(nota) as promedioestudiantes from " +
            "(select nota from respuesta where idusuario = :idusuario and fecha = :fecha group by idusuario, nota) as c1 " +
            "where nota < 3", nativeQuery = true)
    List<IDatosPromedioEstudiante> graficarRespuestasEstudiantesPorIdFecha(int idusuario, Date fecha);

//    @Query(value = "(select fecha, notas, cast(avg(promedio) as decimal(10,1)) as n1, 0 as n2, 0 as n3, 0 as n4 from (select cast(avg(nota) " +
//            "as decimal(10,1)) as promedio, count(nota) as notas, fecha from respuesta group by fecha) as c1 where promedio = 5 group by fecha, " +
//            "notas) union all (select fecha, notas, 0 as n1, cast(avg(promedio) as decimal(10,1)) as n2, 0 as n3, 0 as n4 from (select " +
//            "cast(avg(nota) as decimal(10,1)) as promedio, count(nota) as notas, fecha from respuesta group by fecha) as c1 where promedio < 5 " +
//            "and promedio >= 4 group by fecha, notas) union all (select fecha, notas, 0 as n1, 0 as n2, cast(avg(promedio) as decimal(10,1)) " +
//            "as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, count(nota) as notas, fecha from respuesta group by fecha) " +
//            "as c1 where promedio < 4 and promedio >= 3 group by fecha, notas) union all (select fecha, notas, 0 as n1, 0 as n2, 0 as n3, " +
//            "cast(avg(promedio) as decimal(10,1)) as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, count(nota) as notas, " +
//            "fecha from respuesta group by fecha) as c1 where promedio < 3 group by fecha, notas) order by fecha desc limit 7", nativeQuery = true)
//    List<IDatosPromedioFecha> graficarRespuestas();

//    @Query(value = "(select fecha, cast(avg(promedio) as decimal(10,1)) as n1, 0 as n2, 0 as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, \n" +
//            "fecha from respuesta where fecha <= :fecha group by  fecha) as c1 where promedio = 5 group by fecha) \n" +
//            "union all (select fecha, 0 as n1, cast(avg(promedio) as decimal(10,1)) as n2, 0 as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, \n" +
//            " fecha from respuesta where fecha <= :fecha group by  fecha) as c1 where promedio < 5 and promedio >= 4 \n" +
//            "group by fecha) union all (select fecha, 0 as n1, 0 as n2, cast(avg(promedio) as decimal(10,1)) as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) \n" +
//            "as promedio, fecha from respuesta where fecha <= :fecha group by  fecha) as c1 where promedio < 4 and promedio >= 3 \n" +
//            "group by fecha) union all (select fecha, 0 as n1, 0 as n2, 0 as n3, cast(avg(promedio) as decimal(10,1)) as n4 from (select cast(avg(nota) as decimal(10,1)) \n" +
//            "as promedio, fecha from respuesta where fecha <= :fecha group by fecha) as c1 where promedio < 3 group by fecha) \n" +
//            "order by fecha desc limit 7;", nativeQuery = true)
//    List<IDatosaGraficar> graficarRespuestasPorFecha(Date fecha);
//
//    @Query(value = "(select fecha, cast(avg(promedio) as decimal(10,1)) as n1, 0 as n2, 0 as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, " +
//            "idusuario, fecha from respuesta where idusuario = :idusuario group by idusuario, fecha) as c1 where promedio = 5 group by fecha)" +
//            "union all (select fecha, 0 as n1, cast(avg(promedio) as decimal(10,1)) as n2, 0 as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, " +
//            "idusuario, fecha from respuesta where idusuario = :idusuario group by idusuario, fecha) as c1 where promedio < 5 and promedio >= 4 group by fecha)" +
//            "union all (select fecha, 0 as n1, 0 as n2, cast(avg(promedio) as decimal(10,1)) as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, " +
//            "idusuario, fecha from respuesta where idusuario = :idusuario group by idusuario, fecha) as c1 where promedio < 4 and promedio >= 3 group by fecha)" +
//            "union all (select fecha, 0 as n1, 0 as n2, 0 as n3, cast(avg(promedio) as decimal(10,1)) as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, " +
//            "idusuario, fecha from respuesta where idusuario = :idusuario group by idusuario, fecha) as c1 where promedio < 3 group by fecha) " +
//            "order by fecha desc limit 7", nativeQuery = true)
//    List<IDatosaGraficar> graficarRespuestasPorEstudiante(int idusuario);
//
//    @Query(value = "(select fecha, count(promedio) as n1, 0 as n2, 0 as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, " +
//            "idusuario, fecha from respuesta where fecha <= :fecha and idusuario = :idusuario group by idusuario, fecha) as c1 where promedio = 5 " +
//            "group by fecha) union all (select fecha, 0 as n1, count(promedio) as n2, 0 as n3, 0 as n4 from (select cast(avg(nota) as decimal(10,1)) " +
//            "as promedio, idusuario, fecha from respuesta where fecha <= :fecha and idusuario = :idusuario group by idusuario, fecha) as c1 where " +
//            "promedio < 5 and promedio >= 4 group by fecha) union all (select fecha, 0 as n1, 0 as n2, count(promedio) as n3, 0 as n4 from " +
//            "(select cast(avg(nota) as decimal(10,1)) as promedio, idusuario, fecha from respuesta where fecha <= :fecha and idusuario = :idusuario " +
//            "group by idusuario, fecha) as c1 where promedio < 4 and promedio >= 3 group by fecha) union all (select fecha, 0 as n1, 0 as n2, 0 as n3, " +
//            "count(promedio) as n4 from (select cast(avg(nota) as decimal(10,1)) as promedio, idusuario, fecha from respuesta where fecha <= :fecha " +
//            "and idusuario = :idusuario group by idusuario, fecha) as c1 where promedio < 3 group by fecha) order by fecha desc limit 7", nativeQuery = true)
//    List<IDatosaGraficar> graficarRespuestasFechaEstudiante(Date fecha, int idusuario);
}
