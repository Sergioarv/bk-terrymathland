package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Docente;
import co.com.sergio.bkterrymathmand.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 11:33
 **/

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {

    @Query(value = "select * from docente as d where d.nombre = :nombre", nativeQuery = true)
    Docente docentePorNombre(String nombre);

    @Query(value = "select * from docente as e where e.documento = :documento", nativeQuery = true)
    Docente existePorDocumento(String documento);
}
