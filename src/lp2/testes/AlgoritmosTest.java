package lp2.testes;

import java.util.List;

import lp2.algoritmos.Algoritmos;
import lp2.algoritmos.TipoAlgoritmoPersonalizado;
import lp2.lerDados.Estabelecimento;
import lp2.lerDados.ReadData;
import lp2.lerDados.Usuario;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
public class AlgoritmosTest {
	
	Algoritmos algoritmo;
	Usuario user1;
	
	@Before
	public void instanciaAlgoritmo() throws Exception{
		ReadData.initLists();
		algoritmo = new Algoritmos();
		user1 = ReadData.getUsuarios().get(0);
	}
	
	@After
	public void limpaListas(){
		ReadData.getEstabelecimentos().clear();
		ReadData.getUsuarios().clear();
	}

	@Test
	public void testExecuteAlgoritmoProduto() {
		//Produto Escalar
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
		//testando as recomendacoes
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Gulas", estab.get(0).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Pedro da Picanha", estab.get(1).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Qdoca Bar e Restaurante", estab.get(2).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Bodão Bar e Restaurante", estab.get(3).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Cabana do Possidônio", estab.get(4).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Divino Fogão", estab.get(5).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Manoel da Carne de Sol", estab.get(6).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Bonaparte", estab.get(7).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Cantina de Olavo", estab.get(8).getNome());
		Assert.assertEquals("Erro nas Recomendacoes ProdutoEscalar", "Cantina do Hall das Placas", estab.get(9).getNome());
		
		estab = algoritmo.executeAlgoritmo(39, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user1).get(0);
		Assert.assertFalse(estab.size() == 39);
		
		estab = algoritmo.executeAlgoritmo(100, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user1).get(0);
		Assert.assertFalse(estab.size() == 100);
		
		for(int i=0; i<user1.getOpinioes().size(); i++){
			user1.getOpinioes().set(i, 0);
		}
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.PRODUTO_ESCALAR, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
	}
	
	@Test
	public void testExecuteAlgoritmoCosseno() {
		//Cosseno
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.COSSENO, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
		//testando as recomendacoes
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Cantina do Hall das Placas", estab.get(0).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Cantina de Olavo", estab.get(1).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Super China Restaurante", estab.get(2).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Cantina do Departamento de Meteorologia", estab.get(3).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Girassol", estab.get(4).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Cantinho Universitário", estab.get(5).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Restaurante Golden In China", estab.get(6).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Restaurante Lay China", estab.get(7).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Divino Fogão", estab.get(8).getNome());
		Assert.assertEquals("Erro nas recomendacoes Cosseno", "Riso", estab.get(9).getNome());
		
		estab = algoritmo.executeAlgoritmo(39, TipoAlgoritmoPersonalizado.COSSENO, user1).get(0);
		Assert.assertFalse(estab.size() == 39);
		
		estab = algoritmo.executeAlgoritmo(100, TipoAlgoritmoPersonalizado.COSSENO, user1).get(0);
		Assert.assertFalse(estab.size() == 100);
		
		for(int i=0; i<user1.getOpinioes().size(); i++){
			user1.getOpinioes().set(i, 0);
		}
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.COSSENO, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
	}
	
	@Test
	public void testExecuteAlgoritmoCossenoIntersecao() {
		//Cosseno Intersecao
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
		//testando as recomendacoes
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Cantina do Hall das Placas", estab.get(0).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Restaurante do Alexandre", estab.get(1).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Bonaparte", estab.get(2).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Cantinho Universitário", estab.get(3).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Cantina de Olavo", estab.get(4).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Restaurante Brasília", estab.get(5).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Baixinho Bar e Restaurante", estab.get(6).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Restaurante Golden In China", estab.get(7).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Marmitaria Bom Paladar", estab.get(8).getNome());
		Assert.assertEquals("Erro nas recomendacoes CossenoIntersecao", "Manoel da Carne de Sol", estab.get(9).getNome());
		
		estab = algoritmo.executeAlgoritmo(39, TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, user1).get(0);
		Assert.assertFalse(estab.size() == 39);
		
		estab = algoritmo.executeAlgoritmo(100, TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, user1).get(0);
		Assert.assertFalse(estab.size() == 100);
		
		for(int i=0; i<user1.getOpinioes().size(); i++){
			user1.getOpinioes().set(i, 0);
		}
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.COSSENO_INTERSECAO, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
	}
	
	@Test
	public void testExecuteAlgoritmoSimilaridadeDice() {
		//Similaridade Dice
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
		//testando as recomendacoes
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Cantina do Hall das Placas", estab.get(0).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Restaurante do Alexandre", estab.get(1).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Bonaparte", estab.get(2).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Cantinho Universitário", estab.get(3).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Cantina de Olavo", estab.get(4).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Restaurante Brasília", estab.get(5).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Baixinho Bar e Restaurante", estab.get(6).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Restaurante Golden In China", estab.get(7).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Marmitaria Bom Paladar", estab.get(8).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeDice", "Manoel da Carne de Sol", estab.get(9).getNome());
		
		estab = algoritmo.executeAlgoritmo(39, TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, user1).get(0);
		Assert.assertFalse(estab.size() == 39);
		
		estab = algoritmo.executeAlgoritmo(100, TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, user1).get(0);
		Assert.assertFalse(estab.size() == 100);
		
		for(int i=0; i<user1.getOpinioes().size(); i++){
			user1.getOpinioes().set(i, 0);
		}
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.SIMILARIDADE_DICE, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
	}
	
	@Test
	public void testExecuteAlgoritmoSimilaridadeJaccard() {
		//Similaridade Jaccard
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
		//testando as recomendacoes
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Cantina do Hall das Placas", estab.get(0).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Restaurante do Alexandre", estab.get(1).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Bonaparte", estab.get(2).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Cantinho Universitário", estab.get(3).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Cantina de Olavo", estab.get(4).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Restaurante Brasília", estab.get(5).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Baixinho Bar e Restaurante", estab.get(6).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Restaurante Golden In China", estab.get(7).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Marmitaria Bom Paladar", estab.get(8).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeJaccard", "Manoel da Carne de Sol", estab.get(9).getNome());
		
		estab = algoritmo.executeAlgoritmo(39, TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, user1).get(0);
		Assert.assertFalse(estab.size() == 39);
		
		estab = algoritmo.executeAlgoritmo(100, TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, user1).get(0);
		Assert.assertFalse(estab.size() == 100);
		
		for(int i=0; i<user1.getOpinioes().size(); i++){
			user1.getOpinioes().set(i, 0);
		}
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.SIMILARIDADE_JACCARD, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
	}
	
	@Test
	public void testExecuteAlgoritmoSimilaridadeOverlap() {
		//Similaridade Overlap
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
		//testando as recomendacoes
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Cantina do Hall das Placas", estab.get(0).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Restaurante do Alexandre", estab.get(1).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Bonaparte", estab.get(2).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Cantinho Universitário", estab.get(3).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Cantina de Olavo", estab.get(4).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Restaurante Brasília", estab.get(5).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Baixinho Bar e Restaurante", estab.get(6).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Restaurante Golden In China", estab.get(7).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Marmitaria Bom Paladar", estab.get(8).getNome());
		Assert.assertEquals("Erro nas recomendacoes SimilaridadeOverlap", "Manoel da Carne de Sol", estab.get(9).getNome());
		
		estab = algoritmo.executeAlgoritmo(39, TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, user1).get(0);
		Assert.assertFalse(estab.size() == 39);
		
		estab = algoritmo.executeAlgoritmo(100, TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, user1).get(0);
		Assert.assertFalse(estab.size() == 100);
		
		for(int i=0; i<user1.getOpinioes().size(); i++){
			user1.getOpinioes().set(i, 0);
		}
		estab = algoritmo.executeAlgoritmo(10, TipoAlgoritmoPersonalizado.SIMILARIDADE_OVERLAP, user1).get(0);
		Assert.assertTrue(estab.size() == 10);
		
	}

	@Test
	public void testExecuteGenericRecomendations() {
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeGenericRecomendations(10).get(0);
		Assert.assertTrue(estab.size() == 10);
		
		estab = algoritmo.executeGenericRecomendations(40).get(0);
		// nao pode ser 40. nao ha estabelecimentos suficientes na soma total.
		Assert.assertFalse(estab.size() == 40);
		
		estab = algoritmo.executeGenericRecomendations(100).get(0);
		Assert.assertFalse(estab.size() == 100);
	}

}
