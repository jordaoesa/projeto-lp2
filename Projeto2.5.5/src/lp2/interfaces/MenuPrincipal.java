package lp2.interfaces;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import lp2.lerDados.ReadData;

/**
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
@SuppressWarnings("serial")
public class MenuPrincipal extends JPanel implements ActionListener{

	private JButton botaoVerTodosLugares;
	private JButton botaoCadastrar;
	private JButton botaoPopularidade;
	private JButton botaoGerarRecomendacao;
	private JButton botaoSair;
	private JButton loadFileOpinioes;
	private JButton loadFileEstabelecimentos;
	private JLabel imagemFundo;
	private ImageIcon image;

	public MenuPrincipal(){
		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaTodosComponentes();
		
		//imagem do fundo
		imagemFundo.setIcon(image);
		
		todosToolTipText();
		addNoContainer();
		addEventosComponentes();
		
	}

	private void instanciaTodosComponentes(){
		
		//Botoes
		botaoCadastrar = new JButton("Novo Cadastro");
		botaoVerTodosLugares = new JButton("Ver todos");	
		botaoPopularidade = new JButton("Ver popularidade");		
		botaoGerarRecomendacao = new JButton("Gerar Recomendacoes");		
		botaoSair = new JButton("Sair");		
		loadFileOpinioes = new JButton("Novas Opinioes");			
		loadFileEstabelecimentos = new JButton("Novos Estabelecimentos");
		
		//Label
		imagemFundo = new JLabel();
		image = new ImageIcon("./src/lp2/imagens/restaurante_inicial.jpg");
	}
	
	private void addNoContainer(){

		add(loadFileOpinioes, new AbsoluteConstraints(570, 190, 210, 23));
		add(loadFileEstabelecimentos,new AbsoluteConstraints(570, 231, 210, 23));
		add(botaoCadastrar, new AbsoluteConstraints(570, 26, 210, 23));
		add(botaoVerTodosLugares, new AbsoluteConstraints(570, 67, 210, 23));
		add(botaoPopularidade, new AbsoluteConstraints(570,108,210,23));
		add(botaoGerarRecomendacao, new AbsoluteConstraints(570,149,210,23));
		add(botaoSair, new AbsoluteConstraints(570,272,210,23));
		add(imagemFundo, new AbsoluteConstraints(-1, -2, -1, -1));

	}
	private void addEventosComponentes(){
		//add botoes ao tratamento da classe interna
		botaoCadastrar.addActionListener(this);
		botaoPopularidade.addActionListener(this);
		botaoGerarRecomendacao.addActionListener(this);
		botaoVerTodosLugares.addActionListener(this);
		botaoSair.addActionListener(this);
		loadFileOpinioes.addActionListener(this);
		loadFileEstabelecimentos.addActionListener(this);
	}
	
	private void todosToolTipText(){
		
		//Texto que quando mouse fica sobre cada componente
		botaoCadastrar.setToolTipText("Clique para cadastrar um novo usuario.");
		botaoVerTodosLugares.setToolTipText("Clique para ver opinioes de todos os usuarios.");
		botaoPopularidade.setToolTipText("Clique para ver popularidade dos estabelecimentos");
		botaoGerarRecomendacao.setToolTipText("Clique para gerar recomendacoes para um usuario cadastrado.");
		botaoSair.setToolTipText("Clique para sair.");
		loadFileOpinioes.setToolTipText("Clique para escolher um arquivo de opinioes sobre um estabalecimento.");
		loadFileEstabelecimentos.setToolTipText("Clique para escolher um arquivo com informacoes de estabelecimentos.");
	}
		@Override
		public void actionPerformed(ActionEvent event) {
			//evento do botao Cadastrar
			if(event.getSource() == botaoCadastrar){
				MenuInicial.panelCorpo.removeAll();
				MenuInicial.panelCorpo.add(new CadastraUsuario());
				MenuInicial.panelCorpo.updateUI();
			}
			//evento do botao Ver Popularidade
			if(event.getSource() == botaoPopularidade){
				MenuInicial.panelCorpo.removeAll();
				MenuInicial.panelCorpo.add(new MenuPopularidade());
				MenuInicial.panelCorpo.updateUI();
			}
			//evento do botao VerTodosLugares
			if(event.getSource() == botaoVerTodosLugares){
				MenuInicial.panelCorpo.removeAll();
				MenuInicial.panelCorpo.add(new MenuVerTodos());
				MenuInicial.panelCorpo.updateUI();
			}
			//evento do botao Gerar Recomendacao
			if(event.getSource() == botaoGerarRecomendacao){
				MenuInicial.panelCorpo.removeAll();
				MenuInicial.panelCorpo.add(new MenuGeraRecomendacao());
				MenuInicial.panelCorpo.updateUI();
			}
			//evento do botao Sair
			if(event.getSource() == botaoSair){
				System.exit(0);
			}
			//evento do botao loadFileOpinioes
			if(event.getSource() == loadFileOpinioes){
				try {
					MenuInicial.pathOpinioes = escolheArquivo("opinioes");
					if (MenuInicial.pathOpinioes == null){
						MenuInicial.pathOpinioes = "./src/lp2/arquivos/opinioes-dos-usuarios-v2.data";
					} else {
						ReadData.initLists();
					}
				} catch (Exception e){
					MenuInicial.pathOpinioes = "./src/lp2/arquivos/opinioes-dos-usuarios-v2.data";
					MenuInicial.pathEstabelecimentos = "./src/lp2/arquivos/lista_estabelecimentos_projeto_lp2-v2.data";
					try {
						ReadData.initLists();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, "Arquivo default esta sendo usado.", "Erro ao abrir arquivo", JOptionPane.ERROR_MESSAGE);
				}
			}
			//evento do botao loadFileEstabelecimentos
			if(event.getSource() == loadFileEstabelecimentos){
				try {
					MenuInicial.pathEstabelecimentos = escolheArquivo("estabelecimentos");
					if(MenuInicial.pathEstabelecimentos == null){
						MenuInicial.pathEstabelecimentos = "./src/lp2/arquivos/lista_estabelecimentos_projeto_lp2-v2.data";
					} else {
						ReadData.initLists();
					}
				} catch (Exception e){
					MenuInicial.pathEstabelecimentos = "./src/lp2/arquivos/lista_estabelecimentos_projeto_lp2-v2.data";
					MenuInicial.pathOpinioes = "./src/lp2/arquivos/opinioes-dos-usuarios-v2.data";
					try {
						ReadData.initLists();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(null, "Arquivo default esta sendo usado.", "Erro ao abrir arquivo", JOptionPane.ERROR_MESSAGE);
				}
			}
	}
	private static String escolheArquivo(String tipo) throws IOException{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha o arquivo de " + tipo);
        fileChooser.setApproveButtonText("OK");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
       
        if (!(fileChooser.showOpenDialog(fileChooser) == JFileChooser.CANCEL_OPTION)){ 
            return fileChooser.getSelectedFile().getCanonicalPath();
        }
        return null;
    }
}