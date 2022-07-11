package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.dto.IEstudianteProyeccion;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
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
 * @Date 22/04/2022 11:33
 **/

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query(value = "select * from usuario as e where lower(e.nombre) = lower(:nombre)", nativeQuery = true)
    IEstudianteProyeccion estudianteByNombre(String nombre);

    @Query(value = "select * from usuario as e where lower(e.nombre) like lower(concat('%',:nombre,'%'))", nativeQuery = true)
    Page<Estudiante> estudiantePorFiltro(String nombre, Pageable pageable);

    @Query(value = "select * from usuario as e inner join respuesta as r on r.idusuario = e.idusuario where lower(e.nombre) like lower(concat('%',:nombre,'%')) and r.fecha = :fecha", nativeQuery = true)
    Page<Estudiante> estudiantePorNombreYFecha(String nombre, Date fecha, Pageable pageable);

    @Query(value = "select * from estudiante as e inner join (select * from respuesta as r where r.fecha = :fecha) as rs on e.idusuario = rs.idusuario", nativeQuery = true)
    Page<Estudiante> estudiantePorFechaRespuesta(Date fecha, Pageable pageable);

    @Query(value = "select u.idusuario, u.nombre from usuario as e inner join usuario as u on e.idusuario = u.idusuario", nativeQuery = true)
    List<IEstudianteProyeccion> obtenerIdyNombreEstudiantes();

    @Query(value = "select * from usuario as e where lower(e.nombre) = lower(:nombre)", nativeQuery = true)
    Estudiante obtenerEstudiantePorNombre(String nombre);

    @Query(value = "select * from usuario as e where e.documento = :documento", nativeQuery = true)
    Estudiante existePorDocumento(String documento);

    @Query(value = "select * from usuario as u where u.documento = :documento", nativeQuery = true)
    List<Estudiante> getAllDocumento(String documento);
}
