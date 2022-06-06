package co.com.sergio.bkterrymathmand.repository;

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

    @Query(value = "select * from estudiante as e where e.nombre = :nombre", nativeQuery = true)
    Estudiante estudianteByNombre(String nombre);

    @Query(value = "select * from estudiante as e where e.nombre like %:nombre%", nativeQuery = true)
    List<Estudiante> estudiantePorFiltro(String nombre);

    @Query(value = "select * from estudiante as e left join respuesta as r on e.nombre like %:nombre% or r.fecha = :fecha", nativeQuery = true)
    List<Estudiante> estudiantePorNombreYFecha(String nombre, Date fecha);

    @Query(value = "select * from estudiante as e inner join respuesta as r on r.fecha = :fecha", nativeQuery = true)
    List<Estudiante> estudiantePorFechaRespuesta(Date fecha);
}
