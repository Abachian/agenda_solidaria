package agenda_solidaria.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "El archivo no ha sido encontrado")
public class FileStorageServiceException extends ServiceException {

	public FileStorageServiceException(Throwable t) {
		super(t);
	}

}
