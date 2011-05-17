package lp2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MenuVerTodos extends JFrame{
	Container container = getContentPane();
	
	JScrollPane scrollPane;
	JTextArea campoTexto;
	JButton botaoVoltar;
	
	public MenuVerTodos(){
		
		super("Pitaqueiro das 12h");
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		container.setLayout(null);
		
		scrollPane = new JScrollPane();
		campoTexto = new JTextArea();
		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(600,500,120,23);
		
		campoTexto.setColumns(10);
		campoTexto.setEditable(false);
		campoTexto.setRows(5);
		lerArquivo(campoTexto);
		scrollPane.setViewportView(campoTexto);
		scrollPane.setBounds(10,50,776,450);
		
		container.add(scrollPane);
		container.add(botaoVoltar);
		
		botaoVoltar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MenuVerTodos.this.dispose();
			}
		});
	}
	
	//metodo que ler arquivo txt e joga dentro de JTextArea
	private static void lerArquivo(JTextArea textArea){
		
		 File arquivo = new File("books.txt");
         textArea.setText("");
         
         try {
            BufferedReader input = new BufferedReader(new FileReader(arquivo));
            String linha, texto = "";
            while((linha = input.readLine()) != null){
                texto += linha + "\n";
            }
            textArea.setText(texto);
            input.close();
         } 
         catch (IOException ioe){
        	 //tratar
         }
	}
	
//	public static void main(String args[]){
//		MenuVerTodos menu2 = new MenuVerTodos();
//		menu2.setVisible(true);
//	}
	
}
