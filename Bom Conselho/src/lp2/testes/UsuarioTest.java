package lp2.testes;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
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
public class UsuarioTest {
	
	Usuario user1,user2;
	List<Integer> opinioesUser1 = new ArrayList<Integer>();
	List<Integer> opinioesUser2 = new ArrayList<Integer>();
	
	@Before
	public void criaUsuarios() throws Exception{
		opinioesUser1.add(2);
		opinioesUser2.add(1);
		user1 = new Usuario("Reinaldo",opinioesUser1);
		user2 = new Usuario("Livia",opinioesUser2);
	}
	@Test
	public final void testUsuario() {
		List<Integer> auxUser1 = new ArrayList<Integer>(opinioesUser1);
		List<Integer> auxUser2 = new ArrayList<Integer>(opinioesUser2);
		Assert.assertEquals("Erro no construtor", "Reinaldo", user1.getNome());
		Assert.assertEquals("Erro no construtor", "Livia", user2.getNome());
		Assert.assertEquals("Erro no construtor",auxUser1 , user1.getOpinioes());
		Assert.assertEquals("Erro no construtor",auxUser2 , user2.getOpinioes());
	}

	@Test
	public void testaConstrutor(){
		try {
			user1 = new Usuario(null, opinioesUser1);
			Assert.fail("Devia ter dado erro");
		} catch (Exception e) {
			Assert.assertEquals("Erro no contrutor de usuario", "Nome nulo/vazio.", e.getMessage());
		}
		
		try {
			user1 = new Usuario("nome", null);
			Assert.fail("Devia ter dado erro");
		} catch (Exception e) {
			Assert.assertEquals("Erro no contrutor de usuario", "Lista de Opinioes nula/vazia.", e.getMessage());
		}
		
	}
	
	@Test
	public final void testSetNome() throws Exception {
		try{
			user1.setNome(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Nome nulo/vazio.", ex.getMessage());
		}
		
		try{
			user1.setNome("");
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Nome nulo/vazio.", ex.getMessage());
		}
		user1.setNome("Nazareno");
		user2.setNome("Raquel");

		Assert.assertEquals("Erro no metodo serOpinioes","Nazareno", user1.getNome());
		Assert.assertEquals("Erro no metodo serOpinioes","Raquel", user2.getNome());
	
	}
	@Test
	public void testaSetOpinioes() throws Exception{

		try{
			user1.setOpinioes(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Lista de Opinioes nula/vazia.", ex.getMessage());
		}

		try{
			opinioesUser1.clear();
			user1.setOpinioes(opinioesUser1);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Lista de Opinioes nula/vazia.", ex.getMessage());
		}
		
		List<Integer> auxUser1 = new ArrayList<Integer>();
		auxUser1.add(5);
		auxUser1.add(2);
		user1.setOpinioes(auxUser1);
		//testa se setou o user1 para nova lista.
		Assert.assertEquals("Erro no metodo setOpinioes", auxUser1, user1.getOpinioes());
	}

}
