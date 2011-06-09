package lp2.algoritmos;

import lp2.lerDados.Usuario;

/**
 * Classe que define um dos tipos de algoritmos a serem utilizados 
 * no sistema de recomendacao. A da similaridade por produto escalar.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public class ProdutoEscalar extends Algoritmo{

	/**
	 * Construtor.
	 * @param user1
	 * 			primeiro usuario.
	 * @param user2
	 * 			segundo usuario.
	 * @throws Exception
	 * 			caso algum erro ocorra.
	 */
	public ProdutoEscalar(Usuario user1, Usuario user2) throws Exception {
		super(user1, user2);
	}

	/**
	 * Metodo que calcula a similaridade entre quaisquer dois usuarios
	 * baseando-se no calculo de seu produto escalar.
	 */
	@Override
	public void calculaSimilaridade() {
		Double resultado = 0.0;
		for (int i = 0; i < super.getUser1().getOpinioes().size(); i++) {
            resultado += (super.getUser1().getOpinioes().get(i) * super.getUser2().getOpinioes().get(i));
        }
		super.setResultado(resultado);
	}


}
