package apresentacao;

import javax.swing.table.AbstractTableModel;

import execoes.SelectException;
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
			case 0: try {
				return sistema.getlistarEmailsUsuario().get(linha).getRemetente();
			} catch (SelectException e) {
				e.printStackTrace();
			} 
			case 1: try {
				return sistema.getlistarEmailsUsuario().get(linha).getAssunto();
			} catch (SelectException e) {
				e.printStackTrace();
			}
			case 2: try {
				return sistema.getlistarEmailsUsuario().get(linha).getData();
			} catch (SelectException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void atualizar() {
		fireTableStructureChanged();
	}

}