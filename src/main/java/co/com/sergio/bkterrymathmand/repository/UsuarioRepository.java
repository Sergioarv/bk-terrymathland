package co.com.sergio.bkterrymathmand.repository;

import co.com.sergio.bkterrymathmand.entity.Estudiante;
import co.com.sergio.bkterrymathmand.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

//    @Query(value = "select * from usuario as e where lower(e.nombre) = lower(:nombre)", nativeQuery = true)
//    Usuario obtenerUsuarioPorNombre(String nombre);

    Optional<Usuario> findByNombre(String nombre);

    @Query(value = "select * from usuario as e where e.documento = :documento", nativeQuery = true)
    Usuario existePorDocumento(String documento);
}
