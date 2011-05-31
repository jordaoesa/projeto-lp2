package lp2.testes;

import java.util.ArrayList;

import lp2.interfaces.MenuInicial;
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
public class ReadDataTest {
	
	@Before
	public void ajeitaPathsAntes(){
		MenuInicial.pathEstabelecimentos = "./src/lp2/arquivos/lista_estabelecimentos_projeto_lp2-v2.data";
		MenuInicial.pathOpinioes = "./src/lp2/arquivos/opinioes-dos-usuarios-v2.data";
	}
	
	@After
	public void ajeitaPathsDepois(){
		MenuInicial.pathEstabelecimentos = "./src/lp2/arquivos/lista_estabelecimentos_projeto_lp2-v2.data";
		MenuInicial.pathOpinioes = "./src/lp2/arquivos/opinioes-dos-usuarios-v2.data";
	}

	@Test
	public void testaAntesDePreencherListas(){
		Assert.assertTrue(ReadData.getEstabelecimentos() == null || ReadData.getEstabelecimentos().isEmpty());
		Assert.assertTrue(ReadData.getUsuarios() == null || ReadData.getUsuarios().isEmpty());
	}
	
	@Test
	public void testaAposPreencherLista() throws Exception{
		ReadData.initLists();
		Assert.assertTrue(!ReadData.getEstabelecimentos().isEmpty());
		Assert.assertTrue(!ReadData.getUsuarios().isEmpty());
	}
	
	@Test
	public void testaGetUsuarios() throws Exception{
		ReadData.initLists();
		Assert.assertEquals("Erro no getUsuarios", "Nazareno", ReadData.getUsuarios().get(0).getNome());
		Assert.assertEquals("Erro no getUsuarios", "anonimo1", ReadData.getUsuarios().get(1).getNome());
		Assert.assertEquals("Erro no getUsuarios", "anonimo2", ReadData.getUsuarios().get(2).getNome());
	}

	@Test
	public void testaGetEstabelecimentos() throws Exception{
		ReadData.initLists();
		// A ordem da lista de estabelecimentos eh dada a partir do arquivo de opinoes.
		// Afinal a ordem das opinioes eh quem define a ordem dos estabelecimentos.
		Assert.assertEquals("Erro no getEstabelecimentos", "Bar do Cuscuz e Restaurante", ReadData.getEstabelecimentos().get(0).getNome());
		Assert.assertEquals("Erro no getEstabelecimentos", "Baixinho Bar e Restaurante", ReadData.getEstabelecimentos().get(1).getNome());
		Assert.assertEquals("Erro no getEstabelecimentos", "Bar do George", ReadData.getEstabelecimentos().get(2).getNome());
	}
	
	@Test
	public void testaSetUsuarios() throws Exception{
		ReadData.initLists();
		Assert.assertTrue(!ReadData.getUsuarios().isEmpty());
		ReadData.setUsuarios(new ArrayList<Usuario>());
		Assert.assertTrue(ReadData.getUsuarios().isEmpty());
	}
	
	@Test
	public void testaSetEstabelecimentos() throws Exception{
		ReadData.initLists();
		Assert.assertTrue(!ReadData.getEstabelecimentos().isEmpty());
		ReadData.setEstabelecimentos(new ArrayList<Estabelecimento>());
		Assert.assertTrue(ReadData.getEstabelecimentos().isEmpty());
	}
	
	@Test
	public void testaCarregaArquivoNaoExistenteEstabelecimentos(){
		MenuInicial.pathEstabelecimentos = "";
		try {
			ReadData.initLists();
			Assert.fail("Erra pra ter dado erro.");
		} catch (Exception e) {
			Assert.assertEquals("Erro em initListas", "Problema na leitura do arquivo - Arquivo nao encontrado.", e.getMessage());
		}
	}
	
	@Test
	public void testaCarregaArquivoNaoExistenteOpinioes(){
		MenuInicial.pathOpinioes = "";
		try {
			ReadData.initLists();
			Assert.fail("Erra pra ter dado erro.");
		} catch (Exception e) {
			Assert.assertEquals("Erro em initListas", "Problema na leitura do arquivo - Arquivo nao encontrado.", e.getMessage());
		}
	}
	
	@Test
	public void testaNumberFormatError(){
		MenuInicial.pathOpinioes = "./src/lp2/testes/opinioesNumberFormatError.txt";
		try {
			ReadData.initLists();
			Assert.fail("Erra pra ter dado erro.");
		} catch (Exception e) {
			Assert.assertEquals("Problema na leitura do arquivo - Erro na conversao de numeros.", e.getMessage());
		}
	}
	
	@Test
	public void testaFormatoInvalidoOpinioes(){
		MenuInicial.pathOpinioes = "./src/lp2/testes/arquivoFormatoErrado.txt";
		try {
			ReadData.initLists();
			Assert.fail("Erra pra ter dado erro.");
		} catch (Exception e) {
			Assert.assertEquals("Problema na leitura do arquivo - Padrao de arquivo desconhecido.", e.getMessage());
		}
	}
	
	@Test
	public void testaFormatoInvalidoEstabelecimentos(){
		MenuInicial.pathEstabelecimentos = "./src/lp2/testes/arquivoFormatoErrado.txt";
		try {
			ReadData.initLists();
			Assert.fail("Erra pra ter dado erro.");
		} catch (Exception e) {
			Assert.assertEquals("Problema na leitura do arquivo - Padrao de arquivo desconhecido.", e.getMessage());
		}
	}
	
}
