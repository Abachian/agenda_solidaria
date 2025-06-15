package agenda_solidaria.service.exceptions;

public class InternalServiceException extends ServiceException {

	public InternalServiceException(String msg) {
		super(msg);
	}
	
	public InternalServiceException(String msg, Exception ex) {
		super(msg, ex);
	}

}
