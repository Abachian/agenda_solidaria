package agenda_solidaria.service.mail;

import agenda_solidaria.model.User;
import agenda_solidaria.service.MailService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl extends CoreDelegateMailService implements MailService {
	
	private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

	public MailServiceImpl(JavaMailSender mailSender) {
		super(mailSender);
	}
	
	@Override
	public void enviarOlvidePassword(User user, String tempPassword) {
		logger.info("Enviando email de olvide password al cliente: {}", user.getUsername());
		
		MailData mailData = this.getMailData("OlvidePassword");
		
		// params
		Map<String, Object> params = new HashMap<>();
		params.put("firstName", user.getFirstName());
		params.put("lastName", user.getLastName());
		params.put("username", user.getUsername());
		params.put("password", tempPassword);

		ObjectNode mail = mapper.createObjectNode();
		mail.put("mailFrom", mailData.getMailFrom());
		mail.put("to", user.getEmail());
		mail.put("subject", this.processTemplate(mailData.getSubject(), params));
		mail.put("message", this.processTemplate(mailData.getMessage(), params));
			
		this.send(mail.toString());
	}


	@Override
	public void enviarAltaNuevoUsuario(User user, String password) {
		logger.info("Enviando datos de nuevo usuario al cliente: {}", user.getUsername());

		MailData mailData = this.getMailData("AltaDeUsuarioAgendaSolidaria");

		// params
		Map<String, Object> params = new HashMap<>();
		params.put("firstName", user.getFirstName());
		params.put("lastName", user.getLastName());
		params.put("username", user.getUsername());
		params.put("password", password);

		ObjectNode mail = mapper.createObjectNode();
		mail.put("mailFrom", mailData.getMailFrom());
		mail.put("to", user.getEmail());
		mail.put("subject", this.processTemplate(mailData.getSubject(), params));
		mail.put("message", this.processTemplate(mailData.getMessage(), params));
	
		this.send(mail.toString());
	}
}
