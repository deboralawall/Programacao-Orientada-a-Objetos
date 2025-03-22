package negocio;

import java.sql.SQLException;
import java.util.*;

import dados.*;
import execoes.DeleteException;
import execoes.InsertException;
import execoes.SelectException;
import execoes.UpdateException;
import persistencia.Conexao;
import persistencia.EmailDAO;
import persistencia.UsuarioDAO;

public class Sistema {

	private List<Usuario> usuarios = new LinkedList<>();
	private Usuario logado;
	private static Sistema s = null;
	private UsuarioDAO usuarioDAO;
	private EmailDAO emailDAO;
	private static String senha;
	
	public Sistema(String senha) throws ClassNotFoundException, SQLException, SelectException{
		this.senha = senha;
		Conexao.setSenha(senha);
		usuarioDAO = UsuarioDAO.getInstance();
		emailDAO = EmailDAO.getInstance();
	}

	public Sistema() {

	}

	public static Sistema getInstance(){
		if (s == null) {
			try {
				s = new Sistema(senha);
			} catch (ClassNotFoundException | SQLException | SelectException e) {
				e.printStackTrace();
			}
		}
		return s;
	}

	public void cadastrarUsuario(Usuario usuario) throws InsertException, SelectException{
		// usuarios.add(usuario);
		usuarioDAO.insert(usuario);
	}

	public boolean realizarLogin(String emailString, String senhaString) throws SelectException {
		
		usuarios = usuarioDAO.selectAll();
		
		for (Usuario i : usuarios) {
			if (emailString.equals(i.getEmail()) && senhaString.equals(i.getSenha())) {
				logado = i;
				return true;
			}
		}
		return false;
	}

	public List<Email> getlistarEmailsUsuario() throws SelectException {
		
		List<Email> emailsLogado = new LinkedList<>();
		List<Email> allEmails = emailDAO.selectAll();
		
//		System.out.println(emailDAO.selectAll());
		for(Email email: allEmails) {
			System.out.println(" - " + email.toString());
			if(logado.getId() == email.getDestinatario()) {
				System.out.println("Entrou no if\n");
				emailsLogado.add(email);
			}
		}
//		System.out.println("pop: "+ emailsLogado.toString());
		return emailsLogado;
		//return logado.emails();
	}

	public boolean enviarEmail(Email email) throws InsertException, SelectException {
		//email.setRemetente(logado.getId());
		System.out.println("teste4");
		if(email.getDestinatario() != logado.getId()) {
			System.out.println("teste3");
			System.out.println(email.toString());
			emailDAO.insert(email);
	
			return true;
		}
		return false;
		
		/*if (!(email.getDestinatario().equals(email.getRemetente()))) {
			email.getDestinatario().adicionarEmail(email);
		}*/
	}
	
	/*public boolean checarDestinatario(Email email) {
		if(email.getDestinatario().equals(email.getRemetente())) {
			return true;
		}
		else {
			return false;
		}
	}*/

	/*public void excluirEmailRecebido(Email email) throws DeleteException {
		//logado.emails().remove(email);
		emailDAO.delete(email);
	}*/
	
	public void excluirEmailRecebido(int index) throws DeleteException {
		emailDAO.delete(index);
	}
	
	public Email mostarEmail(int index) throws SelectException {
		for (Email ema : emailDAO.selectAll()){
//			System.out.println(ema.toString());
			if(ema.getId() == index) {
//				System.out.println("entrei no if");
				return ema;
			}	
		}
		return null;
//		return logado.emails().get(index);
	}

	public void realizarLogout() {
		logado = null;
	}

	public List<Usuario> getUsuarios() throws SelectException{
		// return this.usuarios;
		return usuarioDAO.selectAll();
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
	
	public void deleteUsuario(Usuario u) throws DeleteException{
		usuarioDAO.delete(u);
	}
	
	public void atualizarUsuario(Usuario u) throws UpdateException{
		usuarioDAO.update(u);
	}
	
	

}



