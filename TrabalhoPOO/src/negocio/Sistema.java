package negocio;

import java.util.*;

import dados.*;

public class Sistema {

	private List<Usuario> usuarios = new LinkedList<>();
	private Usuario logado;
	private static Sistema s = null;

	public Sistema() {

	}

	public static Sistema getInstance() {
		if (s == null) {
			s = new Sistema();
		}
		return s;
	}

	public void cadastrarUsuario(Usuario usuario) {
		usuarios.add(usuario);
	}

	public boolean realizarLogin(String emailString, String senhaString) {
		for (Usuario i : usuarios) {
			if (emailString.equals(i.getEmail()) && senhaString.equals(i.getSenha())) {
				logado = i;
				return true;
			}
		}
		return false;
	}

	public List<Email> getlistarEmailsUsuario() {
		return logado.emails();
	}

	public void enviarEmail(Email email) {
		email.setRemetente(logado);
		if (!(email.getDestinatario().equals(email.getRemetente()))) {
			email.getDestinatario().adicionarEmail(email);
		}
	}
	
	public boolean checarDestinatario(Email email) {
		if(email.getDestinatario().equals(email.getRemetente())) {
			return true;
		}
		else {
			return false;
		}
	}

	/*public void excluirEmailRecebido(Email email) {
		logado.emails().remove(email);
	}*/
	
	public void excluirEmailRecebido(int index) {
		logado.emails().remove(index);
	}
	
	public Email mostarEmail(int index) {
		return logado.emails().get(index);
	}

	public void realizarLogout() {
		logado = null;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public String[] lista() {
		String[] email = new String[usuarios.size()];
		int i = 0;

		for (Usuario e : usuarios) {
			email[i] = e.getEmail();
			i++;
		}

		return email;
	}
	
	public Usuario getusuarioLogado() {
		return logado;
	}

}