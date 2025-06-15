package ar.com.vwa.extranet.domain;

import java.util.List;

public class LoggedUserDTO {

	private String token;
	private Usuario usuario;
	private List<RolMenu> permisos;

	public LoggedUserDTO(String token, Usuario usuario, List<RolMenu> permisos) {
		this.permisos = permisos;
		this.token = token;
		this.usuario = usuario;
		
		this.usuario.clear();
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<RolMenu> getPermisos() {
		return permisos;
	}

	public void setPermisos(List<RolMenu> permisos) {
		this.permisos = permisos;
	}

}
