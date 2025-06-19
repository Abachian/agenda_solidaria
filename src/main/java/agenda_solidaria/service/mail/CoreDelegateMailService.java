package agenda_solidaria.service.mail;

import agenda_solidaria.service.exceptions.ServiceException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Clase base para implementar servicio de envio de email
 */
@Service
public abstract class CoreDelegateMailService extends CoreMailService {
	
	private static Logger logger = LoggerFactory.getLogger(CoreDelegateMailService.class);

	@Value("${spring.mail.username}")
	private String sender;

	private final JavaMailSender mailSender;

	protected CoreDelegateMailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	public void send(String jsonBody) {
		if(mailSendEnabled == false) {
			logger.warn("Envio de email deshabilitado... :)");
			return;
		}
		try {
			// Parsear el JSON a un objeto MailData
			MailData mailData = mapper.readValue(jsonBody, MailData.class);

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setFrom(mailData.getMailFrom());
			helper.setTo(mailData.getMailTo());
			helper.setSubject(mailData.getSubject());
			helper.setText(mailData.getMessage(), true);

			CompletableFuture.runAsync(() -> {
				try {
					mailSender.send(message);
					logger.info("Correo enviado exitosamente a {}", mailData.getMailTo());
				} catch (Exception e) {
					logger.error("Error al enviar correo a {}", mailData.getMailTo(), e);
				}
			});

		} catch (Exception e) {
			logger.error("Error al procesar el envío de correo", e);
			throw ServiceException.internalError("Error al enviar correo", e);
		}
	}

	}
	

