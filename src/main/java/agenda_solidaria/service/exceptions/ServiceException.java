package agenda_solidaria.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ServiceException extends RuntimeException {


    private static final long serialVersionUID = 1697543975799447690L;

    protected Object error;

    ServiceException(String msg) {
        super(msg);
    }

    ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    ServiceException(String message, Object error) {
        super(message);
        this.error = error;
    }

    ServiceException(Throwable cause) {
        super(cause);
    }

    public static ServiceException badRequestError(String msg) {
        return new BadRequestServiceException(msg);
    }

    public static ServiceException badRequestError(String msg, Object error) {
        return new BadRequestServiceException(msg, error);
    }

    public static ServiceException unauthorizedError(String msg) {
        return new UnauthorizedServiceException(msg);
    }

    public static ServiceException internalError(String msg, Exception ex) {
        return new InternalServiceException(msg, ex);
    }

    public static ServiceException internalError(String msg) {
        return new InternalServiceException(msg);
    }

    public static ServiceException notFoundError(String msg) {
        return new NotFoundServiceException(msg);
    }

    public static ServiceException fileStorageError(Exception e) {
        return new FileStorageServiceException(e);
    }

    public Object getError() {
        return error;
    }
} 