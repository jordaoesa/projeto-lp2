package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lp2.algoritmos.Algoritmos;
import lp2.algoritmos.TipoAlgoritmoPersonalizado;
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
	private static Algoritmos algoritmos;

	public MenuVerSimilares(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaTodosComponentes();
		iniciaComboBoxEstabelecimentos();
		addNoContainer();

		botaoVoltar.addActionListener(this);
		listaSuspensaDeUsuarios.addActionListener(this);

	}
	
	private void instanciaTodosComponentes(){

		algoritmos = new Algoritmos();
		tabela = new JTable();
		scrollPaneTabelaOpinioes = new JScrollPane(tabela, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		botaoVoltar = new JButton("Voltar");	
	}
	
	private void addNoContainer(){
		add(scrollPaneTabelaOpinioes, new AbsoluteConstraints(10,100, 776, 300));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		add(listaSuspensaDeUsuarios, new AbsoluteConstraints(200, 50));
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
		}

		if(event.getSource() == botaoVoltar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new MenuPrincipal());
			MenuInicial.panelCorpo.updateUI();
		}
	}

	private void preencheTabela(){
		List<List<String>> temp = algoritmos.topFiveSimilarities(ReadData.getUsuarios().get(listaSuspensaDeUsuarios.getSelectedIndex()-1), TipoAlgoritmoPersonalizado.COSSENO);
		Object table[][] = new Object[temp.get(0).size()][6];
		
		for(int i=0; i < temp.get(0).size(); i++){
			try {
				table[i][0] = temp.get(i).get(0);
			} catch (Exception ex) {
				table[i][0] = null;
			}
			try {
				table[i][1] = temp.get(i).get(1);
			} catch (Exception ex) {
				table[i][1] = null;
			}
			try {
				table[i][2] = temp.get(i).get(2);
			} catch (Exception ex) {
				table[i][2] = null;
			}
			try {
				table[i][3] = temp.get(i).get(3);
			} catch (Exception ex) {
				table[i][3] = null;
			}
			try {
				table[i][4] = temp.get(i).get(4);
			} catch (Exception ex) {
				table[i][4] = null;
			}
			try {
				table[i][5] = temp.get(i).get(5);
			} catch (Exception ex) {
				table[i][5] = null;
			}
			
		}

		tabela.setModel(new DefaultTableModel(table,
				new String[] { "Usuario", "Estabelecimento 1", "Estabelecimento 2", "Estabelecimento 3", "Estabelecimento 4", "Estabelecimento 5" }));
		
		tabela.getColumnModel().getColumn(0).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
		
	}

}
