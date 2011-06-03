package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import lp2.algoritmos.Algoritmos;
import lp2.lerDados.Estabelecimento;
import lp2.lerDados.ReadData;

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
	private String tipoDeOrdenacao[];
	private JLabel selecioneOrdenacao;
	private static String ordenacaoSelecionada;
	private static JComboBox tiposDeComida;
	private JComboBox listaOrdenacao;
	private static String tipoDeComidaSelecionada;


	private static Algoritmos algoritmos;
	private boolean boolalgoritmoTipo1 = false;
	private boolean boolalgoritmoTipo2 = false;
	private boolean boolalgoritmoTipo3 = false;
	private String usuariosCadastrados[];
	private JLabel usuario ;
	private JLabel algoritmoEscolhido ;
	private JLabel numRecomendacoes;
	private JTextField campoTextoRecomendacoes;
	private ButtonGroup selectAlgorithm ;
	private JRadioButton selectScalarProductAlgorithm;
	private JRadioButton selectPopularityAlgorithm;
	private JRadioButton selectCossineAlgorithm;
	private static JTable tabela;
	private JLabel iconNotificacaoRecomencadao;
	private JInternalFrame frameRecomendacoes;
	private JComboBox listaSuspensaDeUsuarios;
	private JButton botaoGerarRecomendacao ;
	private JButton botaoVoltar;
	private JScrollPane scrollPane;
	private ImageIcon imageOk ;
	private ImageIcon imageErrado ;

	//***
	private JLabel filtro;
	private JLabel palavraChave;
	private String filtros[] = {"", "palavra-chave", "tipo estabelecimento"};
	private JTextField campoTextoPalavraChave;
	private JComboBox listaSuspensaDeFiltros = new JComboBox(filtros);
	private JLabel tipo;
	private String tipos[] = {"", "A la carte", "Self-service", "Prato feito"};
	private JComboBox listaSuspensaDeTiposEstabelecimentos = new JComboBox(tipos);

	boolean tipoFiltroPalavraChave = false;
	boolean tipoFiltroEstabelecimento= false;

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
		frameRecomendacoes.setLocation(125, 200);

		//add RadioButton no buttonGroup
		selectAlgorithm.add(selectPopularityAlgorithm);
		selectAlgorithm.add(selectScalarProductAlgorithm);
		selectAlgorithm.add(selectCossineAlgorithm);


		//####
		tiposDeComida.setVisible(false);

		//propriedades da tabela
		tabela.setEnabled(false);
		scrollPane.setViewportView(tabela);

		//add scroolpane( tabela ), no frameInterno
		frameRecomendacoes.getContentPane().add(scrollPane, new AbsoluteConstraints(0,90,650,180));
		frameRecomendacoes.getContentPane().add(selecioneOrdenacao, new AbsoluteConstraints(10,10));
		frameRecomendacoes.getContentPane().add(listaOrdenacao, new AbsoluteConstraints(10,25));
		frameRecomendacoes.getContentPane().add(tiposDeComida, new AbsoluteConstraints(140,25));

		//		add(selecioneOrdenacao, new AbsoluteConstraints(500,200,140,23));
		//		add(listaOrdenacao, new AbsoluteConstraints(500,230,150,23));
		//		add(tiposDeComida, new AbsoluteConstraints(650,230));
		todosToolTipText();
		addNoContainer();

		//definindo um modelo para a tabela, para que ela nao inicie vazia
		tabela.setModel(new DefaultTableModel(new Object[][]{},
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" }));

		addEventosComponentes();

	}
	private void instanciaTodosComponentes(){
		algoritmos = new Algoritmos();		
		usuariosCadastrados = new String[ReadData.getUsuarios().size()+1];

		//***
		filtro = new JLabel("Filtrar : ");
		palavraChave = new JLabel("Palavra-chave: ");
		campoTextoPalavraChave = new JTextField(20);
		tipo = new JLabel("Tipo: ");


		//##########
		tipoDeOrdenacao = new String[]{"","Ordem Alfabetica","Tipo de Refeicao"};
		String[] tipoDeRefeicoes = {"","A la carte", "Prato feito", "Self-service"};
		selecioneOrdenacao = new JLabel("Tipo de Ordenacao:");

		//ComboBox ######
		listaOrdenacao = new JComboBox(tipoDeOrdenacao);
		tiposDeComida = new JComboBox(tipoDeRefeicoes);

		//Labels
		usuario = new JLabel("Escolha usuario:");
		algoritmoEscolhido = new JLabel("Escolha algoritmo:");
		numRecomendacoes = new JLabel("Qual o numero de recomendacoes?");
		iconNotificacaoRecomencadao = new JLabel();

		//Campo texto
		campoTextoRecomendacoes = new JTextField(20);

		//Button
		selectAlgorithm = new ButtonGroup();		
		selectScalarProductAlgorithm = new JRadioButton("Algoritmo Personalizado");
		selectPopularityAlgorithm = new JRadioButton("Algoritmo Popularidade");
		selectCossineAlgorithm = new JRadioButton("Algoritmo Cosseno");
		botaoVoltar = new JButton("Voltar");
		botaoGerarRecomendacao = new JButton("Gerar recomendacao");

		//Carrega as imagens
		imageOk = new ImageIcon("./src/lp2/imagens/Ok.png");
		imageErrado = new ImageIcon("./src/lp2/imagens/Stop.png");

		tabela = new JTable();
		scrollPane = new JScrollPane();
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
		selectCossineAlgorithm.addActionListener(this);
		selectScalarProductAlgorithm.addActionListener(this);
		botaoGerarRecomendacao.addActionListener(this);
		botaoVoltar.addActionListener(this);

		//####
		listaOrdenacao.addActionListener(this);
		tiposDeComida.addActionListener(this);
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
						if(recomendacao <= 0 || recomendacao > 38)  ///**** Numero magico aqui eh pra ser a qtd d restaurantes maxima permitida =x
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

	}

	private void addNoContainer(){
		add(frameRecomendacoes, new AbsoluteConstraints(70,250));
		add(usuario, new AbsoluteConstraints(50,50,120,23));
		add(listaSuspensaDeUsuarios, new AbsoluteConstraints(180,50,250,23));
		add(algoritmoEscolhido,  new AbsoluteConstraints(50, 90, 140, 23));
		add(selectPopularityAlgorithm, new AbsoluteConstraints(200, 90, 200, 23));
		add(selectScalarProductAlgorithm, new AbsoluteConstraints(200, 120, 200, 23));
		add(selectCossineAlgorithm, new AbsoluteConstraints(410, 90, 200, 23));
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

		add(palavraChave, new AbsoluteConstraints(50, 250, 250, 23));
		add(campoTextoPalavraChave, new AbsoluteConstraints(180,250,250,23));
		add(listaSuspensaDeFiltros, new AbsoluteConstraints(180, 200, 150, 23));
		add(tipo, new AbsoluteConstraints(50, 300, 250, 23));
		add(listaSuspensaDeTiposEstabelecimentos, new AbsoluteConstraints(180, 300, 150, 23));

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

	private static void scalarProductRecomendations(int numberUser, int qtdRecomendacoes){
		List<Estabelecimento> recomendacoes = algoritmos.executeScalarProductRecomendations(qtdRecomendacoes, ReadData.getUsuarios().get(numberUser-1));
		preencheTabela(recomendacoes);
	}

	private static void popularityRecomendations(int numRecomendacao){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendations(numRecomendacao);
		preencheTabela(recomendacoes);
	}
	
	private void cossineRecomendations(int numberUser, int qtdRecomendacoes) {
		List<Estabelecimento> recomendacoes = algoritmos.executeCossineRecomendations(qtdRecomendacoes, ReadData.getUsuarios().get(numberUser-1));
		preencheTabela(recomendacoes);
	}

	private static void popularityRecomendationsOrderly(int numRecomendacao){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendations(numRecomendacao);
		preencheTabelaOrdenadas(recomendacoes, ordenacaoSelecionada);
	}

	private static void scalarProductRecomendationsOrderly(int numberUser, int qtdRecomendacoes){
		List<Estabelecimento> recomendacoes = algoritmos.executeScalarProductRecomendations(qtdRecomendacoes, ReadData.getUsuarios().get(numberUser-1));
		preencheTabelaOrdenadas(recomendacoes, ordenacaoSelecionada);
	}


	private static void preencheTabela(List<Estabelecimento> recomendacoes) {
		Object obj[][] = new Object[recomendacoes.size()][3];
		for(int i=0; i < recomendacoes.size(); i++){
			obj[i][0] = recomendacoes.get(i).getNome();
			obj[i][1] = recomendacoes.get(i).getLocalizacao();
			obj[i][2] = recomendacoes.get(i).getTipoDeComida();
		}

		tabela.setModel(new DefaultTableModel(obj,
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" }));

		//seta o tamanho das colunas
		tabela.getColumnModel().getColumn(2).setPreferredWidth(20);	
		tabela.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(130);
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
		tabela.setModel(new DefaultTableModel(obj,
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" }));

		//seta o tamanho das colunas
		tabela.getColumnModel().getColumn(2).setPreferredWidth(20);	
		tabela.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabela.getColumnModel().getColumn(0).setPreferredWidth(130);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == tiposDeComida){
			tipoDeComidaSelecionada = tiposDeComida.getSelectedItem().toString();
			popularityRecomendationsOrderly(recomendacao);
			//System.out.println(tipoDeComidaSelecionada);
		}
		//evento do botao listaSuspensaDeUsuarios
		if(event.getSource() == listaSuspensaDeUsuarios){
			numUsuario = listaSuspensaDeUsuarios.getSelectedIndex();

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
				if(boolalgoritmoTipo1)
					scalarProductRecomendations(numUsuario, recomendacao);
				if(boolalgoritmoTipo2)
					popularityRecomendations(recomendacao);
			}
		}
			
			if(event.getSource() == listaSuspensaDeFiltros){
				if(listaSuspensaDeFiltros.getSelectedItem().toString().equals("palavra-chave")){
					tipoFiltroPalavraChave = true;
					
					
					//***"A la carte", "Self-service", "Prato feito"
				}if(listaSuspensaDeFiltros.getSelectedItem().toString().equals("tipo estabelecimento")){
					tipoFiltroEstabelecimento = true;
//					if(listaSuspensaDeTiposEstabelecimentos.toString().equals("A la carte")){
//						popularityRecomendationsType(recomendacao, "A la carte");		
//					}if(listaSuspensaDeTiposEstabelecimentos.toString().equals("Self-service")){
//						popularityRecomendationsType(recomendacao, "Self-service");
//					}if(listaSuspensaDeTiposEstabelecimentos.toString().equals("Prato feito")){
//						popularityRecomendationsType(recomendacao, "Prato feito");
//					}
//					
//				}if(listaSuspensaDeFiltros.getSelectedItem().toString().equals("tipo estabelecimento") && boolalgoritmoTipo1){
//					if(listaSuspensaDeTiposEstabelecimentos.toString().equals("A la carte")){
//						scalarProductRecomendationsType(numUsuario, recomendacao, "A la carte");		
//					}if(listaSuspensaDeTiposEstabelecimentos.toString().equals("Self-service")){
//						scalarProductRecomendationsType(numUsuario,recomendacao, "Self-service");
//					}if(listaSuspensaDeTiposEstabelecimentos.toString().equals("Prato feito")){
//						scalarProductRecomendationsType(numUsuario,recomendacao, "Prato feito");
//					}
				}
				
			}
			
			//evento do botao seleciona Algoritmo Popular
		if(event.getSource() == selectPopularityAlgorithm){
			boolalgoritmoTipo1 = false;
			boolalgoritmoTipo2 = true;
			boolalgoritmoTipo3 = false;

			//evento do botao seleciona Algoritmo Produto Escalar
		}if(event.getSource() == selectScalarProductAlgorithm){
			boolalgoritmoTipo1 = true;
			boolalgoritmoTipo2 = false;
			boolalgoritmoTipo3 = false;
		
		}if(event.getSource() == selectCossineAlgorithm){
			boolalgoritmoTipo1 = false;
			boolalgoritmoTipo2 = false;
			boolalgoritmoTipo3 = true;
			
			//evento do botao Gerar Recomendacao
		}if(event.getSource() == botaoGerarRecomendacao && !ReadData.getUsuarios().isEmpty() && !ReadData.getEstabelecimentos().isEmpty()){
			if(numUsuario > 0 && recomendacao > 0){ //tratando o caso de o cara nao ter selecionado nenhum usuario

				if(boolalgoritmoTipo1){
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
						
					}else{
						frameRecomendacoes.setVisible(true);
						scalarProductRecomendations(numUsuario, recomendacao);
					}
				}
				if(boolalgoritmoTipo2){
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
					}else{
						frameRecomendacoes.setVisible(true);
						popularityRecomendations(recomendacao);
					}
				}
				if(boolalgoritmoTipo3){
					
					//MAIS UM ALGORITMOS PRA POR O FILTRO
					frameRecomendacoes.setVisible(true);
					cossineRecomendations(numUsuario, recomendacao);
				}
			}if(numUsuario == 0 || recomendacao <= 0 || (!(boolalgoritmoTipo1) && !(boolalgoritmoTipo2) && !(boolalgoritmoTipo3))){
				JOptionPane.showMessageDialog(null, "\"Usuario/Algoritmo/Numero de recomendacoes\" invalido.");
			}
		}if(event.getSource() == botaoGerarRecomendacao && (ReadData.getUsuarios().isEmpty() || ReadData.getEstabelecimentos().isEmpty())){
			if ((boolalgoritmoTipo1 || boolalgoritmoTipo2) && recomendacao > 0 && numUsuario > 0){
				JOptionPane.showMessageDialog(null, "Arquivo de Opinioes/Estabelecimentos vazio(s). Impossivel gerar recomendacoes.", "Error", JOptionPane.ERROR_MESSAGE);
			} else if(numUsuario == 0 || recomendacao <= 0 || (!(boolalgoritmoTipo1) && !(boolalgoritmoTipo2) && !(boolalgoritmoTipo3))){
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


}
