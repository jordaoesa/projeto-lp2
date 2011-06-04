package lp2.algoritmos;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lp2.lerDados.Estabelecimento;
import lp2.lerDados.ReadData;
import lp2.lerDados.Usuario;

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

	/**
	 * Pearson Correlation Algorithm of recomendations.
	 * 
	 * @param numRecomend
	 *            numero de recomendacoes.
	 * @param user
	 *            usuario.
	 * @return lista dos estabelecimentos recomendados.
	 */
	public List<Estabelecimento> executeCossineRecomendations(int numRecomend, Usuario user) {
		
		List<Cosseno> listaCossenos = new ArrayList<Cosseno>();
		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> copiaEstabelecimentos;
		
		for(int i=0; i<usuarios.size(); i++){
			if(!usuarios.get(i).getNome().equals(user.getNome())){
				Cosseno cosseno = new Cosseno(user, usuarios.get(i));
				cosseno.calculaCosseno();
				listaCossenos.add(cosseno);
			}
		}
		
		Collections.sort(listaCossenos, Collections.reverseOrder());
		
		Iterator<Cosseno> it = listaCossenos.iterator();

		while(estabelecimentosRecomendados.size() < numRecomend && it.hasNext()){
			Cosseno cosseno = it.next();
			for(int i=0; i<estabelecimentos.size(); i++){
				estabelecimentos.get(i).setNota(cosseno.getUser2().getOpinioes().get(i));
			}
			
			copiaEstabelecimentos = new ArrayList<Estabelecimento>(estabelecimentos);
			Collections.sort(copiaEstabelecimentos, Collections.reverseOrder());
			
			for(Estabelecimento estabelecimento : copiaEstabelecimentos){
				if (!estabelecimentosRecomendados.contains(estabelecimento)
						&& estabelecimentosRecomendados.size() < numRecomend
						&& estabelecimento.getNota() > 0
						&& cosseno.getUser1().getOpinioes().get(estabelecimentos.indexOf(estabelecimento)) == 0) {
					
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
	 * Compara todos os algoritmos de recomendacao implementados.
	 * 
	 * @param numRecomendacoes
	 */
	@SuppressWarnings("rawtypes")
	public List<List> compareAlgorithms(int numRecomendacoes) {
		int totalAcertosGenericos = 0;
		int totalAcertosProdutoEscalar = 0;
		int totalAcertosCosseno = 0;
		
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
			
			for(int i=0; i<user.getOpinioes().size(); i++){
				if(user.getOpinioes().get(i) > 0){
					Estabelecimento estabelecimentoEscondido = estabelecimentos.get(i);
					int avaliacaoOriginal = user.getOpinioes().get(i);
					
					user.getOpinioes().set(i, 0); // escondendo estabelecimento
					
					List<Estabelecimento> estabelecimentosGenericos = executeGenericRecomendations(numRecomendacoes);
					List<Estabelecimento> estabelecimentosProdutoEscalar = executeScalarProductRecomendations(numRecomendacoes, user);
					List<Estabelecimento> estabelecimentosCosseno = executeCossineRecomendations(numRecomendacoes, user);
					
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
					
				}
			}
			
			totalAcertosGenericos += acertosGenericos;
			totalAcertosProdutoEscalar += acertosProdutoEscalar;
			totalAcertosCosseno += acertosCosseno;
			
			// Formatacao da lista: Nome | OpinioesPositivas | OpinioesCadastradas | AcertosGenerico | AcertosPersonalizado | AcertosCosseno | PorcentagemGenerico | PorcentagemPersonalizado | PorcentagemCosseno
			List<String> comparacaoPorUsuario = new ArrayList<String>();
			
			comparacaoPorUsuario.add(user.getNome());
			comparacaoPorUsuario.add(String.valueOf(getOpinioesPositivasUser(user)));
			comparacaoPorUsuario.add(String.valueOf(getOpinioesCadastradasUser(user)));
			comparacaoPorUsuario.add(String.valueOf(acertosGenericos));
			comparacaoPorUsuario.add(String.valueOf(acertosProdutoEscalar));
			comparacaoPorUsuario.add(String.valueOf(acertosCosseno));
			if(getOpinioesPositivasUser(user) > 0){
				comparacaoPorUsuario.add(nfDouble.format((acertosGenericos / (double)getOpinioesPositivasUser(user))*100) + "%");
				comparacaoPorUsuario.add(nfDouble.format((acertosProdutoEscalar / (double)getOpinioesPositivasUser(user))*100) + "%");
				comparacaoPorUsuario.add(nfDouble.format((acertosCosseno / (double)getOpinioesPositivasUser(user))*100) + "%");
			}else{
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
				comparacaoPorUsuario.add(nfDouble.format(0) + "%");
			}
			comparacoesUsuarios.add(comparacaoPorUsuario);
			
		}
		comparacaoFinal.add(String.valueOf(getOpinioesPositivasSistema()));
		comparacaoFinal.add(String.valueOf(totalAcertosGenericos));
		comparacaoFinal.add(String.valueOf(totalAcertosProdutoEscalar));
		comparacaoFinal.add(String.valueOf(totalAcertosCosseno));
		if(getOpinioesPositivasSistema() > 0){
			comparacaoFinal.add(nfDouble.format((totalAcertosGenericos / (double) getOpinioesPositivasSistema())*100) + "%");
			comparacaoFinal.add(nfDouble.format((totalAcertosProdutoEscalar / (double) getOpinioesPositivasSistema())*100) + "%");
			comparacaoFinal.add(nfDouble.format((totalAcertosCosseno / (double) getOpinioesPositivasSistema())*100) + "%");
		}else{
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
	
	//*** DA UMA OLHADA SE ISSO TA FUNCIONANDO CERTO ***//
	public List<Estabelecimento> executeScalarProductRecomendationsFilter(int numRecomendacoes, Usuario user, String palavraChave) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> Estabelecimentos =  executeScalarProductRecomendations(estabelecimentos.size(), user);
		
//		for(Estabelecimento est : Estabelecimentos){
//			System.out.println(est.getNome());
//		}
		
		
		int i = 0;
		while (estabelecimentosRecomendados.size() < numRecomendacoes && i < estabelecimentos.size())
		{//for(int i=0; i<numRecomendacoes; i++){
			//if(i < Estabelecimentos.size() && Estabelecimentos.get(i).getNota() > 0){
				if((Estabelecimentos.get(i).getNome().toLowerCase().contains(palavraChave.toLowerCase()))){
				//	estabelecimentosRecomendados.add(Estabelecimentos.get(i));
				}else{
					estabelecimentosRecomendados.add(Estabelecimentos.get(i));
				}
				i++;
			
		}
		
		return estabelecimentosRecomendados;
	}
	
	
	//***
	public List<Estabelecimento> executeGenericRecomendationsFilter(int numRecomendacoes, String palavraChave) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> Estabelecimentos = executeGenericRecomendations(estabelecimentos.size());
		
		System.out.println(estabelecimentosRecomendados.size());
		
//		for(Estabelecimento est : Estabelecimentos){
//			System.out.println(est.getNome());
//		}
		
		int i = 0;
		while (estabelecimentosRecomendados.size() < numRecomendacoes && i < estabelecimentos.size()){
				if((Estabelecimentos.get(i).getNome().toLowerCase().contains(palavraChave.toLowerCase()))){
					//System.out.println("entrou");
					//continue;
//					estabelecimentosRecomendados.add(Estabelecimentos.get(i));
				}else{
					System.out.println("entrou");
					estabelecimentosRecomendados.add(Estabelecimentos.get(i));
				}
				//System.out.println("nao entrou");
				i++;
			
		}
		//System.out.println("tamanho" + estabelecimentosRecomendados.get(0).getNome());
		return estabelecimentosRecomendados;
	}
	
	
	//****
	public List<Estabelecimento> executeGenericRecomendationsType(int numRecomendacoes, String type) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> Estabelecimentos = executeGenericRecomendations(estabelecimentos.size());
		
		int i = 0;
		while (estabelecimentosRecomendados.size() < numRecomendacoes)
		{//for(int i=0; i<numRecomendacoes; i++){
			//if(i < Estabelecimentos.size() && Estabelecimentos.get(i).getNota() > 0){
				if(Estabelecimentos.get(i).getTipoDeComida().equalsIgnoreCase(type)){
					estabelecimentosRecomendados.add(Estabelecimentos.get(i));
				}
				i++;
			
		}
		
		return estabelecimentosRecomendados;
	}
	
	
	//*** DA UMA OLHADA SE TA FUNCIONANDO DIREITO TBM **///
	public List<Estabelecimento> executeScalarProductRecomendationsType(int numRecomendacoes, Usuario user, String type) {

		List<Estabelecimento> estabelecimentosRecomendados = new ArrayList<Estabelecimento>();
		List<Estabelecimento> Estabelecimentos =  executeScalarProductRecomendations(estabelecimentos.size(), user);
		
		int i = 0;
		while (estabelecimentosRecomendados.size() < numRecomendacoes)
		{//for(int i=0; i<numRecomendacoes; i++){
			//if(i < Estabelecimentos.size() && Estabelecimentos.get(i).getNota() > 0){
				if(Estabelecimentos.get(i).getTipoDeComida().equalsIgnoreCase(type)){
					estabelecimentosRecomendados.add(Estabelecimentos.get(i));
				}
				i++;
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
