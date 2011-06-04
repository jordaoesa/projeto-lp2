package lp2.algoritmos;
import lp2.lerDados.Usuario;

/**
 * Classe que define o algoritmo de recomendacao por produto escalar entre dois
 * usuarios.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public class ProdutoEscalar implements Comparable<ProdutoEscalar> {

    private Usuario user1;
    private Usuario user2;
    private int produtoEscalar;
    
    /**
     * Metodo que instancia um objeto da classe ProdutoEscalar que eh responsavel
	 * por executar o algoritmo para recomendacao dos estabelecimentos a comer
	 * baseando-se no calculo do produto escalar entre dois usuarios.
	 *
     * @param user1
     * 			usuario 1
     * @param user2
     * 			usuario 2
     * @throws Exception
     * 			lanca uma excecao caso um ou ambos os usuarios sejam null.
     */
    public ProdutoEscalar(Usuario user1, Usuario user2) throws Exception {
    	if(user1 == null || user2 == null){
    		throw new Exception("Usuario null.");
    	}
        this.user1 = user1;
        this.user2 = user2;
    }
    
    
    /**
     * Calcula o produto escalar.
     */
    public void calculaProdutoEscalar() {
        for (int i = 0; i < user1.getOpinioes().size(); i++) {
            produtoEscalar += (user1.getOpinioes().get(i) * user2.getOpinioes().get(i));
        }
    }

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
     * Retorna o produto escalar atual dos dois usuarios.
     * @return
     * 			o produto escalar os dois usuarios.
     */
    public int getProdutoEscalar() {
        return produtoEscalar;
    }

    /**
     * Modifica o produto escalar atual dos dois usuarios.
     * @param produtoEscalar
     * 			o novo valor do produto escalar
     */
    public void setProdutoEscalar(int produtoEscalar) {
        this.produtoEscalar = produtoEscalar;
    }
    
    /**
     * Compara se o produto de escalar de quaisquer dois usuarios sao iguais.
     */
    @Override
    public int compareTo(ProdutoEscalar o) {
        return this.getProdutoEscalar() - o.getProdutoEscalar();
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + produtoEscalar;
		result = prime * result + ((user1 == null) ? 0 : user1.hashCode());
		result = prime * result + ((user2 == null) ? 0 : user2.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ProdutoEscalar))
			return false;
		ProdutoEscalar produto = (ProdutoEscalar) obj;
		if (this.getUser1().equals(produto.getUser1())
				&& this.getUser2().equals(produto.getUser2())
				&& this.getProdutoEscalar() == produto.getProdutoEscalar())
			return true;
		return false;
	}
    
}
