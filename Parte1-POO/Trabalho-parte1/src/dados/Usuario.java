package dados;

import java.util.*;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private List<Email> emailsRecebidos = new LinkedList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Email> emails(){
        return emailsRecebidos;
    }

    public void adicionarEmail(Email email){
        emailsRecebidos.add(email);
    }

    @Override
    public String toString() {
        String s = new String();

        s += "Nome: " + nome + "\n";
        s += "Email: " + email + "\n";
        s += "Senha: " + senha + "\n";

        return s;
    }

    public boolean equals(Object o){
        if(o instanceof Usuario){
            Usuario u = (Usuario) o;

            if(this.email.equals(u.getEmail())){
                return true;
            }
        }
        return false;
    }

}
