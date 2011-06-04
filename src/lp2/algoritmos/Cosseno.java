package lp2.algoritmos;

import lp2.lerDados.Usuario;


/**
 * 
 * 
 * @author jordaoesa<br>
 * <br>
 * 
 *         Aluno: Jordao Ezequiel Serafim de Araujo <br>
 *         Matricula: 21021526<br>
 * <br>
 */
public class Cosseno implements Comparable<Cosseno> {

	private Usuario user1;
	private Usuario user2;
	private double cosseno;

	/**
	 * 
	 * 
	 * @param user1
	 *            Um usuario qualquer.
	 * @param user2
	 *            Um outro usuario qualquer diferente de user1.
	 */
	public Cosseno(Usuario user1, Usuario user2) {
		this.user1 = user1;
		this.user2 = user2;
		this.cosseno = 0;
	}

	/**
	 * 
	 */
	public void calculaCosseno() {
		double numerador = 0;
		double denominadorUser1 = 0;
		double denominadorUser2 = 0;
		
		for (int i = 0; i < user1.getOpinioes().size(); i++) {
			numerador += user1.getOpinioes().get(i) * user2.getOpinioes().get(i);
			denominadorUser1 += Math.pow(user1.getOpinioes().get(i), 2);
			denominadorUser2 += Math.pow(user2.getOpinioes().get(i), 2);
		}
		cosseno = numerador / (Math.sqrt(denominadorUser1) * Math.sqrt(denominadorUser2));
	}

	/**
	 * Retorna o primeiro usuario.
	 * 
	 * @return O user1.
	 */
	public Usuario getUser1() {
		return user1;
	}

	/**
	 * Define um novo usuario 1.
	 * 
	 * @param user1
	 *            Novo user1.
	 */
	public void setUser1(Usuario user1) {
		this.user1 = user1;
	}

	/**
	 * Retorna o usuario 2.
	 * 
	 * @return user2.
	 */
	public Usuario getUser2() {
		return user2;
	}

	/**
	 * Define um novo usuario 2.
	 * 
	 * @param user2
	 *            Novo user2.
	 */
	public void setUser2(Usuario user2) {
		this.user2 = user2;
	}

	public double getCosseno() {
		return cosseno;
	}

	public void setCosseno(double cosseno) {
		this.cosseno = cosseno;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(Cosseno o) {
		if(cosseno < 0.0)
			return -1;
		else if(cosseno == 0.0)
			return 0;
		else
			return 1;
	}

}
