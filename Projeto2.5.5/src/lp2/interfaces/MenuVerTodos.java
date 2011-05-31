package lp2.interfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import lp2.lerDados.Estabelecimento;
import lp2.lerDados.ReadData;
import lp2.lerDados.Usuario;

/**
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
@SuppressWarnings("serial")
public class MenuVerTodos extends JPanel implements ActionListener{

	private JScrollPane scrollPaneTabelaOpinioes;
	private JScrollPane scrollPaneTabelaResultado;
	private JButton botaoVoltar;
	private JTable tabela;
	private JTable tabelaResultado;
	private JLabel selecioneEstabelecimento;
	private JLabel detalhamentoDeNotas;
	private JComboBox listaSuspensaDeEstabelecimentos;
	private String estabelecimentosCadastrados[];

	public MenuVerTodos(){

		setSize(800,600);
		setVisible(true);
		setLayout(new AbsoluteLayout());
		setBackground(new Color(253, 245, 230));

		instanciaTodosComponentes();
		iniciaComboBoxEstabelecimentos();
		iniciaTabelas();
		addNoContainer();

		botaoVoltar.addActionListener(this);
		listaSuspensaDeEstabelecimentos.addActionListener(this);

	}
	
	private void instanciaTodosComponentes(){

		//tabelas
		tabela = new JTable();
		tabelaResultado = new JTable();
		tabela.setEnabled(false);
		tabelaResultado.setEnabled(false);
		
		//ScroollPanes
		scrollPaneTabelaOpinioes = new JScrollPane();
		scrollPaneTabelaResultado = new JScrollPane();
		scrollPaneTabelaOpinioes.setViewportView(tabela);		
		scrollPaneTabelaResultado.setViewportView(tabelaResultado);	
		
		//Labels
		selecioneEstabelecimento = new JLabel("Selecione o Estabelecimento:");
		detalhamentoDeNotas = new JLabel("Quantidade de votos por tipo de avaliacao");
		
		//Botoes
		botaoVoltar = new JButton("Voltar");	
	}
	
	private void addNoContainer(){
		add(scrollPaneTabelaOpinioes, new AbsoluteConstraints(10,100,776,210));
		add(scrollPaneTabelaResultado, new AbsoluteConstraints(10,350,776,146));
		add(detalhamentoDeNotas, new AbsoluteConstraints(50, 320, 320, 23));
		add(botaoVoltar, new AbsoluteConstraints(600,500,120,23));
		add(selecioneEstabelecimento, new AbsoluteConstraints(50,50,220,23));
		add(listaSuspensaDeEstabelecimentos, new AbsoluteConstraints(270,50,320,23));
	}

	private void iniciaTabelas() {
		tabela.setModel(new DefaultTableModel(new Object[][]{},
				new String[] { "Usuario", "Nota" }));

		tabelaResultado.setModel(new DefaultTableModel(new Object[][]{},
				new String[] { "Avaliacao", "Quantidade de Votos" }));
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
		listaSuspensaDeEstabelecimentos = new JComboBox(estabelecimentosCadastrados);
		
		//texto quando mouse fica sobre o botao.
		listaSuspensaDeEstabelecimentos.setToolTipText("Nome de todos os estabelecimentos");
		botaoVoltar.setToolTipText("Clique para volta ao menu anterior");
	}

	//Metodo que trata todos os eventos
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == listaSuspensaDeEstabelecimentos){
			if(listaSuspensaDeEstabelecimentos.getSelectedIndex() != 0){
				preencheTabela();
				preencheTabelaResultado();
			}else
				iniciaTabelas();
		}

		if(event.getSource() == botaoVoltar){
			MenuInicial.panelCorpo.removeAll();
			MenuInicial.panelCorpo.add(new MenuPrincipal());
			MenuInicial.panelCorpo.updateUI();
		}
	}

	private void preencheTabela(){
		Object table[][] = new Object[ReadData.getUsuarios().size()][2];
		try{
			for(int i=0; i<ReadData.getUsuarios().size(); i++){
				table[i][1] = ReadData.getUsuarios().get(i).getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1);
				table[i][0] = ReadData.getUsuarios().get(i).getNome();
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Selecione um Estabelecimento", "ERRO", JOptionPane.ERROR_MESSAGE);
		}

		tabela.setModel(new DefaultTableModel(table,
				new String[] { "Usuario", "Nota" }));
	}

	private void preencheTabelaResultado() {
		Object table[][] = new Object[11][2]; 
		String avaliacao[] = {"5: Incrivel. sensacional. impressionante","4: Muito bom", "3: Bastante bom", "2: Eh bonzinho",
				"1: Nao eh ruim", "0: Nao conheco","-1: Acho um pouco ruim", "-2: Acho ruim","-3: Acho bastante ruim", 
				"-4: Acho muito ruim","-5: Detesto" };

		int notas[] = new int[11];
		try{
			for(Usuario user : ReadData.getUsuarios()){
				if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == 5){
					notas[0] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == 4){
					notas[1] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == 3){
					notas[2] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == 2){
					notas[3] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == 1){
					notas[4] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == 0){
					notas[5] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == -1){
					notas[6] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == -2){
					notas[7] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == -3){
					notas[8] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == -4){
					notas[9] += 1;
				}else if(user.getOpinioes().get(listaSuspensaDeEstabelecimentos.getSelectedIndex()-1) == -5){
					notas[10] += 1;
				}
			}

			for(int i=0; i<11; i++){
				table[i][0] = avaliacao[i];
				table[i][1] = notas[i];
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Selecione um Estabelecimento", "ERRO", JOptionPane.ERROR_MESSAGE);
		}

		tabelaResultado.setModel(new DefaultTableModel(table,
				new String[] { "Avaliacao", "Quantidade de Votos" }));
	}

}
