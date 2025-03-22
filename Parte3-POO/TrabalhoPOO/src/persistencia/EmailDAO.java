package persistencia;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import execoes.DeleteException;
import execoes.InsertException;
import execoes.SelectException;
import execoes.UpdateException;

import dados.*;

public class EmailDAO {
	
	private static EmailDAO instance = null;
	
	private PreparedStatement selectNewId;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	
	public static EmailDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance == null) {
			instance = new EmailDAO();
		}
		return instance;
	}
	
	private EmailDAO() throws ClassNotFoundException, SQLException, SelectException{
		Connection conexao = Conexao.getConexao();
		selectNewId = conexao.prepareStatement("SELECT nextval('emailID')");
		insert = conexao.prepareStatement("INSERT INTO emails(assunto, corpo, data_envio, remetente_id, destinatario_id) values (?, ?, ?, ?, ?)");
		selectAll = conexao.prepareStatement("SELECT * FROM emails");
		update = conexao.prepareStatement("UPDATE emails set assunto = ?, corpo = ?, data = ?, remetente = ?, destinatario = ? WHERE id = ?");
		delete = conexao.prepareStatement("DELETE FROM emails WHERE id = ?");
	}
	
	public void insert (Email email) throws InsertException, SelectException{
		System.out.println("teste1");
		try {
			//System.out.println();
			// insert.setInt(1, selectNewId());
			insert.setString(1, email.getAssunto());
			insert.setString(2, email.getCorpo());
			insert.setDate(3, email.getData());
			insert.setInt(4, email.getRemetente()); // remetente
			insert.setInt(5, email.getDestinatario()); // destinatario
			insert.executeUpdate();
			
		} catch (SQLException e) {
			throw new InsertException("Erro ao inserir email");
		}
		System.out.println("teste2");
	}
	
	public List<Email> selectAll () throws SelectException{
		List<Email> emails = new LinkedList<>();
//		System.out.println("teste");
		try {
			
			//selectAll.setInt(1, email);
			ResultSet rs  = selectAll.executeQuery();
			
			while(rs.next()) {
								
				int id = rs.getInt(1);
				String assunto = rs.getString(2);
				String corpo = rs.getString(3);
				Date data = rs.getDate(4);
				int remetente = rs.getInt(5);
				int destinatario = rs.getInt(6);
				
				emails.add(new Email(id, assunto, corpo, data, remetente, destinatario));
				
			}
		} catch (SQLException e) {
			throw new SelectException("Erro ao buscar email");
		}
		
//		for(Email email : emails) {
//			System.out.println(email.getDestinatario());
//		}
		
		return emails;
	}
	
	public void update (Email email) throws UpdateException{
		try {
			update.setString(1, email.getAssunto());
			update.setString(2, email.getCorpo());
			update.setDate(3, email.getData());
			update.setInt(4, email.getId()); // remetente
			update.setInt(5, email.getId()); // destinatario
			update.executeUpdate();
		} catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar email");
		}
	}
	
		
	public void delete(int index) throws DeleteException {
		try {
			delete.setInt(1, index);
			delete.executeUpdate();
		}catch (SQLException e) {
			throw new DeleteException("Erro ao deletar email");
		}
	}	

}

