package apresentacao;



import javax.swing.table.AbstractTableModel;
import negocio.Sistema;

public class TabelaEmails extends AbstractTableModel {

	private static Sistema sistema = Sistema.getInstance();
	private String[] cabecalho = { "Destinatario", "Assunto", "Data" };

	@Override
	public int getRowCount() {
		if (sistema.getusuarioLogado() != null)
			return sistema.getusuarioLogado().emails().size();	
		else {
			return 0;
		}
	}

	@Override
	public int getColumnCount() {
		return cabecalho.length;
	}

	public String getColumnName(int column) {
		return cabecalho[column];
	}

	@Override
	public Object getValueAt(int linha, int coluna) {
		switch (coluna) {
			case 0: return sistema.getlistarEmailsUsuario().get(linha).getRemetente(); 
			case 1: return sistema.getlistarEmailsUsuario().get(linha).getAssunto();
			case 2: return sistema.getlistarEmailsUsuario().get(linha).getData();
		}
		return null;
	}

	public void atualizar() {
		fireTableStructureChanged();
	}

}