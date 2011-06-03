package lp2.testes;

import java.util.List;

import lp2.algoritmos.Algoritmos;
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
	public void testExecuteScalarProductRecomendations() {
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeScalarProductRecomendations(10, user1);
		Assert.assertTrue(estab.size() == 10);
		
		estab = algoritmo.executeScalarProductRecomendations(39, user1);
		// nao pode ser 39. nao ha estabelecimentos suficientes nos usuarios semelhantes a este usuario e que tenham nota superior a 0.
		Assert.assertFalse(estab.size() == 39);
		
		estab = algoritmo.executeScalarProductRecomendations(100, user1);
		Assert.assertFalse(estab.size() == 100);
		
		for(int i=0; i<user1.getOpinioes().size(); i++){
			user1.getOpinioes().set(i, 0);
		}
		estab = algoritmo.executeScalarProductRecomendations(10, user1);
		Assert.assertTrue(estab.size() == 10);
		
	}

	@Test
	public void testExecuteGenericRecomendations() {
		List<Estabelecimento> estab = null;
		estab = algoritmo.executeGenericRecomendations(10);
		Assert.assertTrue(estab.size() == 10);
		
		estab = algoritmo.executeGenericRecomendations(39);
		// nao pode ser 39. nao ha estabelecimentos suficientes na soma total que mantenham a nota superior a 0.
		Assert.assertFalse(estab.size() == 39);
		
		estab = algoritmo.executeGenericRecomendations(100);
		Assert.assertFalse(estab.size() == 100);
	}

}
