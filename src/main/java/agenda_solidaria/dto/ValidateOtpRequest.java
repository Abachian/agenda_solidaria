package agenda_solidaria.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ValidateOtpRequest {
    @NotEmpty
    private String requestId;
    
    @NotEmpty
    private String code;
} 