package lp2.lerDados;


/**
 * Classe que define um estabelecimento.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public class Estabelecimento implements Comparable<Estabelecimento> {
    
    private String nome;
    private String localizacao;
    private String tipoDeComida;
    private int nota;

    
    /**
     * Metodo que instancia um objeto da classe Estabelecimento que eh responsavel
	 * por guardar informacoes sobre um determinado restaurante como seu nome, localizacao e tipo de refeicao servida.
	 *
     * @param nome
     * 			nome do estabelecimento.
     * @param localizacao
     * 			localizacao do estabelecimento.
     * @param tipoDeComida
     * 			tipo de comida servida.
     * @throws Exception
     * 			caso algun dos parametros passados seja null.
     */
    public Estabelecimento(String nome, String localizacao, String tipoDeComida) throws Exception {
    	if(nome == null || nome.equals(""))
    		throw new Exception("Nome nulo/vazio.");
    	if(localizacao == null || localizacao.equals(""))
    		throw new Exception("Localizacao nula/vazia.");
    	if(tipoDeComida == null || tipoDeComida.equals(""))
    		throw new Exception("Tipo de comida nulo/vazio.");
    	
        this.nome = nome;
        this.localizacao = localizacao;
        this.tipoDeComida = tipoDeComida;
    }

    /**
     * Retorna o nome do estabelecimento.
     * @return
     * 			o nome do estabelecimento.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Modifica o nome atual do estabelecimento.
     * @param nome
     * 			novo nome do estabelecimento.
     * @throws Exception
     * 			caso o novo nome passado seja igual a null.
     */
    public void setNome(String nome) throws Exception {
    	if(nome == null || nome.equals(""))
    		throw new Exception("Nome nulo/vazio.");
        this.nome = nome;
    }

    /**
     * Retorna a localizacao do estabelecimento.
     * @return
     * 			a localizacao do estabelecimento.
     */
    public String getLocalizacao() {
        return localizacao;
    }

    /**
     * Modifica a localizacao atual do estabelecimento.
     * @param localizacao
     * 			a nova localizacao do estabelecimento.
     * @throws Exception
     * 			caso a nova localizao seja igual a null.
     */
    public void setLocalizacao(String localizacao) throws Exception {
    	if(localizacao == null || localizacao.equals(""))
    		throw new Exception("Localizacao nula/vazia.");
        this.localizacao = localizacao;
    }
    
    /**
     * Retorna o tipo de refeicao servido no estabelecimento.
     * @return
     * 			o tipo de refeicao servido no estabelecimento.
     */
    public String getTipoDeComida() {
        return tipoDeComida;
    }

    /**
     * Modifica o tipo de refeicao servido no estabelecimento.
     * @param tipoDeComida
     * 			novo tipo de refeicao servido no estabelecimeto.
     * @throws Exception
     * 			caso o novo tipo de refeicao passado seja igual a null.
     */
    public void setTipoDeComida(String tipoDeComida) throws Exception {
    	if(tipoDeComida == null || tipoDeComida.equals(""))
    		throw new Exception("Tipo de comida nulo/vazio.");
        this.tipoDeComida = tipoDeComida;
    }

    /**
     * Retorna a nota do estabelecimento.
     * @return
     * 			a nota do estabelecimento.
     */
    public int getNota() {
        return nota;
    }

    /**
     * Modifica a nota atual do estabelecimeto.
     * @param nota
     * 			a nota atual do estabelecimeto.
     */
    public void setNota(int nota) {
        this.nota = nota;
    }
    
    /**
     * Compara se dois estabelecimentos sao iguais mediante a comparacao de suas notas.
     */
    @Override
    public int compareTo(Estabelecimento o) {
        return this.getNota() - o.getNota();
    }

	/**
	 * Compara dois estabelecimentos pelos nomes. Utilizado para ordenacao dos
	 * estabelecimentos em ordem alfabetica.
	 * 
	 * @param outro
	 *            O outro estabelecimentos.
	 * @return Um inteiro representando o nome que vem antes ou depois.
	 */
    public int comparePorNome(Estabelecimento outro){
    	return this.getNome().compareTo(outro.getNome());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((localizacao == null) ? 0 : localizacao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + nota;
		result = prime * result
				+ ((tipoDeComida == null) ? 0 : tipoDeComida.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Estabelecimento))
			return false;
		Estabelecimento estabelecimento = (Estabelecimento) obj;
		if (this.getNome().equals(estabelecimento.getNome())
				&& this.getLocalizacao().equals(
						estabelecimento.getLocalizacao())
				&& this.getTipoDeComida().equals(
						estabelecimento.getTipoDeComida())
				&& this.getNota() == estabelecimento.getNota())
			return true;
		return false;
	}
	@Override
	public String toString(){
		return getNome() + ", " + getLocalizacao() + "," + getTipoDeComida(); 
	}
    
}
