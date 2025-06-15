package agenda_solidaria.service.mail;

import java.util.List;

public class MailTemplate {
	
	private String engine;

	private String mailFrom;

	private List<MailData> mails;
	
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public List<MailData> getMails() {
		return mails;
	}
	
	public void setMails(List<MailData> mails) {
		this.mails = mails;
	}

}
