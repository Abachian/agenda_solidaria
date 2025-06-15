package agenda_solidaria.service;


import agenda_solidaria.model.User;

public interface MailService {

	public void enviarOlvidePassword(User user);

	public void enviarAltaNuevoUsuario(User usuario, String password);

}
