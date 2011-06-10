package lp2.Threads;

import java.util.List;

import javax.swing.JTable;
import javax.swing.JWindow;
import javax.swing.table.DefaultTableModel;
import lp2.interfaces.*;
import lp2.algoritmos.Algoritmos;

public class ComparaAlgoritmos implements Runnable{
	Algoritmos algoritmos;
	int numRecomendado; 
	JWindow window;
	JTable tableUser;
	JTable tableFinais;

	public ComparaAlgoritmos(JWindow window,int numRecomendado,JTable tabelaUsuarios,JTable tabelaFinais){
		this.window = window;
		this.numRecomendado = numRecomendado;
		this.tableUser = tabelaUsuarios;
		this.tableFinais = tabelaFinais;
		algoritmos = new Algoritmos();
	}

	public void run() {  
		List<List> comparacoes = algoritmos.compareAlgorithms(numRecomendado);
		preencheTabelaComparacoesUsuarios((List<List<String>>)comparacoes.get(0));
		preencheTabelaComparacoesFinais((List<String>)comparacoes.get(1));
		window.dispose();
	}

	private void preencheTabelaComparacoesUsuarios(List<List<String>> comparacoesUsuarios){
		Object obj[][] = new Object[comparacoesUsuarios.size()][10];
		for(int i=0; i<comparacoesUsuarios.size(); i++){
			obj[i][0] = comparacoesUsuarios.get(i).get(0);
			obj[i][1] = comparacoesUsuarios.get(i).get(1);
			obj[i][2] = comparacoesUsuarios.get(i).get(2);
			obj[i][3] = comparacoesUsuarios.get(i).get(3);
			obj[i][4] = comparacoesUsuarios.get(i).get(4);
			obj[i][5] = comparacoesUsuarios.get(i).get(5);
			obj[i][6] = comparacoesUsuarios.get(i).get(6);
			obj[i][7] = comparacoesUsuarios.get(i).get(7);
			obj[i][8] = comparacoesUsuarios.get(i).get(8);
			obj[i][9] = comparacoesUsuarios.get(i).get(9);
		}

		this.tableUser.setModel(new DefaultTableModel(obj,
				new String[] { "Nome", "Opiniões Positivas", "Opiniões Cadastradas", "Porcentagem Popularidade", "Porcentagem Produto Escalar", "Porcentagem Cosseno", "Porcentagem Cosseno Interseção", "Porcentagem Similaridade Dice", "Porcentagem Similaridade Jaccard", "Porcentagem Similaridade Overlap" }));
		MenuComparaAlgoritmos.setTabelaPorUsuario();
	}

	private void preencheTabelaComparacoesFinais(List<String> comparacoesFinais){
		Object obj[][] = new Object[1][9];
		for(int i=0; i<comparacoesFinais.size(); i++){
			obj[0][i] = comparacoesFinais.get(i);
		}
		this.tableFinais.setModel(new DefaultTableModel(obj,
				new String[] { "Opiniões Positivas do Sistema", "Opiniões Cadastradas no Sistema", "Porcentagem Popularidade", "Porcentagem Produto Escalar", "Porcentagem Cosseno", "Porcentagem Cosseno Interseção", "Porcentagem Similaridade Dice", "Porcentagem Similaridade Jaccard", "Porcentagem Similaridade Overlap" }));
		MenuComparaAlgoritmos.setTamanhoTabelaFinais();
	}
}
