package lp2.testes;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import lp2.algoritmos.CossenoIntersecao;
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
public class CossenoIntersecaoTest {
	CossenoIntersecao cossenoInt1, cossenoInt2;

	List<Integer> opinioesUser1 = new ArrayList<Integer>();
	List<Integer> opinioesUser2 = new ArrayList<Integer>();
	List<Integer> opinioesUser3 = new ArrayList<Integer>();

	Usuario user1;
	Usuario user2;
	Usuario user3;

	@Before
	public void criaCossenosInt() throws Exception{
		
		opinioesUser1.add(2);
		opinioesUser2.add(1);
		opinioesUser3.add(3);
		user1 = new Usuario("Reinaldo",opinioesUser1);
		user2 = new Usuario("Livia",opinioesUser2);
		user3 = new Usuario("Raquel", opinioesUser3);
		cossenoInt1 = new CossenoIntersecao(user1, user2);
		
	}

	@Test
	public void testaConstrutor(){

		try{
			new CossenoIntersecao(null, user2);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Erro no construtor", "Usuario null.",ex.getMessage());
		}

		try{
			new CossenoIntersecao(user1, null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Erro no construtor", "Usuario null.",ex.getMessage());
		}

		try {
			user1.setDataHoraCadastro("12:12:12 12:12:12");
			user2.setDataHoraCadastro("12:12:12 12:12:12");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertEquals("Erro no construtor", user1,cossenoInt1.getUser1());
		Assert.assertEquals("Erro no construtor", user2,cossenoInt1.getUser2());

	}
	@Test
	public void testaSetUser1() throws Exception{
		
		try{
			cossenoInt1.setUser1(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Usuario null.", ex.getMessage());
		}
		user3.setDataHoraCadastro("12:12:12 12:12:12");
		cossenoInt1.setUser1(user3);
		Assert.assertEquals("Erro no metodo setUser1", user3,cossenoInt1.getUser1());

	}

	@Test
	public void testaSetUser2() throws Exception{

		try{
			cossenoInt1.setUser2(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Usuario null.", ex.getMessage());
		}
		user3.setDataHoraCadastro("12:12:12 12:12:12");
		cossenoInt1.setUser2(user3);
		Assert.assertEquals("Erro no metodo setUser2", user3,cossenoInt1.getUser2());
		
	}
	
	@Test
	public void testaGetResultado(){
		
		Assert.assertEquals("Erro no metodo getResultado", 0.0,cossenoInt1.getResultado(), 0.0005);
		cossenoInt1.calculaSimilaridade();
		Assert.assertEquals("Erro no metodo getResultado", 0.0,cossenoInt1.getResultado(), 0.0005);
		
	}
	@Test
	public void testaSetResultado(){
		
		Assert.assertEquals("Erro no metodo setResultado", 0.0,cossenoInt1.getResultado(), 0.0005);
		cossenoInt1.setResultado(5);
		Assert.assertEquals("Erro no metodo setResultado", 5.0,cossenoInt1.getResultado(), 0.0005);
		
	}
	@Test
	public void testaCompareTo() throws Exception{
		
		cossenoInt2 = new CossenoIntersecao(user1, user3);
		cossenoInt2.calculaSimilaridade();
		cossenoInt1.calculaSimilaridade();
		Assert.assertEquals("Erro no metodo compareTo", 0, cossenoInt1.compareTo(cossenoInt2));
		Assert.assertEquals("Erro no metodo compareTo", 0, cossenoInt2.compareTo(cossenoInt1));
		
	}
}

