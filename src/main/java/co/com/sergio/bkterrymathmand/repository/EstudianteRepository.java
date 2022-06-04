package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
