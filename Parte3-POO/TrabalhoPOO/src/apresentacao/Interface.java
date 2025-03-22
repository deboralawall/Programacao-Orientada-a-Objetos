package apresentacao;

import negocio.Sistema;
import persistencia.EmailDAO;
import persistencia.UsuarioDAO;
import dados.Email;
import dados.Usuario;
import execoes.DeleteException;
import execoes.InsertException;
import execoes.SelectException;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;

public class Interface extends JFrame {

	private static List<Usuario> usuarios = new LinkedList<>();
	private static Sistema sistema = Sistema.getInstance();
	private static Usuario logado;
	private JPanel root;
	private JTextField txtNome; // painel de cadastro
	private JTextField txtEmail; // painel de cadastro
	private JPasswordField txtSenha; // painel de cadastro
	private JTextField txtEmail1; // painel de login
	private JPasswordField txtSenha1; // painel de login
	private JComboBox<String> escolhaDestinatario; // painel enviar email
	private JTextField txtAssunto; // painel enviar email
	private JTextField txtAssunto1; // painel responder email
	private JTable table; // caixa de msg
	private TabelaEmails tabelaEmails = new TabelaEmails(); // caixa de msg
	private static UsuarioDAO usuarioDAO;
	private static EmailDAO emailDAO;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface frame = new Interface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Interface() throws SelectException {

		setTitle("Aplicativo de Correio Eletronico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 574, 446);
		root = new JPanel();
		root.setForeground(Color.RED);
		root.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(root);
		root.setLayout(new CardLayout(0, 0));

		// Painel de Login
		JPanel telaLogin = new JPanel();
		telaLogin.setBackground(new Color(222, 221, 218));
		root.add(telaLogin, "name_53416453111758");
		telaLogin.setLayout(null);

		JLabel lblemail = new JLabel("Email");
		lblemail.setBackground(new Color(0, 0, 0));
		lblemail.setForeground(new Color(0, 0, 0));
		lblemail.setFont(new Font("Dialog", Font.BOLD, 12));
		lblemail.setBounds(33, 117, 70, 15);
		telaLogin.add(lblemail);

		txtEmail1 = new JTextField();
		txtEmail1.setBounds(90, 111, 384, 28);
		txtEmail1.setColumns(10);
		telaLogin.add(txtEmail1);

		JLabel lblsenha = new JLabel("Senha");
		lblsenha.setForeground(new Color(0, 0, 0));
		lblsenha.setBackground(new Color(36, 31, 49));
		lblsenha.setFont(new Font("Dialog", Font.BOLD, 12));
		lblsenha.setBounds(33, 200, 70, 15);
		telaLogin.add(lblsenha);

		txtSenha1 = new JPasswordField();
		txtSenha1.setBounds(90, 194, 384, 28);
		telaLogin.add(txtSenha1);

		// Painel Cadastro de Usuario
		JPanel cadastroUsuario = new JPanel();
		root.add(cadastroUsuario, "name_1078485375552");
		cadastroUsuario.setLayout(null);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(32, 95, 70, 15);
		cadastroUsuario.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(105, 89, 368, 28);
		cadastroUsuario.add(txtNome);
		txtNome.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(32, 178, 70, 15);
		cadastroUsuario.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(105, 172, 368, 28);
		cadastroUsuario.add(txtEmail);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setBounds(32, 258, 70, 15);
		cadastroUsuario.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(105, 252, 368, 28);
		cadastroUsuario.add(txtSenha);

		// Painel para o Usuario Logado
		JPanel usuarioLogado = new JPanel();
		root.add(usuarioLogado, "name_58966553878436");
		usuarioLogado.setLayout(null);

		// Painel de opções para o usuário Logado
		JPanel panel = new JPanel();
		panel.setBounds(152, 12, 389, 375);
		usuarioLogado.add(panel);
		panel.setLayout(new CardLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(130, 34, 422, 353);
		panel.add(scrollPane);
		
		// DefaultTableModel model = new DefaultTableModel();
		Object data[][] = null;
					
		String col[] = {"id", "Destinatario", "Assunto", "Data" };
		DefaultTableModel model = new DefaultTableModel(data, col);
		table = new JTable(model);
		
		scrollPane.setViewportView(table);
		//table.setModel(tabelaEmails);
		
		// botao enviar o novo email a partir da caixa de msg
		JPanel caixaMsg = new JPanel();
		caixaMsg.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(caixaMsg, "name_9573952773535");
		caixaMsg.setLayout(null);

		JLabel lblDestinatario = new JLabel("Enviar para");
		lblDestinatario.setBounds(12, 16, 107, 46);
		caixaMsg.add(lblDestinatario);
		
		String lista[] = {""};
		
		DefaultComboBoxModel<String> ComboBox = new DefaultComboBoxModel<String>(lista);
		
		escolhaDestinatario = new JComboBox<String>(ComboBox);
		escolhaDestinatario.setBounds(116, 27, 261, 24);
		caixaMsg.add(escolhaDestinatario);

		JLabel lblAssunto = new JLabel("Assunto");
		lblAssunto.setBounds(34, 66, 85, 34);
		caixaMsg.add(lblAssunto);

		txtAssunto = new JTextField();
		txtAssunto.setBounds(116, 71, 261, 26);
		caixaMsg.add(txtAssunto);
		txtAssunto.setColumns(10);

		JTextPane txtCorpoEmail = new JTextPane();
		txtCorpoEmail.setBounds(31, 112, 323, 217);
		caixaMsg.add(txtCorpoEmail);

		// Evento botao cancelar o envio de um email
		JButton btnCancelarEnvio = new JButton("Cancelar");
		btnCancelarEnvio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAssunto.setText("");
				txtCorpoEmail.setText("");
				panel.removeAll();
				panel.repaint();
				panel.revalidate();
				panel.add(scrollPane);
				escolhaDestinatario.removeAllItems(); 
			}
		});
		btnCancelarEnvio.setBounds(207, 338, 117, 25);
		caixaMsg.add(btnCancelarEnvio);

		// Evendo botão novo email da tela de Usuário Logado
		JButton btnEnviarEmail = new JButton("Novo Email");
		btnEnviarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAssunto.setText("");
				txtCorpoEmail.setText("");
				panel.removeAll();
				panel.repaint();
				panel.revalidate();
				panel.add(caixaMsg);
				
				int i = 0;
				
				for(String email : sistema.lista()) {
					escolhaDestinatario.insertItemAt(email, i);
				};
			}
		});
		btnEnviarEmail.setBounds(12, 277, 117, 25);
		usuarioLogado.add(btnEnviarEmail);
		
		JButton btnNovoEmail = new JButton("Enviar");
		btnNovoEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Email email = new Email();
				Date date = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
				String emailEscolhido = (String) escolhaDestinatario.getSelectedItem();
                Usuario dest = null;
                
				try {
					dest = sistema.getUsuarios().stream().filter(u -> u.getEmail().equals(emailEscolhido)).findFirst().get();
				} catch (SelectException e1) {
					e1.printStackTrace();
				}
                
				email.setDestinatario(dest.getId());
				email.setAssunto(txtAssunto.getText());
				email.setCorpo(txtCorpoEmail.getText());
				email.setData(date);
				//System.out.println(logado.getId());
				email.setRemetente(logado.getId());

				txtAssunto.setText("");
				txtCorpoEmail.setText("");

				try {
					if(!sistema.enviarEmail(email)) {
						JOptionPane.showMessageDialog(null, "Não é possível enviar o email");
					}else {
						JOptionPane.showMessageDialog(null, "Email enviado");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (InsertException e1) {
					e1.printStackTrace();
				} catch (SelectException e1) {
					e1.printStackTrace();
				}
				
				panel.removeAll();
				panel.repaint();
				panel.revalidate();
				panel.add(scrollPane);
				escolhaDestinatario.removeAllItems(); 
			}
		});
		btnNovoEmail.setBounds(78, 338, 117, 25);
		caixaMsg.add(btnNovoEmail);
			
		//painel responder email
		JPanel responderEmail = new JPanel();
		responderEmail.setLayout(null);
		responderEmail.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.add(responderEmail, "name_37237918677074");
		
		JLabel lblAssunto1 = new JLabel("Assunto");
		lblAssunto1.setBounds(31, 42, 85, 34);
		responderEmail.add(lblAssunto1);
		
		txtAssunto1 = new JTextField();
		txtAssunto1.setColumns(10);
		txtAssunto1.setBounds(104, 47, 261, 26);
		responderEmail.add(txtAssunto1);
		
		JTextPane txtCorpoEmail1 = new JTextPane();
		txtCorpoEmail1.setBounds(31, 88, 323, 241);
		responderEmail.add(txtCorpoEmail1);
		
		JButton btnCancelarEnvio_1 = new JButton("Cancelar");
		btnCancelarEnvio_1.setBounds(207, 338, 117, 25);
		responderEmail.add(btnCancelarEnvio_1);
		btnCancelarEnvio_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAssunto1.setText("");
				txtCorpoEmail1.setText("");
				panel.removeAll();
				panel.repaint();
				panel.revalidate();
				panel.add(scrollPane);
			}
		});
		
		/*
		 * Email email = new Email();
				Date date = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
				String emailEscolhido = (String) escolhaDestinatario.getSelectedItem();
                Usuario dest = null;
                
				try {
					dest = sistema.getUsuarios().stream().filter(u -> u.getEmail().equals(emailEscolhido)).findFirst().get();
				} catch (SelectException e1) {
					e1.printStackTrace();
				}
                
				email.setDestinatario(dest.getId());
				email.setAssunto(txtAssunto.getText());
				email.setCorpo(txtCorpoEmail.getText());
				email.setData(date);
				//System.out.println(logado.getId());
				email.setRemetente(logado.getId());

				txtAssunto.setText("");
				txtCorpoEmail.setText("");

				try {
					if(!sistema.enviarEmail(email)) {
						JOptionPane.showMessageDialog(null, "Não é possível enviar o email");
					}else {
						JOptionPane.showMessageDialog(null, "Email enviado");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (InsertException e1) {
					e1.printStackTrace();
				} catch (SelectException e1) {
					e1.printStackTrace();
				}
				
				panel.removeAll();
				panel.repaint();
				panel.revalidate();
				panel.add(scrollPane);
				escolhaDestinatario.removeAllItems(); 
		 * */
		
		
		JButton btnEnviarEmail_1 = new JButton("Enviar");
		btnEnviarEmail_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Email email = new Email();
				Date date = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
				
				try {
					email.setDestinatario(sistema.getlistarEmailsUsuario().get(table.getSelectedRow()).getRemetente());
				} catch (SelectException e1) {
					e1.printStackTrace();
				}

//				email.setDestinatario(remetente.getId());
				email.setAssunto(txtAssunto1.getText());
				email.setCorpo(txtCorpoEmail1.getText());
				email.setData(date);
				//System.out.println(logado.getId());
				email.setRemetente(logado.getId());

				txtAssunto.setText("");
				txtCorpoEmail.setText("");

				try {
					if(!sistema.enviarEmail(email)) {
						JOptionPane.showMessageDialog(null, "Não é possível enviar o email");
					}else {
						JOptionPane.showMessageDialog(null, "Email enviado");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (InsertException e1) {
					e1.printStackTrace();
				} catch (SelectException e1) {
					e1.printStackTrace();
				}
				


				JOptionPane.showMessageDialog(null, "Email respondido");

				panel.removeAll();
				panel.repaint();
				panel.revalidate();
				panel.add(scrollPane);
				escolhaDestinatario.removeAllItems(); 
			}
		});
		btnEnviarEmail_1.setBounds(78, 338, 117, 25);
		responderEmail.add(btnEnviarEmail_1);
		
		JButton btnResponderEmail = new JButton("Responder");
		btnResponderEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtAssunto1.setText("");
				txtCorpoEmail1.setText("");
				panel.removeAll();
				panel.repaint();
				panel.revalidate();
				panel.add(responderEmail);
			}
		});
		
		JButton btnMostarEmail = new JButton("Mostrar");
		btnMostarEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
//				System.out.println("aaaaaaa: " + table.getSelectedRow());
//				System.out.println("teste aaaa"+sistema.mostarEmail(table.getSelectedRow()));
				
				int column = 0;
				int row = table.getSelectedRow();
				String value = table.getModel().getValueAt(row, column).toString();
				
//				System.out.println("value: " + value);
				try {
					JOptionPane.showMessageDialog(null, sistema.mostarEmail(Integer.parseInt(value)));
//					System.out.println(sistema.mostarEmail(Integer.parseInt(value)));
				} catch (NumberFormatException | SelectException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				//JOptionPane.showMessageDialog(null, sistema.mostarEmail(table.getSelectedRow()));
			}
		});
		btnMostarEmail.setFont(new Font("Dialog", Font.BOLD, 12));
		btnMostarEmail.setBounds(12, 33, 117, 25);
		usuarioLogado.add(btnMostarEmail);
		btnResponderEmail.setBounds(12, 160, 117, 25);
		usuarioLogado.add(btnResponderEmail);

		// Evento botão SAIR da tela de usuario logado
		JButton btnSairLogado = new JButton("Sair");
		btnSairLogado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.setRowCount(0);
				txtAssunto.setText("");
				txtCorpoEmail.setText("");
				root.removeAll();
				root.repaint();
				root.revalidate();
				root.add(telaLogin);
				//tabelaEmails.close();
			}
		});
		btnSairLogado.setBounds(12, 338, 117, 25);
		usuarioLogado.add(btnSairLogado);
		
		JButton btnRemoveEmail = new JButton("Remover");
		btnRemoveEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(table.getSelectedRow() != -1) {
					System.out.println(table.getSelectedRow());
					
					int column = 0;
					int row = table.getSelectedRow();
					String value = table.getModel().getValueAt(row, column).toString();
					
					try {
						sistema.excluirEmailRecebido(Integer.parseInt(value));
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (DeleteException e1) {
						e1.printStackTrace();
					}
					
					tabelaEmails.atualizar();
					
					JOptionPane.showMessageDialog(null, "Email removido");
				}
						
			}
		});
		btnRemoveEmail.setBounds(12, 220, 117, 25);
		usuarioLogado.add(btnRemoveEmail);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tam = 1;
				
				try {
					model.setRowCount(0);
					for(Email email: sistema.getlistarEmailsUsuario()) {
						/*data[tam][0] = "r3";
						data[tam][1] = "r2";
						data[tam][2] = "r1";
						tam = tam+1;*/
						
						Object[] row = { email.getId(), email.getDestinatario(), email.getAssunto(), email.getData() };
						model.addRow(row);
						//table.getModel().setValueAt(2,2,2);
						//model.addRow(2, 2, 2);
					}
				} catch (SelectException e1) {
					e1.printStackTrace();
				};
			}
		});
		btnAtualizar.setBounds(12, 96, 117, 25);
		usuarioLogado.add(btnAtualizar);

		// Botao realiza Login da tela de login
		JButton btnLoginInicial = new JButton("Login");
		btnLoginInicial.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLoginInicial.setBounds(136, 287, 117, 25);
		btnLoginInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String emailString = txtEmail1.getText();
				String senhaString = String.valueOf(txtSenha1.getPassword());

				txtEmail1.setText("");
				txtSenha1.setText("");

				try {
					if (sistema.realizarLogin(emailString, senhaString)) {
						logado = sistema.getusuarioLogado();
						
						root.removeAll();
						root.repaint();
						root.revalidate();
						root.add(usuarioLogado);
						tabelaEmails.atualizar();	
						
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao fazer login");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (SelectException e1) {
					e1.printStackTrace();
				}

			}
		});
		telaLogin.add(btnLoginInicial);

		// Botao Cadastrar Usuario da Tela de Login
		JButton btnCadstroInicial = new JButton("Cadastrar");
		btnCadstroInicial.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCadstroInicial.setBounds(302, 287, 117, 25);
		btnCadstroInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				root.removeAll();
				root.repaint();
				root.revalidate();
				root.add(cadastroUsuario);
			}
		});
		telaLogin.add(btnCadstroInicial);

		// Botao Cadastrar Usuario da Tela de Cadastro de Usuario
		JButton btnCadastrarCadastroUsuario = new JButton("Cadastrar");
		btnCadastrarCadastroUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuario usuario = new Usuario();
				Email email = new Email();
				String txtsenha = String.copyValueOf(txtSenha.getPassword());

				usuario.setNome(txtNome.getText());
				usuario.setEmail(txtEmail.getText());
				usuario.setSenha(txtsenha);
				
				try {
					sistema.cadastrarUsuario(usuario);
				} catch (InsertException | SelectException e1) {
					e1.printStackTrace();
				}

				txtNome.setText("");
				txtEmail.setText("");
				txtSenha.setText("");
				
				JOptionPane.showMessageDialog(null, "Usuario cadastrado");
				
				root.removeAll();
				root.repaint();
				root.revalidate();
				root.add(telaLogin);

			}
		});
		btnCadastrarCadastroUsuario.setBounds(225, 331, 117, 25);
		cadastroUsuario.add(btnCadastrarCadastroUsuario);

	}
	

	
}
