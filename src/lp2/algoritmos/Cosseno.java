package lp2.algoritmos;

import lp2.lerDados.Usuario;

/**
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public class Cosseno extends Algoritmo{

	public Cosseno(Usuario user1, Usuario user2) throws Exception {
		super(user1, user2);
	}

	@Override
	public void calculaSimilaridade() {
		Double numerador = 0.0;
		Double denominadorUser1 = 0.0;
		Double denominadorUser2 = 0.0;
		Double resultado = 0.0;
		
		for (int i = 0; i < super.getUser1().getOpinioes().size(); i++) {
			numerador += super.getUser1().getOpinioes().get(i) * super.getUser2().getOpinioes().get(i);
			denominadorUser1 += Math.pow(super.getUser1().getOpinioes().get(i), 2);
			denominadorUser2 += Math.pow(super.getUser2().getOpinioes().get(i), 2);
		}
		resultado = numerador / (Math.sqrt(denominadorUser1) * Math.sqrt(denominadorUser2));
		super.setResultado(resultado);
	}

}
