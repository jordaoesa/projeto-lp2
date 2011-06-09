package lp2.Threads;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class CarregaMaps implements Runnable {
	String url;
	BufferedImage imgCarregada = null;
	final JFrame frame = new JFrame("Bom Conselho no Google Mapa");
	JLabel iconMaisZoom = new JLabel(new ImageIcon("./src/lp2/imagens/maisZoom.png"));
	JLabel iconMenosZoom = new JLabel(new ImageIcon("./src/lp2/imagens/menosZoom.png"));
	JLabel imgFundo = null;
	final JSlider controlaZoom = new JSlider(SwingConstants.VERTICAL,0,21,14);
	JWindow window;
	public CarregaMaps(final String url,JWindow window){
		this.url = url;
		this.window = window;
		controlaZoom.addChangeListener(new ChangeListener() {
		@Override
			public void stateChanged(ChangeEvent arg0) {
				imgFundo.setIcon(new ImageIcon(getImage(zoom(url, controlaZoom.getValue()))));
				frame.validate();
			}
		});
		
	}
	public void run(){
		imgCarregada = getImage(url);
		frame.setSize(605,512);
		frame.setResizable(false);
		frame.setLayout(new AbsoluteLayout());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		controlaZoom.setMajorTickSpacing(1);
		frame.getContentPane().add(iconMaisZoom, new AbsoluteConstraints(10, 10,-1, 30));
		frame.getContentPane().add(iconMenosZoom, new AbsoluteConstraints(10, 160, -1, 30));
		frame.getContentPane().add(controlaZoom, new AbsoluteConstraints(14,34,20,130));
		imgFundo = new JLabel(new ImageIcon(imgCarregada));
		frame.getContentPane().add(imgFundo, new AbsoluteConstraints(-1, -2, -1, -1));
		window.dispose();
		frame.setVisible(true);
	}
	
//	public void setImagemLabel(){
//		imgFundo.setIcon(arg0)
//	}
	
	private BufferedImage getImage(String caminho) {

		URL url;
		try {
			url = new URL(caminho);
			return ImageIO.read(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("mal forma url"); 
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Nossa!Ta sem internet", "Erro", JOptionPane.ERROR_MESSAGE);	
		}
		return null;

	}
	private String zoom(String url, int zoom){
		String setURL = "";
		setURL = url.replace(url.substring(42, 50),"zoom=" + String.valueOf(zoom));
		return setURL;
	}
}
