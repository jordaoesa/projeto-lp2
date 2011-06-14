package lp2.lerDados;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que define o objeto Usuario que utiliza o sistema.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 */
public class Usuario {
    
    private String nome;
    private String dataHoraCadastro;
	private List<Integer> opinioes = new ArrayList<Integer>();

    /**
     * Metodo que instancia um objeto da classe Usuario que eh responsavel
	 * por aramazenar informacoes sobre estes para que o sistema seja capaz de gerar 
	 * recomendacoes baseadas nesses dados.
     * 
     * @param nome
     * 			nome do usuario.
     * @param opinioes
     * 			List com suas opinioes sobre os estabelecimentos.
     * @throws Exception
     * 			caso algum ou ambos os parametros passados seja null.
     */
    public Usuario(String nome, List<Integer> opinioes) throws Exception {
    	if(nome == null || nome.equals(""))
    		throw new Exception("Nome nulo/vazio.");
    	if(opinioes == null || opinioes.isEmpty())
    		throw new Exception("Lista de Opinioes nula/vazia.");
    	
        this.nome = nome;
        this.opinioes = opinioes;
    }

    /**
     * Retorna o nome do usuario.
     * @return
     * 			o nome do usuario.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Modifica o nome atual do usuario.
     * @param nome
     * 			novo nome do usuario.
     * @throws Exception
     * 			caso o novo nome passado seja null.
     */
    public void setNome(String nome) throws Exception {
    	if(nome == null || nome.equals(""))
    		throw new Exception("Nome nulo/vazio.");
        this.nome = nome;
    }

    /**
     * Retorna um List contendo todas as opinioes do usuario.
     * @return
     *			um List contendo todas as opinioes do usuario.
     */
    public List<Integer> getOpinioes() {
        return opinioes;
    }

    /**
     * Modifica o List das opinioes do usuario.
     * @param opinioes
     * 			novo List de opinioes.
     * @throws Exception
     * 			caso o novo List passado seja igual a null.
     */
    public void setOpinioes(List<Integer> opinioes) throws Exception {
    	if(opinioes == null || opinioes.isEmpty())
    		throw new Exception("Lista de Opinioes nula/vazia.");
        this.opinioes = opinioes;
    }
    
    /**
     * Retorna a data e hora em que o usuario fez alguma modificacao em seus dados no sistema.
     * @return
     * 			a data e hora em que o usuario fez alguma modificacao em seus dados no sistema.
     */
    public String getDataHoraCadastro() {
		return dataHoraCadastro;
	}

   /**
    * Modifica a data e hora em que o usuario fez alguma modificacao em seus dados no sistema.
    * @param dataHoraCadastro
    * 			a nova data e hora em que o usuario fez alguma modificacao em seus dados no sistema.
    * @throws Exception
    * 			caso a informacao passada seja igual a null.
    */
	public void setDataHoraCadastro(String dataHoraCadastro) throws Exception{
		if(dataHoraCadastro == null){
			throw new Exception("Data/Hora do Cadastro nula/vazia.");
		}
		this.dataHoraCadastro = dataHoraCadastro;
	}
	
	/**
	 * Compara a precedencia de nosmes dos usuarios.
	 * @param user
	 * @return Retorna um numero indicando o menor ou maior String.
	 */
	public int comparaPorNome(Usuario user){
		return this.getNome().compareToIgnoreCase(user.getNome());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((dataHoraCadastro == null) ? 0 : dataHoraCadastro.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((opinioes == null) ? 0 : opinioes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Usuario))
			return false;
		Usuario usuario = (Usuario) obj;
		if (this.getNome().equals(usuario.getNome())
				&& this.getDataHoraCadastro().equals(
						usuario.getDataHoraCadastro())
				&& this.getOpinioes().equals(usuario.getOpinioes()))
			return true;
		return false;
	}
	
}
