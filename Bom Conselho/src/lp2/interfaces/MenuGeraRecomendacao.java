package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	private static Algoritmos algoritmos;
	private boolean boolalgoritmoTipo1 = false;
	private boolean boolalgoritmoTipo2 = false;
	private String usuariosCadastrados[];
	private JLabel usuario ;
	private JLabel algoritmoEscolhido ;
	private JLabel numRecomendacoes;
	private JTextField campoTextoRecomendacoes;
	private ButtonGroup selectAlgorithm ;
	private JRadioButton selectScalarProductAlgorithm;
	private JRadioButton selectPopularityAlgorithm ;
	private static JTable tabela;
	private JLabel iconNotificacaoRecomencadao;
	private JInternalFrame frameRecomendacoes;
	private JComboBox listaSuspensaDeUsuarios;
	private JButton botaoGerarRecomendacao ;
	private JButton botaoVoltar;
	private JScrollPane scrollPane;
	private ImageIcon imageOk ;
	private ImageIcon imageErrado ;

	public MenuGeraRecomendacao(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaTodosComponentes();
		iniciaComboBoxUsuarios();

		//seta propriedades do internalFrame
		frameRecomendacoes.setClosable(true);
		frameRecomendacoes.setTitle("Recomendacoes");
		frameRecomendacoes.setLocation(125, 200);

		//add RadioButton no buttonGroup
		selectAlgorithm.add(selectPopularityAlgorithm);
		selectAlgorithm.add(selectScalarProductAlgorithm);
		
		//propriedades da tabela
		tabela.setEnabled(false);
		scrollPane.setViewportView(tabela);
		
		//add scroolpane( tabela ), no frameInterno
		frameRecomendacoes.getContentPane().add(scrollPane);

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
		selectScalarProductAlgorithm.addActionListener(this);
		botaoGerarRecomendacao.addActionListener(this);
		botaoVoltar.addActionListener(this);
		eventoCampoTexto();
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
						if(recomendacao < 0)
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
		add(frameRecomendacoes, new AbsoluteConstraints(70,250,650,180));
		add(usuario, new AbsoluteConstraints(50,50,120,23));
		add(listaSuspensaDeUsuarios, new AbsoluteConstraints(180,50,250,23));
		add(algoritmoEscolhido,  new AbsoluteConstraints(50, 90, 140, 23));
		add(selectPopularityAlgorithm, new AbsoluteConstraints(200, 90, 200, 23));
		add(selectScalarProductAlgorithm, new AbsoluteConstraints(200, 120, 200, 23));
		add(numRecomendacoes, new AbsoluteConstraints(50, 150, 250, 23));
		add(campoTextoRecomendacoes, new AbsoluteConstraints(310, 150, 150, 23));
		add(botaoGerarRecomendacao, new AbsoluteConstraints(50, 500, 200, 23));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		add(iconNotificacaoRecomencadao, new AbsoluteConstraints(465,140,40,40));

	}

	private static void scalarProductRecomendations(int numberUser, int qtdRecomendacoes){
		List<Estabelecimento> recomendacoes = algoritmos.executeScalarProductRecomendations(qtdRecomendacoes, ReadData.getUsuarios().get(numberUser-1));
		preencheTabela(recomendacoes);
	}

	private static void popularityRecomendations(int numRecomendacao){
		List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendations(numRecomendacao);
		preencheTabela(recomendacoes);
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

	@Override
	public void actionPerformed(ActionEvent event) {
		//evento do botao listaSuspensaDeUsuarios
		if(event.getSource() == listaSuspensaDeUsuarios){
			numUsuario = listaSuspensaDeUsuarios.getSelectedIndex();

			//evento do botao seleciona Algoritmo Popular
		}if(event.getSource() == selectPopularityAlgorithm){
			boolalgoritmoTipo1 = false;
			boolalgoritmoTipo2 = true;

			//evento do botao seleciona Algoritmo Produto Escalar
		}if(event.getSource() == selectScalarProductAlgorithm){
			boolalgoritmoTipo1 = true;
			boolalgoritmoTipo2 = false;

			//evento do botao Gerar Recomendacao
		}if(event.getSource() == botaoGerarRecomendacao && !ReadData.getUsuarios().isEmpty() && !ReadData.getEstabelecimentos().isEmpty()){
			if(numUsuario > 0 && recomendacao > 0){ //tratando o caso de o cara nao ter selecionado nenhum usuario

				if(boolalgoritmoTipo1){
					frameRecomendacoes.setVisible(true);
					scalarProductRecomendations(numUsuario, recomendacao);
				}
				if(boolalgoritmoTipo2){
					frameRecomendacoes.setVisible(true);
					popularityRecomendations(recomendacao);
				}
			}if(numUsuario == 0 || recomendacao <= 0 || (!(boolalgoritmoTipo1) && !(boolalgoritmoTipo2))){
				JOptionPane.showMessageDialog(null, "\"Usuario/Algoritmo/Numero de recomendacoes\" invalido.");
			}
		}if(event.getSource() == botaoGerarRecomendacao && (ReadData.getUsuarios().isEmpty() || ReadData.getEstabelecimentos().isEmpty())){
			if ((boolalgoritmoTipo1 || boolalgoritmoTipo2) && recomendacao > 0 && numUsuario > 0){
				JOptionPane.showMessageDialog(null, "Arquivo de Opinioes/Estabelecimentos vazio(s). Impossivel gerar recomendacoes.", "Error", JOptionPane.ERROR_MESSAGE);
			} else if(numUsuario == 0 || recomendacao <= 0 || (!(boolalgoritmoTipo1) && !(boolalgoritmoTipo2))){
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

	}


}
