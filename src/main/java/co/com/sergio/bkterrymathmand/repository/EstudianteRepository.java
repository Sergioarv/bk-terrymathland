package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.dto.IEstudianteProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 11:33
 **/

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query(value = "select * from estudiante as e where lower(e.nombre) = lower(:nombre)", nativeQuery = true)
    IEstudianteProyeccion estudianteByNombre(String nombre);

    @Query(value = "select * from estudiante as e where e.nombre like %:nombre% ORDER BY idusuario", nativeQuery = true)
    List<Estudiante> estudiantePorFiltro(String nombre);

    @Query(value = "select * from (select * from estudiante as e where e.nombre like %:nombre%) as c1 inner join (select * from respuesta as r where r.fecha = :fecha) as c2 on c1.idusuario = c2.idusuario order by c2.fecha", nativeQuery = true)
    List<Estudiante> estudiantePorNombreYFecha(String nombre, Date fecha);

    @Query(value = "select * from estudiante as e inner join (select * from respuesta as r where r.fecha = :fecha) as rs on e.idusuario = rs.idusuario order by e.idusuario", nativeQuery = true)
    List<Estudiante> estudiantePorFechaRespuesta(Date fecha);

    @Query(value = "select * from estudiante", nativeQuery = true)
    List<IEstudianteProyeccion> obtenerIdyNombreEstudiantes();

    @Query(value = "select * from estudiante as e where lower(e.nombre) = lower(:nombre)", nativeQuery = true)
    Estudiante obtenerEstudiantePorNombre(String nombre);

    @Query(value = "select * from estudiante as e where e.documento = :documento", nativeQuery = true)
    Estudiante existePorDocumento(String documento);
}
