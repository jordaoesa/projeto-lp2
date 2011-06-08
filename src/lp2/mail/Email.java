package lp2.mail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.InvalidNameException;
import javax.swing.JOptionPane;

public class Email extends  Mensagem implements Runnable {

        private final String usuario = "bom.conselho.ufcg@gmail.com";
        private final String senha = "irvileflaviajordao2011";
    
        private static final String SMTP_HOST_NAME = "smtp.gmail.com";
        private static final String SMTP_PORT = "465";
        
        public static final String EMAILDOREMETENTE = "bom.conselho.ufcg@gmail.com";
        private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        private String to;
        private String from;
       
        public Email(String to, String text) throws InvalidNameException, NullPointerException {
                super(text);
                this.to = to;
                this.from = "WebServicoes-BomConselho";
        }
        public String getTo() {
                return to;
        }
        public String getFrom() {
                return from;
        }

        public void run() {
                try {
                        Properties mailProps = new Properties();
                        mailProps.put("mail.smtp.host", SMTP_HOST_NAME);
                       
                        Autenticacao auth = null;
                        auth = new Autenticacao (usuario,senha);
                       
                        mailProps.put("mail.smtp.auth", "true");
                        mailProps.put("mail.user",usuario);
                        mailProps.put("mail.from",getFrom());
                        mailProps.put("mail.to", getTo());
                       
                        mailProps.put("mail.smtp.host", SMTP_HOST_NAME);
                        mailProps.put("mail.smtp.auth", "true");
                        mailProps.put("mail.debug", "false");
                       
                        mailProps.put("mail.smtp.port", SMTP_PORT);
                        mailProps.put("mail.smtp.socketFactory.port", SMTP_PORT);
                        mailProps.put("mail.smtp.socketFactory.class", SSL_FACTORY);
                        mailProps.put("mail.smtp.socketFactory.fallback", "false");
                       
                       
                        Session mailSession = Session.getDefaultInstance(mailProps,auth);
                       
                       
                        mailSession.setDebug(false);
                       
                        Message email = new MimeMessage (mailSession);
                        email.setRecipients( Message.RecipientType.TO, InternetAddress.parse(getTo()) );                       
                       
                        email.setFrom( new InternetAddress(getFrom()));
                        email.setSubject(getSubject());
                        email.setContent( getText(), "text/plain" );
                       
                        Transport tr = mailSession.getTransport("smtp");
                        tr.connect(SMTP_HOST_NAME, usuario, senha);
                        email.saveChanges();  
                        tr.sendMessage(email, email.getAllRecipients());
                        JOptionPane.showMessageDialog(null,"Email enviado com sucesso");
                        tr.close();
                }
                catch (Exception e) {  
                        JOptionPane.showMessageDialog(null, e.getMessage());  
                }  
        }
}


