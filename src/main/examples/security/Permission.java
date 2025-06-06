package ar.com.vwa.extranet.services.security;

import java.util.ArrayList;
import java.util.List;

import ar.com.vwa.extranet.domain.RolMenu;

public record Permission(String authority, Boolean edicion) {
	
	public static List<Permission> build(List<RolMenu> permisos) {
		List<Permission> permissions = new ArrayList<>();
		for (RolMenu rolMenu : permisos) {
			permissions.add(new Permission(rolMenu.getMenu().getDescripcion(), rolMenu.getEdicion()));
		}
		
		return permissions;
	}
	
}
