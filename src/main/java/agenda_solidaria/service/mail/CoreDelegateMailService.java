package agenda_solidaria.service.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.CompletableFuture;

/**
 * Clase base para implementar servicio de envio de email
 */
public abstract class CoreDelegateMailService extends CoreMailService {
	
	private static Logger logger = LoggerFactory.getLogger(CoreDelegateMailService.class);

   //TODO Falta la implementacion
	public void send(String jsonBody) {
		if(mailSendEnabled == false) {
			logger.warn("Envio de email deshabilitado... :)");
			return;
		}
		
		logger.info("Enviando {}", jsonBody);

	}
	
}
