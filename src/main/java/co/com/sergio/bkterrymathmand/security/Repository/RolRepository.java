package co.com.sergio.bkterrymathmand.security.Repository;

import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @project bk-terry-math-land
 * @Author Sergio Abelardo Rodríguez Vásquez
 * @Email ingsergiorodriguezv@gmail.com
 * @Date 07/07/2022 16:51
 **/
public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
