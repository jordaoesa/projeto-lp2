package lp2.interfaces;
//code.google.
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import lp2.algoritmos.Algoritmos;
import lp2.algoritmos.TipoAlgoritmoPersonalizado;
import lp2.lerDados.Estabelecimento;
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
public class MenuGeraRecomendacao extends JPanel implements ActionListener{

	private int numUsuario;
	private int recomendacao = 0;

	//###
	String estabelecimentosRemovidos = "";
	private String tipoDeOrdenacao[];
	private JLabel selecioneOrdenacao;
	private static String ordenacaoSelecionada;
	private static JComboBox tiposDeComida;
	private JComboBox listaOrdenacao;
	private static String tipoDeComidaSelecionada;
	private JButton botaoRemoveRecomendacao;


	private static Algoritmos algoritmos;
	private boolean boolalgoritmoTipo1 = false;
	private boolean boolalgoritmoTipo2 = false;
	private boolean boolalgoritmoTipo3 = false;
	private boolean boolalgoritmoTipo4 = false;
	private boolean boolalgoritmoTipo5 = false;
	private boolean boolalgoritmoTipo6 = false;
	private boolean boolalgoritmoTipo7 = false;
	private String usuariosCadastrados[];
	private JLabel usuario ;
	private JLabel algoritmoEscolhido ;
	private JLabel numRecomendacoes;
	private JTextField campoTextoRecomendacoes;
	private ButtonGroup selectAlgorithm ;
	private JRadioButton selectScalarProductAlgorithm;
	private JRadioButton selectPopularityAlgorithm;
	private JRadioButton selectCosineAlgorithm;
	private JRadioButton selectCossenoIntersecao;
	private JRadioButton selectSimilaridadeDice;
	private JRadioButton selectSimilaridadeJaccard;
	private JRadioButton selectSimilaridadeOverlap;
	private static JTable tabela;
	private JLabel iconNotificacaoRecomencadao;
	private JInternalFrame frameRecomendacoes;
	private JComboBox listaSuspensaDeUsuarios;
	private JButton botaoGerarRecomendacao ;
	private JButton botaoVoltar;
	private JScrollPane scrollPane;
	private ImageIcon imageOk ;
	private ImageIcon imageErrado ;

	//****
	private JLabel filtro;
	private JLabel palavraChave;
	private JLabel local;
	private JTextField campoTextoLocalizacao;
	private String filtros[] = {"", "palavra-chave", "tipo estabelecimento", "localização"};
	private JTextField campoTextoPalavraChave;
	private JComboBox listaSuspensaDeFiltros = new JComboBox(filtros);
	private JLabel tipo;
	private String tipos[] = {"", "A la carte", "Self-service", "Prato feito"};
	private JComboBox listaSuspensaDeTiposEstabelecimentos = new JComboBox(tipos);

	private boolean tipoFiltroPalavraChave = false;
	private boolean tipoFiltroEstabelecimento= false;
	private boolean tipoFiltroLocalizacao = false;
	
	private JLabel labelBusca;
	private JTextField campoBusca;
	private JList listaEncontrados;
	private List<Integer> indicesEncontrados;
	private static JTable tabelaNotRecomendacoes;
	private JScrollPane scrollNotRecomendacoes;
	private JLabel labelRecomend;
	private JLabel labelNotRecomend;
	
	public MenuGeraRecomendacao(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaTodosComponentes();
		iniciaComboBoxUsuarios();
		
		//seta propriedades do internalFrame
		frameRecomendacoes.setLayout(new AbsoluteLayout());
		frameRecomendacoes.setClosable(true);
		frameRecomendacoes.setTitle("Recomendacoes");
		//frameRecomendacoes.setLocation(10, 10);
		//add RadioButton no buttonGroup
		selectAlgorithm.add(selectPopularityAlgorithm);
		selectAlgorithm.add(selectScalarProductAlgorithm);
		selectAlgorithm.add(selectCosineAlgorithm);
		selectAlgorithm.add(selectCossenoIntersecao);
		selectAlgorithm.add(selectSimilaridadeDice);
		selectAlgorithm.add(selectSimilaridadeJaccard);
		selectAlgorithm.add(selectSimilaridadeOverlap);
		//####
		tiposDeComida.setVisible(false);

		//uma unica linha selecionada
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tabela);

		//Classe que pega evento do titulo da tabela
		JTableHeader header = tabela.getTableHeader();
		header.setUpdateTableInRealTime(true);
		header.addMouseListener(new MouseAdapter() {
			int contaclicked = 2;
			public void mouseClicked(MouseEvent evt){
				if(contaclicked % 2 == 0){
					contaclicked ++;
					if(boolalgoritmoTipo1)
						scalarProductRecomendationsOrderlyTable(numUsuario, recomendacao);
					if(boolalgoritmoTipo2)
						popularityRecomendationsOrderlyTable(recomendacao);
				}else{
					contaclicked ++;
					if(boolalgoritmoTipo1)
						//scalarProductRecomendations(numUsuario, recomendacao);
						executaAlgoritmo(TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, numUsuario, recomendacao);
					if(boolalgoritmoTipo2)
						popularityRecomendations(recomendacao);
				}
			}
		});


		tabela.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent evt){
				estabelecimentosRemovidos += tabelaRecomendacaoEvento(evt) + " ";
			}
		});
		
		//***
		listaSuspensaDeTiposEstabelecimentos.setVisible(false);
		palavraChave.setVisible(false);
		local.setVisible(false);
		campoTextoLocalizacao.setVisible(false);
		campoTextoPalavraChave.setVisible(false);
		tipo.setVisible(false);

		//propriedades da tabela
		scrollPane.setViewportView(tabela);
		tabelaNotRecomendacoes.setEnabled(false);
		scrollNotRecomendacoes.setViewportView(tabelaNotRecomendacoes);

		//add scroolpane( tabela ), no frameInterno
		frameRecomendacoes.getContentPane().add(scrollPane, new AbsoluteConstraints(0,90,650,180));
		frameRecomendacoes.getContentPane().add(selecioneOrdenacao, new AbsoluteConstraints(10,10));
		frameRecomendacoes.getContentPane().add(listaOrdenacao, new AbsoluteConstraints(10,25));
		frameRecomendacoes.getContentPane().add(tiposDeComida, new AbsoluteConstraints(140,25));
		frameRecomendacoes.getContentPane().add(labelRecomend, new AbsoluteConstraints(280,70));
		frameRecomendacoes.getContentPane().add(labelNotRecomend, new AbsoluteConstraints(280, 280));
		frameRecomendacoes.getContentPane().add(scrollNotRecomendacoes, new AbsoluteConstraints(0,300,650,180));
		frameRecomendacoes.getContentPane().add(botaoRemoveRecomendacao, new AbsoluteConstraints(400,25));

		//		add(selecioneOrdenacao, new AbsoluteConstraints(500,200,140,23));
		//		add(listaOrdenacao, new AbsoluteConstraints(500,230,150,23));
		//		add(tiposDeComida, new AbsoluteConstraints(650,230));
		todosToolTipText();
		addNoContainer();

		//definindo um modelo para a tabela, para que ela nao inicie vazia
		tabela.setModel(new DefaultTableModel(new Object[][]{},
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" }));

		tabelaNotRecomendacoes.setModel(new DefaultTableModel(new Object[][]{},
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" }));
		
		addEventosComponentes();

	}
	private void instanciaTodosComponentes(){
		algoritmos = new Algoritmos();		
		usuariosCadastrados = new String[ReadData.getUsuarios().size()+1];

		//***
		filtro = new JLabel("Filtrar : ");
		palavraChave = new JLabel("Palavra-chave: ");
		local = new JLabel("Local: ");
		campoTextoPalavraChave = new JTextField(20);
		campoTextoLocalizacao = new JTextField(20);
		tipo = new JLabel("Tipo: ");



		//##########
		tipoDeOrdenacao = new String[]{"","Ordem Alfabetica","Tipo de Refeicao"};
		String[] tipoDeRefeicoes = {"","A la carte", "Prato feito", "Self-service"};
		selecioneOrdenacao = new JLabel("Tipo de Ordenacao:");
		botaoRemoveRecomendacao = new JButton("Remover");

		//ComboBox ######
		listaOrdenacao = new JComboBox(tipoDeOrdenacao);
		tiposDeComida = new JComboBox(tipoDeRefeicoes);

		//Labels
		usuario = new JLabel("Escolha usuario:");
		algoritmoEscolhido = new JLabel("Escolha algoritmo:");
		numRecomendacoes = new JLabel("Qual o numero de recomendacoes?");
		iconNotificacaoRecomencadao = new JLabel();
		labelBusca = new JLabel("Busca: ");
		labelRecomend = new JLabel("Locais onde ir");
		labelNotRecomend = new JLabel("Locais onde nao ir");
		
		//Campo texto
		campoTextoRecomendacoes = new JTextField(20);
		campoBusca = new JTextField();
		
		//Button
		selectAlgorithm = new ButtonGroup();		
		selectScalarProductAlgorithm = new JRadioButton("Algoritmo Personalizado");
		selectPopularityAlgorithm = new JRadioButton("Algoritmo Popularidade");
		selectCosineAlgorithm = new JRadioButton("Algoritmo Cosseno");
		selectCossenoIntersecao = new JRadioButton("Cosseno Intersecao");
		selectSimilaridadeDice = new JRadioButton("Similaridade Dice");
		selectSimilaridadeJaccard = new JRadioButton("Similaridade Jaccard");
		selectSimilaridadeOverlap = new JRadioButton("Similaridade Overlap");
		botaoVoltar = new JButton("Voltar");
		botaoGerarRecomendacao = new JButton("Gerar recomendacao");

		//Carrega as imagens
		imageOk = new ImageIcon("./src/lp2/imagens/Ok.png");
		imageErrado = new ImageIcon("./src/lp2/imagens/Stop.png");

		listaEncontrados = new JList();
		tabela = new JTable();
		tabelaNotRecomendacoes = new JTable();
		scrollPane = new JScrollPane();
		scrollNotRecomendacoes = new JScrollPane();
		frameRecomendacoes = new JInternalFrame();		

	}

	private void iniciaComboBoxUsuarios(){
		usuariosCadastrados[0] = ""; // so pra deixar vazio o primeiro campo, pra nao ficar como se ja tivesse um usuarios selecionado
		for(int i=0; i<ReadData.getUsuarios().size(); i++){
			usuariosCadastrados[i+1] = (i+1) + ": " +ReadData.getUsuarios().get(i).getNome();
		}
		listaSuspensaDeUsuarios = new JComboBox(usuariosCadastrados);

	}

	private void addEventosComponentes(){
		//add botoes ao tratamento da classe interna
		listaSuspensaDeUsuarios.addActionListener(this);
		selectPopularityAlgorithm.addActionListener(this);
		selectCosineAlgorithm.addActionListener(this);
		selectCossenoIntersecao.addActionListener(this);
		selectSimilaridadeDice.addActionListener(this);
		selectSimilaridadeJaccard.addActionListener(this);
		selectSimilaridadeOverlap.addActionListener(this);
		selectScalarProductAlgorithm.addActionListener(this);
		botaoGerarRecomendacao.addActionListener(this);
		botaoVoltar.addActionListener(this);
		
		//####
		listaOrdenacao.addActionListener(this);
		tiposDeComida.addActionListener(this);
		botaoRemoveRecomendacao.addActionListener(this);
		eventoCampoTexto();
		
		//***
		listaSuspensaDeFiltros.addActionListener(this);
		listaSuspensaDeTiposEstabelecimentos.addActionListener(this);
		
		
	}
	//trata eventos do JTextField
	private void eventoCampoTexto(){

		campoTextoRecomendacoes.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				try{
					if(campoTextoRecomendacoes.getText() == null || campoTextoRecomendacoes.getText().equals("") ){
						iconNotificacaoRecomencadao.setVisible(false);
						recomendacao = 0;
					}else{	
						iconNotificacaoRecomencadao.setVisible(true);
						recomendacao = Integer.parseInt(campoTextoRecomendacoes.getText());
//						if(recomendacao <= 0 || recomendacao > 38)  ///**** Numero magico aqui eh pra ser a qtd d restaurantes maxima permitida =x
//							iconNotificacaoRecomencadao.setIcon(imageErrado);
//						else
						iconNotificacaoRecomencadao.setIcon(imageOk);
					}
				}catch(Exception ex){
					recomendacao = 0;
					iconNotificacaoRecomencadao.setIcon(imageErrado);
				}
			}
		});
		
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
							campoBusca.setText(ReadData.getUsuarios().get(numUsuario-1).getNome());
		            	}
			        }
			    });
			}
		});

	}

	private void addNoContainer(){
		add(frameRecomendacoes, new AbsoluteConstraints(65,15));
		add(usuario, new AbsoluteConstraints(50,50,120,23));
		add(listaSuspensaDeUsuarios, new AbsoluteConstraints(180,50,250,23));
		add(labelBusca, new AbsoluteConstraints(460,50,60,23));
		add(campoBusca, new AbsoluteConstraints(520,50,200,23));
		add(listaEncontrados, new AbsoluteConstraints(520,73,200,-1));
		add(algoritmoEscolhido,  new AbsoluteConstraints(50, 90, 140, 23));
		add(selectPopularityAlgorithm, new AbsoluteConstraints(200, 90, 200, 23));
		add(selectScalarProductAlgorithm, new AbsoluteConstraints(200, 120, 200, -1));
		add(selectCosineAlgorithm, new AbsoluteConstraints(410, 90, 200, 23));
		add(selectCossenoIntersecao, new AbsoluteConstraints(410, 120, 200, 23));
		add(selectSimilaridadeDice, new AbsoluteConstraints(620, 90, 200, 23));
		add(selectSimilaridadeJaccard, new AbsoluteConstraints(620, 120, 200, 23));
		add(selectSimilaridadeOverlap, new AbsoluteConstraints(620, 150, 200, 23));
		add(numRecomendacoes, new AbsoluteConstraints(50, 150, 250, 23));
		add(campoTextoRecomendacoes, new AbsoluteConstraints(310, 150, 150, 23));
		add(botaoGerarRecomendacao, new AbsoluteConstraints(50, 500, 200, 23));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		add(iconNotificacaoRecomencadao, new AbsoluteConstraints(465,140,40,40));

		//		//###
		//		add(selecioneOrdenacao, new AbsoluteConstraints(500,200,140,23));
		//		add(listaOrdenacao, new AbsoluteConstraints(500,230,150,23));
		//		add(tiposDeComida, new AbsoluteConstraints(650,230));

		//***
		add(filtro, new AbsoluteConstraints(50, 200, 120, 23));
		add(listaSuspensaDeFiltros, new AbsoluteConstraints(180, 200, 150, 23));

		add(palavraChave, new AbsoluteConstraints(50, 250, 250, 23));
		add(campoTextoPalavraChave, new AbsoluteConstraints(180,250,250,23));	
		
		add(local, new AbsoluteConstraints(50, 250, 250, 23));
		add(campoTextoLocalizacao, new AbsoluteConstraints(180,250,250,23));
		
	
		add(tipo, new AbsoluteConstraints(50, 250, 250, 23));
		add(listaSuspensaDeTiposEstabelecimentos, new AbsoluteConstraints(180, 250, 250, 23));
		

	}

	//***
	private static void popularityRecomendationsFilter(int numRecomendacoes, String palavraChave){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendationsFilter(numRecomendacoes, palavraChave);
		System.out.println("tamanho" + recomendacoes.get(0).getNome());
		preencheTabela(recomendacoes);
	}

	//***
	private static void scalarProductRecomendationsFilter(int numberUser, int numRecomendacoes, String palavraChave){
		List<Estabelecimento> recomendacoes = algoritmos.executeScalarProductRecomendationsFilter(numRecomendacoes, ReadData.getUsuarios().get(numberUser-1), palavraChave);
		preencheTabela(recomendacoes);
	}

	//***
	private static void popularityRecomendationsType(int numRecomendacoes, String type){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendationsType(numRecomendacoes, type);
		preencheTabela(recomendacoes);
	}

	//***
	private static void scalarProductRecomendationsType(int numberUser, int numRecomendacoes, String type){
		List<Estabelecimento> recomendacoes = algoritmos.executeScalarProductRecomendationsType(numRecomendacoes, ReadData.getUsuarios().get(numberUser-1), type);
		preencheTabela(recomendacoes);
	}
	
	//****
	private static void scalarProductRecomendationsLocation(int numberUser, int numRecomendacoes, String local){
		List<Estabelecimento> recomendacoes = algoritmos.executeScalarProductRecomendationsLocation(numRecomendacoes, ReadData.getUsuarios().get(numberUser-1), local);
		preencheTabela(recomendacoes);
	}
	
	///****
	private static void popularityRecomendationsLocation(int numRecomendacoes, String local){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendationsLocation(numRecomendacoes, local);
		preencheTabela(recomendacoes);
	}
	
	private static void executaAlgoritmo(TipoAlgoritmoPersonalizado tipo, int numberUser, int qtdRecomendacoes){
		List<Estabelecimento> recomendacoes = new ArrayList<Estabelecimento>();
		List<Estabelecimento> naoRecomendados = new ArrayList<Estabelecimento>();
		if(tipo.equals(TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, ReadData.getUsuarios().get(numberUser-1));
			recomendacoes = resultados.get(0);
			naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.COSSENO)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.COSSENO, ReadData.getUsuarios().get(numberUser-1));
			recomendacoes = resultados.get(0);
			naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, ReadData.getUsuarios().get(numberUser-1));
			recomendacoes = resultados.get(0);
			naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, ReadData.getUsuarios().get(numberUser-1));
			recomendacoes = resultados.get(0);
			naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, ReadData.getUsuarios().get(numberUser-1));
			recomendacoes = resultados.get(0);
			naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, ReadData.getUsuarios().get(numberUser-1));
			recomendacoes = resultados.get(0);
			naoRecomendados = resultados.get(1);
		}
		preencheTabela(recomendacoes);
		preencheTabela2(naoRecomendados);
	}

//	private static void scalarProductRecomendations(int numberUser, int qtdRecomendacoes){
//		List<Estabelecimento> recomendacoes = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, ReadData.getUsuarios().get(numberUser-1)).get(0);
//		preencheTabela(recomendacoes);
//		List<Estabelecimento> naoRecomendados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, ReadData.getUsuarios().get(numberUser-1)).get(1);
//		preencheTabela2(naoRecomendados);
//	}

	private static void popularityRecomendations(int numRecomendacao){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendations(numRecomendacao).get(0);
		preencheTabela(recomendacoes);
		List<Estabelecimento> naoRecomendados = algoritmos.executeGenericRecomendations(numRecomendacao).get(1);
		preencheTabela2(naoRecomendados);
	}
	
//	private void cosineRecomendations(int numberUser, int qtdRecomendacoes) {
//		List<Estabelecimento> recomendacoes = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.COSSENO, ReadData.getUsuarios().get(numberUser-1)).get(0);
//		preencheTabela(recomendacoes);
//		List<Estabelecimento> naoRecomendados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.COSSENO, ReadData.getUsuarios().get(numberUser-1)).get(1);
//		preencheTabela2(naoRecomendados);
//	}

	private static void popularityRecomendationsOrderly(int numRecomendacao){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendations(numRecomendacao).get(0);
		preencheTabelaOrdenadas(recomendacoes, ordenacaoSelecionada);
	}

	private static void scalarProductRecomendationsOrderly(int numberUser, int qtdRecomendacoes){
		List<Estabelecimento> recomendacoes = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, ReadData.getUsuarios().get(numberUser-1)).get(0);
		preencheTabelaOrdenadas(recomendacoes, ordenacaoSelecionada);
	}
	
	//&&&&&&&&&&@@@@@@@@@@@@@@@@@@@
	private static void popularityRecomendationsOrderlyTable(int numRecomendacao){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendations(numRecomendacao).get(0);
		preencheTabelaOrdenadas(recomendacoes, "Ordem Alfabetica");
	}
	
	private static void scalarProductRecomendationsOrderlyTable(int numberUser, int qtdRecomendacoes){
		List<Estabelecimento> recomendacoes = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, ReadData.getUsuarios().get(numberUser-1)).get(0);
		preencheTabelaOrdenadas(recomendacoes, "Ordem Alfabetica");
	}


	private static void preencheTabela(List<Estabelecimento> recomendacoes) {
		Object obj[][] = new Object[recomendacoes.size()][3];
		for(int i=0; i < recomendacoes.size(); i++){
			obj[i][0] = recomendacoes.get(i).getNome();
			obj[i][1] = recomendacoes.get(i).getLocalizacao();
			obj[i][2] = recomendacoes.get(i).getTipoDeComida();
		}

		tabela.setModel(new DefaultTableModel(obj,
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" })
		{
			public boolean isCellEditable(int rowIndex, int mColIndex){  
				return false;  
			}
		});

		//seta o tamanho das colunas
		tabela.getColumnModel().getColumn(2).setPreferredWidth(20);	
		tabela.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(130);
		
		tabelaNotRecomendacoes.getColumnModel().getColumn(2).setPreferredWidth(20);	
		tabelaNotRecomendacoes.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaNotRecomendacoes.getColumnModel().getColumn(0).setPreferredWidth(130);
	}
	
	private static void preencheTabela2(List<Estabelecimento> recomendacoes) {
		Object obj[][] = new Object[recomendacoes.size()][3];
		for(int i=0; i < recomendacoes.size(); i++){
			obj[i][0] = recomendacoes.get(i).getNome();
			obj[i][1] = recomendacoes.get(i).getLocalizacao();
			obj[i][2] = recomendacoes.get(i).getTipoDeComida();
		}

		tabelaNotRecomendacoes.setModel(new DefaultTableModel(obj,
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" }));

		tabelaNotRecomendacoes.getColumnModel().getColumn(2).setPreferredWidth(20);	
		tabelaNotRecomendacoes.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaNotRecomendacoes.getColumnModel().getColumn(0).setPreferredWidth(130);
	}

	private static void preencheTabelaOrdenadas(List<Estabelecimento> recomendacoes,String ordenacao) {
		Object obj[][] = new Object[recomendacoes.size()][3];

		if(ordenacao.equals("Ordem Alfabetica")){

			List<Estabelecimento> estabelecimentosOrdenados = new ArrayList<Estabelecimento>(recomendacoes);
			for(int i=0; i<estabelecimentosOrdenados.size(); i++){
				for(int j=i+1; j<estabelecimentosOrdenados.size(); j++){
					if(estabelecimentosOrdenados.get(i).comparePorNome(estabelecimentosOrdenados.get(j)) > 0){
						Estabelecimento temp = estabelecimentosOrdenados.get(i);
						estabelecimentosOrdenados.set(i, estabelecimentosOrdenados.get(j));
						estabelecimentosOrdenados.set(j, temp);
					}
				}
			}

			for(int i=0; i < estabelecimentosOrdenados.size(); i++){
				obj[i][0] = estabelecimentosOrdenados.get(i).getNome();
				obj[i][1] = estabelecimentosOrdenados.get(i).getLocalizacao();
				obj[i][2] = estabelecimentosOrdenados.get(i).getTipoDeComida();
			}
		}

		if(ordenacao.equals("Tipo de Refeicao")){

			int numLugarAdicionados = 0;
			//adiciona nas primeiras posicoes os lugares que tem o tipo escolhido
			for(int i=0; i < recomendacoes.size(); i++){
				if(recomendacoes.get(i).getTipoDeComida().equals(tipoDeComidaSelecionada)){
					obj[numLugarAdicionados][0] = recomendacoes.get(i).getNome();
					obj[numLugarAdicionados][1] = recomendacoes.get(i).getLocalizacao();
					obj[numLugarAdicionados][2] = recomendacoes.get(i).getTipoDeComida();
					numLugarAdicionados ++;
				}
			}
			//adiciona todos os outros
			for (int i = 0; i < recomendacoes.size(); i++) {
				if(!recomendacoes.get(i).getTipoDeComida().equals(tipoDeComidaSelecionada)){
					obj[numLugarAdicionados][0] = recomendacoes.get(i).getNome();
					obj[numLugarAdicionados][1] = recomendacoes.get(i).getLocalizacao();
					obj[numLugarAdicionados][2] = recomendacoes.get(i).getTipoDeComida();
					numLugarAdicionados++;
				}
			}
		}
		//seta a tabela para nao ser editada nenhuma celula.
		tabela.setModel(new DefaultTableModel(obj,
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" })
		{
			public boolean isCellEditable(int rowIndex, int mColIndex){  
				return false;  
			}  
		});
		//seta o tamanho das colunas
		tabela.getColumnModel().getColumn(2).setPreferredWidth(20);	
		tabela.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(130);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		//evento do botaoRemoverRecomendacao do InternalFrame
		if(event.getSource()==botaoRemoveRecomendacao){
			if(boolalgoritmoTipo1){
				removeRecomendacaoPopular(recomendacao,estabelecimentosRemovidos);
			}if(boolalgoritmoTipo2)
				removeRecomendacaoPersonalizados(recomendacao, estabelecimentosRemovidos,TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, numUsuario);
			if(boolalgoritmoTipo3){
				removeRecomendacaoPersonalizados(recomendacao, estabelecimentosRemovidos,TipoAlgoritmoPersonalizado.COSSENO, numUsuario);
			}if(boolalgoritmoTipo4){
				removeRecomendacaoPersonalizados(recomendacao, estabelecimentosRemovidos,TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, numUsuario);
			}if(boolalgoritmoTipo5){
				removeRecomendacaoPersonalizados(recomendacao, estabelecimentosRemovidos,TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, numUsuario);
			}if(boolalgoritmoTipo6){
				removeRecomendacaoPersonalizados(recomendacao, estabelecimentosRemovidos,TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, numUsuario);
			}if(boolalgoritmoTipo7){
				removeRecomendacaoPersonalizados(recomendacao, estabelecimentosRemovidos,TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, numUsuario);
			}
		}
		if(event.getSource() == tiposDeComida){
			tipoDeComidaSelecionada = tiposDeComida.getSelectedItem().toString();
			popularityRecomendationsOrderly(recomendacao);
			//System.out.println(tipoDeComidaSelecionada);
		}
		//evento do botao listaSuspensaDeUsuarios
		if(event.getSource() == listaSuspensaDeUsuarios){
			numUsuario = listaSuspensaDeUsuarios.getSelectedIndex();
			campoBusca.setText("");
			//evento do botao listaOrdenacao #########
		}if(event.getSource() == listaOrdenacao) {

			ordenacaoSelecionada = listaOrdenacao.getSelectedItem().toString();

			if(ordenacaoSelecionada.equals("Ordem Alfabetica")){

				tiposDeComida.setVisible(false);
				if(boolalgoritmoTipo1)
					scalarProductRecomendationsOrderly(numUsuario, recomendacao);
				if(boolalgoritmoTipo2)
					popularityRecomendationsOrderly(recomendacao);
			}

			if(ordenacaoSelecionada.equals("Tipo de Refeicao")){
				tiposDeComida.setVisible(true);
				if(boolalgoritmoTipo1)
					scalarProductRecomendationsOrderly(numUsuario, recomendacao);
				if(boolalgoritmoTipo2)
					popularityRecomendationsOrderly(recomendacao);
			//pra voltar pro default	
			}if(ordenacaoSelecionada.equals("")){
				tiposDeComida.setVisible(false);
				if(boolalgoritmoTipo1)
					executaAlgoritmo(TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, numUsuario, recomendacao);
					//scalarProductRecomendations(numUsuario, recomendacao);
				if(boolalgoritmoTipo2)
					popularityRecomendations(recomendacao);
			}
		}
			
		if(event.getSource() == listaSuspensaDeFiltros){
			if(listaSuspensaDeFiltros.getSelectedItem().toString().equals("palavra-chave")){
				
				tipoFiltroPalavraChave = true;
				tipoFiltroEstabelecimento = false;
				palavraChave.setVisible(true);
				campoTextoPalavraChave.setVisible(true);
				
				tipo.setVisible(false);
				listaSuspensaDeTiposEstabelecimentos.setVisible(false);
				local.setVisible(false);
				campoTextoLocalizacao.setVisible(false);
				
			}if(listaSuspensaDeFiltros.getSelectedItem().toString().equals("tipo estabelecimento")){

				tipoFiltroEstabelecimento = true;
				tipoFiltroPalavraChave = false;
				
				palavraChave.setVisible(false);
				campoTextoPalavraChave.setVisible(false);
				local.setVisible(false);
				campoTextoLocalizacao.setVisible(false);
				
				tipo.setVisible(true);
				listaSuspensaDeTiposEstabelecimentos.setVisible(true);
				
			}if(listaSuspensaDeFiltros.getSelectedItem().toString().equals("localização")){

				
				tipoFiltroLocalizacao = true;
				tipoFiltroEstabelecimento = false;
				tipoFiltroPalavraChave = false;
				
				local.setVisible(true);
				campoTextoLocalizacao.setVisible(true);
				
				tipo.setVisible(false);
				listaSuspensaDeTiposEstabelecimentos.setVisible(false);
				palavraChave.setVisible(false);
				campoTextoPalavraChave.setVisible(false);
			}
			
		}
			
			//evento do botao seleciona Algoritmo Popular
		if(event.getSource() == selectPopularityAlgorithm){
			trocaAlgoritmo(1);
			//evento do botao seleciona Algoritmo Produto Escalar
		}if(event.getSource() == selectScalarProductAlgorithm){
			trocaAlgoritmo(2);
		}if(event.getSource() == selectCosineAlgorithm){
			trocaAlgoritmo(3);
		}if(event.getSource() == selectCossenoIntersecao){
			trocaAlgoritmo(4);
		}if(event.getSource() == selectSimilaridadeDice){
			trocaAlgoritmo(5);
		}if(event.getSource() == selectSimilaridadeJaccard){
			trocaAlgoritmo(6);
		}if(event.getSource() == selectSimilaridadeOverlap){
			trocaAlgoritmo(7);
			
			//evento do botao Gerar Recomendacao
		}if(event.getSource() == botaoGerarRecomendacao && !ReadData.getUsuarios().isEmpty() && !ReadData.getEstabelecimentos().isEmpty()){
			estabelecimentosRemovidos = "";
			if(numUsuario > 0 && recomendacao > 0){ //tratando o caso de o cara nao ter selecionado nenhum usuario

				if(boolalgoritmoTipo2){
					if(tipoFiltroPalavraChave && !(campoTextoPalavraChave.getText().trim().equals(""))){
						frameRecomendacoes.setVisible(true);
						scalarProductRecomendationsFilter(numUsuario, recomendacao, campoTextoPalavraChave.getText());
					}else if(tipoFiltroEstabelecimento){
						if(listaSuspensaDeTiposEstabelecimentos.getSelectedItem().toString().equals("A la carte")){
							frameRecomendacoes.setVisible(true);
							scalarProductRecomendationsType(numUsuario, recomendacao, "A la carte");
						}if(listaSuspensaDeTiposEstabelecimentos.getSelectedItem().toString().equals("Self-service")){
							frameRecomendacoes.setVisible(true);
							scalarProductRecomendationsType(numUsuario, recomendacao, "Self-service");
						}if(listaSuspensaDeTiposEstabelecimentos.getSelectedItem().toString().equals("Prato feito")){
							frameRecomendacoes.setVisible(true);
							scalarProductRecomendationsType(numUsuario, recomendacao, "Prato feito");
						}
						
					}if(tipoFiltroLocalizacao && !(campoTextoLocalizacao.getText().trim().equals(""))){
						frameRecomendacoes.setVisible(true);
						scalarProductRecomendationsLocation(numUsuario, recomendacao, campoTextoLocalizacao.getText());
						
					}else{
						frameRecomendacoes.setVisible(true);
						executaAlgoritmo(TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, numUsuario, recomendacao);
						//scalarProductRecomendations(numUsuario, recomendacao);
					}
				}
				if(boolalgoritmoTipo1){
					if(tipoFiltroPalavraChave && !(campoTextoPalavraChave.getText().trim().equals(""))){
						frameRecomendacoes.setVisible(true);
						popularityRecomendationsFilter(recomendacao, campoTextoPalavraChave.getText());
						
					}else if(tipoFiltroEstabelecimento){
						if(listaSuspensaDeTiposEstabelecimentos.getSelectedItem().toString().equals("A la carte")){
							frameRecomendacoes.setVisible(true);
							popularityRecomendationsType(recomendacao, "A la carte");
						}if(listaSuspensaDeTiposEstabelecimentos.getSelectedItem().toString().equals("Self-service")){
							frameRecomendacoes.setVisible(true);
							popularityRecomendationsType(recomendacao, "Self-service");
						}if(listaSuspensaDeTiposEstabelecimentos.getSelectedItem().toString().equals("Prato feito")){
							frameRecomendacoes.setVisible(true);
							popularityRecomendationsType(recomendacao, "Prato feito");
						}
					}if(tipoFiltroLocalizacao && !(campoTextoLocalizacao.getText().trim().equals(""))){
						frameRecomendacoes.setVisible(true);
						popularityRecomendationsLocation(recomendacao, campoTextoLocalizacao.getText());
						
					}else{
						frameRecomendacoes.setVisible(true);
						popularityRecomendations(recomendacao);
					}
				}
				if(boolalgoritmoTipo3){
					
					//MAIS UM ALGORITMOS PRA POR O FILTRO
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.COSSENO, numUsuario, recomendacao);
					//cossineRecomendations(numUsuario, recomendacao);
				}
				if(boolalgoritmoTipo4){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, numUsuario, recomendacao);
				}
				if(boolalgoritmoTipo5){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, numUsuario, recomendacao);
				}
				if(boolalgoritmoTipo6){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, numUsuario, recomendacao);
				}
				if(boolalgoritmoTipo7){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, numUsuario, recomendacao);
				}
			}if(numUsuario == 0 || recomendacao <= 0 || !temAlgoritmoSelecionado()){
				JOptionPane.showMessageDialog(null, "\"Usuario/Algoritmo/Numero de recomendacoes\" invalido.");
			}
		}if(event.getSource() == botaoGerarRecomendacao && (ReadData.getUsuarios().isEmpty() || ReadData.getEstabelecimentos().isEmpty())){
			if ((boolalgoritmoTipo1 || boolalgoritmoTipo2) && recomendacao > 0 && numUsuario > 0){
				JOptionPane.showMessageDialog(null, "Arquivo de Opinioes/Estabelecimentos vazio(s). Impossivel gerar recomendacoes.", "Error", JOptionPane.ERROR_MESSAGE);
			} else if(numUsuario == 0 || recomendacao <= 0 || !temAlgoritmoSelecionado()){
				JOptionPane.showMessageDialog(null, "\"Usuario/Algoritmo/Numero de recomendacoes\" invalido.");
			}
		}if(event.getSource() == botaoVoltar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new MenuPrincipal());
			MenuInicial.panelCorpo.updateUI();
		}
	}

	private void todosToolTipText(){

		listaSuspensaDeUsuarios.setToolTipText("Nome de todos os usuarios.");
		botaoVoltar.setToolTipText("Clique para voltar ao menu anterior.");
		botaoGerarRecomendacao.setToolTipText("Clique para gerar uma recomendacao.");
		
		//****
		listaSuspensaDeFiltros.setToolTipText("Tipos de filtros de pesquisa.");
		listaSuspensaDeTiposEstabelecimentos.setToolTipText("Tipos de estabelecimentos.");

	}
	

	//metodo que pega a linha selecionada
	private String tabelaRecomendacaoEvento(MouseEvent evt) {                                                
		int indiceLinha;  
		indiceLinha = tabela.getSelectedRow();   
		//Pega a linha que foi clicada  
		Object valoresLinhaTabela = tabela.getValueAt(indiceLinha,0);
		//System.out.println(valoresLinhaTabela);  
		
		return String.valueOf(valoresLinhaTabela);
	} 

	private void trocaAlgoritmo(int alg){
		boolalgoritmoTipo1 = false;
		boolalgoritmoTipo2 = false;
		boolalgoritmoTipo3 = false;
		boolalgoritmoTipo4 = false;
		boolalgoritmoTipo5 = false;
		boolalgoritmoTipo6 = false;
		boolalgoritmoTipo7 = false;
		
		switch(alg){
			case 1:
				boolalgoritmoTipo1 = true;
				break;
			case 2:
				boolalgoritmoTipo2 = true;
				break;
			case 3:
				boolalgoritmoTipo3 = true;
				break;
			case 4:
				boolalgoritmoTipo4 = true;
				break;
			case 5:
				boolalgoritmoTipo5 = true;
				break;
			case 6:
				boolalgoritmoTipo6 = true;
				break;
			case 7:
				boolalgoritmoTipo7 = true;
				break;
			default:
				//pass
				break;
		}
	}
	
	private boolean temAlgoritmoSelecionado(){
		if(boolalgoritmoTipo1 || boolalgoritmoTipo2 || boolalgoritmoTipo3 || boolalgoritmoTipo4 || boolalgoritmoTipo5 || boolalgoritmoTipo6 || boolalgoritmoTipo7)
			return true;
		return false;
	}
	
	//Quando remover uma recomendacao,recomendar outra.
	private void removeRecomendacaoPopular(int numRecomendacao, String nomeEstabelecimentos){
		List<List<Estabelecimento>> recomendacoes = algoritmos.executeGenericRecomendationsRemove(numRecomendacao,nomeEstabelecimentos);
		preencheTabela(recomendacoes.get(0));
		preencheTabela2(recomendacoes.get(1));
	}
	//quando remove uma recomendao, coma algoritmos personalizados
	private void removeRecomendacaoPersonalizados(int numRecomendacao,String nomeEstabelecimentos, TipoAlgoritmoPersonalizado tipoAlgoritmo, int numUser){
		List<List<Estabelecimento>> estabelecimentosRecomendados = algoritmos.executePersonalizeRecomendationsRemove(numRecomendacao, nomeEstabelecimentos,tipoAlgoritmo,numUser);
//		for(Estabelecimento e : estabelecimentosRecomendados.get(1)){
//			System.out.println(e.getNome());
//		}
		preencheTabela(estabelecimentosRecomendados.get(0));
		preencheTabela2(estabelecimentosRecomendados.get(1));
	}
}
