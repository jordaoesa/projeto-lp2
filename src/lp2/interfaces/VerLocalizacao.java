package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import lp2.lerDados.Estabelecimento;
import lp2.lerDados.ReadData;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

public class VerLocalizacao extends JPanel implements ActionListener{

	//http://maps.google.com/maps/api/staticmap?&zoom=16&size=600x512&markers=size:m|color:red|Rua+Gilo+Guedes,Campina+Grande,BR|Rua+Santa+Cecilia,Campina+Grande+BR&mobile=true&maptype=hybrid&sensor=false


	//http://maps.google.com/maps/api/staticmap?center=Rua+Gilo+Guedes,Campina+Grande,BR|Rua+Santa+Cecilia&zoom=12&size=400x400&maptype=roadmap&sensor=false
	//http://maps.google.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=12&size=400x400&maptype=satellite&sensor=false
	//http://maps.google.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=12&size=400x400&maptype=hybrid&sensor=false

	//Thread carregandoThread;
	//CarregaImagem imagemCarregando;
	String urlFinal;
	boolean selecionadaVisaoSatelite = false;
	boolean selecionadaVisaoHibrida = false;
	boolean selecionadaVisaoEstrada = false;
	boolean selecionadaVisaoNormal = false;

	JFrame frameMostraMapa;
	JButton botaoLocalizar;
	JButton botaoVoltar;
	JLabel insiraLocal;
	JLabel ondeDesejaIr;
	JPanel visualizaMapa;
	JLabel imageMapa;
	JTextField endereco;
	JComboBox listaEstabelecimetosCadastrados;
	String[] estabelecimentosCadastrados;
	BufferedImage buffImgMapa;
	String linkDefault = "http://maps.google.com/maps/api/staticmap?center=Campina+Grande,BR&zoom=2&size=400x400&maptype=hybrid&sensor=true";
	String enderecoDestino = "";
	String link;
	private JRadioButton tipoVisaoSatelite;
	private JRadioButton tipoVisaoHibrida;
	private JRadioButton tipoVisaoEstradas;
	private JRadioButton tipoVisaoNormal;
	private ButtonGroup selecionaVisao;

	public VerLocalizacao(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		botaoLocalizar = new JButton("Localizar");
		botaoVoltar = new JButton("Voltar");
		insiraLocal = new JLabel("Insira o local");
		ondeDesejaIr = new JLabel("Onde deseja ir:");
		endereco = new JTextField();
		tipoVisaoSatelite = new JRadioButton("Visao Satelite");
		tipoVisaoHibrida = new JRadioButton("Visao Hibrida");
		tipoVisaoEstradas = new JRadioButton("Visao Estradas");
		tipoVisaoNormal = new JRadioButton("Visao Normal");
		selecionaVisao = new ButtonGroup();
		selecionaVisao.add(tipoVisaoHibrida);
		selecionaVisao.add(tipoVisaoEstradas);
		selecionaVisao.add(tipoVisaoNormal);
		selecionaVisao.add(tipoVisaoSatelite);

		visualizaMapa = new JPanel();

		iniciaComboBoxEstabelecimentos();

		//		imagemCarregando = new CarregaImagem();
		//		carregandoThread = new Thread(imagemCarregando);
		//		carregandoThread.start();
		//buffImgMapa = getImage(linkDefault);
		//imageMapa = new JLabel(new ImageIcon(buffImgMapa));
		//visualizaMapa.add(imageMapa);

		//		getContentPane().add(insiraLocal, new AbsoluteConstraints(50,50,123,23));
		//		getContentPane().add(endereco, new AbsoluteConstraints(50,80,200,25));
		//		getContentPane().add(ondeDesejaIr, new AbsoluteConstraints(50,120,123,23));
		//		getContentPane().add(listaEstabelecimetosCadastrados, new AbsoluteConstraints(50,150,320,23));
		//		getContentPane().add(botaoLocalizar, new AbsoluteConstraints(50,220,123,23));
		//		getContentPane().add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		//		getContentPane().add(tipoVisaoEstradas,new AbsoluteConstraints(400,50,200,25));
		//		getContentPane().add(tipoVisaoHibrida,new AbsoluteConstraints(400,75,200,25));
		//		getContentPane().add(tipoVisaoSatelite,new AbsoluteConstraints(400,100,200,25));
		//		getContentPane().add(tipoVisaoNormal,new AbsoluteConstraints(400,120,200,25));
		//		
		add(insiraLocal, new AbsoluteConstraints(50,50,123,23));
		add(endereco, new AbsoluteConstraints(50,80,200,25));
		add(ondeDesejaIr, new AbsoluteConstraints(50,120,123,23));
		add(listaEstabelecimetosCadastrados, new AbsoluteConstraints(50,150,320,23));
		add(botaoLocalizar, new AbsoluteConstraints(50,220,123,23));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		add(tipoVisaoEstradas,new AbsoluteConstraints(400,50,200,25));
		add(tipoVisaoHibrida,new AbsoluteConstraints(400,75,200,25));
		add(tipoVisaoSatelite,new AbsoluteConstraints(400,100,200,25));
		add(tipoVisaoNormal,new AbsoluteConstraints(400,120,200,25));


		endereco.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				System.out.println(endereco.getText());
			}
		});


		listaEstabelecimetosCadastrados.addActionListener(this);
		botaoLocalizar.addActionListener(this);
		botaoVoltar.addActionListener(this);
		tipoVisaoEstradas.addActionListener(this);
		tipoVisaoHibrida.addActionListener(this);
		tipoVisaoNormal.addActionListener(this);
		tipoVisaoSatelite.addActionListener(this);
	}



	//	class CarregaImagem extends Thread{
	//		
	//		public void run(){
	//			buffImgMapa = getImage(linkDefault);
	//		}
	//	}

	public static BufferedImage getImage(String caminho) {

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
	//	
	//	public static String fazURLImageNormal(String lugar1,String lugar2){
	//		String fonte = lugar1.replace(" ", "+") + ",Campina+Grande,BR";
	//		String destino = lugar2.replace(" ", "+") + ",Campina+Grande,BR";
	//		String completa = fonte + "|";
	//		completa += destino;
	//		String link = "http://maps.google.com/maps/api/staticmap?&zoom=14&size=600x512&markers=size:m|color:red|" + completa + "&mobile=true&sensor=false";
	//		return link;
	//	}
	//	
	public static String concatenaDestinos(String lugar1,String lugar2){
		String fonte = lugar1.replace(" ", "+") + ",Campina+Grande,BR";
		String destino = lugar2.replace(" ", "+") + ",Campina+Grande,BR";
		String completa = fonte + "|";
		completa += destino;
		return completa;
	}
	public static String setTipoImagem(String tipo, String fonteDestino){
		String setURL = "";
		if(tipo.equals("Satelite")){
			return setURL = "http://maps.google.com/maps/api/staticmap?&zoom=14&size=600x512&markers=size:m|color:red|" + fonteDestino + "&mobile=true&maptype=satellite&sensor=false";
		}if(tipo.equals("Hibrida")){
			return setURL = "http://maps.google.com/maps/api/staticmap?&zoom=14&size=600x512&markers=size:m|color:red|" + fonteDestino + "&mobile=true&maptype=hybrid&sensor=false";
		}if(tipo.equals("Estrada")){
			return setURL = "http://maps.google.com/maps/api/staticmap?&zoom=14&size=600x512&markers=size:m|color:red|" + fonteDestino + "&mobile=true&maptype=roadmap&sensor=false";
		}if(tipo.equals("Normal")){
			return setURL = "http://maps.google.com/maps/api/staticmap?&zoom=14&size=600x512&markers=size:m|color:red|" + fonteDestino + "&mobile=true&sensor=false";
		}
		return setURL;
	}

	private void iniciaComboBoxEstabelecimentos() {
		//tamanho da lista de estabelecimentos + 1,pq logo abaixo eh adicionado um espaco vazio.
		estabelecimentosCadastrados = new String[ReadData.getEstabelecimentos().size()+1];
		// so pra deixar vazio o primeiro campo, pra nao ficar como se ja tivesse um usuarios selecionado
		estabelecimentosCadastrados[0] = "";
		int i = 1;
		for(Estabelecimento estabelecimento : ReadData.getEstabelecimentos()){
			estabelecimentosCadastrados[i] = estabelecimento.getNome();
			i ++;
		}
		listaEstabelecimetosCadastrados = new JComboBox(estabelecimentosCadastrados);

	}

	//	public static void main(String args[]){
	//		VerLocalizacao ver = new VerLocalizacao();
	//		ver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == botaoLocalizar){
			if (!(enderecoDestino.equals(""))  && (!endereco.getText().equals(""))){
				String fonteDestino = concatenaDestinos(endereco.getText(), enderecoDestino);
				if(selecionadaVisaoNormal){
					urlFinal = setTipoImagem("Normal", fonteDestino);
					System.out.println(urlFinal);
					criaFrame(urlFinal);
				}else if(selecionadaVisaoEstrada){
					urlFinal = setTipoImagem("Estrada", fonteDestino);
					System.out.println(urlFinal);
					criaFrame(urlFinal);
				}else if(selecionadaVisaoHibrida){
					urlFinal = setTipoImagem("Hibrida", fonteDestino);
					System.out.println(urlFinal);
					criaFrame(urlFinal);
				}else if(selecionadaVisaoSatelite){
					urlFinal = setTipoImagem("Satelite", fonteDestino);
					System.out.println(urlFinal);
					criaFrame(urlFinal);
				}else
					JOptionPane.showMessageDialog(null, "Escolha enderecos/Tipo de Visao", "Erro", JOptionPane.ERROR_MESSAGE);
			}else{
				 JOptionPane.showMessageDialog(null, "Escolha enderecos/Tipo de Visao", "Erro", JOptionPane.ERROR_MESSAGE);
			}

		}
		if(event.getSource() == tipoVisaoNormal){
			System.out.println("tipo normal");
			selecionadaVisaoNormal = true;
			selecionadaVisaoSatelite = false;
			selecionadaVisaoHibrida = false;
			selecionadaVisaoEstrada = false;
		}if(event.getSource() == tipoVisaoHibrida){
			selecionadaVisaoHibrida = true;
			selecionadaVisaoSatelite = false;
			selecionadaVisaoEstrada = false;
			selecionadaVisaoNormal = false;
		}if(event.getSource() == tipoVisaoSatelite){
			selecionadaVisaoSatelite = true;
			selecionadaVisaoHibrida = false;
			selecionadaVisaoEstrada = false;
			selecionadaVisaoNormal = false;
		}if(event.getSource() == tipoVisaoEstradas){
			selecionadaVisaoEstrada = true;
			selecionadaVisaoSatelite = false;
			selecionadaVisaoHibrida = false;
			selecionadaVisaoNormal = false;
		}

		if(event.getSource() == listaEstabelecimetosCadastrados){
			if(listaEstabelecimetosCadastrados.getSelectedIndex() > 0){
				enderecoDestino = ReadData.getEstabelecimentos().get(listaEstabelecimetosCadastrados.getSelectedIndex()-1).getLocalizacao().split(",")[0];
			}else
				enderecoDestino = "";
			if(enderecoDestino.equals("Campus da UFCG")){
				enderecoDestino = "Rua Aprígio Veloso";
			}else if(enderecoDestino.equals("Shopping Boulevard")){
				enderecoDestino = "Av. Pref Severino Bezerra Cabral";
			}
		}
		if(event.getSource() == botaoVoltar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new CadastraUsuario());
			MenuInicial.panelCorpo.updateUI();
		}
	}
	private void criaFrame(String URL){
		BufferedImage imgCarregada;
		final JFrame frame = new JFrame("Bom Conselho no Google Mapa");
		frame.setSize(605,512);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try{
		imgCarregada = getImage(URL);
		JLabel imgLabel = new JLabel(new ImageIcon(imgCarregada));
		frame.setContentPane(imgLabel);
		frame.setVisible(true);
		}catch(NullPointerException ex){
			ex.getStackTrace();
		}
	}

}
