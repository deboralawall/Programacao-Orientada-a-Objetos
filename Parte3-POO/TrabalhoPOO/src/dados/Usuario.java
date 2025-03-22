package dados;

import java.util.*;

import persistencia.EmailDAO;

public class Usuario {
	
	private int id;
	private String nome;
	private String email;
	private String senha;
	private List<Email> emailsRecebidos = new LinkedList<>();

	public Usuario(int id, String nome, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public Usuario() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String string) {
		this.email = string;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Email> emails() {
		return emailsRecebidos;
	}

	public void adicionarEmail(Email email) {
		emailsRecebidos.add(email);
		
	}

	@Override
	public String toString() {
		String s = new String();

		//s += "Nome: " + nome + "\n";
		//s += "Email: " + email + "\n";
		//s += "Senha: " + senha + "\n";
		s  += email;

		return s;
	}

	public boolean equals(Object o) {
		if (o instanceof Usuario) {
			Usuario u = (Usuario) o;

			if (this.email.equals(u.getEmail())) {
				return true;
			}
		}
		return false;
	}

}