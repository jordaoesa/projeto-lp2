package lp2.interfaces;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import lp2.lerDados.ReadData;


/**
 * Classe responsavel por iniciar o sistema, fazendo a leitura de
 * arquivos padroes e iniciando a janela da interface grafica para o usuario.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
public class Main {

	
	/**
	 * Metodo que inicia a execucao do sistema  para o usuario, e
	 * faz a leitura dos arquivos padroes para tal.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		iniciaSplashScreen();
		ReadData.initLists();
	}

	private static void iniciaSplashScreen(){

		final JWindow janelaApresentacao = new JWindow();
		final JProgressBar barra = new JProgressBar();

		//carrega uma imagem no label imagem
		JLabel imagem = new JLabel();
		ImageIcon image = new ImageIcon("./src/lp2/imagens/apresentacao.jpg");
		imagem.setIcon(image);
		//seta o tamanho/altura da barra
		barra.setPreferredSize(new Dimension(407,15));
		barra.setStringPainted(true);
		//define layout da janela
		janelaApresentacao.getContentPane().setLayout(new BorderLayout());
		//add no container da janela a imagem e barra.
		janelaApresentacao.getContentPane().add(imagem,BorderLayout.CENTER);
		janelaApresentacao.getContentPane().add(barra,BorderLayout.SOUTH);
		
		new Thread(){
			Random random = new Random();
			public void run(){
				int progressBarra = 0;
				//aumenta o progresso da barra
				while(progressBarra < 101){
					barra.setValue(Math.min(progressBarra, 100));
					try {
						Thread.sleep(500);
					} catch (InterruptedException ignore) {
					}
					progressBarra += random.nextInt(30);
				}
				//fecha janela
				janelaApresentacao.dispose();
				new MenuInicial();
			}
		}.start();
		
		janelaApresentacao.pack();
		janelaApresentacao.setVisible(true);
		janelaApresentacao.setLocationRelativeTo(null);
	}
}
