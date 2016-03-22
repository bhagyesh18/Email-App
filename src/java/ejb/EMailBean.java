package ejb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bean.MailData;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/*
Author Bhagyesh-zi2736 & Heliun5533
*/
@Stateful
@LocalBean
public class EMailBean implements EMail {
  @Resource(name="mail/JamesMailSession")
   private Session session; 
   private static final String mailer = "JavaMailer";
   private Store store=null;
   private Folder folder = null; 
   private Message [] messages=null;
   private Date lastUpdated;

   public EMailBean(){}

   public void sendMessage(String recipient, String sender, String subject, String data) {
     try {
       Message msg = new MimeMessage(session);
       msg.setFrom(new InternetAddress(sender,false));
       msg.setRecipients(Message.RecipientType.TO,
             InternetAddress.parse(recipient, false));
       msg.setSubject(subject);
       Date timeStamp = new Date();
       msg.setText(data);
       msg.setHeader("X-Mailer", mailer);
       msg.setSentDate(timeStamp);
       Transport.send(msg);
     } catch (Exception e) { throw new EJBException(e.getMessage());
     }
   }

   
   public List<MailData> getAllMessages() {
	   	
	   List<MailData> mails = new ArrayList<MailData>();
	   
       try {
    	   	   Date currentDate = new Date();
    	   	   long timeSpanInMins = (currentDate.getTime() - lastUpdated.getTime())/1000;
    	   	
    	   	   if(timeSpanInMins>60)
    	   	   {
    	    	   folder = store.getFolder("INBOX");
    	    	   folder.open(Folder.READ_WRITE);
    	    	   messages = folder.getMessages();
    	    	   lastUpdated = currentDate;
    	   	   }
    	   	   
			   for(Message message : messages)
			   {
				   MailData mailData = new MailData();
				   mailData.setMailNumber(message.getMessageNumber());
				   Address[] fromAdd = message.getFrom();
				   InternetAddress sender = (InternetAddress) fromAdd[0];
				   mailData.setFrom(sender.getAddress());
				   mailData.setSubject(message.getSubject());
				   mailData.setDate(message.getSentDate());
				   mailData.setMessage(message.getContent().toString());
				   mails.add(mailData);
			   }
		   
		} catch (MessagingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
      
	   return mails;  
   }
 
   
   public void connect(String login,String passwd) throws AuthenticationFailedException {
       try {
	    	   store=session.getStore("pop3");
		       store.connect("134.154.10.165",login,passwd);
	    	   folder = store.getFolder("INBOX");
	    	   folder.open(Folder.READ_WRITE);
	    	   messages = folder.getMessages();
	    	   lastUpdated = new Date();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		catch (AuthenticationFailedException e) {
			throw new AuthenticationFailedException();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
   }

   
   public void disconnect(List<Integer> deletedMailsNumbers) {
     try {
	    
    	   if(deletedMailsNumbers != null)
    	   {
    		   for(Integer mailNumber: deletedMailsNumbers)
    			   for(int i=0; i<messages.length; i++)
    			   {
    				   if(mailNumber == messages[i].getMessageNumber())
    				   {
    					   messages[i].setFlag(Flags.Flag.DELETED, true);
    					   System.out.println("Marked DELETE for message: " +  messages[i].getSubject());
    				   }
    				   
    			   }
    	   }
	      
	       folder.close(true);
	       store.close();
     }catch(Exception e) {System.out.println(e.toString());
     }
   }
}