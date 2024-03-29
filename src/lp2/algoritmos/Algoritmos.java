package lp2.algoritmos;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lp2.interfaces.MenuGeraRecomendacao;
import lp2.lerDados.Estabelecimento;
import lp2.lerDados.ReadData;
import lp2.lerDados.Usuario;

/**
 * Classe que define dois dos tipos de algoritmos a serem utilizados 
 * no sistema de recomendacao.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 * 
 */
public class Algoritmos {

	private static List<Usuario> usuarios;
	private static List<Estabelecimento> estabelecimentos;
	private List<Algoritmo> listaAlgoritmos;

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
	 * Executa um algoritmo de acordo com a String tipo selecionado.
	 * 
	 * @param numRecomendacoes
	 *            O numero de recomendacoes que o algoritmo deve tentar fazer.
	 * @param tipo
	 *            O tipo de algoritmo a ser rodado.
	 * @param user
	 *            O usuario ao qual se quer fazer as recomendacoes.
	 * @return A lista de estabelecimentos recomendados.
	 */
	public List<List<Estabelecimento>> executeAlgoritmo(int numRecomendacoes, TipoAlgoritmoPersonalizado tipo, Usuario user){
		
		listaAlgoritmos = new ArrayList<Algoritmo>();
		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> estabelecimentosNaoRecomendados = new ArrayList<Estabelecimento>();
		List<List<Estabelecimento>> returnList = new ArrayList<List<Estabelecimento>>();
		List<Estabelecimento> copiaEstabelecimentos = new ArrayList<Estabelecimento>();
		
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
		
		calculaSimilaridade(user, tipo);
		
		try {
			// voltando ao nome original do usuario
			user.setNome(nomeOriginal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		selecionaEstabelecimentosRecomendados(estabelecimentosRecomendados, copiaEstabelecimentos, numRecomendacoes);
		selecionaEstabelecimentosNaoRecomendados(estabelecimentosNaoRecomendados, estabelecimentosRecomendados, copiaEstabelecimentos, numRecomendacoes);
		
		returnList.add(estabelecimentosRecomendados);
		returnList.add(estabelecimentosNaoRecomendados);

		return returnList;
	}


	/**
	 * Metodo responsavel por verificar o tipo do algoritmo a ser executado e
	 * calcular a similaridade usuario-usuario.
	 * 
	 * @param user
	 *            O usuario para o qual se quer os similares.
	 * @param tipo
	 *            O tipo do algoritmo a ser executado.
	 */
	private void calculaSimilaridade(Usuario user, TipoAlgoritmoPersonalizado tipo){
		
		for(int i=0; i<usuarios.size(); i++){
			if(!usuarios.get(i).getNome().equals(user.getNome())){
				try{
					Algoritmo algoritmo = null;
					if(tipo.equals(TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR)){
						algoritmo = new ProdutoEscalar(user, usuarios.get(i));
					}else if(tipo.equals(TipoAlgoritmoPersonalizado.COSSENO)){
						algoritmo = new Cosseno(user, usuarios.get(i));
					}else if(tipo.equals(TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO)){
						algoritmo = new CossenoIntersecao(user, usuarios.get(i));
					}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE)){
						algoritmo = new SimilaridadeDice(user, usuarios.get(i));
					}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD)){
						algoritmo = new SimilaridadeJaccard(user, usuarios.get(i));
					}else if(tipo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP)){
						algoritmo = new SimilaridadeOverlap(user, usuarios.get(i));
					}
					algoritmo.calculaSimilaridade();
					listaAlgoritmos.add(algoritmo);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	/**
	 * Metodo responsavel por percorrer a lista de algoritmos e procurar os
	 * estabelecimentos mais recomendados para o usuario.
	 * 
	 * @param estabelecimentosRecomendados
	 *            A lista de estabelecimentos recomendados.
	 * @param copiaEstabelecimentos
	 *            Uma copia da lista de estabelecimentos.
	 * @param numRecomendacoes
	 *            O numero de estabelecimentos a ser recomendado.
	 */
	private void selecionaEstabelecimentosRecomendados(List<Estabelecimento> estabelecimentosRecomendados, List<Estabelecimento> copiaEstabelecimentos, int numRecomendacoes){
		
		Collections.sort(listaAlgoritmos, Collections.reverseOrder());
		Iterator<Algoritmo> it = listaAlgoritmos.iterator();

		while(estabelecimentosRecomendados.size() < numRecomendacoes && it.hasNext()){
			Algoritmo algoritmo = it.next();
			for(int i=0; i<estabelecimentos.size(); i++){
				estabelecimentos.get(i).setNota(algoritmo.getUser2().getOpinioes().get(i));
			}
			
			//if(tipo.equals(Estabelecimentos.RECOMENDADOS)){
			copiaEstabelecimentos = new ArrayList<Estabelecimento>(estabelecimentos);
			Collections.sort(copiaEstabelecimentos, Collections.reverseOrder());
			
			for(Estabelecimento estabelecimento : copiaEstabelecimentos){
				if (!estabelecimentosRecomendados.contains(estabelecimento)
						&& !MenuGeraRecomendacao.estabelecimentosRemovidos.contains(estabelecimento.getNome())
						&& estabelecimentosRecomendados.size() < numRecomendacoes
						&& estabelecimento.getNota() > 0
						&& algoritmo.getUser1().getOpinioes().get(estabelecimentos.indexOf(estabelecimento)) == 0) {

					estabelecimentosRecomendados.add(estabelecimento);
				}
			}
			if(estabelecimentosRecomendados.size() == numRecomendacoes || !it.hasNext()){
				break;
			}
		}
		
	}

	/**
	 * Metodo responsavel por percorrer a lista de algoritmos e procurar os
	 * estabelecimentos menos recomendados para o usuario.
	 * 
	 * @param estabelecimentosNaoRecomendados
	 *            A lista de estabelecimentos nao recomendados.
	 * @param estabelecimentosRecomendados
	 *            A lista de estabelecimentos recomendados.
	 * @param copiaEstabelecimentos
	 *            Uma copia da lista de estabelecimentos.
	 * @param numRecomendacoes
	 *            O numero de estabelecimentos a ser recomendado.
	 */
	private void selecionaEstabelecimentosNaoRecomendados(List<Estabelecimento> estabelecimentosNaoRecomendados, List<Estabelecimento> estabelecimentosRecomendados, List<Estabelecimento> copiaEstabelecimentos, int numRecomendacoes) {

		//Collections.sort(listaAlgoritmos, Collections.reverseOrder());
		Iterator<Algoritmo> itContrario = listaAlgoritmos.iterator();

		while(estabelecimentosNaoRecomendados.size() < numRecomendacoes && itContrario.hasNext()){
			Algoritmo algoritmoContrario = itContrario.next();
			for(int i=0; i<estabelecimentos.size(); i++){
				estabelecimentos.get(i).setNota(algoritmoContrario.getUser2().getOpinioes().get(i));
			}
			
			copiaEstabelecimentos = new ArrayList<Estabelecimento>(estabelecimentos);
			Collections.sort(copiaEstabelecimentos);
			
			for(Estabelecimento estabelecimento : copiaEstabelecimentos){
				if (!estabelecimentosNaoRecomendados.contains(estabelecimento)
						&& !estabelecimentosRecomendados.contains(estabelecimento)
						&& estabelecimentosNaoRecomendados.size() < numRecomendacoes
						&& estabelecimento.getNota() < 0
						&& algoritmoContrario.getUser1().getOpinioes().get(estabelecimentos.indexOf(estabelecimento)) == 0) {

					estabelecimentosNaoRecomendados.add(estabelecimento);
				}
			}
			if(estabelecimentosNaoRecomendados.size() == numRecomendacoes || !itContrario.hasNext()){
				break;
			}
		}
		
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
	public List<List<Estabelecimento>> executeGenericRecomendations(int numRecomendacoes) {

		final int NUM_AVALIACOES = usuarios.get(0).getOpinioes().size();
		List<Integer> notasEstabelecimentos = new ArrayList<Integer>();
		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> estabelecimentosNaoRecomendados = new ArrayList<Estabelecimento>();
		List<List<Estabelecimento>> returnList = new ArrayList<List<Estabelecimento>>();
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
		
		Collections.sort(copiaEstabelecimentos);
		
		for(int i=0; i<numRecomendacoes; i++){
			if(i < copiaEstabelecimentos.size() && copiaEstabelecimentos.get(i).getNota() <= 0 && !estabelecimentosRecomendados.contains(copiaEstabelecimentos.get(i))){
				estabelecimentosNaoRecomendados.add(copiaEstabelecimentos.get(i));
			}
		}
		
		returnList.add(estabelecimentosRecomendados);
		returnList.add(estabelecimentosNaoRecomendados);
		
		return returnList;
	}
	
	/**
	 * Compara todos os algoritmos de recomendacao implementados.
	 * 
	 * @param numRecomendacoes
	 */
	@SuppressWarnings("rawtypes")
	public List<List> compareAlgorithms(int numRecomendacoes) {
		int totalAcertosGenericos = 0;
		int totalAcertosProdutoEscalar = 0;
		int totalAcertosCosseno = 0;
		int totalAcertosCossenoIntersecao = 0;
		int totalAcertosSimilaridadeDice = 0;
		int totalAcertosSimilaridadeJaccard = 0;
		int totalAcertosSimilaridadeOverlap = 0;
		
		List<List> returnList = new ArrayList<List>();
		List<List<String>> comparacoesUsuarios = new ArrayList<List<String>>();
		List<String> comparacaoFinal = new ArrayList<String>();
		
		NumberFormat nfDouble = NumberFormat.getNumberInstance();
		nfDouble.setMinimumFractionDigits(2);
		nfDouble.setMaximumFractionDigits(2);
		
		for(Usuario user : usuarios){
			int acertosGenericos = 0;
			int acertosProdutoEscalar = 0;
			int acertosCosseno = 0;
			int acertosCossenoIntersecao = 0;
			int acertosSimilaridadeDice = 0;
			int acertosSimilaridadeJaccard = 0;
			int acertosSimilaridadeOverlap = 0;
			
			for(int i=0; i<user.getOpinioes().size(); i++){
				if(user.getOpinioes().get(i) > 0){
					Estabelecimento estabelecimentoEscondido = estabelecimentos.get(i);
					int avaliacaoOriginal = user.getOpinioes().get(i);
					
					user.getOpinioes().set(i, 0); // escondendo estabelecimento
					
					List<Estabelecimento> estabelecimentosGenericos = executeGenericRecomendations(numRecomendacoes).get(0);
					List<Estabelecimento> estabelecimentosProdutoEscalar = executeAlgoritmo(numRecomendacoes, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user).get(0);
					List<Estabelecimento> estabelecimentosCosseno = executeAlgoritmo(numRecomendacoes, TipoAlgoritmoPersonalizado.COSSENO, user).get(0);
					List<Estabelecimento> estabelecimentosCossenoIntersecao = executeAlgoritmo(numRecomendacoes, TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, user).get(0);
					List<Estabelecimento> estabelecimentosSimilaridadeDice = executeAlgoritmo(numRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, user).get(0);
					List<Estabelecimento> estabelecimentosSimilaridadeJaccard = executeAlgoritmo(numRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, user).get(0);
					List<Estabelecimento> estabelecimentosSimilaridadeOverlap = executeAlgoritmo(numRecomendacoes, TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, user).get(0);
					
					user.getOpinioes().set(i, avaliacaoOriginal); // redefinindo a nota original do estabelecimento escondido
					
					for(Estabelecimento estabelecimento : estabelecimentosGenericos){
						if(estabelecimento.getNome().equals(estabelecimentoEscondido.getNome())){
							acertosGenericos++;
							break;
						}
					}
					
					for(Estabelecimento estabelecimento : estabelecimentosProdutoEscalar){
						if(estabelecimento.getNome().equals(estabelecimentoEscondido.getNome())){
							acertosProdutoEscalar++;
							break;
						}
					}
					
					for(Estabelecimento estabelecimento : estabelecimentosCosseno){
						if(estabelecimento.getNome().equals(estabelecimentoEscondido.getNome())){
							acertosCosseno++;
							break;
						}
					}
					
					for(Estabelecimento estabelecimento : estabelecimentosCossenoIntersecao){
						if(estabelecimento.getNome().equals(estabelecimentoEscondido.getNome())){
							acertosCossenoIntersecao++;
							break;
						}
					}
					
					for(Estabelecimento estabelecimento : estabelecimentosSimilaridadeDice){
						if(estabelecimento.getNome().equals(estabelecimentoEscondido.getNome())){
							acertosSimilaridadeDice++;
							break;
						}
					}
					
					for(Estabelecimento estabelecimento : estabelecimentosSimilaridadeJaccard){
						if(estabelecimento.getNome().equals(estabelecimentoEscondido.getNome())){
							acertosSimilaridadeJaccard++;
							break;
						}
					}
					
					for(Estabelecimento estabelecimento : estabelecimentosSimilaridadeOverlap){
						if(estabelecimento.getNome().equals(estabelecimentoEscondido.getNome())){
							acertosSimilaridadeOverlap++;
							break;
						}
					}
					
				}
			}
			
			totalAcertosGenericos += acertosGenericos;
			totalAcertosProdutoEscalar += acertosProdutoEscalar;
			totalAcertosCosseno += acertosCosseno;
			totalAcertosCossenoIntersecao += acertosCossenoIntersecao;
			totalAcertosSimilaridadeDice += acertosSimilaridadeDice;
			totalAcertosSimilaridadeJaccard += acertosSimilaridadeJaccard;
			totalAcertosSimilaridadeOverlap += acertosSimilaridadeOverlap;
			
			List<String> comparacaoPorUsuario = new ArrayList<String>();
			
			comparacaoPorUsuario.add(user.getNome());
			comparacaoPorUsuario.add(String.valueOf(getOpinioesPositivasUser(user)));
			comparacaoPorUsuario.add(String.valueOf(getOpinioesCadastradasUser(user)));
			
			if(getOpinioesPositivasUser(user) > 0){
				comparacaoPorUsuario.add(nfDouble.format((acertosGenericos / (double)getOpinioesPositivasUser(user))*100) + "%");
				comparacaoPorUsuario.add(nfDouble.format((acertosProdutoEscalar / (double)getOpinioesPositivasUser(user))*100) + "%");
				comparacaoPorUsuario.add(nfDouble.format((acertosCosseno / (double)getOpinioesPositivasUser(user))*100) + "%");
				comparacaoPorUsuario.add(nfDouble.format((acertosCossenoIntersecao / (double)getOpinioesPositivasUser(user))*100) + "%");
				comparacaoPorUsuario.add(nfDouble.format((acertosSimilaridadeDice / (double)getOpinioesPositivasUser(user))*100) + "%");
				comparacaoPorUsuario.add(nfDouble.format((acertosSimilaridadeJaccard / (double)getOpinioesPositivasUser(user))*100) + "%");
				comparacaoPorUsuario.add(nfDouble.format((acertosSimilaridadeOverlap / (double)getOpinioesPositivasUser(user))*100) + "%");
			}else{
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
			}
			comparacoesUsuarios.add(comparacaoPorUsuario);
			
		}
		comparacaoFinal.add(String.valueOf(getOpinioesPositivasSistema()));
		comparacaoFinal.add(String.valueOf(getOpinioesCadastradasSistema()));

		if(getOpinioesPositivasSistema() > 0){
			comparacaoFinal.add(nfDouble.format((totalAcertosGenericos / (double) getOpinioesPositivasSistema())*100) + "%");
			comparacaoFinal.add(nfDouble.format((totalAcertosProdutoEscalar / (double) getOpinioesPositivasSistema())*100) + "%");
			comparacaoFinal.add(nfDouble.format((totalAcertosCosseno / (double) getOpinioesPositivasSistema())*100) + "%");
			comparacaoFinal.add(nfDouble.format((totalAcertosCossenoIntersecao / (double) getOpinioesPositivasSistema())*100) + "%");
			comparacaoFinal.add(nfDouble.format((totalAcertosSimilaridadeDice / (double) getOpinioesPositivasSistema())*100) + "%");
			comparacaoFinal.add(nfDouble.format((totalAcertosSimilaridadeJaccard / (double) getOpinioesPositivasSistema())*100) + "%");
			comparacaoFinal.add(nfDouble.format((totalAcertosSimilaridadeOverlap / (double) getOpinioesPositivasSistema())*100) + "%");
		}else{
			comparacaoFinal.add(nfDouble.format(0) + "%");
			comparacaoFinal.add(nfDouble.format(0) + "%");
			comparacaoFinal.add(nfDouble.format(0) + "%");
			comparacaoFinal.add(nfDouble.format(0) + "%");
			comparacaoFinal.add(nfDouble.format(0) + "%");
			comparacaoFinal.add(nfDouble.format(0) + "%");
			comparacaoFinal.add(nfDouble.format(0) + "%");
		}
		
		returnList.add(comparacoesUsuarios);
		returnList.add(comparacaoFinal);
		
		return returnList;
	}
	
	/**
	 * Metodo responsavel por verificar a quantidade de opinioes positivas de
	 * todos os usuarios. Ou seja, a quantidade de opinioes maiores que 0.
	 * 
	 * @return A quantidade de opinioes positivas.
	 */
	private static int getOpinioesPositivasSistema() {
		int numOpinioesPositivas = 0;
		for(Usuario user : usuarios){
			for(Integer opiniao : user.getOpinioes()){
				if(opiniao > 0){
					numOpinioesPositivas++;
				}
			}
		}
		return numOpinioesPositivas;
	}
	
	private static int getOpinioesCadastradasSistema(){
		int numOpinioesCadastradas = 0;
		for(Usuario user : usuarios){
			for(Integer opiniao : user.getOpinioes()){
				if(opiniao != 0){
					numOpinioesCadastradas++;
				}
			}
		}
		return numOpinioesCadastradas;
	}

	/**
	 * Metodo responsavel por verificar a quantidade de opinioes cadastradas de
	 * um usuario. Ou seja, todas as opinioes diferentes de 0.
	 * 
	 * @param user
	 *            O usuario que se deseja o numero de opinioes cadastradas.
	 * @return A quantidade de opinioes cadastradas desse usuario.
	 */
	private static int getOpinioesCadastradasUser(Usuario user) {
		int numOpinioesCadastradas = 0;
		for(Integer opiniao : user.getOpinioes()){
			if(opiniao != 0){
				numOpinioesCadastradas++;
			}
		}
		return numOpinioesCadastradas;
	}

	/**
	 * Metodo responsavel por verificar a quantidade de opinioes positivas de um
	 * usuario. Ou seja, a quantidade de opinioes maiores que 0.
	 * 
	 * @param user
	 *            O usuario que se deseja o numero de opinioes cadastradas.
	 * @return A quantidade de opinioes positivas desse usuario.
	 */
	private static int getOpinioesPositivasUser(Usuario user) {
		int numOpinioesPositivas = 0;
		for(Integer opiniao : user.getOpinioes()){
			if(opiniao > 0){
				numOpinioesPositivas++;
			}
		}
		return numOpinioesPositivas;
	}
	
	
	/***
	 * Metodo que tem a funcao de retornar o nome dos estabelecimentos que um determinado usuario recomendou para outro.
	 * 
	 * @param user
	 * 			Usuario que deseja ter recomendacoes.
	 * @param tipo
	 * 			tipo de algoritmo escolhido para o mesmo.
	 * @return
	 * 			um list de list de string contendo o nome dos estabelecimentos.
	 */
	public List<List<String>> topFiveSimilarities(Usuario user, TipoAlgoritmoPersonalizado tipo) {
		listaAlgoritmos = new ArrayList<Algoritmo>();
		List<List<String>> returnList = new ArrayList<List<String>>();
		final int NUM_USUARIOS_SIMILARES = 10;

		calculaSimilaridade(user, tipo);
		
		Collections.sort(listaAlgoritmos, Collections.reverseOrder());
		for (int i = 0; i < listaAlgoritmos.size() && returnList.size() < NUM_USUARIOS_SIMILARES; i++) {
			List<String> formatacaoDeLinha = new ArrayList<String>();
			formatacaoDeLinha.add(listaAlgoritmos.get(i).getUser2().getNome());
			formatacaoDeLinha.addAll(estabelecimentosRecomendadosPor_Para(listaAlgoritmos.get(i).getUser2(), listaAlgoritmos.get(i).getUser1()));
			returnList.add(formatacaoDeLinha);
		}

		return returnList;
	}

	/**
	 * Metodo que retorna a lista de estabelecimentos recomendados de um usuario
	 * para outro
	 * 
	 * @param similar
	 *            O usuario similar que vai recomendar.
	 * @param user
	 *            O usuario que recebera as recomendacoes.
	 * @return A lista com os nomes dos estabelecimentos recomendados.
	 */
	private List<String> estabelecimentosRecomendadosPor_Para(Usuario similar, Usuario user) {
		List<String> returnList = new ArrayList<String>();
		final int NUM_ESTAB_RECOMEND = 5;
		final int ESTAB_DESCONHECIDO = 0;
		
		for(int i=0; i<estabelecimentos.size(); i++){
			estabelecimentos.get(i).setNota(similar.getOpinioes().get(i));
		}
		
		List<Estabelecimento> copiaEstabelecimentos = new ArrayList<Estabelecimento>(estabelecimentos);
		Collections.sort(copiaEstabelecimentos, Collections.reverseOrder());
		
		for(Estabelecimento estabelecimento : copiaEstabelecimentos){
			if (!returnList.contains(estabelecimento.getNome())
					&& returnList.size() < NUM_ESTAB_RECOMEND
					&& estabelecimento.getNota() > 0
					&& user.getOpinioes().get(estabelecimentos.indexOf(estabelecimento)) == ESTAB_DESCONHECIDO) {

				returnList.add(estabelecimento.getNome());
			}
		}
	
		return returnList;
	}

	//*** 
	/**
	 * Metodo responsavel por executar as recomendacoes personalizadas para um
	 * usuario, e filtrar as buscas por palavra-chave. As recomendacoes personalizdas baseiam-se na similaridade de gostos
	 * de restaurantes entre dois usuarios, ou seja, quanto mais parecidas suas opinioes sobre um restaurante, mais provavel eh que o
	 * usuario em questao goste desse restaurante. As recomendacoes personalizadas sao
	 * sempre distintas para os usuarios, pois a
	 * nota influencia nas recomendacoes, e o filtro por palavra-chave tambem.
	 * 
	 * @param numRecomendacoes
	 * 			quantidade de recomendacoes desejadas.
	 * @param user
	 * 			Usuario que deseja as recomendacoes.
	 * @param palavraChave
	 * 			palavra-chave a ser filtrada.
	 * @return
	 * 			um list com os estabelecimentos recomendados filtrados.
	 */
	public List<Estabelecimento> executeScalarProductRecomendationsFilter(int numRecomendacoes, Usuario user, String palavraChave) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> estabelecimentosRec = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user).get(0);
		
		int i = 0;
		while (i < estabelecimentosRec.size() && estabelecimentosRecomendados.size() < numRecomendacoes){
				if((estabelecimentosRec.get(i).getNome().toLowerCase().contains(palavraChave.trim().toLowerCase()))){
					//continue;
				}else{
					estabelecimentosRecomendados.add(estabelecimentosRec.get(i));
				}
				
				i++;
			
		}
		return estabelecimentosRecomendados;
	}
	
	//***
	/**
	 * Metodo responsavel por executar as recomendacoes genericas para um
	 * usuario, e filtrar as buscas por palavra-chave. As recomendacoes genericas baseiam-se na popularidade
	 * dos restaurantes, ou seja, quanto mais popular um restaurante eh, mais provavel eh que o
	 * usuario em questao goste desse restaurante. As recomendacoes genericas sao quase
	 * sempre iguais para todos os usuarios, elas mudam apenas quando a
	 * nota escondida influencia nas recomendacoes, e quando a palavra-chave a ser filtrada difere.
	 * 
	 * @param numRecomendacoes
	 * 			quantidade de recomendacoes desejadas.
	 * @param palavraChave
	 * 			palavra-chave a ser filtrada.
	 * @return
	 * 			um list com os estabelecimentos recomendados.
	 */
	public List<Estabelecimento> executeGenericRecomendationsFilter(int numRecomendacoes, String palavraChave) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> Estabelecimentos = executeGenericRecomendations(estabelecimentos.size()).get(0);
		
		int i = 0;
		while (i < Estabelecimentos.size() && estabelecimentosRecomendados.size() < numRecomendacoes){
				if((Estabelecimentos.get(i).getNome().toLowerCase().contains(palavraChave.trim().toLowerCase()))){
					//continue;
				}else{
					estabelecimentosRecomendados.add(Estabelecimentos.get(i));
				}
					
				
				
				i++;	
		}
		return estabelecimentosRecomendados;
	}
	
	
	//****
	/**
	 *Metodo responsavel por executar as recomendacoes genericas para um
	 * usuario, e filtrar as buscas por tipo de estabelecimento. As recomendacoes genericas baseiam-se na popularidade
	 * dos restaurantes, ou seja, quanto mais popular um restaurante eh, mais provavel eh que o
	 * usuario em questao goste desse restaurante. As recomendacoes genericas sao quase
	 * sempre iguais para todos os usuarios, elas mudam apenas quando a
	 * nota escondida influencia nas recomendacoes, e quando o tipo de estabelecimento a ser filtrado difere.
	 * 
	 * @param numRecomendacoes
	 * 			quantidade de recomendacoes desejadas.
	 * @param type
	 * 			tipo de estabelecimento, o qual deseja filtrar.
	 * @return
	 * 			um list dos estabelecimentos recomendados.
	 */
	public List<Estabelecimento> executeGenericRecomendationsType(int numRecomendacoes, String type) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> Estabelecimentos = executeGenericRecomendations(estabelecimentos.size()).get(0);
		
		int i = 0;
		while (i < Estabelecimentos.size() && estabelecimentosRecomendados.size() < numRecomendacoes){
				if(Estabelecimentos.get(i).getTipoDeComida().equalsIgnoreCase(type)){
					estabelecimentosRecomendados.add(Estabelecimentos.get(i));
				}
				i++;
			
		}
		
		return estabelecimentosRecomendados;
	}
	
	
	//***
	/**
	 * Metodo responsavel por executar as recomendacoes personalizadas para um
	 * usuario, e filtrar as buscas por tipo de estabelecimento. As recomendacoes personalizdas baseiam-se na similaridade de gostos
	 * de restaurantes entre dois usuarios, ou seja, quanto mais parecidas suas opinioes sobre um restaurante, mais provavel eh que o
	 * usuario em questao goste desse restaurante. As recomendacoes personalizadas sao
	 * sempre distintas para os usuarios, pois a
	 * nota influencia nas recomendacoes, e os filtro por tipo tambem.
	 * 
	 * @param numRecomendacoes
	 * 			quantidade de recomendacoes desejadas.
	 * @param user
	 * 			Usuario que deseja as recomendacoes.
	 * @param type
	 * 			tipo de estabelecimento a ser filtrado.
	 * @return
	 * 			um list com os estabelecimentos recomendados filtrados por tipo de estabelecimento.
	 */
	public List<Estabelecimento> executeScalarProductRecomendationsType(int numRecomendacoes, Usuario user, String type) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> estabelecimentosRec = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user).get(0);
		
		int i = 0;
		while (i < estabelecimentosRec.size() && estabelecimentosRecomendados.size() < numRecomendacoes){
				if(estabelecimentosRec.get(i).getTipoDeComida().equalsIgnoreCase(type)){
					estabelecimentosRecomendados.add(estabelecimentosRec.get(i));
				}
				i++;
		}
		
		return estabelecimentosRecomendados;
	}
	
	//********
	/**
	 *Metodo responsavel por executar as recomendacoes genericas para um
	 * usuario, e filtrar as buscas por localizacao. As recomendacoes genericas baseiam-se na popularidade
	 * dos restaurantes, ou seja, quanto mais popular um restaurante eh, mais provavel eh que o
	 * usuario em questao goste desse restaurante. As recomendacoes genericas sao quase
	 * sempre iguais para todos os usuarios, elas mudam apenas quando a
	 * nota escondida influencia nas recomendacoes, e quando o tipo de localizacao do estabelecimento difere.
	 * 
	 * @param numRecomendacoes
	 * 			quantidade de recomendacoes desejadas.
	 * @param localizacao
	 * 			localizacao do estabelecimento, o qual deseja filtrar.
	 * @return
	 * 			um list dos estabelecimentos recomendados.
	 */
	public List<Estabelecimento> executeGenericRecomendationsLocation(int numRecomendacoes, String localizacao){
		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> Estabelecimentos = executeGenericRecomendations(estabelecimentos.size()).get(0);
		
		int i = 0;
		while(i < Estabelecimentos.size() && estabelecimentosRecomendados.size() < numRecomendacoes){
			if(Estabelecimentos.get(i).getLocalizacao().toLowerCase().contains(localizacao.toLowerCase())){
				estabelecimentosRecomendados.add(Estabelecimentos.get(i));
			}
			i++;
		}
		
		
		return estabelecimentosRecomendados;
	}
	
	//*******
	/**
	 * Metodo responsavel por executar as recomendacoes personalizadas para um
	 * usuario, e filtrar as buscas por localizacao do estabelecimento. As recomendacoes personalizdas baseiam-se na similaridade de gostos
	 * de restaurantes entre dois usuarios, ou seja, quanto mais parecidas suas opinioes sobre um restaurante, mais provavel eh que o
	 * usuario em questao goste desse restaurante. As recomendacoes personalizadas sao
	 * sempre distintas para os usuarios, pois a
	 * nota influencia nas recomendacoes, e os filtro por localizacao tambem.
	 * 
	 * @param numRecomendacoes
	 * 			quantidade de recomendacoes desejadas.
	 * @param user
	 * 			Usuario que deseja as recomendacoes.
	 * @param localizacao
	 * 			localizacao do estabelecimento a ser filtrado.
	 * @return
	 * 			um list com os estabelecimentos recomendados filtrados de acordo com sua localizacao.
	 */
	public List<Estabelecimento> executeScalarProductRecomendationsLocation(int numRecomendacoes, Usuario user, String localizacao){
		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> estabelecimentosRec = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user).get(0);
		
		int i = 0;
		while(i < estabelecimentosRec.size() && estabelecimentosRecomendados.size() < numRecomendacoes){
			if(estabelecimentosRec.get(i).getLocalizacao().toLowerCase().contains(localizacao.toLowerCase())){
				estabelecimentosRecomendados.add(estabelecimentosRec.get(i));
			}
			i++;
		}
		
		
		return estabelecimentosRecomendados;
	}
	
	
	//***********MENINOS NAO ENTENDI BEM O Q ESSA FUNCAO FAZ, DEEM UMA OLHADA SE TA CERTO
	//Metodo que remove as recomendacoes selecionadas
	/**
	 * Metodo que tem a funcao de remover os estabelecimentos selecionados em uma lista de estabelecimentos recomendados 
	 * ao usuario. Apos receber a recomendacao o usuario podera pedir novas recomendacoes, entao se faz necessario limpar
	 * a lista de recomendacoes anteriores.
	 * 
	 * @param numRecomendacoes
	 * 			quantidade de recomendacoes.
	 * @param listaRemovidos
	 * 			lista dos intens a serem removidos.
	 * @return	
	 * 			um list de lists
	 */
	
	public List<List<Estabelecimento>> executeGenericRecomendationsRemove(int numRecomendacoes,String listaRemovidos) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> estabelecimentosNaoRecomendados = new ArrayList<Estabelecimento>();
		List<List<Estabelecimento>> Estabelecimentos = executeGenericRecomendations(estabelecimentos.size());
		List<List<Estabelecimento>> returnList = new ArrayList<List<Estabelecimento>>();
		
		
		for(Estabelecimento e : Estabelecimentos.get(1)){
			estabelecimentosNaoRecomendados.add(e);
		}
		
		int i = 0;
		while (estabelecimentosRecomendados.size() < numRecomendacoes && i < Estabelecimentos.get(0).size()){
			if(!listaRemovidos.contains(Estabelecimentos.get(0).get(i).getNome())){
				estabelecimentosRecomendados.add(Estabelecimentos.get(0).get(i));
			}else if(!estabelecimentosNaoRecomendados.contains(Estabelecimentos.get(0).get(i))){
				estabelecimentosNaoRecomendados.add(Estabelecimentos.get(0).get(i));
			}
			i++;
		}
		
		
		
		returnList.add(estabelecimentosRecomendados);
		returnList.add(estabelecimentosNaoRecomendados);
		return returnList;
	}
	

	//**OLHEM ESSE TBM MENINOS
	/**
	 * Metodo que tem a funcao de remover da lista de recomendacoes,
	 * disponibilizada para o cliente, algum estabelecimento o qual ele
	 * nao goste ou ja tenho ido.
	 * 
	 * @param numRecomendacoes
	 * 			quantidade de recomendacoes desejadas.
	 * @param listaRemovidos
	 * 			lista dos estabelecimentos a serem removidos.
	 * @param tipoDeAlgoritmo
	 * 			tipo do algoritmo utilizado para gerar as recomendacoes.
	 * @param numberUser
	 * 			numero do usuario.
	 * @return
	 * 			um list de list com estabelecimentos recomendados,
	 * sem os estabelecimentos que foram removidos.
	 */
	//Metodo que remove as recomendacoes Popularidade selecionadas
	
	public List<List<Estabelecimento>> executePersonalizeRecomendationsRemove(int numRecomendacoes,String listaRemovidos,TipoAlgoritmoPersonalizado tipoDeAlgoritmo,int numberUser) {
		
		List<Estabelecimento> estabelecimentosAlgoritmoRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> estabelecimentosNaoRecomendados = new ArrayList<Estabelecimento>();
		List<List<Estabelecimento>> returnList = new ArrayList<List<Estabelecimento>>();
		
		if(tipoDeAlgoritmo.equals(TipoAlgoritmoPersonalizado.COSSENO)){
			estabelecimentosAlgoritmoRecomendados = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.COSSENO, ReadData.getUsuarios().get(numberUser-1)).get(0);
		}else if(tipoDeAlgoritmo.equals(TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO)){
			estabelecimentosAlgoritmoRecomendados = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, ReadData.getUsuarios().get(numberUser-1)).get(0);
		}else if(tipoDeAlgoritmo.equals(TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR)){
			estabelecimentosAlgoritmoRecomendados = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, ReadData.getUsuarios().get(numberUser-1)).get(0);
		}else if(tipoDeAlgoritmo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE)){
			estabelecimentosAlgoritmoRecomendados = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, ReadData.getUsuarios().get(numberUser-1)).get(0);
		}else if(tipoDeAlgoritmo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD)){
			estabelecimentosAlgoritmoRecomendados = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, ReadData.getUsuarios().get(numberUser-1)).get(0);
		}else if(tipoDeAlgoritmo.equals(TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP)){
			estabelecimentosAlgoritmoRecomendados = executeAlgoritmo(estabelecimentos.size(), TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, ReadData.getUsuarios().get(numberUser-1)).get(0);
		}

		int i = 0;
		while (estabelecimentosRecomendados.size() < numRecomendacoes && i < estabelecimentosAlgoritmoRecomendados.size()){
			if(!listaRemovidos.contains(estabelecimentosAlgoritmoRecomendados.get(i).getNome())){
				//pass
			//}else{
				estabelecimentosRecomendados.add(estabelecimentosAlgoritmoRecomendados.get(i));
			}
			i++;

		}
		
		Collections.sort(listaAlgoritmos, Collections.reverseOrder());
		Iterator<Algoritmo> itContrario = listaAlgoritmos.iterator();

		while(estabelecimentosNaoRecomendados.size() < numRecomendacoes && itContrario.hasNext()){
			Algoritmo algoritmoContrario = itContrario.next();
			for(int j=0; j<estabelecimentos.size(); j++){
				estabelecimentos.get(j).setNota(algoritmoContrario.getUser2().getOpinioes().get(j));
			}
			
			List<Estabelecimento> copiaEstabelecimentos = new ArrayList<Estabelecimento>(estabelecimentos);
			Collections.sort(copiaEstabelecimentos);
			
			for(Estabelecimento estabelecimento : copiaEstabelecimentos){
				if (!estabelecimentosNaoRecomendados.contains(estabelecimento)
						&& !estabelecimentosRecomendados.contains(estabelecimento)
						&& estabelecimentosNaoRecomendados.size() < numRecomendacoes
						&& estabelecimento.getNota() < 0
						&& algoritmoContrario.getUser1().getOpinioes().get(estabelecimentos.indexOf(estabelecimento)) == 0) {

					estabelecimentosNaoRecomendados.add(estabelecimento);
				}
			}
			if(estabelecimentosNaoRecomendados.size() == numRecomendacoes || !itContrario.hasNext()){
				break;
			}
		}
		
		returnList.add(estabelecimentosRecomendados);
		returnList.add(estabelecimentosNaoRecomendados);
		
		return returnList;
	}

	/**
	 * Modifica o list de usuarios do sistema.
	 * @param lista
	 * 			um novo list contendo os usuarios do sistema.
	 */
	private static void setUsuarios(List<Usuario> lista){
		usuarios = lista;
	}
	
	/**
	 * Modifica o list de estabelecimentos do sistema.
	 * @param lista
	 * 			o novo list contendo os estabelecimentos do sistema.
	 */
	private static void setEstabelecimentos(List<Estabelecimento> lista){
		estabelecimentos = lista;
	}

}
