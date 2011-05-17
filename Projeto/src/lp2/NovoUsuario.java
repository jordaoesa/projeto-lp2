package lp2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NovoUsuario extends JFrame {

	Container container = getContentPane();
	
	//Array com todas opinioes possiveis
	String avaliacao[] = {"0:Nao conheco", "-5:Detesto", "-4:Acho muito ruim","-3:Acho bastante ruim",
			"-2:Acho ruim", "-1:Acho um pouco ruim","1:Nao eh ruim","2:Eh bonzinho","3:Bastante bom",
			"4:Muito bom","5:Incrivel,sensacional,impressionante"};
	
	//rotulo do nome e area para editar o nome.
	JLabel nome = new JLabel("Nome:              ");
	JTextField areaNome = new JTextField(20);
	
	//instancia todas as comboBox
	JComboBox listaSuspensa = new JComboBox(avaliacao);
	JComboBox listaSuspensa2 = new JComboBox(avaliacao);
	JComboBox listaSuspensa3 = new JComboBox(avaliacao);
	JComboBox listaSuspensa4 = new JComboBox(avaliacao);
	JComboBox listaSuspensa5 = new JComboBox(avaliacao);
	
	
	JLabel lugar1 = new JLabel("Baixinho Bar e Restaurante");
	JLabel lugar2 = new JLabel("Bar do Cuscuz e Restaurante");
	JLabel lugar3 = new JLabel("Bar do Gerge");
	JLabel lugar4 = new JLabel("Bar do Santos");
	JLabel lugar5 = new JLabel("Bodao Bar e Restaurante");
	//<html>Código<br>Produto</html>
	
	JLabel imagemFundo;
	ImageIcon image = new ImageIcon("imagens/fundo2.jpg");
	
	JButton botaoVoltar = new JButton("Voltar");
	
	public NovoUsuario(){
		super("Pitaqueiro das 12h");
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		//imagem do fundo
		imagemFundo = new JLabel();
		imagemFundo.setIcon(image);
		imagemFundo.setBounds(-1, -2, 800, 600);
		
		//nome.setForeground(new Color(255,255,255));
		nome.setBounds(50,50,123,23);
		areaNome.setBounds(90,50,123,23);
		
		//seta posicao do botao voltar
		botaoVoltar.setBounds(600,500,120,23);
		
		//seta posicao de todos os rotulos com nomes dos lugares e os comboBox
		lugar1.setBounds(50,90,260,23);
		listaSuspensa.setBounds(50,110,240,23);
		
		lugar2.setBounds(50, 135, 260, 23);
		listaSuspensa2.setBounds(50, 160, 240,23);
		
		lugar3.setBounds(50, 190, 260, 23);
		listaSuspensa3.setBounds(50, 210, 240,23);
		
		lugar4.setBounds(50, 240, 230, 23);
		listaSuspensa4.setBounds(50, 265, 240,23);
		
		lugar5.setBounds(50, 300, 260, 23);
		listaSuspensa5.setBounds(50, 320, 240,23);
		
		container.add(nome);
		container.add(areaNome);
		
		container.add(lugar1);
		container.add(listaSuspensa);
		container.add(lugar2);
		container.add(listaSuspensa2);
		container.add(lugar3);
		container.add(listaSuspensa3);
		container.add(lugar4);
		container.add(listaSuspensa4);
		container.add(lugar5);
		container.add(listaSuspensa5);
		container.add(botaoVoltar);
		container.add(imagemFundo);
		
		
		botaoVoltar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NovoUsuario.this.dispose();
			}
		});
	}
	
//	public static void main(String args[]){
//		NovoUsuario M = new NovoUsuario();
//	}
}
