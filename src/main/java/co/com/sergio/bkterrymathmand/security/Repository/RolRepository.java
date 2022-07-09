package co.com.sergio.bkterrymathmand.security.Repository;

import co.com.sergio.bkterrymathmand.entity.Rol;
import co.com.sergio.bkterrymathmand.security.enums.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Integer> {

    Optional<Rol> findByRolNombre(RolNombre rolNombre);
}
