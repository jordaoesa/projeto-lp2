package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import lp2.algoritmos.Algoritmos;
import lp2.algoritmos.TipoAlgoritmoPersonalizado;
import lp2.lerDados.ReadData;
import lp2.lerDados.Usuario;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 * Classe que cria e executa a interface grafica do menu ver similares para
 * interacao com o usuario do sistema. Esse menu disponibiliza ao usuario os 10
 * usuarios que possuem opinioes mais similares as suas, e lhe recomenda cinco
 * estabelecimentos por usuario similar.
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
	private JLabel labelBusca;
	private JTextField campoBusca;
	private JList listaEncontrados;
	private List<Integer> indicesEncontrados;
	private int numUsuario = 0;
	
	private static Algoritmos algoritmos;
	
	
	/**
	 * Metodo que cria e inicia a interface grafica do menu ver similares, 
	 *  para interacao com o usuario.
	 */
	public MenuVerSimilares(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaTodosComponentes();
		iniciaComboBoxEstabelecimentos();
		addNoContainer();
		addButtonGroup();
		addActionListener();
		setLayoutDefaultTabela();
		
	}
	
	private void addButtonGroup(){

		selectAlgorithm.add(selectScalarProductAlgorithm);
		selectAlgorithm.add(selectCosineAlgorithm);
		selectAlgorithm.add(selectCossenoIntersecao);
		selectAlgorithm.add(selectSimilaridadeDice);
		selectAlgorithm.add(selectSimilaridadeJaccard);
		selectAlgorithm.add(selectSimilaridadeOverlap);
			
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
		eventosKey();
		
	}
	
	private void eventosKey(){
		
		campoBusca.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt){
				String nome = campoBusca.getText();
				indicesEncontrados = new ArrayList<Integer>();
				List<String> nomesEncontrados = new ArrayList<String>();
				
				for(int i=0; i<ReadData.getUsuarios().size(); i++){
					Usuario user = ReadData.getUsuarios().get(i);
					
					if(user.getNome().length() >= nome.length() && user.getNome().subSequence(0, nome.length()).toString().equalsIgnoreCase(nome) && indicesEncontrados.size() < 5){
						indicesEncontrados.add(i);
						nomesEncontrados.add(user.getNome());
					}
				}
				
				final String nomesUsers[] = new String[nomesEncontrados.size()];
				for(int i=0; i<nomesEncontrados.size(); i++){
					nomesUsers[i] = nomesEncontrados.get(i);
				}
				
				if(campoBusca.getText().equals("")){
					listaEncontrados.setVisible(false);
					numUsuario = -1;
					listaSuspensaDeUsuarios.setSelectedIndex(0);
				}else{
					listaEncontrados.setVisible(true);
				}
				
				listaEncontrados.setSize(200, 27*nomesEncontrados.size());
				listaEncontrados.setModel(new AbstractListModel() {
					String nomes[] = nomesUsers;
					@Override
					public int getSize() { return nomes.length; }
					@Override
					public Object getElementAt(int index) { return nomes[index]; }
				});
				
				listaEncontrados.addListSelectionListener(new ListSelectionListener() {
		            public void valueChanged(ListSelectionEvent evt) {
		            	if(listaEncontrados.getSelectedIndex() >= 0){
		            		listaEncontrados.setVisible(false);
			            	numUsuario = indicesEncontrados.get(listaEncontrados.getSelectedIndex());
							listaSuspensaDeUsuarios.setSelectedIndex(numUsuario+1);
							campoBusca.setText(ReadData.getUsuarios().get(numUsuario).getNome());
		            	}
			        }
			    });
			}
		});
		
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
		descricaoTabela = new JLabel("Tabela que mostra os 10 Usuarios mais similares e 5 estabelecimentos recomendados por eles.");
		labelBusca = new JLabel("Busca:");
		campoBusca = new JTextField();
		listaEncontrados = new JList();
		
		selectAlgorithm = new ButtonGroup();
		selectScalarProductAlgorithm = new JRadioButton("Produto Escalar");
		selectCosineAlgorithm = new JRadioButton("Cosseno");
		selectCossenoIntersecao = new JRadioButton("Cosseno Interseção");
		selectSimilaridadeDice = new JRadioButton("Similaridade Dice");
		selectSimilaridadeJaccard = new JRadioButton("Similaridade Jaccard");
		selectSimilaridadeOverlap = new JRadioButton("Similaridade Overlap");
		
	}
	
	private void addNoContainer(){
		add(labelBusca, new AbsoluteConstraints(490,50,60,23));
		add(campoBusca, new AbsoluteConstraints(550,50,200,23));
		add(listaEncontrados, new AbsoluteConstraints(550,73,200,-1));
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

	/**
	 * Metodo que tem a funcao de verificar e capturar 
	 * eventos do usuario com a interface grafica.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == listaSuspensaDeUsuarios){
			campoBusca.setText("");
			if(listaSuspensaDeUsuarios.getSelectedIndex() == 0)
				setLayoutDefaultTabela();
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

				setLayoutTabela();
			}
		} catch(RuntimeException e){
			JOptionPane.showMessageDialog(null, "Selecione o Usuario/Algoritmo", "Erro", JOptionPane.ERROR_MESSAGE);
		} catch(Exception e){
			JOptionPane.showMessageDialog(null, "Selecione o Usuario/Algoritmo", "Erro", JOptionPane.ERROR_MESSAGE);
		} 
		
	}
	
	private void setLayoutTabela(){
		tabela.getColumnModel().getColumn(0).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(1).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(2).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(3).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(4).setPreferredWidth(200);
		tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
	}
	
	private void setLayoutDefaultTabela(){
		tabela.setModel(new DefaultTableModel(new Object[][]{}, new String[] {
				"Usuario", "Estabelecimento 1", "Estabelecimento 2",
				"Estabelecimento 3", "Estabelecimento 4",
				"Estabelecimento 5" }));
		setLayoutTabela();
	}

}
