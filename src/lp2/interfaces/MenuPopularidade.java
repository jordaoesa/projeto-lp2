package lp2.interfaces;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import lp2.algoritmos.Algoritmos;
import lp2.lerDados.Estabelecimento;
import lp2.lerDados.ReadData;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;


/**
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
@SuppressWarnings("serial")
public class MenuPopularidade extends JPanel implements ActionListener{
	
	private static Algoritmos algoritmos;
	private JScrollPane scrollPane;
	private JButton botaoVoltar;
	private JTable tabelaEstabelecimento;
	private JLabel popularidade;

	public MenuPopularidade(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));		
		
		instanciaTodosComponentes();

		//desabilita edicao da tabela
		tabelaEstabelecimento.setEnabled(false);
		//executa algoritmo de popularidade
		popularityRecomendations();

		addNoContainer();
		
		//texto quando mouse fica parado sobre o botao
		botaoVoltar.setToolTipText("Clique para voltar ao menu anterior");
		
		//add botoes ao tratamento da classe interna
		botaoVoltar.addActionListener(this);
	}	
	private void instanciaTodosComponentes(){
		algoritmos = new Algoritmos();
		tabelaEstabelecimento = new JTable();
		botaoVoltar = new JButton("Voltar");
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(tabelaEstabelecimento);
		popularidade = new JLabel("Ranking de Popularidade");;	
	}
	private void addNoContainer(){
		add(scrollPane, new AbsoluteConstraints(10,60,776,433));
		add(popularidade, new AbsoluteConstraints(310,30,200,23));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		
	}
	//metodo que mostra os mais populares e preeche a tabela
	private void popularityRecomendations(){
		if(ReadData.getUsuarios().size() > 0 && ReadData.getEstabelecimentos().size() > 0){
			List<Estabelecimento> recomendacoes = algoritmos.executeGenericRecomendations(ReadData.getEstabelecimentos().size());
			preencheTabela(recomendacoes);
			
		}else{
			List<Estabelecimento> temp = new ArrayList<Estabelecimento>();
			preencheTabela(temp);

		}
	}
	private void preencheTabela(List<Estabelecimento> recomendacoes) {
		Object obj[][] = new Object[recomendacoes.size()][3];
		for(int i=0; i < recomendacoes.size(); i++){
			obj[i][0] = recomendacoes.get(i).getNome();
			obj[i][1] = recomendacoes.get(i).getLocalizacao();
			obj[i][2] = recomendacoes.get(i).getTipoDeComida();
		}
		
		tabelaEstabelecimento.setModel(new DefaultTableModel(obj,
				new String[] { "Restaurante", "Localizacao", "Tipo de Comida" }));
		
		//seta o tamanho das colunas
		tabelaEstabelecimento.getColumnModel().getColumn(2).setPreferredWidth(20);	
		tabelaEstabelecimento.getColumnModel().getColumn(1).setPreferredWidth(190);
		tabelaEstabelecimento.getColumnModel().getColumn(0).setPreferredWidth(130);
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == botaoVoltar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new MenuPrincipal());
			MenuInicial.panelCorpo.updateUI();
		}
	}

}