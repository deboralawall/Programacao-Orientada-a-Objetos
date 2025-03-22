package negocio;

import java.util.*;
import dados.*;

public class Sistema {
    private List<Usuario> usuarios = new LinkedList<>();
    private Usuario logado;

    public void cadastrarUsuario(Usuario usuario){
        usuarios.add(usuario);
    }

    public boolean realizarLogin(String email, String senha){
        for(Usuario i: usuarios){
            if(email.equals(i.getEmail()) && senha.equals(i.getSenha())){
                logado = i;
                return true;
            }
        }
        return false;
    }

    public List<Email> listarEmailsUsuario(){
        return logado.emails();
    }

    public void enviarEmail(Email email){
        email.setRemetente(logado);
        if(!(email.getDestinatario().equals(email.getRemetente()))){
            email.getDestinatario().adicionarEmail(email);
        }
    }

    public void excluirEmailRecebido(Email email){
        logado.emails().remove(email);
    }

    public void realizarLogout(){
        logado = null;
    }

    public List<Usuario> getUsuarios(){
        return this.usuarios;
    }

}
