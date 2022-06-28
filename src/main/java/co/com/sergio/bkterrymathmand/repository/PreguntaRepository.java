package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 10:27
 **/

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {
    @Query(value = "select * from pregunta as p where p.idpregunta = :id", nativeQuery = true)
    Page<Pregunta> filtrarPorId(int id, Pageable pageable);

    @Query(value = "select * from pregunta as p where lower(p.enunciado) like lower(concat('%',:filtro,'%'))", nativeQuery = true)
    Page<Pregunta> filtrarPor(String filtro, Pageable pageable);

    @Query(value = "select * from pregunta as p where p.idpregunta = :id or lower(p.enunciado) like lower(concat('%',:filtro,'%'))", nativeQuery = true)
    Page<Pregunta> filtrarPorIdOEnunciado(int id, String filtro, Pageable pageable);

}
