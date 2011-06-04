package lp2.lerDados;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lp2.interfaces.MenuInicial;

/**
 * Classe que le os dados de um arquivo para futura manipulacao no sistema de
 * recomendacoes.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 * 
 */
public class ReadData {
	
	private static List<Usuario> usuarios;
	private static List<Estabelecimento> estabelecimentos;
	
	/**
	 * Metodo que instancia um objeto da classe ReadData que eh responsavel
	 * por ler e armazenar os dados contidos nos arquivos, que nos oferecem informacaoes 
	 * sobre os restaurantes e sobre as opinioes dos usuarios do sistema.
	 *
	 */
	public static void initLists() throws Exception{
		usuarios = new ArrayList<Usuario>();
		estabelecimentos = new ArrayList<Estabelecimento>();
		
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(new FileInputStream(MenuInicial.pathEstabelecimentos), "UTF-8"));
			List<Estabelecimento> listaTemporaria = new ArrayList<Estabelecimento>();
			String testaPadraoDoArquivo = buffer.readLine(); //comendo linha inicial
			if((testaPadraoDoArquivo == null) || !testaPadraoDoArquivo.equals("Nome;EndereÁo;\"Tipo de almoÁo (self service, prato feito, a la carte)\"") 
					&& !testaPadraoDoArquivo.equals("Nome;Endere√ßo;\"Tipo de almo√ßo (self service, prato feito, a la carte)\"")){
				throw new Exception("Padrao de arquivo desconhecido.");
			}
			String linha;
			
			while((linha = buffer.readLine()) != null){
				String estabelecimento_localizacao_comida[];
				if(linha.contains(";"))
					estabelecimento_localizacao_comida = linha.split(";");
				else
					estabelecimento_localizacao_comida = linha.split(",");
				
				if(estabelecimento_localizacao_comida != null && estabelecimento_localizacao_comida.length == 3)
					listaTemporaria.add(new Estabelecimento(estabelecimento_localizacao_comida[0], estabelecimento_localizacao_comida[1].replace("\"", ""), estabelecimento_localizacao_comida[2]));
			}

			buffer = new BufferedReader(new InputStreamReader(new FileInputStream(MenuInicial.pathOpinioes), "UTF-8"));
			testaPadraoDoArquivo = buffer.readLine();
			if ((testaPadraoDoArquivo == null) || !testaPadraoDoArquivo
					.equals("IndicaÁ„o de data e hora;Seu nome;Bar do Cuscuz e Restaurante;Baixinho Bar e Restaurante;" +
							"Bar do George;Bar do Santos;Bod„o Bar e Restaurante;Bonaparte;Bongustaio;Cabana do PossidÙnio;" +
							"Cantina da Sayonara;Cantina de Dona InÍs;Cantina de Olavo;Cantina do Departamento de Meteorologia;" +
							"Cantina do Hall das Placas;Cantinho Universit·rio;Chinatown;Divino Fog„o;Fazenda;Giraffas;Girassol;" +
							"Gulas;Manoel da carne de sol;Marmitaria ArtCulin·ria;Marmitaria Bom paladar;Marmitaria da Mama;Paladar;" +
							"Pedro da Picanha;Qdoca Bar e Restaurante;Restaurante BrasÌlia;Restaurante da Quadra;Restaurante do Alexandre;" +
							"Restaurante do Paran·;Restaurante Golden in China;Restaurante Lay China;Riso;Spolleto;Super China Restaurante;" +
							"Trailer do Marcus;Tux·;Varandas Bar e Restaurante")
					&& !testaPadraoDoArquivo
							.equals("Indica√ß√£o de data e hora;Seu nome;Bar do Cuscuz e Restaurante;Baixinho Bar e Restaurante;" +
									"Bar do George;Bar do Santos;Bod√£o Bar e Restaurante;Bonaparte;Bongustaio;Cabana do Possid√¥nio;" +
									"Cantina da Sayonara;Cantina de Dona In√™s;Cantina de Olavo;Cantina do Departamento de Meteorologia;" +
									"Cantina do Hall das Placas;Cantinho Universit√°rio;Chinatown;Divino Fog√£o;Fazenda;Giraffas;Girassol;" +
									"Gulas;Manoel da carne de sol;Marmitaria ArtCulin√°ria;Marmitaria Bom paladar;Marmitaria da Mama;Paladar;" +
									"Pedro da Picanha;Qdoca Bar e Restaurante;Restaurante Bras√≠lia;Restaurante da Quadra;Restaurante do " +
									"Alexandre;Restaurante do Paran√°;Restaurante Golden in China;Restaurante Lay China;Riso;Spolleto;Super" +
									" China Restaurante;Trailer do Marcus;Tux√°;Varandas Bar e Restaurante")) {
				throw new Exception("Padrao de arquivo desconhecido.");
			}
			String ordemDosEstabelecimentos[] = testaPadraoDoArquivo.split(";");

			int k=0;
			while((linha = buffer.readLine()) != null){
				String nome = null;
				List<Integer> opinioes = new ArrayList<Integer>();
				
				String dadosLinha[] = linha.split(";");
				if(dadosLinha != null && dadosLinha.length > 1){
					if(dadosLinha[1].equals("")){
						k++;
						if(k >= 1000){
							dadosLinha[1] = "anonimo" + k;
						}else if(k < 1000 && k >= 100){
							dadosLinha[1] = "anonimo0" + k;
						}else if(k < 100 && k >= 10){
							dadosLinha[1] = "anonimo00" + k;
						}else if(k < 10 && k >= 0){
							dadosLinha[1] = "anonimo000" + k;
						}
					}
					nome = dadosLinha[1];
					for(int i=2; i<dadosLinha.length; i++){
						if(dadosLinha[i].contains("\"")){
							opinioes.add(Integer.parseInt(dadosLinha[i].substring(1,2)));
						}else{
							if(dadosLinha[i].substring(0,1).equals("-")){
								opinioes.add(Integer.parseInt(dadosLinha[i].substring(0,2)));
							}else{
								opinioes.add(Integer.parseInt(dadosLinha[i].substring(0,1)));
							}
						}
					}
				}
		
				if(nome != null && !nome.equals("") && opinioes != null && !opinioes.isEmpty() && opinioes.size() == 39){
					Usuario temp = new Usuario(nome, opinioes);
					temp.setDataHoraCadastro(dadosLinha[0]);
					usuarios.add(temp);
				}
			}
			
			//ordenando os usuarios
			for(int i=0; i<usuarios.size(); i++){
				for(int j=i+1; j<usuarios.size(); j++){
					if(usuarios.get(i).comparaPorNome(usuarios.get(j)) > 0){
						Usuario temp = usuarios.get(i);
						usuarios.set(i, usuarios.get(j));
						usuarios.set(j, temp);
					}
				}
			}
			
			//ordenando os estabelecimentos
			for(int i=2; i < ordemDosEstabelecimentos.length; i++){
				for(Estabelecimento e : listaTemporaria){
					if(ordemDosEstabelecimentos[i].equalsIgnoreCase(e.getNome())){
						estabelecimentos.add(e);
						break;
					}
				}
			}
		} catch (NumberFormatException e){
			throw new Exception("Problema na leitura do arquivo - Erro na conversao de numeros.");
		} catch (FileNotFoundException e) {
			throw new Exception("Problema na leitura do arquivo - Arquivo nao encontrado.");
		} catch (IOException e) {
			throw new Exception("Problema na leitura do arquivo - Erro de InputOutput.");
		} catch (Exception e){
			throw new Exception("Problema na leitura do arquivo - " + e.getMessage());
		} finally {
			try {
				if(buffer != null)
					buffer.close();
			} catch (IOException e) {
				throw new Exception("Os arquivos nao foram fechados corretamente. E altamente recomendado que o programa seja reiniciado.");
			}
		}
	}
	
	/**
	 * Retorna um List contendo todos os usuarios cadastrados no sistema.
	 * @return
	 * 			um List contendo todos os usuarios cadastrados no sistema.
	 */
	public static List<Usuario> getUsuarios() {
		return usuarios;
	}

	
	/**
	 * Modifica o List que contem os usuarios do sistema.
	 * @param usuarios
	 * 			novo List contendo os usuarios do sistema.
	 * @throws Exception
	 * 			caso o novo List contendo os usuarios seja igual a null.
	 */
	public static void setUsuarios(List<Usuario> usuarios) throws Exception{
		if(usuarios == null){
			throw new Exception("Lista de Usuarios nula/vazia.");
		}
		ReadData.usuarios = usuarios;
	}

	/**
	 * Retorna um List contento todos os estabelecimentos contidos no sistema.
	 * @return
	 * 			um List contento todos os estabelecimentos contidos no sistema.
	 */
	public static List<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	/**
	 * Modifica o List que contem todos os estabelecimentos do sistem.
	 * @param estabelecimentos
	 * 			novo List dos estebelecimentos contidos no sistema.
	 * @throws Exception
	 * 			caso o novo List dos estabelecimentos seja igual a null.
	 */
	public static void setEstabelecimentos(List<Estabelecimento> estabelecimentos) throws Exception{
		if(estabelecimentos == null){
			throw new Exception("Lista de Estabelecimentos nula/vazia.");
		}
		ReadData.estabelecimentos = estabelecimentos;
	}
	
}
