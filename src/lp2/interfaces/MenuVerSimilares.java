package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lp2.lerDados.ReadData;
import lp2.lerDados.Usuario;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
@SuppressWarnings("serial")
public class MenuVerSimilares extends JPanel implements ActionListener{

	private JScrollPane scrollPaneTabelaOpinioes;
	private JButton botaoVoltar;
	private JTable tabela;
	private JComboBox listaSuspensaDeUsuarios;
	private String usuariosCadastrados[];

	public MenuVerSimilares(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaTodosComponentes();
		iniciaComboBoxEstabelecimentos();
		iniciaTabelas();
		addNoContainer();

		botaoVoltar.addActionListener(this);
		listaSuspensaDeUsuarios.addActionListener(this);

	}
	
	private void instanciaTodosComponentes(){

		tabela = new JTable();
		tabela.setEnabled(false);
		
		scrollPaneTabelaOpinioes = new JScrollPane();
		scrollPaneTabelaOpinioes.setViewportView(tabela);			
		
		botaoVoltar = new JButton("Voltar");	
	}
	
	private void addNoContainer(){
		add(scrollPaneTabelaOpinioes, new AbsoluteConstraints(10,100,776,210));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
	}

	private void iniciaTabelas() {
		tabela.setModel(new DefaultTableModel(new Object[][]{},
				new String[] { "Usuario", "Nota" }));
	}

	private void iniciaComboBoxEstabelecimentos() {
		usuariosCadastrados = new String[ReadData.getUsuarios().size()+1];
		usuariosCadastrados[0] = "";
		int i = 1;
		for(Usuario usuario : ReadData.getUsuarios()){
			usuariosCadastrados[i] = usuario.getNome();
			i ++;
		}
		listaSuspensaDeUsuarios = new JComboBox(usuariosCadastrados);
		listaSuspensaDeUsuarios.setToolTipText("Nome de todos os estabelecimentos");
		botaoVoltar.setToolTipText("Clique para volta ao menu anterior");
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == listaSuspensaDeUsuarios){
			if(listaSuspensaDeUsuarios.getSelectedIndex() != 0)
				preencheTabela();
			else
				iniciaTabelas();
		}

		if(event.getSource() == botaoVoltar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new MenuPrincipal());
			MenuInicial.panelCorpo.updateUI();
		}
	}

	private void preencheTabela(){
		Object table[][] = new Object[ReadData.getUsuarios().size()][2];

		for(int i=0; i<ReadData.getUsuarios().size(); i++){
			
		}

		tabela.setModel(new DefaultTableModel(table,
				new String[] { "Usuario", "Estabelecimento 1", "Estabelecimento 2", "Estabelecimento 3", "Estabelecimento 4", "Estabelecimento 5" }));
	}

}
