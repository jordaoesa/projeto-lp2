package lp2.mail;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Classe responsavel por autenticar o endereco de email do sistema, para que o
 * mesmo possa enviar as recomendacoes para o usuario via web.
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 * 
 * 
 * <br>
 *         OBS: Auxiliado por Felipe De Sousa. 
 */
class Autenticacao extends Authenticator { 
        private String usuario; 
        private String senha; 
        
        /**
         * Autentica o email do sistema.
         * @param usuario
         * 			usuario
         * @param senha
         * 			senha
         */
        public Autenticacao(String usuario, String senha) { 
                this.usuario = usuario; 
                this.senha = senha; 
        } 
  
        protected PasswordAuthentication getPasswordAuthentication() { 
                return new PasswordAuthentication (usuario,senha); 
        } 
}

