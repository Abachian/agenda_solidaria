package agenda_solidaria.service.exceptions;

public class BadRequestServiceException extends ServiceException {

	public BadRequestServiceException(String msg) {
		super(msg);
	}
	
	public BadRequestServiceException(String msg, Object error) {
		super(msg, error);
	}

}
