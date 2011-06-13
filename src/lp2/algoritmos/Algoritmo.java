package lp2.algoritmos;

import lp2.lerDados.Usuario;

/**
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public abstract class Algoritmo implements Comparable<Algoritmo> {

    private Usuario user1;
    private Usuario user2;
    private double resultado;
    
    /**
	 *
     * @param user1
     * 			usuario 1
     * @param user2
     * 			usuario 2
     * @throws Exception
     * 			lanca uma excecao caso um ou ambos os usuarios sejam null.
     */
    public Algoritmo(Usuario user1, Usuario user2) throws Exception {
    	if(user1 == null || user2 == null){
    		throw new Exception("Usuario null.");
    	}
        this.user1 = user1;
        this.user2 = user2;
    }
    
    
    /**
     * Metodo que calcula a similaridade entre quaisquer dois usuarios
	 * baseando-se no calculo do algoritmo em questao.
     */
    public abstract void calculaSimilaridade();

    /**
     * Retorna o atual usuario 1
     * @return
     * 			o usuario 1
     */
    public Usuario getUser1() {
        return user1;
    }

    /**
     * Modifica o atual usuario 1
     * @param user1
     * 			novo usuario 1
     * @throws Exception
     * 			caso o novo usuario passado for null.
     */
    public void setUser1(Usuario user1) throws Exception {
    	if(user1 == null)
    		throw new Exception("Usuario null.");
        this.user1 = user1;
    }

    /**
     * Retorna o atual usuario 2
     * @return
     * 			o usuario 2
     */
    public Usuario getUser2() {
        return user2;
    }

    /**
     * Modifica o atual usuario 2
     * @param user2
     * 			novo usuario 2
     * @throws Exception
     * 			caso o novo usuario passado seja null.
     */
    		
    public void setUser2(Usuario user2) throws Exception {
    	if(user2 == null)
    		throw new Exception("Usuario null.");
        this.user2 = user2;
    }

    /**
     * Retorna o resultado atual.
     * @return
     * 			o resultado atual.
     * 
     */
    public double getResultado() {
		return resultado;
	}
    
    /**
     * Modifica o resultado atual.
     * @param resultado
     * 			o novo resultado.
     */
	public void setResultado(double resultado) {
		this.resultado = resultado;
	}

	/**
     * Compara se o produto de escalar de quaisquer dois usuarios sao iguais.
     */
    @Override
    public int compareTo(Algoritmo o) {
    	if(resultado < o.getResultado())
			return -1;
		else if(resultado == o.getResultado())
			return 0;
		else
			return 1;
    }
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(resultado);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((user1 == null) ? 0 : user1.hashCode());
		result = prime * result + ((user2 == null) ? 0 : user2.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Algoritmo))
			return false;
		Algoritmo algoritmo = (Algoritmo) obj;
		if (this.getUser1().equals(algoritmo.getUser1())
				&& this.getUser2().equals(algoritmo.getUser2())
				&& this.getResultado() == algoritmo.getResultado())
			return true;
		return false;
	}
	
}
