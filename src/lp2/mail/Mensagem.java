package lp2.mail;
import javax.naming.InvalidNameException;


public class Mensagem { 
        private String subject; 
        private String text; 
        public Mensagem(String text) throws InvalidNameException, NullPointerException { 
                this.subject = "Recomendações de estabelecimentos"; 
                this.text = text; 
        } 
    
        public String getSubject() { 
                return subject; 
        }
        
        public String getText() { 
                return text; 
        }
     
    
} 