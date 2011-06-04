package lp2.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.table.DefaultTableModel;

import lp2.algoritmos.Algoritmos;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

@SuppressWarnings("serial")
public class ComparaAlgoritmos extends JPanel implements ActionListener{

	private JWindow window = new JWindow();
	private JProgressBar barra = new JProgressBar();
	
	JanelaAguarde esperePorFavor = new JanelaAguarde();
	Thread executaJanelaAguarde;
	
	ComparaAlgoritmosAqui processaAlgoritmo = new ComparaAlgoritmosAqui();
	Thread executaProcessaAlgoritmo;
	
	
	private static Algoritmos algoritmos;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane2;
	private JButton botaoVoltar;
	private JButton botaoComparar;
	private JTable tabelaComparacoesPorUsuario;
	private JTable tabelaComparacoesFinais;
	private JLabel comparacoesPorUsuario;
	private JLabel comparacoesFinais;
	private JLabel numRecomendacoes;
	private JLabel iconNotificacao;
	private JTextField areaRecomendacoes;
	private ImageIcon imageOk;
	private ImageIcon imageErrado;
	private int numRecomend = 0;

	public ComparaAlgoritmos(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));		

		instanciaTodosComponentes();

		//desabilita edicao da tabela
		tabelaComparacoesPorUsuario.setEnabled(false);
		tabelaComparacoesFinais.setEnabled(false);
		//executa algoritmo de popularidade
		//popularityRecomendations();

		addNoContainer();
		trataEventoCampoTexto();

		//seta o tamanho/altura da barra
		barra.setPreferredSize(new Dimension(407,20));
		barra.setStringPainted(true);

		//carrega uma imagem no label imagem
		JLabel imagem = new JLabel();
		ImageIcon image = new ImageIcon("./src/lp2/imagens/apresentacao.jpg");
		imagem.setIcon(image);

		//define layout da janela
		window.getContentPane().setLayout(new BorderLayout());
		//add no container da janela a imagem e barra.
		window.getContentPane().add(imagem,BorderLayout.CENTER);
		window.getContentPane().add(barra,BorderLayout.SOUTH);
		
		//texto quando mouse fica parado sobre o botao
		botaoVoltar.setToolTipText("Clique para voltar ao menu anterior");
		botaoComparar.setToolTipText("Clique para comparar os algoritmos");
		//add botoes ao tratamento da classe interna
		botaoVoltar.addActionListener(this);
		botaoComparar.addActionListener(this);

		//esperePorFavor();
	}

	private void trataEventoCampoTexto(){
		areaRecomendacoes.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				try{
					if(areaRecomendacoes == null ||  areaRecomendacoes.getText().equals("")){
						iconNotificacao.setVisible(false);
						numRecomend = 0;
					}else{	
						iconNotificacao.setVisible(true);
						numRecomend = Integer.parseInt(areaRecomendacoes.getText());
						if(numRecomend <= 0)
							iconNotificacao.setIcon(imageErrado);
						else
							iconNotificacao.setIcon(imageOk);
					}
				}catch(Exception ex){
					numRecomend = 0;
					iconNotificacao.setIcon(imageErrado);
				}
			}
		});
	}

	private void instanciaTodosComponentes(){
		algoritmos = new Algoritmos();
		tabelaComparacoesPorUsuario = new JTable();
		tabelaComparacoesFinais = new JTable();
		botaoVoltar = new JButton("Voltar");
		botaoComparar = new JButton("Comparar");
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tabelaComparacoesPorUsuario);
		scrollPane2 = new JScrollPane();
		scrollPane2.setViewportView(tabelaComparacoesFinais);
		comparacoesPorUsuario = new JLabel("Comparacoes por Usuario");
		comparacoesFinais = new JLabel("Comparacoes Finais");
		numRecomendacoes = new JLabel("Numero de recomendacoes:");
		iconNotificacao = new JLabel();
		areaRecomendacoes = new JTextField();
		imageOk = new ImageIcon("./src/lp2/imagens/Ok.png");
		imageErrado = new ImageIcon("./src/lp2/imagens/Stop.png");

	}

	private void addNoContainer(){
		add(scrollPane, new AbsoluteConstraints(10,90,776,330));
		add(scrollPane2, new AbsoluteConstraints(10,450,776,45));

		add(comparacoesPorUsuario, new AbsoluteConstraints(310,60,200,23));
		add(comparacoesFinais, new AbsoluteConstraints(310,420,200,23));

		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));

		add(numRecomendacoes, new AbsoluteConstraints(10, 30, 200, 23));
		add(areaRecomendacoes, new AbsoluteConstraints(210, 30, 100, 23));
		add(iconNotificacao, new AbsoluteConstraints(320, 20, 40, 40));
		add(botaoComparar, new AbsoluteConstraints(400, 30, 100, 23));
	}

	private void preencheTabelaComparacoesUsuarios(List<List<String>> comparacoesUsuarios) {
		Object obj[][] = new Object[comparacoesUsuarios.size()][9];
		for(int i=0; i<comparacoesUsuarios.size(); i++){
			obj[i][0] = comparacoesUsuarios.get(i).get(0);
			obj[i][1] = comparacoesUsuarios.get(i).get(1);
			obj[i][2] = comparacoesUsuarios.get(i).get(2);
			obj[i][3] = comparacoesUsuarios.get(i).get(3);
			obj[i][4] = comparacoesUsuarios.get(i).get(4);
			obj[i][5] = comparacoesUsuarios.get(i).get(5);
			obj[i][6] = comparacoesUsuarios.get(i).get(6);
			obj[i][7] = comparacoesUsuarios.get(i).get(7);
			obj[i][8] = comparacoesUsuarios.get(i).get(8);
		}

		tabelaComparacoesPorUsuario.setModel(new DefaultTableModel(obj,
				new String[] { "Nome", "Opinioes Positivas", "Opinioes Cadastradas", "Acertos Generico", "Acertos Personalizado", "Acertos Cosseno", "Porcentagem Generico", "Porcentagem Personalizado", "Porcentagem Cosseno" }));
	}

	private void preencheTabelaComparacoesFinais(List<String> comparacoesFinais){
		Object obj[][] = new Object[1][7];
		for(int i=0; i<comparacoesFinais.size(); i++){
			obj[0][i] = comparacoesFinais.get(i);
		}
		tabelaComparacoesFinais.setModel(new DefaultTableModel(obj,
				new String[] { "Opinioes Positivas do Sistema", "Acertos Generico", "Acertos Personalizado", "Acertos Cosseno", "Porcentagem Generico", "Porcentagem Personalizado", "Porcentagem Cosseno" }));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == botaoVoltar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new MenuPrincipal());
			MenuInicial.panelCorpo.updateUI();
		}
		if(event.getSource() == botaoComparar){	

			executaJanelaAguarde = new Thread(esperePorFavor);
			executaProcessaAlgoritmo = new Thread(processaAlgoritmo);
			
			if(numRecomend > 0){
				executaJanelaAguarde.start();
				executaProcessaAlgoritmo.start();
				barra.setValue(100);
			}else{
				JOptionPane.showMessageDialog(null, "Numero de recomendacoes invalido.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	//Classe que compara os algoritmos 
	class ComparaAlgoritmosAqui extends Thread {   
		public void run() {  
			List<List> comparacoes = algoritmos.compareAlgorithms(numRecomend);
			preencheTabelaComparacoesUsuarios((List<List<String>>)comparacoes.get(0));
			preencheTabelaComparacoesFinais((List<String>)comparacoes.get(1));
			
			//pra quando terminar de comparar setar os 100%
			barra.setValue(100);
			try{
				//pra demorar um pouco antes de fechar tudo
				Thread.sleep(100);
			}
			catch(InterruptedException ignore){
				ignore.getStackTrace();
			}
			window.setVisible(false);
		}
		
	}
	
	//Classe interna da janela Aguarde
	class JanelaAguarde extends Thread {

		int progressBarra = 0;
		Random random = new Random();
		
		public JanelaAguarde(){
			
			//seta o tamanho/altura da barra
			barra.setPreferredSize(new Dimension(407,20));
			barra.setStringPainted(true);
			
			//carrega uma imagem no label imagem
			JLabel imagem = new JLabel();
			ImageIcon image = new ImageIcon("./src/lp2/imagens/apresentacao.jpg");
			imagem.setIcon(image);

			//define layout da janela
			window.getContentPane().setLayout(new BorderLayout());
			//add no container da janela a imagem e barra.
			window.getContentPane().add(imagem,BorderLayout.CENTER);
			window.getContentPane().add(barra,BorderLayout.SOUTH);
		}
		
		public void run(){
			progressBarra = 0;
			window.pack();
			window.setVisible(true);
			window.setLocationRelativeTo(null);
			
			//aumenta o progresso da barra enquanto estiver processando o compara
			//algoritmo
			while (executaProcessaAlgoritmo.isAlive()) {
				barra.setValue(Math.min(progressBarra, 100));
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignore) {
				}
				progressBarra += random.nextInt(20);
			}
		
		}
	}


	}
