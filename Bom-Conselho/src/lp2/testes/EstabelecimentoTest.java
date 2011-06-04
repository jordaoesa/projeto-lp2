package lp2.testes;

import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import lp2.lerDados.Estabelecimento;
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
public class EstabelecimentoTest {
	Estabelecimento estabelecimento1,estabelecimento2;
	
	@Before
	public void criaEstabelecimentos() throws Exception{
		estabelecimento1 = new Estabelecimento("Bar do Cuscuz e Restaurante", "Rua Dr Severino Cruz, 771, Centro", "A la carte");
		estabelecimento2 = new Estabelecimento("Divino Fogão", "Shopping Boulevard", "Self-service");
	}
	
	@Test
	public void testaConstrutor(){
		Assert.assertEquals("Erro no construtor", "Bar do Cuscuz e Restaurante",estabelecimento1.getNome());
		Assert.assertEquals("Erro no construtor", "Rua Dr Severino Cruz, 771, Centro",estabelecimento1.getLocalizacao());
		Assert.assertEquals("Erro no construtor", "A la carte",estabelecimento1.getTipoDeComida());
	}
	
	@Test
	public void testaErrosNoConstrutor(){
		try {
			estabelecimento1 = new Estabelecimento(null, "as", "ss");
			Assert.fail("Esperava erro");
		} catch (Exception e) {
			Assert.assertEquals("Erro no construtor", "Nome nulo/vazio.", e.getMessage());
		}
		
		try {
			estabelecimento1 = new Estabelecimento("asd", null, "ss");
			Assert.fail("Esperava erro");
		} catch (Exception e) {
			Assert.assertEquals("Erro no construtor", "Localizacao nula/vazia.", e.getMessage());
		}
		
		try {
			estabelecimento1 = new Estabelecimento("asd", "as", null);
			Assert.fail("Esperava erro");
		} catch (Exception e) {
			Assert.assertEquals("Erro no construtor", "Tipo de comida nulo/vazio.", e.getMessage());
		}
	}
	
	@Test
	public void testaSetNome() throws Exception{
		//testa setar para nome null
		try{
			estabelecimento1.setNome(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Nome nulo/vazio.", ex.getMessage());
		}
		
		//testa setar nome para vazio
		try{
			estabelecimento1.setNome("");
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Nome nulo/vazio.", ex.getMessage());
		}
		
		//seta o nome
		estabelecimento1.setNome("Nazareno");
		//testa se mudou o nome.
		Assert.assertEquals("Erro no metodo serOpinioes","Nazareno", estabelecimento1.getNome());
	}
	
	@Test
	public void testaSetLocalizacao() throws Exception{
		//testa setar para Localizacao null
		try{
			estabelecimento1.setLocalizacao(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Localizacao nula/vazia.", ex.getMessage());
		}
		
		//testa setar localizacao para vazio
		try{
			estabelecimento1.setLocalizacao("");
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Localizacao nula/vazia.", ex.getMessage());
		}
		
		//seta a localizacao
		estabelecimento1.setLocalizacao("Rua Otávio Amorim, 15, Cruzeiro");
		//testa se mudou a localizacao
		Assert.assertEquals("Erro no metodo serOpinioes","Rua Otávio Amorim, 15, Cruzeiro", estabelecimento1.getLocalizacao());
	}
	@Test
	public void testaSetTipoDeComida() throws Exception{
		//testa setar para tipo de comida null
		try{
			estabelecimento1.setTipoDeComida(null);
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Tipo de comida nulo/vazio.", ex.getMessage());
		}
		
		//testa setar tipo de comida para vazio
		try{
			estabelecimento1.setTipoDeComida("");
			Assert.fail("Esperava erro");
		}catch(Exception ex){
			Assert.assertEquals("Tipo de comida nulo/vazio.", ex.getMessage());
		}
		//seta o tipo de comida
		estabelecimento1.setTipoDeComida("Prato feito");
		//testa se mudou o tipo de comida
		Assert.assertEquals("Erro no metodo serOpinioes","Prato feito", estabelecimento1.getTipoDeComida());
	}
	@Test
	public void testaSetNota(){
		//verifica se a nota iniciada eh 0
		Assert.assertEquals("Erro no metodo getNota", 0, estabelecimento1.getNota());
		//verifica se setou a nota.
		estabelecimento1.setNota(2);
		Assert.assertEquals("Erro no metodo getNota", 2, estabelecimento1.getNota());
	}
	@Test
	public void testaCompareTo(){
		
		//como os dois estabelecimentos iniciam com notas 0. entao suas diferencas tem que ser 0 .Verifica a reciproca
		Assert.assertEquals("Erro no metodo compareTo", 0,estabelecimento1.compareTo(estabelecimento2));
		Assert.assertEquals("Erro no metodo compareTo", 0,estabelecimento2.compareTo(estabelecimento1));
		
		//seta nota as notas e testa
		estabelecimento1.setNota(4);
		Assert.assertEquals("Erro no metodo compareTo", 4,estabelecimento1.compareTo(estabelecimento2));
		Assert.assertEquals("Erro no metodo compareTo", -4,estabelecimento2.compareTo(estabelecimento1));
		
	}
	@Test
	public void testaEquals() throws Exception{
		
		//add 1 elemento na lista de opinoes apenas para nao lancar excecao
		List<Integer> opinioesUser1 = new ArrayList<Integer>();
		opinioesUser1.add(2);
		Usuario usuarioAux = new Usuario("Reinaldo",opinioesUser1);
		Estabelecimento estabelecimentoAux = estabelecimento1;
		
		//verifica com objetos de outra instancia
		Assert.assertFalse(estabelecimento1.equals(usuarioAux));
		
		//verifica com objetos de mesma instancia mas diferentes
		Assert.assertFalse(estabelecimento1.equals(estabelecimento2));
		
		//verifica objetos da mesma instancia mais identicos
		Assert.assertTrue(estabelecimento1.equals(estabelecimentoAux ));
	}
	
}
