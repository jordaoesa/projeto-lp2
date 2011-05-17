package lp2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGeraRecomendacao extends JFrame {
	
	Container container = getContentPane();
	
	//array com nomes dos usuarios cadastrados.
	String usuariosCadastrados[] = {"Irvile", "Jordao" , "Flavia"};
	
	JLabel usuario = new JLabel("Escolha usuario:");
	JLabel algoritmoEscolhido = new JLabel("Escolha algoritmo:");
	JLabel numRecomendacoes = new JLabel("Qual o numero de recomendacoes?");
	
	JCheckBox algoritmoTipo1 = new JCheckBox("Algoritmo Personalizado");
	JCheckBox algoritmoTipo2 = new JCheckBox("Algoritmo Popularidade");
	JTextField campoTextoRecomendacoes = new JTextField(20);
	
	JComboBox listaSuspensaDeUsuarios = new JComboBox(usuariosCadastrados);
	
	JButton botaoGerarRecomendacao = new JButton("Gerar recomendacao");
	JButton botaoVoltar = new JButton("Voltar");
	
	JLabel imagemFundo;
	ImageIcon image = new ImageIcon("imagens/fundo2.jpg");
	
	public MenuGeraRecomendacao(){
		super("Pitaqueiro das 12h");
		setSize(800,600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		//imagem do fundo
		imagemFundo = new JLabel();
		imagemFundo.setIcon(image);
		imagemFundo.setBounds(-1, -2, 800, 600);
		
		usuario.setBounds(50,50,123,23);
		listaSuspensaDeUsuarios.setBounds(150,50,123,23);
		
		algoritmoEscolhido.setBounds(50, 90, 123, 23);
		algoritmoTipo1.setBounds(170, 90, 170, 23);
		algoritmoTipo2.setBounds(170, 120, 170, 23);
		
		numRecomendacoes.setBounds(50, 150, 230, 23);
		campoTextoRecomendacoes.setBounds(260, 150, 150, 23);
		
		//seta posicao do botao gerar recomendacao
		botaoGerarRecomendacao.setBounds(50, 500, 160, 23);
		//seta posicao do botao voltar
		botaoVoltar.setBounds(600,500,120,23);
		
		container.add(usuario);
		container.add(listaSuspensaDeUsuarios);
		container.add(algoritmoEscolhido);
		container.add(algoritmoTipo1);
		container.add(algoritmoTipo2);
		container.add(numRecomendacoes);
		container.add(campoTextoRecomendacoes);
		container.add(botaoGerarRecomendacao);
		container.add(botaoVoltar);
		container.add(imagemFundo);
		
		botaoVoltar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MenuGeraRecomendacao.this.dispose();
			}
		});
	}
//	public static void main(String args[]){
//		MenuGeraRecomendacao m = new MenuGeraRecomendacao();
//		
//	}
}
