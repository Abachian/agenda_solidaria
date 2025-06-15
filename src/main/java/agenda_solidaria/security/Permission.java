package agenda_solidaria.security;


import agenda_solidaria.model.Role;

import java.util.ArrayList;
import java.util.List;

public record Permission(String authority) {
	
	public static List<Permission> build(List<Role> permisos) {
		List<Permission> permissions = new ArrayList<>();
		for (Role role : permisos) {
			permissions.add(new Permission(role.name()));
		}
		
		return permissions;
	}
	
}
