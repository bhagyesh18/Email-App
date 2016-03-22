package ejb;

import java.util.List;

import javax.ejb.Remote;
import javax.mail.AuthenticationFailedException;

import bean.MailData;
/*
Author Bhagyesh-zi2736 & Heliun5533
*/
@Remote
public interface EMail {
  public void sendMessage(String recipient,String sender,String subject, String data);
  public void connect(String login, String passwd) throws AuthenticationFailedException;
  public void disconnect(List<Integer> deletedMailsNumbers);
  public List<MailData> getAllMessages();
}