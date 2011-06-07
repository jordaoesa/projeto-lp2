package lp2.testes;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import lp2.algoritmos.ProdutoEscalar;
import lp2.lerDados.Usuario;

import org.junit.Before;
import org.junit.Test;


/**
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
public class ProdutoEscalarTest {
	ProdutoEscalar produto1,produto2;

	List<Integer> opinioesUser1 = new ArrayList<Integer>();
	List<Integer> opinioesUser2 = new ArrayList<Integer>();
	List<Integer> opinioesUser3 = new ArrayList<Integer>();

	Usuario user1;
	Usuario user2;
	Usuario user3;

	@Before
	public void criaProdutosEscalar() throws Exception{
		opinioesUser1.add(2);
		opinioesUser2.add(1);
		opinioesUser3.add(3);
		user1 = new Usuario("Reinaldo",opinioesUser1);
		user2 = new Usuario("Livia",opinioesUser2);
		user3 = new Usuario("Raquel", opinioesUser3);
		produto1 = new ProdutoEscalar(user1, user2);
	}

	@Test
	public void testaConstrutor(){
		//testa primeiro parametro como null
		try{
			new ProdutoEscalar(null, user2);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Erro no construtor", "Usuario null.",ex.getMessage());
		}

		//testa segundo parametro como null
		try{
			new ProdutoEscalar(user1, null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Erro no construtor", "Usuario null.",ex.getMessage());
		}

		//testa retorno dos parametros do construtor
		try {
			user1.setDataHoraCadastro("12:12:12 12:12:12");
			user2.setDataHoraCadastro("12:12:12 12:12:12");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals("Erro no construtor", user1,produto1.getUser1());
		Assert.assertEquals("Erro no construtor", user2,produto1.getUser2());

	}
	@Test
	public void testaSetUser1() throws Exception{
		//testa setar para usuario null
		try{
			produto1.setUser1(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Usuario null.", ex.getMessage());
		}
		user3.setDataHoraCadastro("12:12:12 12:12:12");
		produto1.setUser1(user3);
		Assert.assertEquals("Erro no metodo setUser1", user3,produto1.getUser1());

	}

	@Test
	public void testaSetUser2() throws Exception{
		//testa setar para usuario null
		try{
			produto1.setUser2(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Usuario null.", ex.getMessage());
		}
		user3.setDataHoraCadastro("12:12:12 12:12:12");
		produto1.setUser2(user3);
		Assert.assertEquals("Erro no metodo setUser1", user3,produto1.getUser2());
	}
	
	@Test
	public void testaCalculaProdutoEscalar(){
		
		//verifica produtoEscalar antes de calcular, tem que ser 0
		Assert.assertEquals("Erro no metodo calculaProdutoEscalar", 0.0,produto1.getResultado());
		
		//como as notas sao 2 em user1 e 1 em user2, produto escalar tem que ser 2
		produto1.calculaSimilaridade();
		Assert.assertEquals("Erro no metodo calculaProdutoEscalar", 2.0,produto1.getResultado());
	}
	@Test
	public void testaSetProdutoEscalar(){
		//antes de setar verifica que eh 0
		Assert.assertEquals("Erro no metodo calculaProdutoEscalar", 0.0,produto1.getResultado());
		//depois de setar
		produto1.setResultado(5);
		Assert.assertEquals("Erro no metodo calculaProdutoEscalar", 5.0,produto1.getResultado());
	}
	@Test
	public void testaCompareTo() throws Exception{
		produto2 = new ProdutoEscalar(user1, user3);
		
		//uso o metodo calculaProduto, porque se nao sera 0.
		produto2.calculaSimilaridade();
		produto1.calculaSimilaridade();
		
		//note que produto escalar de produto1 tem que ser 2,pois eh 2*1 = 2
		//e produto escalar de produto2 tem que ser 6,pois eh 2*3 = 6
		Assert.assertEquals("Erro no metodo compareTo", -1, produto1.compareTo(produto2));//-4 pois produto1 eh menor que produto2
		Assert.assertEquals("Erro no metodo compareTo", 1, produto2.compareTo(produto1));//4 produto2 eh maior que produto1
	}
}

