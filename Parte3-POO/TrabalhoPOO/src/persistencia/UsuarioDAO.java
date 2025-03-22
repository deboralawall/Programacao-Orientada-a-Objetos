package persistencia;

import java.sql.Connection;
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

public class UsuarioDAO {
	
	private static UsuarioDAO instance = null;
	//private static EmailDAO emailDAO = null;
		
	private PreparedStatement selectNewId;
	private PreparedStatement selectAll;
	private PreparedStatement insert;
	private PreparedStatement delete;
	private PreparedStatement update;
	
	public static UsuarioDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
		if(instance == null) {
			instance = new UsuarioDAO();
		}
		return instance;
	}
	
	private UsuarioDAO() throws ClassNotFoundException, SQLException, SelectException{
		
		Connection conexao = Conexao.getConexao();
		
		selectNewId = conexao.prepareStatement("SELECT nextval('id')");
		insert = conexao.prepareStatement("INSERT INTO usuarios(nome, email, senha) VALUES (?, ?, ?)");
		selectAll = conexao.prepareStatement("SELECT * FROM usuarios");
		update = conexao.prepareStatement("UPDATE usuarios set nome = ?, email = ?, senha = ? WHERE id = ?");
		delete = conexao.prepareStatement("DELETE FROM usuarios WHERE id = ?");
		
		//emailDAO = EmailDAO.getInstance();
	}
	
	private int selectNewId() throws SelectException{
		try {
			ResultSet rs = selectNewId.executeQuery();
			
			if(rs.next()) {
				return rs.getInt(1);
			}
		}catch (SQLException e) {
			throw new SelectException("Erro ao buscar novo id do usuario");
		}
		return 0;
	}
	
	public void insert (Usuario usuario) throws InsertException, SelectException {
		try {
			//usuario.setId(selectNewId());
			//insert.setInt(1, usuario.getId());
			insert.setString(1, usuario.getNome());
			insert.setString(2, usuario.getEmail());
			insert.setString(3, usuario.getSenha());
			insert.executeUpdate();
			//usuario.getEmail().set;
			//emailDAO.insert(usuario.getEmail());
		} catch (SQLException e) {
			throw new InsertException("Erro ao inserir usuario");
		}
	}
	
	public List<Usuario> selectAll () throws SelectException{
		
		List<Usuario> usuarios = new LinkedList<>();
		
		try {
			ResultSet rs  = selectAll.executeQuery();
			
			while (rs.next()) {
				
				int id = rs.getInt(1);
				String nome = rs.getString(2);
				String email = rs.getString(3);
				String senha = rs.getString(4);
				
				//Usuario usuario = new Usuario(id, nome, email, senha);
				
				//System.out.println(id + " " + email);
				
				usuarios.add(new Usuario(id, nome, email, senha));
				
			}
		} catch (SQLException e) {
			throw new SelectException("Erro ao buscar usuario");
		}
		
		return usuarios;
	}
	
	public void update (Usuario usuario) throws UpdateException{
		try {
			//emailDAO.update(usuario.getEmail());
			update.setString(1, usuario.getNome());
			update.setString(2, usuario.getEmail());
			update.setString(3, usuario.getSenha());
			update.executeUpdate();
		} catch (SQLException e) {
			throw new UpdateException("Erro ao atualizar usuario");
		}
	}
	
		
	public void delete(Usuario usuario) throws DeleteException {
		try {
			delete.setInt(1, usuario.getId());
			delete.executeUpdate();
		}catch (SQLException e) {
			throw new DeleteException("Erro ao deletar usuario");
		}
	}	

}
