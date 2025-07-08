package agenda_solidaria.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import agenda_solidaria.dto.CreateVolunteerRequestDto;
import agenda_solidaria.dto.CreateVolunteerResponseDto;
import agenda_solidaria.model.Volunteer;

@Mapper
public interface VolunteerMapper {

    @Mapping(target = "document", source = "document")
    @Mapping(target = "documentType", source = "documentType")
    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.birthDate", source = "birthDate")
    @Mapping(target = "user.email", source = "email")
    @Mapping(target = "user.password", source = "password")
    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "adress", source = "adress")
    @Mapping(target = "educationLevel", source = "educationLevel")
    @Mapping(target = "occupation", source = "occupation")
    @Mapping(target = "images", ignore = true)
    @Mapping(target = "professions", ignore = true)
    @Mapping(target = "postulates", ignore = true)
    Volunteer toEntity(CreateVolunteerRequestDto dto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "volunteerId", source = "id")
    CreateVolunteerResponseDto toResponseDto(Volunteer volunteer);
}