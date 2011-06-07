package lp2.algoritmos;

import lp2.lerDados.Usuario;

/**
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public class ProdutoEscalar extends Algoritmo{

	public ProdutoEscalar(Usuario user1, Usuario user2) throws Exception {
		super(user1, user2);
	}

	@Override
	public void calculaSimilaridade() {
		Double resultado = 0.0;
		for (int i = 0; i < super.getUser1().getOpinioes().size(); i++) {
            resultado += (super.getUser1().getOpinioes().get(i) * super.getUser2().getOpinioes().get(i));
        }
		super.setResultado(resultado);
	}


}
