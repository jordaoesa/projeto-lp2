package lp2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInicial extends JFrame{

	Container container = getContentPane();
	
	JButton botaoVerTodosLugares;
	JButton botaoCadastrar;
	JButton botaoPopularidade;
	JButton botaoGerarRecomendacao;
	JButton botaoSair;
	
	JLabel imagemFundo;
	ImageIcon image = new ImageIcon("imagens/restaurante_inicial.jpg");
	
	public MenuInicial(){

		super("Pitaqueiro das 12h");
		setSize(800,600);
		setVisible(true);
		//pra parar o programa quando fechar.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		container.setLayout(null);
		
		//imagem do fundo
		imagemFundo = new JLabel();
		imagemFundo.setIcon(image);
		imagemFundo.setBounds(-1, -2, 800, 600);
		
		//pra cada botao seta a sua posicao.
		botaoCadastrar = new JButton("Novo Cadastro");
		botaoCadastrar.setBounds(600, 26, 120, 23);
		
		botaoVerTodosLugares = new JButton("Ver todos");
		botaoVerTodosLugares.setBounds(600, 67, 100, 23);
		
		botaoPopularidade = new JButton("Ver popularidade");
		botaoPopularidade.setBounds(600,108,175,23);
		
		botaoGerarRecomendacao = new JButton("Gerar Recomendacoes");
		botaoGerarRecomendacao.setBounds(600,149,170,23);
		
		botaoSair = new JButton("Sair");
		botaoSair.setBounds(600,190,100,23);
		
		
		container.add(botaoVerTodosLugares);
		container.add(botaoCadastrar);
		container.add(botaoGerarRecomendacao);
		container.add(botaoPopularidade);
		container.add(botaoSair);
		container.add(imagemFundo);
		
		//evento do botao Sair
		botaoSair.addActionListener(new ActionListener(){	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		//evento do botao ver todos
		botaoVerTodosLugares.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuVerTodos menu = new MenuVerTodos();
			}
		});
		//evento do botao Ver Popularidade
		botaoPopularidade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MenuPopularidade menu = new MenuPopularidade();
			}
		});
		
	}
//	public static void main(String args[]){
//		MenuInicial menu = new MenuInicial();
//		//pra parar o programa quando fechar.
//		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		menu.setVisible(true);
//
//	}
}
