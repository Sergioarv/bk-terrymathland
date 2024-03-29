package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.dto.ICartillaProyeccion;
import co.com.sergio.bkterrymathmand.entity.Cartilla;
import co.com.sergio.bkterrymathmand.entity.Pregunta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 12/06/2022 15:08
 **/
@Repository
public interface CartillaRepository extends JpaRepository<Cartilla, Integer> {

    @Query(value = "select * from cartilla as c where c.idcartilla = :idcartilla", nativeQuery = true)
    List<Cartilla> filtrarPorId(int idcartilla);

    @Query("select p from Cartilla cp join cp.preguntas p WHERE cp.idcartilla = :idcartilla")
    Page<Pregunta> obtenerPreguntasPorId(int idcartilla, Pageable pageable);

    /** Juego **/
    @Query(value = "select * from cartilla as c order by c.nombre", nativeQuery = true)
    List<ICartillaProyeccion> listarCartillas();
}
