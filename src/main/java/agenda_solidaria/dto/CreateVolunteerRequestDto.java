package agenda_solidaria.dto;

import agenda_solidaria.model.DocumentType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateVolunteerRequestDto {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String password;
    private String username;
    private String gender;
    private String adress;
    private String educationLevel;
    private String occupation;
    private String document;
    private DocumentType documentType;

}
