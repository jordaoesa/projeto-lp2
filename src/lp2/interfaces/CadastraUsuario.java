package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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
public class CadastraUsuario extends JPanel implements ActionListener {

	//Mudanca Localizacao
	private JButton botaoComoChegar;
	
	private String nomeEstabelecimento;
	private Algoritmos algoritmos;
	private boolean boolalgoritmoTipo1 = false;
	private boolean boolalgoritmoTipo2 = false;
	private boolean boolalgoritmoTipo3 = false;
	private boolean boolalgoritmoTipo4 = false;
	private boolean boolalgoritmoTipo5 = false;
	private boolean boolalgoritmoTipo6 = false;
	private boolean boolalgoritmoTipo7 = false;
	private List<String> notas;
	private List<String> nomesEstabelecimentos;
	private List<String> estabelecimentosAdicionados;
	private int indiceEstabelecimento = -1;
	private int indiceNotas = 0;
	private String avaliacao[] = {"","0 : Nao conheco", "-5: Detesto", "-4: Acho muito ruim","-3: Acho bastante ruim",
			"-2: Acho ruim", "-1: Acho um pouco ruim","1 : Nao e ruim","2 : E bonzinho","3 : Bastante bom",
			"4 : Muito bom","5 : Incrivel. sensacional. impressionante"};
	private String estabelecimentosCadastrados[];
	private JComboBox listaSuspensaDeEstabelecimentos;
	private JTable tabelaResultado;
	private JScrollPane scrollPane;
	private JLabel selecioneEstabelecimento;
	private JLabel nomeUsuario;
	private JLabel notaEstabelecimento;
	private JTextField areaNomeUsuario;
	private JButton botaoVoltar;
	private JButton botaoAdicionar;
	private JButton botaoRemover;
	private JButton botaoGerarRecomendacao;
	private JButton botaoGravarUsuario;
	private JComboBox listaSuspensaNotas;
	private JRadioButton selectScalarProductAlgorithm;
	private JRadioButton selectPopularityAlgorithm;
	private JRadioButton selectCosineAlgorithm;
	private JRadioButton selectCossenoIntersecao;
	private JRadioButton selectSimilaridadeDice;
	private JRadioButton selectSimilaridadeJaccard;
	private JRadioButton selectSimilaridadeOverlap;
	private ButtonGroup selectAlgorithm;
	private JInternalFrame frameRecomendacoes;
	private JTable tabelaRecomendacoes;
	private JScrollPane scrollPaneRecomendacoes;
	private JTextField areaNumRecomendacoes;
	private JLabel numeroDeRecomendacoes;
	private JLabel iconNotificacaoRecomencadao;
	private JLabel iconNotificacaoNome;
	private ImageIcon imageOk;
	private ImageIcon imageErrado;
	private int recomendacao = 0;

	public CadastraUsuario(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaComponentes();

		//propriedades do frame interno
		frameRecomendacoes.setClosable(true);
		frameRecomendacoes.setTitle("Recomendacoes");
		frameRecomendacoes.setLocation(125, 200);

		//propriedade da tabela
		tabelaRecomendacoes.setEnabled(false);
		scrollPaneRecomendacoes.setViewportView(tabelaRecomendacoes);

		//add no frame interno
		frameRecomendacoes.getContentPane().add(scrollPaneRecomendacoes);

		iniciaComboBoxEstabelecimento();
		iniciaArrayNotasENomes();
		addNoContainer();
		

		//inicia tabelaResultado 
		tabelaResultado.setModel(new DefaultTableModel(new Object[][]{},
				new String[] { "Estabelecimento", "Nota" }));

		todosToolTipText();
		addNoContainer();
		trataEventoCampoTexto();
		addEventosComponentes();
		escondeFrameInterno();

	}
	
	private void instanciaComponentes(){
		
		//##LOCALIZACAO
		botaoComoChegar = new JButton("Como chegar?");
		
		//Algoritmos
		algoritmos = new Algoritmos();

		//scrollPanes
		scrollPane = new JScrollPane();
		scrollPaneRecomendacoes = new JScrollPane();

		//tabelas
		tabelaRecomendacoes = new JTable();
		tabelaResultado = new JTable();
		tabelaResultado.setEnabled(false);

		//RadionButton/ButonGroup
		selectScalarProductAlgorithm = new JRadioButton("Popularidade");
		selectPopularityAlgorithm = new JRadioButton("Produto Escalar");
		selectCosineAlgorithm = new JRadioButton("Cosseno");
		selectCossenoIntersecao = new JRadioButton("Cosseno Intersecao");
		selectSimilaridadeDice = new JRadioButton("Similaridade Dice");
		selectSimilaridadeJaccard = new JRadioButton("Similaridade Jaccard");
		selectSimilaridadeOverlap = new JRadioButton("Similaridade Overlap");
		selectAlgorithm = new ButtonGroup();
		selectAlgorithm.add(selectPopularityAlgorithm);
		selectAlgorithm.add(selectScalarProductAlgorithm);
		selectAlgorithm.add(selectCosineAlgorithm);
		selectAlgorithm.add(selectCossenoIntersecao);
		selectAlgorithm.add(selectSimilaridadeDice);
		selectAlgorithm.add(selectSimilaridadeJaccard);
		selectAlgorithm.add(selectSimilaridadeOverlap);

		//JLabels
		nomeUsuario = new JLabel("Nome:");
		selecioneEstabelecimento = new JLabel("Selecione o Estabelecimento:");
		notaEstabelecimento = new JLabel("Nota do estabelecimento:");	
		numeroDeRecomendacoes = new JLabel("Numero de Recomendacoes:");
		iconNotificacaoNome = new JLabel();
		iconNotificacaoRecomencadao = new JLabel();

		//carrega as imagens das notificacoes
		imageOk = new ImageIcon("./src/lp2/imagens/Ok.png");
		imageErrado = new ImageIcon("./src/lp2/imagens/Stop.png");

		//arrays/list
		estabelecimentosCadastrados = new String[ReadData.getEstabelecimentos().size()+1];
		notas = new ArrayList<String>();
		nomesEstabelecimentos = new ArrayList<String>();
		estabelecimentosAdicionados = new ArrayList<String>();

		//comboBox
		listaSuspensaNotas = new JComboBox(avaliacao);

		//botoes
		botaoVoltar = new JButton("Voltar");
		botaoGerarRecomendacao = new JButton("Gerar recomendacao");
		botaoRemover = new JButton("Remover");
		botaoAdicionar = new JButton("Adicionar");
		botaoGravarUsuario = new JButton("Gravar Usuario");

		//campo de texto
		areaNomeUsuario = new JTextField();
		areaNumRecomendacoes = new JTextField();

		scrollPane.setViewportView(tabelaResultado);

		//frameInterno
		frameRecomendacoes = new JInternalFrame();

	}
	
	private void iniciaComboBoxEstabelecimento(){
		int i = 1;
		estabelecimentosCadastrados[0] = ""; // so pra deixar vazio o primeiro campo, pra nao ficar como se ja tivesse um usuarios selecionado
		for(Estabelecimento estabelecimento : ReadData.getEstabelecimentos()){
			estabelecimentosCadastrados[i] = estabelecimento.getNome();
			i ++;
		}
		listaSuspensaDeEstabelecimentos = new JComboBox(estabelecimentosCadastrados);
	}

	private void addNoContainer(){

		//add no container do JPanel
		add(frameRecomendacoes, new AbsoluteConstraints(70,250,650,180));
		add(listaSuspensaDeEstabelecimentos, new AbsoluteConstraints(270,100,320,23));
		add(iconNotificacaoRecomencadao, new AbsoluteConstraints(365,450,40,40));
		add(iconNotificacaoNome, new AbsoluteConstraints(305,40,40,40));
		add(nomeUsuario, new AbsoluteConstraints(50,50,50,23));
		add(areaNomeUsuario, new AbsoluteConstraints(100,50,200,23));
		add(areaNumRecomendacoes, new AbsoluteConstraints(260,460,100,23));
		add(numeroDeRecomendacoes, new AbsoluteConstraints(50,460,210,23));
		add(notaEstabelecimento, new AbsoluteConstraints(50, 140, 200, 23));
		add(listaSuspensaNotas, new AbsoluteConstraints(270, 140, 320, 23));
		add(selectPopularityAlgorithm, new AbsoluteConstraints(50, 430, 180, 23));
		add(selectScalarProductAlgorithm, new AbsoluteConstraints(50, 400, 180, 23));
		add(selectCosineAlgorithm, new AbsoluteConstraints(240, 400, 180, 23));
		add(selectCossenoIntersecao, new AbsoluteConstraints(240, 430, 180, 23));
		add(selectSimilaridadeDice, new AbsoluteConstraints(430, 400, 180, 23));
		add(selectSimilaridadeJaccard, new AbsoluteConstraints(430, 430, 180, 23));
		add(selectSimilaridadeOverlap, new AbsoluteConstraints(620, 400, 180, 23));
		add(scrollPane, new AbsoluteConstraints(10,230,776,147));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		add(botaoGerarRecomendacao, new AbsoluteConstraints(50, 500, 190, 23));
		add(botaoGravarUsuario, new AbsoluteConstraints(320, 500, 160, 23));
		add(botaoAdicionar, new AbsoluteConstraints(50,180,120,23));
		add(botaoRemover, new AbsoluteConstraints(200,180,120,23));
		add(botaoComoChegar, new AbsoluteConstraints(600,100,120,23));
		add(selecioneEstabelecimento, new AbsoluteConstraints(50,100,210,23));

	}

	private void iniciaArrayNotasENomes(){
		for(int j=0; j<ReadData.getEstabelecimentos().size(); j++){
			nomesEstabelecimentos.add(ReadData.getEstabelecimentos().get(j).getNome());
			notas.add("0 : Nao conheco");
		}
	}
	
	private void escondeFrameInterno(){

		//so pra remover o frame interno quando clicar em outro local no panel
		MouseAdapter escondeFrameInterno = new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				frameRecomendacoes.setVisible(false);
			}
		};

		this.addMouseListener(escondeFrameInterno);
		tabelaResultado.addMouseListener(escondeFrameInterno);
		listaSuspensaDeEstabelecimentos.addMouseListener(escondeFrameInterno);
		scrollPane.addMouseListener(escondeFrameInterno);
		selecioneEstabelecimento.addMouseListener(escondeFrameInterno);
		nomeUsuario.addMouseListener(escondeFrameInterno);
		notaEstabelecimento.addMouseListener(escondeFrameInterno);
		areaNomeUsuario.addMouseListener(escondeFrameInterno);
		botaoVoltar.addMouseListener(escondeFrameInterno);
		botaoAdicionar.addMouseListener(escondeFrameInterno);
		botaoRemover.addMouseListener(escondeFrameInterno);
		botaoGravarUsuario.addMouseListener(escondeFrameInterno);
		listaSuspensaNotas.addMouseListener(escondeFrameInterno);
		selectScalarProductAlgorithm.addMouseListener(escondeFrameInterno);
		selectPopularityAlgorithm.addMouseListener(escondeFrameInterno);
		frameRecomendacoes.addMouseListener(escondeFrameInterno);
		scrollPaneRecomendacoes.addMouseListener(escondeFrameInterno);
		areaNumRecomendacoes.addMouseListener(escondeFrameInterno);
		numeroDeRecomendacoes.addMouseListener(escondeFrameInterno);
		iconNotificacaoRecomencadao.addMouseListener(escondeFrameInterno);
		iconNotificacaoNome.addMouseListener(escondeFrameInterno);

	}

	private void addEventosComponentes(){

		//add todos componentes para tratar eventos
		botaoAdicionar.addActionListener(this);
		botaoGerarRecomendacao.addActionListener(this);
		botaoGravarUsuario.addActionListener(this);
		selectPopularityAlgorithm.addActionListener(this);
		selectScalarProductAlgorithm.addActionListener(this);
		selectCosineAlgorithm.addActionListener(this);
		selectCossenoIntersecao.addActionListener(this);
		selectSimilaridadeDice.addActionListener(this);
		selectSimilaridadeJaccard.addActionListener(this);
		selectSimilaridadeOverlap.addActionListener(this);
		listaSuspensaDeEstabelecimentos.addActionListener(this);
		listaSuspensaNotas.addActionListener(this);
		botaoVoltar.addActionListener(this);
		botaoRemover.addActionListener(this);
		
		//alteracao Localizacao
		botaoComoChegar.addActionListener(this);

	}

	private void trataEventoCampoTexto(){
		areaNumRecomendacoes.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				//trata tipo de entrada,se eh apenas inteiro
				try{
					if(areaNumRecomendacoes == null ||  areaNumRecomendacoes.getText().equals("")){
						iconNotificacaoRecomencadao.setVisible(false);
						recomendacao = 0;
					}else{	
						iconNotificacaoRecomencadao.setVisible(true);
						recomendacao = Integer.parseInt(areaNumRecomendacoes.getText());
						if(recomendacao <= 0)
							iconNotificacaoRecomencadao.setIcon(imageErrado);
						else
							iconNotificacaoRecomencadao.setIcon(imageOk);
					}
				}catch(Exception ex){
					recomendacao = 0;
					iconNotificacaoRecomencadao.setIcon(imageErrado);
				}
			}
		});

		areaNomeUsuario.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				if (areaNomeUsuario.getText().equals("")) {
					iconNotificacaoNome.setVisible(false);
				} else {
					iconNotificacaoNome.setVisible(true);
					iconNotificacaoNome.setIcon(imageOk);
				}
			}
		});

	}

//	private void scalarProductRecomendations(Usuario usuario, int qtdRecomendacoes){
//		List<Estabelecimento> recomendacoes = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, usuario).get(0);
//		preencheTabela(recomendacoes);
//	}
//
	private void popularityRecomendations(int numRecomendacao){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendations(numRecomendacao).get(0);
		preencheTabela(recomendacoes);
	}
	
	private void executaAlgoritmo(TipoAlgoritmoPersonalizado tipo, Usuario user, int qtdRecomendacoes){
		List<Estabelecimento> recomendacoes = new ArrayList<Estabelecimento>();
		//List<Estabelecimento> naoRecomendados = new ArrayList<Estabelecimento>();
		if(tipo.equals(TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user);
			recomendacoes = resultados.get(0);
			//naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.COSSENO)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.COSSENO, user);
			recomendacoes = resultados.get(0);
			//naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, user);
			recomendacoes = resultados.get(0);
			//naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, user);
			recomendacoes = resultados.get(0);
			//naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, user);
			recomendacoes = resultados.get(0);
			//naoRecomendados = resultados.get(1);
		}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP)){
			List<List<Estabelecimento>> resultados = algoritmos.executeAlgoritmo(qtdRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, user);
			recomendacoes = resultados.get(0);
			//naoRecomendados = resultados.get(1);
		}
		preencheTabela(recomendacoes);
		//preencheTabela2(naoRecomendados);
	}

	private void preencheTabela(List<Estabelecimento> recomendacoes) {
		Object obj[][] = new Object[recomendacoes.size()][3];
		for(int i=0; i < recomendacoes.size(); i++){
			obj[i][0] = recomendacoes.get(i).getNome();
			obj[i][1] = recomendacoes.get(i).getLocalizacao();
			obj[i][2] = recomendacoes.get(i).getTipoDeComida();
		}
		tabelaRecomendacoes.setModel(new DefaultTableModel(obj,
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" }));
		
		//seta o tamanho das colunas
		tabelaRecomendacoes.getColumnModel().getColumn(2).setPreferredWidth(20);	
		tabelaRecomendacoes.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaRecomendacoes.getColumnModel().getColumn(0).setPreferredWidth(130);
	}
	private void preencheTabelaNotas(List<String> estabelecimento, List<String> notas){
		Object table[][] = new Object[estabelecimento.size()][2];
		for (int i = 0; i < estabelecimento.size(); i++) {
			table[i][0] = estabelecimento.get(i);
			table[i][1] = notas.get(nomesEstabelecimentos.indexOf(estabelecimento.get(i)));
		}
		tabelaResultado.setModel(new DefaultTableModel(table,
				new String[] { "Estabelecimento", "Nota" }));
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent event) {
		//LOCALIZACAO
		if(event.getSource() == botaoComoChegar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new VerLocalizacao());
			MenuInicial.panelCorpo.updateUI();
		}
		//evento do botaoAdicionar
		if(event.getSource() == botaoAdicionar){
			frameRecomendacoes.setVisible(false);
			if(indiceEstabelecimento == -1 || indiceNotas == 0){
				JOptionPane.showMessageDialog(null, "Escolha Estabelecimento/Nota","Error",JOptionPane.ERROR_MESSAGE);
			} else {
				nomeEstabelecimento = ReadData.getEstabelecimentos().get(indiceEstabelecimento).getNome();
				if(!(estabelecimentosAdicionados.contains(nomeEstabelecimento))){
					estabelecimentosAdicionados.add(nomeEstabelecimento);
					notas.set(nomesEstabelecimentos.indexOf(nomeEstabelecimento), avaliacao[indiceNotas]);
				}else if(JOptionPane.showConfirmDialog(null, "Estabelecimento ja cadastrado. Deseja atualizar a nota?", "*_*", JOptionPane.YES_NO_OPTION) == 0){
					notas.set(nomesEstabelecimentos.indexOf(nomeEstabelecimento), avaliacao[indiceNotas]);
				}
				//atualiza tabela estabelecimento/nota
				preencheTabelaNotas(estabelecimentosAdicionados, notas);
			}
			
		//evento do botaoRemover	
		}if(event.getSource() == botaoRemover){
			frameRecomendacoes.setVisible(false);
			if(indiceEstabelecimento == -1 || estabelecimentosAdicionados.size() == 0){
				JOptionPane.showMessageDialog(null, "Escolha Estabelecimento/Nao Adicionado","Error",JOptionPane.ERROR_MESSAGE);
			}else{
				nomeEstabelecimento = ReadData.getEstabelecimentos().get(indiceEstabelecimento).getNome();
				if(!(estabelecimentosAdicionados.contains(nomeEstabelecimento))){
					JOptionPane.showMessageDialog(null, "Escolha Estabelecimento/Nao Adicionado","Error",JOptionPane.ERROR_MESSAGE);
				}else{
					estabelecimentosAdicionados.remove(nomeEstabelecimento);
					//para o estabelecimento removido dar nota 0
					notas.set(nomesEstabelecimentos.indexOf(nomeEstabelecimento), "0 : Nao conheco");
					//atualiza tabela
					preencheTabelaNotas(estabelecimentosAdicionados, notas);
				}
			}
		//Evento listaSuspensaDeEstabeleecimentos
		}if(event.getSource() == listaSuspensaDeEstabelecimentos){
			frameRecomendacoes.setVisible(false);
			indiceEstabelecimento = listaSuspensaDeEstabelecimentos.getSelectedIndex()-1;

		//evento da listaSuspensa de Notas
		}if(event.getSource() == listaSuspensaNotas){
			frameRecomendacoes.setVisible(false);
			indiceNotas = listaSuspensaNotas.getSelectedIndex();

		//evento do botao seleciona Algoritmo Popular
		}if(event.getSource() == selectPopularityAlgorithm){
			frameRecomendacoes.setVisible(false);
			trocaAlgoritmo(1);
		}if(event.getSource() == selectScalarProductAlgorithm){
			frameRecomendacoes.setVisible(false);
			trocaAlgoritmo(2);
		}if(event.getSource() == selectCosineAlgorithm){
			frameRecomendacoes.setVisible(false);
			trocaAlgoritmo(3);
		}if(event.getSource() == selectCossenoIntersecao){
			frameRecomendacoes.setVisible(false);
			trocaAlgoritmo(4);
		}if(event.getSource() == selectSimilaridadeDice){
			frameRecomendacoes.setVisible(false);
			trocaAlgoritmo(5);
		}if(event.getSource() == selectSimilaridadeJaccard){
			frameRecomendacoes.setVisible(false);
			trocaAlgoritmo(6);
		}if(event.getSource() == selectSimilaridadeOverlap){
			frameRecomendacoes.setVisible(false);
			trocaAlgoritmo(7);
		}
		
		if(event.getSource() == botaoGravarUsuario){
			frameRecomendacoes.setVisible(false);
			String nome = areaNomeUsuario.getText();
			if(nome.replace(" ", "").equals(""))
				nome = "";
			Date data = new Date();
			String armazenaUser = "";
			armazenaUser += data.toLocaleString() + ";";
			armazenaUser += nome + ";";
			
			StringBuffer buf = new StringBuffer();
			for(String nota : notas)
				buf.append(nota + ";");
			armazenaUser += buf.toString().substring(0, buf.toString().length()-1);
			
			armazenaUser = "\n"+armazenaUser;
			BufferedWriter buffer = null;
			try {
				buffer = new BufferedWriter(new FileWriter(MenuInicial.pathOpinioes, true));
				buffer.append(armazenaUser);
				buffer.close();
				JOptionPane.showMessageDialog(null, "Usuario gravado com sucesso", "Informacao", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Erro ao gravar Usuario", "Erro", JOptionPane.ERROR_MESSAGE);
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			} finally {
				try {
					ReadData.initLists();
				} catch (Exception e) {
					e.printStackTrace();
				}

				areaNomeUsuario.setText("");
				iconNotificacaoRecomencadao.setVisible(false);
				iconNotificacaoNome.setVisible(false);
				frameRecomendacoes.setVisible(false);
				listaSuspensaDeEstabelecimentos.setSelectedIndex(0);
				listaSuspensaNotas.setSelectedIndex(0);
				selectAlgorithm.clearSelection();
				areaNumRecomendacoes.setText("");
				recomendacao = 0;
				selectAlgorithm.setSelected(null, false);
				estabelecimentosAdicionados.clear();
				tabelaResultado.setModel(new DefaultTableModel(new Object[][]{},
						new String[] { "Estabelecimento", "Nota" }));
			}

		}if(event.getSource() == botaoGerarRecomendacao && !ReadData.getEstabelecimentos().isEmpty() && !ReadData.getUsuarios().isEmpty()){
			List<Integer> opinioes = new ArrayList<Integer>();
			for(String nota : notas){
				if(nota.subSequence(0, 1).equals("-")){
					opinioes.add(Integer.parseInt(nota.substring(0, 2)));
				}else{
					opinioes.add(Integer.parseInt(nota.substring(0, 1)));
				}
			}

			Usuario usuario = null;
			try {
				String nome = areaNomeUsuario.getText();
				if(nome == null || nome.equals(""))
					nome = "anonimo";
				usuario = new Usuario(nome, opinioes);

				if (boolalgoritmoTipo1 && recomendacao > 0){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, usuario, recomendacao);
					//scalarProductRecomendations(usuario, recomendacao);
				} else if (boolalgoritmoTipo2 && recomendacao > 0){
					frameRecomendacoes.setVisible(true);
					popularityRecomendations(recomendacao);
				}else if(boolalgoritmoTipo3 && recomendacao > 0){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.COSSENO, usuario, recomendacao);
				}else if(boolalgoritmoTipo4 && recomendacao > 0){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, usuario, recomendacao);
				}else if(boolalgoritmoTipo5 && recomendacao > 0){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, usuario, recomendacao);
				}else if(boolalgoritmoTipo6 && recomendacao > 0){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, usuario, recomendacao);
				}else if(boolalgoritmoTipo7 && recomendacao > 0){
					frameRecomendacoes.setVisible(true);
					executaAlgoritmo(TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, usuario, recomendacao);
				} else {
					JOptionPane.showMessageDialog(null, "Erro em recomendacoes/algoritmo", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}


			//evento do botao gerar recomendacoes quando as lista de estabelecimentos ou opinioes estao vazias.
		}if(event.getSource() == botaoGerarRecomendacao && (ReadData.getEstabelecimentos().isEmpty() || ReadData.getUsuarios().isEmpty())){
			if (temAlgoritmoSelecionado() && recomendacao > 0){
				JOptionPane.showMessageDialog(null, "Arquivo de Opinioes/Estabelecimentos vazio(s). Impossivel gerar recomendacoes.", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Erro em recomendacoes/algoritmo", "Erro", JOptionPane.ERROR_MESSAGE);
			}


		//evento do botao voltar.
		}if(event.getSource() == botaoVoltar){
			frameRecomendacoes.setVisible(false);
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new MenuPrincipal());
			MenuInicial.panelCorpo.updateUI();
		}
	}

	private void todosToolTipText(){

		listaSuspensaDeEstabelecimentos.setToolTipText("Nome de todos os estabelecimentos");
		listaSuspensaNotas.setToolTipText("Lista de notas para o estabelecimento");
		botaoAdicionar.setToolTipText("Adiciona uma opiniao na tabela");
		botaoVoltar.setToolTipText("Clique para voltar ao menu anterior");
		botaoGerarRecomendacao.setToolTipText("Clique para gerar uma recomendacao");
		botaoGravarUsuario.setToolTipText("Grava um usuario no arquivo");
		botaoRemover.setToolTipText("Remove opiniao da tabela");
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

}
