package dados;

import java.sql.Date;

public class Email {

	private int id;
	private String assunto;
	private String corpo;
	private Date data;
	private int destinatario;
	private int remetente;

	public Email(int id, String assunto, String corpo, Date data, int remetente, int destinatario) {
		this.id = id;
		this.assunto = assunto;
		this.corpo = corpo;
		this.data = data;
		this.destinatario = destinatario;
		this.remetente = remetente;
		
	}
	
	public Email() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(int destinatario) {
		this.destinatario = destinatario;
	}

	public int getRemetente() {
		return remetente;
	}

	public void setRemetente(int remetente) {
		this.remetente = remetente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date date) {
		this.data = date;
	}

	public String toString() {
		String s = new String();
		
		s += "Assunto: " + assunto + "\n";
		s += "Corpo: " + corpo + "\n";
		s += "Data: " + data + "\n";
		s += "Remetente: " + remetente + "\n";
		s += "Destinatario: " + destinatario + "\n";

		return s;
	}

}