package ar.com.vwa.extranet.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.vwa.extranet.aop.Loggable;
import ar.com.vwa.extranet.domain.Rol;
import ar.com.vwa.extranet.domain.Usuario;
import ar.com.vwa.extranet.services.UsuarioService;
import ar.com.vwa.extranet.services.security.VWUser;
import ar.com.vwa.extranet.views.GetItemsResponse;
import ar.com.vwa.extranet.views.SaveUsuarioRequest;
import ar.com.vwa.extranet.views.UpdateUsuarioRequest;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("usuarios")
@Tag(name = "usuarios")
@Loggable
public class UsuariosRestService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Devuelve todos los usuarios
	 * @return
	 */
	@GetMapping
	public GetItemsResponse<Usuario> getAllUsuarios() {
		List<Usuario> usuarios = this.usuarioService.findAllUsuariosActivos();
		return new GetItemsResponse<>(usuarios);
	}
	
	@GetMapping("/search/{criterio}")
	public GetItemsResponse<VWUser> searchUsuarios(@PathVariable("criterio") String criterio) {
		List<VWUser> usuarios = this.usuarioService.searchVwUsers(criterio);
		return new GetItemsResponse<>(usuarios);
	}
	
	/**
	 * Busca un Usuario
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public Usuario getUsuario(@PathVariable("id") String id) {
		return this.usuarioService.getUsuario(id);
	}
	
	/**
	 * Crea un nuevo Usuario
	 * @param Usuario
	 * @return
	 */
	@PostMapping
	public void createUsuario(@Validated @RequestBody SaveUsuarioRequest request) {
		this.usuarioService.createUsuario(request);
	}
	
	/**
	 * Actualiza un Usuario
	 * @param id
	 * @param Usuario
	 * @return
	 */
	@PutMapping("{id}")
	public void updateUsuario(@PathVariable("id") String id, @Validated @RequestBody UpdateUsuarioRequest request) {
		this.usuarioService.updateUsuario(id,request);
	}
	
	/**
	 * Elimina un Usuario
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public void deleteUsuario(@PathVariable("id") String id) {
		this.usuarioService.deleteUsuario(id);
	}

	@GetMapping("/{id}/roles")
	public GetItemsResponse<Rol> getRolesForUsuario(@PathVariable("id") Long id) {
		Usuario usuario = this.usuarioService.getUsuarioById(id);
		return new GetItemsResponse<>(new ArrayList<>(usuario.getRoles()));
	}
	
    @GetMapping("/roles")
    public GetItemsResponse<Rol> getRolesForUsuario() {
        return new GetItemsResponse<>(new ArrayList<>(usuarioService.getRoles()));
    }

}
