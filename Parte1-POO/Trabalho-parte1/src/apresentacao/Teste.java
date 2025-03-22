package apresentacao;

import java.util.*;
import dados.*;
import negocio.Sistema;

public class Teste {
    private static Scanner s = new Scanner(System.in);
    private static Sistema sistema = new Sistema();

    public static void main(String[] args){
        int opcao = -1;

        while(opcao != 0){
            menu();
            opcao = s.nextInt();

            switch (opcao){
                case 0: break;
                case 1: cadastrarUsuario(); break;
                case 2: if(login()){
                    opcoesUsuario();
                } break;

            }
        }
    }

    public static void opcoesUsuario(){
        int opUsu = -1;

        while (opUsu != 0){
            menuUsuario();
            opUsu = Integer.parseInt(s.nextLine());
            switch (opUsu) {
                case 0: break;
                case 1: listarEmails(); break;
                case 2: enviarEmail(); break;
                case 3: excluirEmail(); break;
                case 4: responderEmail(); break;
                //case 5: logout(); break;
            }
        }
    }

    public static void menu(){ // Tela principal
        System.out.println("*****Escolha uma opção*****");
        System.out.println("0 - Sair");
        System.out.println("1 - Cadastrar um novo usuário");
        System.out.println("2 - Fazer Login");
    }

    public static void menuUsuario(){ // Tela de apresentação do usuário logado
        System.out.println("*****Escolha uma opção*****");
        System.out.println("0 - Logout");
        System.out.println("1 - Listar os e-mails recebidos");
        System.out.println("2 - Enviar novo e-mail");
        System.out.println("3 - Excluir e-mail");
        System.out.println("4 - Responder e-mail");
    }

    public static Usuario novoUsuario() {
        System.out.print("Nome: ");
        String nome = s.nextLine();
        nome = s.nextLine();

        System.out.print("Email: ");
        String email = s.nextLine();

        System.out.print("Senha: ");
        String senha = s.nextLine();

        Usuario u = new Usuario(); // Criação de novo objeto

        u.setNome(nome);
        u.setEmail(email);
        u.setSenha(senha);

        return u;
    }

    public static void cadastrarUsuario(){
        sistema.cadastrarUsuario(novoUsuario());
    }

    public static boolean login(){
        System.out.println("Login: ");

        System.out.print("Email: ");
        String email = s.nextLine();
        email = s.nextLine();

        System.out.print("Senha: ");
        String senha = s.nextLine();

        if(sistema.realizarLogin(email, senha)){
            System.out.println("Login realizado com sucesso!");
            return true;
        }
        else{
            System.out.println("Login não foi realizado!");
            return false;
        }
    }

    public static void enviarEmail(){

        listaUsuarios();

        System.out.print("Destinatário: ");
        int escolha = Integer.parseInt(s.nextLine());

        Usuario u = sistema.getUsuarios().get(escolha);

        System.out.println("Corpo: ");
        String corpo = s.nextLine();

        System.out.print("Data: ");
        String data = s.nextLine();

        System.out.print("Hora: ");
        String hora = s.nextLine();

        Email e = new Email();

        e.setDestinatario(u);
        e.setCorpo(corpo);
        e.setData(data);
        e.setHora(hora);
        sistema.enviarEmail(e);
    }

    public static void excluirEmail(){

        listarEmails();

        System.out.print("Indice do email a ser deletado: ");
        int escolha = Integer.parseInt(s.nextLine());

        Email e = sistema.listarEmailsUsuario().get(escolha);

        sistema.excluirEmailRecebido(e);
    }

    public static void responderEmail(){

        listarEmails();

        System.out.print("Escolher indice do email para responder: ");
        int escolha = Integer.parseInt(s.nextLine());

        Email e = sistema.listarEmailsUsuario().get(escolha);

        System.out.println("Corpo: ");
        String corpo = s.nextLine();

        System.out.print("Data: ");
        String data = s.nextLine();

        System.out.print("Hora: ");
        String hora = s.nextLine();

        Email email = new Email();

        email.setDestinatario(e.getRemetente());
        email.setCorpo(corpo);
        email.setData(data);
        email.setHora(hora);
        sistema.enviarEmail(email);
    }

    public static void listaUsuarios(){ // Mostra os usuarios cadastrados
        int i = 0;
        System.out.println("----------------------------");
        for(Usuario u: sistema.getUsuarios()){
            System.out.println("Indice: "+i);
            System.out.println(u);
            System.out.println("----------------------------");
            i++;
        }
    }

    public static void listarEmails(){
        int i = 0;
        System.out.println("----------------------------");
        for(Email e: sistema.listarEmailsUsuario()){
            System.out.println("Indice: "+i);
            System.out.println(e);
            i++;
            System.out.println("----------------------------");
        }
    }
}