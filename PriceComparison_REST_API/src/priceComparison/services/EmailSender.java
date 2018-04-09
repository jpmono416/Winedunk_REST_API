package priceComparison.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    
	private String senderSMTPHost;
	public String getSenderSMTPHost() { return this.senderSMTPHost; }
	public void setSenderSMTPHost(String senderSMTPHost) { this.senderSMTPHost = senderSMTPHost; }
    
	private String senderSMTPPort;
	public String getSenderSMTPPort() { return this.senderSMTPPort; }
	public void setSenderSMTPPort(String senderSMTPPort) { this.senderSMTPPort = senderSMTPPort; }
    
	private String senderUsername;
	public String getSenderUsername() { return this.senderUsername; }
	public void setSenderUsername(String senderUsername) { this.senderUsername = senderUsername; }
    
	private String senderEmailAddress;
	public String getSenderEmailAddress() { return this.senderEmailAddress; }
	public void setSenderEmailAddress(String senderEmailAddress) { this.senderEmailAddress = senderEmailAddress; }
	
	private String senderPassword;
	public String getSenderPassword() { return this.senderPassword; }
	public void setSenderPassword(String senderPassword) { this.senderPassword = senderPassword; }
	
	private String receiverEmailAddress;
	public String getReceiverEmailAddress() { return this.receiverEmailAddress; }
	public void setReceiverEmailAddress(String receiverEmailAddress) { this.receiverEmailAddress = receiverEmailAddress; }
	
	private String emailSubject;
	public String getEmailSubject() { return this.emailSubject; }
	public void setEmailSubject(String emailSubject) { this.emailSubject = emailSubject; }
	
	private String emailText;
	public String getEmailText() { return this.emailText; }
	public void setEmailText(String emailText) { this.emailText = emailText; }
	
    private static Properties props = new Properties();
    
    public EmailSender() {

    	Config config = new Config();
    	setSenderSMTPHost(config.getEmailSmtpHost());
		setSenderSMTPPort(config.getEmailSmtpPort());
		setSenderUsername(config.getEmailUsername());
		setSenderEmailAddress(config.getEmailAddress());
		setSenderPassword(config.getEmailPassword());
    	setReceiverEmailAddress(""); // to be assigned from external
    	setEmailSubject(""); // to be assigned from external
    	setEmailText(""); // to be assigned from external

    	// setting properties
		props.put("mail.smtps.user", getSenderUsername());
		props.put("mail.smtps.from", getSenderEmailAddress());
    	props.put("mail.smtps.host", getSenderSMTPHost());
		props.put("mail.smtps.port", getSenderSMTPPort());
		props.put("mail.smtps.auth", "true"); 
		props.put("mail.smtps.sendpartial", "true");
		props.put("mail.smtps.starttls.enable", "true"); 
    }
    
    public void send() {
    	System.out.println("GOT TO THE SEND METHOD"); // TODO DELETE
    	
		// creating new Session with properties
		Session session = Session.getInstance(props,
	            new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(senderEmailAddress, senderPassword);
	                }
	            });
    	
    	try{
			
    		Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmailAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmailAddress));
            message.setSubject(emailSubject);
            message.setContent(emailText, "text/html; charset=utf-8");
  
            Transport transport = session.getTransport("smtp");
            transport.connect(senderSMTPHost, senderEmailAddress, senderPassword);
            transport.sendMessage(message, message.getAllRecipients());
  
            System.out.println("#################### Done #################### ");
    		
		}catch (MessagingException e){
			System.out.println("MessagingException");
    		throw new RuntimeException(e);
		}
    		
    }
    
}