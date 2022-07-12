package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Opcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @project bk-terrymathmand
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 22/04/2022 12:23
 **/

@Repository
public interface OpcionRepository extends JpaRepository<Opcion, Integer> {

    @Query(value = "select * from opcion as o where o.idpregunta = :idpregunta", nativeQuery = true)
    List<Opcion> obtenerOpcionesDePregunta(int idpregunta);

}
