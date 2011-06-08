package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private JLabel selecioneUsuario;
	private JLabel selecioneAlgoritmo;
	private JLabel descricaoTabela;
	private JComboBox listaSuspensaDeUsuarios;
	private String usuariosCadastrados[];
	
	private ButtonGroup selectAlgorithm ;
	private JRadioButton selectScalarProductAlgorithm;
	private JRadioButton selectCosineAlgorithm;
	private JRadioButton selectCossenoIntersecao;
	private JRadioButton selectSimilaridadeDice;
	private JRadioButton selectSimilaridadeJaccard;
	private JRadioButton selectSimilaridadeOverlap;

	private static Algoritmos algoritmos;
	
	public MenuVerSimilares(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaTodosComponentes();
		iniciaComboBoxEstabelecimentos();
		addNoContainer();
		
		selectAlgorithm.add(selectScalarProductAlgorithm);
		selectAlgorithm.add(selectCosineAlgorithm);
		selectAlgorithm.add(selectCossenoIntersecao);
		selectAlgorithm.add(selectSimilaridadeDice);
		selectAlgorithm.add(selectSimilaridadeJaccard);
		selectAlgorithm.add(selectSimilaridadeOverlap);
		
		addActionListener();
		
	}
	
	private void addActionListener(){
		
		botaoVoltar.addActionListener(this);
		listaSuspensaDeUsuarios.addActionListener(this);
		
		selectCosineAlgorithm.addActionListener(this);
		selectCossenoIntersecao.addActionListener(this);
		selectSimilaridadeDice.addActionListener(this);
		selectSimilaridadeJaccard.addActionListener(this);
		selectSimilaridadeOverlap.addActionListener(this);
		selectScalarProductAlgorithm.addActionListener(this);
		
	}
	
	private void instanciaTodosComponentes(){

		algoritmos = new Algoritmos();
		tabela = new JTable();
		tabela.setEnabled(false);
		scrollPaneTabelaOpinioes = new JScrollPane(tabela, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		botaoVoltar = new JButton("Voltar");
		selecioneUsuario = new JLabel("Selecione o Usuario:");
		selecioneAlgoritmo = new JLabel("Selecione o Algoritmo:");
		descricaoTabela = new JLabel("Tabela que mostra os 10 Usuario mais similare e 5 estabelecimentos recomendados por eles.");
		
		selectAlgorithm = new ButtonGroup();
		selectScalarProductAlgorithm = new JRadioButton("Produto Escalar");
		selectCosineAlgorithm = new JRadioButton("Cosseno");
		selectCossenoIntersecao = new JRadioButton("Cosseno Intersecao");
		selectSimilaridadeDice = new JRadioButton("Similaridade Dice");
		selectSimilaridadeJaccard = new JRadioButton("Similaridade Jaccard");
		selectSimilaridadeOverlap = new JRadioButton("Similaridade Overlap");
		
	}
	
	private void addNoContainer(){
		add(scrollPaneTabelaOpinioes, new AbsoluteConstraints(10,250, 776, 203));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		add(listaSuspensaDeUsuarios, new AbsoluteConstraints(220, 50, 250, 23));
		add(selecioneUsuario, new AbsoluteConstraints(40, 50, 200, 23));
		add(selecioneAlgoritmo, new AbsoluteConstraints(40, 90, 200, 23));
		add(descricaoTabela, new AbsoluteConstraints(70,200, 700, 50));
		
		add(selectScalarProductAlgorithm, new AbsoluteConstraints(200, 90, 200, 23));
		add(selectCosineAlgorithm, new AbsoluteConstraints(200, 120, 200, -1));
		add(selectCossenoIntersecao, new AbsoluteConstraints(410, 90, 200, 23));
		add(selectSimilaridadeDice, new AbsoluteConstraints(410, 120, 200, 23));
		add(selectSimilaridadeJaccard, new AbsoluteConstraints(620, 90, 200, 23));
		add(selectSimilaridadeOverlap, new AbsoluteConstraints(620, 120, 200, 23));
		
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
			if(listaSuspensaDeUsuarios.getSelectedIndex() != 0 && selectAlgorithm.getSelection() != null)
				preencheTabela();
		}
		
		if(event.getSource() == selectScalarProductAlgorithm)
			preencheTabela();
		if(event.getSource() == selectCosineAlgorithm)
			preencheTabela();
		if(event.getSource() == selectCossenoIntersecao)
			preencheTabela();
		if(event.getSource() == selectSimilaridadeDice)
			preencheTabela();
		if(event.getSource() == selectSimilaridadeJaccard)
			preencheTabela();
		if(event.getSource() == selectSimilaridadeOverlap)
			preencheTabela();
		
		if(event.getSource() == botaoVoltar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new MenuPrincipal());
			MenuInicial.panelCorpo.updateUI();
		}
	}

	private void preencheTabela(){
		List<List<String>> listaDeSimilaridades = new ArrayList<List<String>>();

		try{
			if(selectAlgorithm.getSelection().equals(selectScalarProductAlgorithm.getModel())){
				listaDeSimilaridades = algoritmos.topFiveSimilarities(ReadData.getUsuarios().get(listaSuspensaDeUsuarios.getSelectedIndex()-1), TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR);
			}else if(selectAlgorithm.getSelection().equals(selectCosineAlgorithm.getModel())){
				listaDeSimilaridades = algoritmos.topFiveSimilarities(ReadData.getUsuarios().get(listaSuspensaDeUsuarios.getSelectedIndex()-1), TipoAlgoritmoPersonalizado.COSSENO);
			}else if(selectAlgorithm.getSelection().equals(selectCossenoIntersecao.getModel())){
				listaDeSimilaridades = algoritmos.topFiveSimilarities(ReadData.getUsuarios().get(listaSuspensaDeUsuarios.getSelectedIndex()-1), TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO);
			}else if(selectAlgorithm.getSelection().equals(selectSimilaridadeDice.getModel())){
				listaDeSimilaridades = algoritmos.topFiveSimilarities(ReadData.getUsuarios().get(listaSuspensaDeUsuarios.getSelectedIndex()-1), TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE);
			}else if(selectAlgorithm.getSelection().equals(selectSimilaridadeJaccard.getModel())){
				listaDeSimilaridades = algoritmos.topFiveSimilarities(ReadData.getUsuarios().get(listaSuspensaDeUsuarios.getSelectedIndex()-1), TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD);
			}else if(selectAlgorithm.getSelection().equals(selectSimilaridadeOverlap.getModel())){
				listaDeSimilaridades = algoritmos.topFiveSimilarities(ReadData.getUsuarios().get(listaSuspensaDeUsuarios.getSelectedIndex()-1), TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP);
			}
			
			Object table[][] = new Object[listaDeSimilaridades.size()][6];
			
			for(int i=0; i < listaDeSimilaridades.size(); i++){
				try {
					table[i][0] = listaDeSimilaridades.get(i).get(0);
				} catch (Exception ex) {
					table[i][0] = null;
				}
				try {
					table[i][1] = listaDeSimilaridades.get(i).get(1);
				} catch (Exception ex) {
					table[i][1] = null;
				}
				try {
					table[i][2] = listaDeSimilaridades.get(i).get(2);
				} catch (Exception ex) {
					table[i][2] = null;
				}
				try {
					table[i][3] = listaDeSimilaridades.get(i).get(3);
				} catch (Exception ex) {
					table[i][3] = null;
				}
				try {
					table[i][4] = listaDeSimilaridades.get(i).get(4);
				} catch (Exception ex) {
					table[i][4] = null;
				}
				try {
					table[i][5] = listaDeSimilaridades.get(i).get(5);
				} catch (Exception ex) {
					table[i][5] = null;
				}
				
				tabela.setModel(new DefaultTableModel(table, new String[] {
						"Usuario", "Estabelecimento 1", "Estabelecimento 2",
						"Estabelecimento 3", "Estabelecimento 4",
						"Estabelecimento 5" }));

				tabela.getColumnModel().getColumn(0).setPreferredWidth(200);
				tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
				tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
				tabela.getColumnModel().getColumn(3).setPreferredWidth(200);
				tabela.getColumnModel().getColumn(4).setPreferredWidth(200);
				tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Selecione o Usuario/Algoritmo", "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}

}
