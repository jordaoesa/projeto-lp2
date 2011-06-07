package lp2.algoritmos;

import lp2.lerDados.Usuario;

/**
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public class CossenoIntersecao extends Algoritmo{

	public CossenoIntersecao(Usuario user1, Usuario user2) throws Exception {
		super(user1, user2);
	}

	@Override
	public void calculaSimilaridade() {
		Double numerador = 0.0;
		Double resultado = 0.0;
		
		for(int i=0; i<super.getUser1().getOpinioes().size(); i++){
			if(super.getUser1().getOpinioes().get(i) == super.getUser2().getOpinioes().get(i)){
				numerador++;
			}
		}

		resultado = numerador /(Math.sqrt(super.getUser1().getOpinioes().size()) * Math.sqrt(super.getUser2().getOpinioes().size()));
		super.setResultado(resultado);
	}

}
