package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 21/04/2022 10:27
 **/

@Repository
public interface PreguntaRepository extends JpaRepository<Pregunta, Integer> {
    @Query(value = "select * from pregunta as p where p.idpregunta = :id", nativeQuery = true)
    List<Pregunta> filtrarPorId(int id);

    @Query(value = "select * from pregunta as p where p.enunciado like %:filtro% order by p.idpregunta", nativeQuery = true)
    List<Pregunta> filtrarPor(String filtro);

    @Query(value = "select * from pregunta as p where p.idpregunta = :id or p.enunciado like %:filtro% order by p.idpregunta", nativeQuery = true)
    List<Pregunta> filtrarPorIdOEnunciado(int id, String filtro);

    @Query(value = "select max(idpregunta) from pregunta",nativeQuery = true)
    int ultimoId();
}
