package lp2.algoritmos;

import lp2.lerDados.Usuario;

/**
 *  Classe que define um dos tipos de algoritmos a serem utilizados 
 * no sistema de recomendacao. A da similaridade de Jaccard.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public class SimilaridadeJaccard extends Algoritmo{

	/**
	 * Construtor do algoritmo da similaridade de Jaccard.
	 * @param user1
	 * 			primeiro usuario.
	 * @param user2
	 * 			segundo usuario.
	 * @throws Exception
	 * 			caso ocorra algum erro.
	 * 
	 */
	public SimilaridadeJaccard(Usuario user1, Usuario user2) throws Exception {
		super(user1, user2);
	}

	/**
	 * Metodo que calcula a similaridade entre quaisquer dois usuarios
	 * baseando-se no calculo da similaridade de Jaccard.
	 */
	@Override
	public void calculaSimilaridade() {
		Double numerador = 0.0;
		Double denominador = 0.0;
		Double resultado = 0.0;
		
		for(int i=0; i<super.getUser1().getOpinioes().size(); i++){
			if(super.getUser1().getOpinioes().get(i).equals(super.getUser2().getOpinioes().get(i)))
				numerador++;
			else
				denominador++;
		}
		resultado = numerador / denominador;
		super.setResultado(resultado);
	}

}
