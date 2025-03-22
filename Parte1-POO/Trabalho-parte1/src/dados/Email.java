package dados;

import java.util.List;

public class Email {
    private String corpo;
    private String data;
    private String hora;
    private Usuario destinatario;
    private Usuario remetente;

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public String toString() {
        String s = new String();

        s += "Remetente: " + remetente.toString() + "\n";
        s += "Destinatário: " + destinatario.toString() + "\n";
        s += "Corpo: " + corpo + "\n";
        s += "Data: " + data + " ";
        s += "Hora: " + hora + "\n";

        return s;
    }
}
