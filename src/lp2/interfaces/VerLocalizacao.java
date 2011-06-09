package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

import lp2.Threads.CarregaMaps;
import lp2.Threads.JanelaAguarde;
import lp2.lerDados.Estabelecimento;
import lp2.lerDados.ReadData;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;



/**
 *  Classe responsavel por mostrar a localizacao de um determinado
 *  estabeleimento para o usuario atraves de um mapa.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
@SuppressWarnings("serial")
public class VerLocalizacao extends JPanel implements ActionListener{

	
	
	//http://maps.google.com/maps/api/staticmap?&zoom=16&size=600x512&markers=size:m|color:red|Rua+Gilo+Guedes,Campina+Grande,BR|Rua+Santa+Cecilia,Campina+Grande+BR&mobile=true&maptype=hybrid&sensor=false


	//http://maps.google.com/maps/api/staticmap?center=Rua+Gilo+Guedes,Campina+Grande,BR|Rua+Santa+Cecilia&zoom=12&size=400x400&maptype=roadmap&sensor=false
	//http://maps.google.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=12&size=400x400&maptype=satellite&sensor=false
	//http://maps.google.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=12&size=400x400&maptype=hybrid&sensor=false

	JanelaAguarde esperePorFavor = new JanelaAguarde();
	Thread executaJanelaAguarde;
	Thread executaCarregaMaps;
	
	String urlFinal;
	boolean selecionadaVisaoSatelite = false;
	boolean selecionadaVisaoHibrida = false;
	boolean selecionadaVisaoTerreno = false;
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
	private JRadioButton tipoVisaoTerreno;
	private JRadioButton tipoVisaoNormal;
	private ButtonGroup selecionaVisao;

	
	/**
	 * Metodo que cria e inicia a uma janela que mostra ao usuario 
	 * a localiacao do estabelecimento em questao, e a distancia do
	 * mesmo para sua casa.
	 */
	 
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
		tipoVisaoTerreno = new JRadioButton("Visao Terrena");
		tipoVisaoNormal = new JRadioButton("Visao Normal");
		selecionaVisao = new ButtonGroup();
		selecionaVisao.add(tipoVisaoHibrida);
		selecionaVisao.add(tipoVisaoTerreno);
		selecionaVisao.add(tipoVisaoNormal);
		selecionaVisao.add(tipoVisaoSatelite);

		visualizaMapa = new JPanel();

		iniciaComboBoxEstabelecimentos();
	
		add(insiraLocal, new AbsoluteConstraints(50,50,123,23));
		add(endereco, new AbsoluteConstraints(50,80,200,25));
		add(ondeDesejaIr, new AbsoluteConstraints(50,120,123,23));
		add(listaEstabelecimetosCadastrados, new AbsoluteConstraints(50,150,320,23));
		add(botaoLocalizar, new AbsoluteConstraints(50,220,123,23));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		add(tipoVisaoTerreno,new AbsoluteConstraints(400,50,200,25));
		add(tipoVisaoHibrida,new AbsoluteConstraints(400,75,200,25));
		add(tipoVisaoSatelite,new AbsoluteConstraints(400,100,200,25));
		add(tipoVisaoNormal,new AbsoluteConstraints(400,120,200,25));


		endereco.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent evt) {
				//System.out.println(endereco.getText());
			}
		});


		listaEstabelecimetosCadastrados.addActionListener(this);
		botaoLocalizar.addActionListener(this);
		botaoVoltar.addActionListener(this);
		tipoVisaoTerreno.addActionListener(this);
		tipoVisaoHibrida.addActionListener(this);
		tipoVisaoNormal.addActionListener(this);
		tipoVisaoSatelite.addActionListener(this);
	}
	
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
		}if(tipo.equals("Normal")){
			return setURL = "http://maps.google.com/maps/api/staticmap?&zoom=14&size=600x512&markers=size:m|color:red|" + fonteDestino + "&mobile=true&maptype=roadmap&sensor=false";
		}if(tipo.equals("Terreno")){
			return setURL = "http://maps.google.com/maps/api/staticmap?&zoom=14&size=600x512&markers=size:m|color:red|" + fonteDestino + "&mobile=true&maptype=terrain&sensor=false";
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

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == botaoLocalizar){
			if (!(enderecoDestino.equals(""))  && (!endereco.getText().equals(""))){
				String fonteDestino = concatenaDestinos(endereco.getText(), enderecoDestino);
				if(selecionadaVisaoNormal){
					urlFinal = setTipoImagem("Normal", fonteDestino);
					starThreads();
				}else if(selecionadaVisaoTerreno){
					urlFinal = setTipoImagem("Terreno", fonteDestino);
					starThreads();
				}else if(selecionadaVisaoHibrida){
					urlFinal = setTipoImagem("Hibrida", fonteDestino);
					starThreads();
					
				}else if(selecionadaVisaoSatelite){
					urlFinal = setTipoImagem("Satelite", fonteDestino);
					starThreads();
				}else
					JOptionPane.showMessageDialog(null, "Escolha enderecos/Tipo de Visao", "Erro", JOptionPane.ERROR_MESSAGE);
			}else{
				JOptionPane.showMessageDialog(null, "Escolha enderecos/Tipo de Visao", "Erro", JOptionPane.ERROR_MESSAGE);
			}

		}
		if(event.getSource() == tipoVisaoNormal){
			selecionadaVisaoNormal = true;
			selecionadaVisaoSatelite = false;
			selecionadaVisaoHibrida = false;
			selecionadaVisaoTerreno = false;
		}if(event.getSource() == tipoVisaoHibrida){
			selecionadaVisaoHibrida = true;
			selecionadaVisaoSatelite = false;
			selecionadaVisaoTerreno = false;
			selecionadaVisaoNormal = false;
		}if(event.getSource() == tipoVisaoSatelite){
			selecionadaVisaoSatelite = true;
			selecionadaVisaoHibrida = false;
			selecionadaVisaoTerreno = false;
			selecionadaVisaoNormal = false;
		}if(event.getSource() == tipoVisaoTerreno){
			selecionadaVisaoTerreno = true;
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
	
	private void instanciaThreads(){
		executaCarregaMaps = new Thread(new CarregaMaps(urlFinal,esperePorFavor.getWindow()));
		executaJanelaAguarde = new Thread(esperePorFavor);
	
	}
	private void starThreads(){
		instanciaThreads();
		executaJanelaAguarde.start();
		executaCarregaMaps.start();
	}
	
	private void desabilitaTodosBotoes(){
		botaoLocalizar.setEnabled(false);
		botaoVoltar.setEnabled(false);
		tipoVisaoSatelite.setEnabled(false);
		tipoVisaoHibrida.setEnabled(false);
		tipoVisaoTerreno.setEnabled(false);
		tipoVisaoNormal.setEnabled(false);
		botaoLocalizar.setEnabled(false);
	}
	private void habilitaTodosBotoes(){
		botaoLocalizar.setEnabled(true);
		botaoVoltar.setEnabled(true);
		tipoVisaoSatelite.setEnabled(true);
		tipoVisaoHibrida.setEnabled(true);
		tipoVisaoTerreno.setEnabled(true);
		tipoVisaoNormal.setEnabled(true);
		botaoLocalizar.setEnabled(true);
	}
}
