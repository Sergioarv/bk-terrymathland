package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.dto.IDatosPromedioEstudiante;
import co.com.sergio.bkterrymathmand.dto.IDatosPromedioNotas;
import co.com.sergio.bkterrymathmand.dto.IRespuestaProyeccion;
import co.com.sergio.bkterrymathmand.entity.Respuesta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "select * from respuesta r where r.fecha = :fecha order by r.fecha desc", nativeQuery = true)
    Page<Respuesta> obtenerRespuestasPorFecha(Date fecha, Pageable pageable);

    @Query(value = "select * from respuesta r where r.fecha = :fechaS and r.idusuario = :idusuario", nativeQuery = true)
    Respuesta obtenerRespuestaPorFechaYidUsuario(Date fechaS, int idusuario);

//    @Query(value = "select * from (select * from respuesta as r where r.fecha = :fecha) as c2 inner join (select * from estudiante as e where e.idusuario = :idusuario) as c1 on c1.idusuario = c2.idusuario", nativeQuery = true)
    @Query(value = "select * from respuesta as r where r.fecha = :fecha and r.idusuario = :idusuario order by r.fecha desc", nativeQuery = true)
    Page<Respuesta> respuestasPorEstudianteYFecha(int idusuario, Date fecha, Pageable pageable);

    @Query(value = "select * from respuesta as r where r.idusuario = :idusuario order by r.fecha desc", nativeQuery = true)
    Page<Respuesta> respuestaPorEstudiante(int idusuario, Pageable pageable);

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
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta as r where r.idusuario = :idusuario and r.nota = 5) as c1 " +
            " union all select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta as r where r.idusuario = :idusuario and r.nota < 5 and r.nota >= 4) as c1 " +
            "union all select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta as r where r.idusuario = :idusuario and r.nota < 4 and r.nota >= 3) as c1 " +
            "union all  select count(nota) as promedioestudiantes from " +
            "(select cast(avg(nota) as decimal(10,1)) as nota from respuesta as r where r.idusuario = :idusuario and r.nota < 3) as c1", nativeQuery = true)
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
}
