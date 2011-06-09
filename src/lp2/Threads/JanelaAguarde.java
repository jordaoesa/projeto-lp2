package lp2.Threads;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class JanelaAguarde implements Runnable{
	
	private JWindow janelaAguarde = new JWindow();
	public JanelaAguarde(){

		//carrega uma imagem no label imagem
		JLabel imagem = new JLabel();
		ImageIcon image = new ImageIcon("./src/lp2/imagens/aguarde.gif");
		imagem.setIcon(image);

		//define layout da janela
		janelaAguarde.getContentPane().setLayout(new BorderLayout());
		//add no container da janela a imagem e barra.
		janelaAguarde.getContentPane().add(imagem,BorderLayout.CENTER);
	}
	
	public void run(){
		janelaAguarde.pack();
		janelaAguarde.setVisible(true);
		janelaAguarde.setLocationRelativeTo(null);
	
	}
	public JWindow getWindow(){
		return this.janelaAguarde;
	}
	                           
}
	



