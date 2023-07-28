package dados;

import java.time.LocalDate;

public class Email {

	private String assunto;
	private String corpo;
	private LocalDate data;
	private Usuario destinatario;
	private Usuario remetente;

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public Usuario getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(Usuario destinatario) {
		this.destinatario = destinatario;
	}

	public Usuario getRemetente() {
		return remetente;
	}

	public void setRemetente(Usuario remetente) {
		this.remetente = remetente;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String toString() {
		String s = new String();

		//s += "Remetente: " + remetente.toString() + "\n";
		//s += "Destinatario: " + destinatario.toString() + "\n";
		s += "Assunto: " + assunto + "\n";
		s += "Corpo: " + corpo + "\n";
		//s += "Data: " + data + "\n";

		return s;
	}

}