package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Docente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 19/05/2022 10:52
 **/

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {

    @Query(value = "select * from usuario as u where u.nombre = :nombre", nativeQuery = true)
    Docente docentePorNombre(String nombre);

    @Query(value = "select * from usuario as u where u.documento = :documento", nativeQuery = true)
    Docente existePorDocumento(String documento);

    @Query(value = "select * from usuario as u where lower(u.nombre) like lower(concat('%',:nombre,'%')) and lower(u.documento) like lower(concat('%',:documento,'%'))", nativeQuery = true)
    Page<Docente> docentePorNombreYDocumento(String nombre, String documento, Pageable pageable);

    @Query(value = "select * from usuario as u where lower(u.nombre) like lower(concat('%',:nombre,'%'))", nativeQuery = true)
    Page<Docente> docentePorFiltro(String nombre, Pageable pageable);

    @Query(value = "select * from usuario as u where u.documento like %:documento%", nativeQuery = true)
    Page<Docente> docentePorDocumento(String documento, Pageable pageable);


    @Query(value = "select * from usuario as u where u.documento = :documento", nativeQuery = true)
    List<Docente> getAllDocumento(String documento);
}
