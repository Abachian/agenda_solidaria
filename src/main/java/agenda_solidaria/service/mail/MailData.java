package agenda_solidaria.service.mail;

import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Data
public class MailData {

	private String messageId;
	private String productName = "AGENDA SOLIDARIA";
	private String mailCc;
	private String mailFrom;
	private String mailTo;
	private String subject;
	private String message;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}

}
