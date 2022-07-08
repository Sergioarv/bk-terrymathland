package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Docente;
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
public interface DocenteRepository extends JpaRepository<Docente, Integer> {

    @Query(value = "select * from usuario as u where u.nombre = :nombre", nativeQuery = true)
    Docente docentePorNombre(String nombre);

    @Query(value = "select * from usuario as u where u.documento = :documento", nativeQuery = true)
    Docente existePorDocumento(String documento);

    @Query(value = "select * from usuario as u where u.nombre like %:nombre%", nativeQuery = true)
    List<Docente> estudiantePorNombreYCorreo(String nombre);

    @Query(value = "select * from usuario as u where u.nombre like %:nombre%", nativeQuery = true)
    List<Docente> estudiantePorFiltro(String nombre);

    @Query(value = "select * from usuario as u where u.correo like %:correo%", nativeQuery = true)
    List<Docente> estudiantePorCorreo(String correo);


    @Query(value = "select * from usuario as u where u.documento = :documento", nativeQuery = true)
    List<Docente> getAllDocumento(String documento);
}
