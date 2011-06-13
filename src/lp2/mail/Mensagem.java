package lp2.mail;
import javax.naming.InvalidNameException;



/**
 * Classe responsavel por configurar a mensagem que sera 
 * enviada ao usuario do sistema por email
 * 
 * @author Flavia Gangorra<br>
 *         Irvile Rodrigues Lavor<br>
 *         Jordao Ezequiel Serafim de Araujo<br>
 *
 */
public class Mensagem { 
        private String subject; 
        private String text; 
        
        /**
         * Metodo que configura a mensagem a ser enviada
         * ao destinatario por email.
         * @param text
         * 			texto da mensagem, recomendacoes.
         * @throws InvalidNameException
         * @throws NullPointerException
         */
        public Mensagem(String text) throws InvalidNameException, NullPointerException { 
                this.subject = "Recomendações de estabelecimentos"; 
                this.text = text; 
        } 
        

        /**
         * Retorna o subject do email.
         * @return
         * 			o subject do email.
         */
        public String getSubject() { 
                return subject; 
        }
        
        
        /**
         * Retorna o texto do email.
         * @return
         * 			o texto do email.
         */
        public String getText() { 
                return text; 
        }
     
    
} 