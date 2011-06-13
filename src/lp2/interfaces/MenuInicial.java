package lp2.interfaces;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe que cria e executa a interface do menu inicial do sistema.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
@SuppressWarnings("serial")
public class MenuInicial extends JFrame implements ActionListener{

	private boolean type1 = true;
	private boolean type2 = false;
	private UIManager.LookAndFeelInfo looks[]; // variavel que recebera qual tema o programa vai carregar;
	private String subItensTemas[] = {"Tipo 1 - Motif","Tipo 2 - Metal"};
	private String subItensSobre[] = {"Integrantes","Programa"};
 	private String itens[] = {"Arquivo", "Temas","Sobre"};
	private String subItensArquivo[] = {"Sair"};
	private JMenuBar menuBarra;
	private JMenu menus[];
	private JMenuItem menuItensArquivo[];
	private JMenuItem menuItensTemas[];
	private JMenuItem menuItensSobre[];
	public static String pathEstabelecimentos = "./src/lp2/arquivos/lista_estabelecimentos_projeto_lp2-v2.data";
	public static String pathOpinioes = "./src/lp2/arquivos/opinioes-dos-usuarios-v2.data";
	protected static JPanel panelCorpo = new JPanel();
	private Container container = getContentPane();
	
	
	
	/**
	 * Metodo que cria e inicia a interface grafica do menu inicial, para
	 * interacao com o usuario.
	 */
	public MenuInicial(){
		super("Bom Conselho");
		
		
		iniciaBarraFerramentas();
		alteraTema();
		
		setSize(800,600);
		setVisible(true);
		setResizable(false);
		setBackground(new Color(253, 245, 230));
		// pra quando abrir o programa ele abrir no meio da tela do pc
		setLocationRelativeTo(null);
		//pra parar o programa quando fechar.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//seta o layout do JFrame/JPanel
		container.setLayout(new BorderLayout());
		panelCorpo.setLayout(new BorderLayout());
		
		panelCorpo.add(new MenuPrincipal());
		
		//Posiciona panelCorpo no centro da tela
		add(panelCorpo, BorderLayout.CENTER);
		//posiciona menuBarra no norte da tela
		add(menuBarra, BorderLayout.NORTH);
		ImageIcon imagemTituloJanela = new ImageIcon("./src/lp2/imagens/iconApp.png");  
		setIconImage(imagemTituloJanela.getImage());
		
		//atualiza todo o panel
		panelCorpo.updateUI();
		addEventosComponentes();
	}
	
	private void addEventosComponentes(){
		menuItensArquivo[0].addActionListener(this);
		menuItensTemas[0].addActionListener(this);
		menuItensTemas[1].addActionListener(this);
		menuItensSobre[0].addActionListener(this);
		menuItensSobre[1].addActionListener(this);		
	}
	
	
	private void iniciaBarraFerramentas(){
		menuBarra = new JMenuBar();
		menus = new JMenu[itens.length];
		menuItensArquivo = new JMenuItem[subItensArquivo.length];
		menuItensTemas = new JMenuItem[subItensTemas.length];
		menuItensSobre = new JMenuItem[subItensSobre.length];
		
		//Adiciona as barras Arquivo
		for (int i = 0; i < itens.length; i++) {
			menus[i] = new JMenu(itens[i]);
			menuBarra.add(menus[i]);
		}
		//adicina o menus suspenso de Arquivo
		for (int i = 0; i < subItensArquivo.length; i++) {
			menuItensArquivo[i] = new JMenuItem(subItensArquivo[i]);
			menus[0].add(menuItensArquivo[i]);
		}
		//
		for(int i = 0; i< subItensTemas.length; i++){
			menuItensTemas[i]= new JMenuItem(subItensTemas[i]);
			menus[1].add(menuItensTemas[i]);
		}
		for(int i = 0; i< subItensSobre.length; i++){
			menuItensSobre[i]= new JMenuItem(subItensSobre[i]);
			menus[2].add(menuItensSobre[i]);
		}
	}
	
	
	//metodo que altera o tema da GUI
	/**
	 * Metodo que altera o tema da interface grafica .
	 */
	
	public void alteraTema(){ 
		
		if (type1 == true){
			looks = UIManager.getInstalledLookAndFeels();
			try{
				UIManager.setLookAndFeel(looks[1].getClassName());
				SwingUtilities.updateComponentTreeUI(this);
			}catch(Exception e){
				e.printStackTrace();
			}
			menuItensTemas[0].setEnabled(false); 
			menuItensTemas[1].setEnabled(true);
		}else if (type2 == true){
			looks = UIManager.getInstalledLookAndFeels();
			try{
				UIManager.setLookAndFeel(looks[0].getClassName());
				SwingUtilities.updateComponentTreeUI(this);
			}catch(Exception e){
				e.printStackTrace();
			}
			menuItensTemas[0].setEnabled(true);
			menuItensTemas[1].setEnabled(false);
		}
	}

	//Metodo que trata todos os eventos
	
	/**
	 * Metodo responsavel por verificar e capturar eventos do
	 * usuario com a interface grafica.
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == menuItensTemas[0]){
			type1 = true;
			type2 = false;
			alteraTema();
		}else if(event.getSource() == menuItensTemas[1]){
			type1 = false;
			type2 = true;
			alteraTema();
		}else if(event.getSource() == menuItensArquivo[0]){
			System.exit(EXIT_ON_CLOSE);
		}else if(event.getSource() == menuItensSobre[0]){
			JOptionPane.showMessageDialog(MenuInicial.this, "Programa desenvolvido por:" + "\n" + "	Irvile Rodrigues"+ "\n"
					                              + "	Jordão Ezequiel" + "\n" + "	Flavia Gangorra","Sobre Integrantes",JOptionPane.PLAIN_MESSAGE);
		}else if (event.getSource() == menuItensSobre[1]){
			JOptionPane.showMessageDialog(MenuInicial.this,"Bom Conselho" + "\n" + "Versao: 2.5.5" + "\n" +
					           "Programa que recomenda lugares para almocar","Sobre Programa",JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	/**
	 * Modifica o caminho do arquivo com os dados sobre os estabelecimetos
	 * para leitura e utilizacao no sistema.
	 * @param path
	 * 			novo caminho do arquivo.
	 */
	public static void setPathEstabelecimentos(String path){
		pathEstabelecimentos = path;
	}
	
	
	/**
	 * Modifica o caminho do arquivo com os dados sobre as opinioes 
	 * dos usuarios para leitura e utilizacao no sistema.
	 * @param path
	 * 			novo caminho do arquivo.
	 */
	public static void setPathOpinioes(String path){
		pathOpinioes = path;
	}

}
