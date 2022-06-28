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

@Repository
public interface CartillaRepository extends JpaRepository<Cartilla, Integer> {

    @Query(value = "select * from cartilla as c where c.idcartilla = :idcartilla", nativeQuery = true)
    List<Cartilla> filtrarPorId(int idcartilla);

    @Query(value = "select * from cartilla as c order by c.nombre", nativeQuery = true)
    List<ICartillaProyeccion> listarCartillas();

    @Query(value = "select p.* from pregunta as p inner join (select * from cartilla_pregunta as cp where cp.idcartilla = :idcartilla) as c1 on p.idpregunta = c1.idpregunta", nativeQuery = true)
    Page<Pregunta> obtenerPreguntasPorId(int idcartilla, Pageable pageable);
}
