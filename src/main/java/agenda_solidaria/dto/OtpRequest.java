package agenda_solidaria.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class OtpRequest {
    @NotEmpty
    private String requestId;
} 