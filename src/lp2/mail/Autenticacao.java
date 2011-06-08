package lp2.mail;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class Autenticacao extends Authenticator { 
        private String usuario; 
        private String senha; 
        public Autenticacao(String usuario, String senha) { 
                this.usuario = usuario; 
                this.senha = senha; 
        } 
  
        protected PasswordAuthentication getPasswordAuthentication() { 
                return new PasswordAuthentication (usuario,senha); 
        } 
}

