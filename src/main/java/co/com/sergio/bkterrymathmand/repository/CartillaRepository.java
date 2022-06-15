package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Cartilla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartillaRepository extends JpaRepository<Cartilla, Integer> {

    @Query(value = "select * from cartilla as c where c.idcartilla = :idcartilla", nativeQuery = true)
    List<Cartilla> filtrarPorId(int idcartilla);
}
