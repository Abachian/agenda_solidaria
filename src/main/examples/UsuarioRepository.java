package ar.com.vwa.extranet.repositories;

import ar.com.vwa.extranet.domain.Estado;
import ar.com.vwa.extranet.domain.Rol;
import ar.com.vwa.extranet.domain.Usuario;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByUsernameIgnoreCase(String username);

	Usuario findByOtpUuid(String uuid);
	
	Usuario findByPasswordForgetUuid(String uuid);

	Usuario findByEmail(String email);
	
	@Query("select distinct(u) from Usuario u left join fetch u.roles r left join fetch u.proveedores p where u.estado != 2")
	List<Usuario> findByNoBaja();
	
	List<Usuario> findByEstadoIn(Estado... estado);

	@Query("select u from Usuario u join u.roles r where r = ?1")
	List<Usuario> findByRol(Rol rol);

	boolean existsByUsername(@NotEmpty String username);

	boolean existsByEmailAndEstadoNot(@NotEmpty String email, @NotEmpty Estado estado);

}
