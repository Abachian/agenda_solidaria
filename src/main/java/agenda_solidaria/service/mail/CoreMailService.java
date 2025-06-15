package agenda_solidaria.service.mail;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.text.GStringTemplateEngine;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import agenda_solidaria.service.exceptions.ServiceException;
import java.util.List;
import java.util.Map;

/**
 * Clase abstract para implementar servicio de envio de email
 */
public abstract class CoreMailService {

	private static Logger logger = LoggerFactory.getLogger(CoreMailService.class);

	protected boolean mailSendEnabled = true;

	protected ObjectMapper mapper;
	protected GStringTemplateEngine engine;
	protected MailTemplate mailTemplate;

	public CoreMailService() {
		this.mapper = new ObjectMapper();

		try {
			Resource resource = new ClassPathResource("mail-templates.json");

			byte[] binaryData = FileCopyUtils.copyToByteArray(resource.getInputStream());
			mailTemplate = mapper.readValue(binaryData, MailTemplate.class);
		} catch (Exception e) {
			logger.error("Error al procesar mail-templates", e);
			throw ServiceException.internalError("Error al procesar mail-templates", e);
		}

		if (mailTemplate.getEngine().equals("gstring"))
			this.engine = new GStringTemplateEngine();
		else
			throw ServiceException.internalError("No existe el engine " + mailTemplate.getEngine());
	}

	protected MailData getMailData(String messageId) {
		List<MailData> mails = mailTemplate.getMails();
		for (MailData mailData : mails) {
			if (mailData.getMessageId().equals(messageId)) {
				if (StringUtils.isEmpty(mailData.getMailFrom()))
					mailData.setMailFrom(mailTemplate.getMailFrom());
				return mailData;
			}
		}

		logger.error("No hay configuracion de mail para {}", messageId);
		throw ServiceException.internalError("No hay configuracion de mail para " + messageId);
	}

	protected String processTemplate(String templateStr, Map<String, Object> params) {
		try {
			return this.engine.createTemplate(templateStr).make(params).toString();
		} catch (Exception e) {
			logger.error("Error al procesar template", e);
			throw ServiceException.internalError("Error al procesar template", e);
		}
	}

}
