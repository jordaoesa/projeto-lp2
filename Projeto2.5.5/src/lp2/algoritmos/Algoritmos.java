package lp2.algoritmos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lp2.lerDados.*;

/**
 * Classe que define os tipos de algoritmos a serem utilizados no sistema de
 * recomendacao.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 * 
 */
public class Algoritmos {

	private static List<Usuario> usuarios;
	private static List<Estabelecimento> estabelecimentos;

	/**
	 * Metodo que instancia um objeto da classe Algoritmos que eh responsavel
	 * por executar os algoritmos para recomendacao dos estabelecimentos a comer
	 * e fazer comparacoes de performance de recomendacao desses algoritmos.
	 */
	public Algoritmos(){
		setEstabelecimentos(ReadData.getEstabelecimentos());
		setUsuarios(ReadData.getUsuarios());
	}
	
	/**
	 * Metodo responsavel por executar as recomendacoes personalizadas para um
	 * usuario. As recomendacoes personalizadas baseiam-se no valor do produto
	 * escalar entre os vetores de notas de dois usuarios. Quanto maior esse
	 * valor, maior a semelhanca de gostos desses dois usuarios.
	 * @param numRecomend
	 * 			numero de recomendacoes.
	 * @param user
	 * 			usuario.
	 * @return
	 * 			lista dos estabelecimentos recomendados.
	 */
	public List<Estabelecimento> executeScalarProductRecomendations(int numRecomend, Usuario user) {
		
		List<ProdutoEscalar> listaProdutos = new ArrayList<ProdutoEscalar>();
		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> copiaEstabelecimentos;
		
		String nomeOriginal = user.getNome();
		try {
			// definindo um novo nome para o usuario para distingui-lo dos demais.
			// estou fazendo isso para que usuarios com o mesmo nome tbm possam ser testados
			// em criterio de similaridade.
			user.setNome(user.getDataHoraCadastro() + "-" + user.getNome() + "- Nome definido para diferenciar este usuario dos demais." +
					"Este eh o usuario ao qual serao feitas as recomendacoes.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(int i=0; i<usuarios.size(); i++){
			if(!usuarios.get(i).getNome().equals(user.getNome())){
				ProdutoEscalar produto = null;
				try {
					produto = new ProdutoEscalar(user, usuarios.get(i));
					produto.calculaProdutoEscalar();
					listaProdutos.add(produto);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		try {
			// voltando ao nome original do usuario
			user.setNome(nomeOriginal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collections.sort(listaProdutos, Collections.reverseOrder());
		
		Iterator<ProdutoEscalar> it = listaProdutos.iterator();

		while(estabelecimentosRecomendados.size() < numRecomend && it.hasNext()){
			ProdutoEscalar produto = it.next();
			for(int i=0; i<estabelecimentos.size(); i++){
				estabelecimentos.get(i).setNota(produto.getUser2().getOpinioes().get(i));
			}
			
			copiaEstabelecimentos = new ArrayList<Estabelecimento>(estabelecimentos);
			Collections.sort(copiaEstabelecimentos, Collections.reverseOrder());
			
			for(Estabelecimento estabelecimento : copiaEstabelecimentos){
				if (!estabelecimentosRecomendados.contains(estabelecimento)
						&& estabelecimentosRecomendados.size() < numRecomend
						&& estabelecimento.getNota() > 0
						&& produto.getUser1().getOpinioes().get(estabelecimentos.indexOf(estabelecimento)) == 0) {

					estabelecimentosRecomendados.add(estabelecimento);
				}
			}
			if(estabelecimentosRecomendados.size() == numRecomend || !it.hasNext()){
				return estabelecimentosRecomendados;
			}
		}
		
		return new ArrayList<Estabelecimento>();
	}
	
	
	/**
	 * Metodo responsavel por executar as recomendacoes genericas para um
	 * usuario. As recomendacoes genericas baseiam-se na popularidade dos
	 * restaurantes, ou seja, quanto mais popular um restaurante eh, mais provavel eh que o
	 * usuario em questao goste desse restaurante. As recomendacoes genericas sao
	 * quase sempre iguais para todos os usuarios, elas mudam apenas quando a
	 * nota escondida influencia nas recomendacoes.
	 * @param numRecomendacoes
	 * 			numero de recomendacoes.
	 * @return
	 * 			uma lista dos estabelecimentos recomendados.
	 */
	public List<Estabelecimento> executeGenericRecomendations(int numRecomendacoes) {

		final int NUM_AVALIACOES = usuarios.get(0).getOpinioes().size();
		List<Integer> notasEstabelecimentos = new ArrayList<Integer>();
		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> copiaEstabelecimentos;
		
		for(int i=0; i<NUM_AVALIACOES; i++){
			notasEstabelecimentos.add(0);
		}
		for(Usuario user : usuarios){
			for(int i=0; i<user.getOpinioes().size(); i++){
				notasEstabelecimentos.set(i, (notasEstabelecimentos.get(i)+user.getOpinioes().get(i)));
			}
		}
		
		for(int i=0; i<estabelecimentos.size(); i++){
			estabelecimentos.get(i).setNota(notasEstabelecimentos.get(i));
		}
		
		copiaEstabelecimentos = new ArrayList<Estabelecimento>(estabelecimentos);
		Collections.sort(copiaEstabelecimentos, Collections.reverseOrder());
		
		for(int i=0; i<numRecomendacoes; i++){
			if(i < copiaEstabelecimentos.size() && copiaEstabelecimentos.get(i).getNota() > 0){
				estabelecimentosRecomendados.add(copiaEstabelecimentos.get(i));
			}
		}
		return estabelecimentosRecomendados;
	}
	
	private static void setUsuarios(List<Usuario> lista){
		usuarios = lista;
	}
	
	private static void setEstabelecimentos(List<Estabelecimento> lista){
		estabelecimentos = lista;
	}
}
