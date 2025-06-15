package agenda_solidaria.dto;

import agenda_solidaria.model.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequestDto {

	@Nullable
	private String username;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String email;
	
	private Role role;

}
