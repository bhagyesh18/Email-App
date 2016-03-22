package bean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.AuthenticationFailedException;

import ejb.EMail;
/*

Author Bhagyesh-zi2736 & Heliun5533
*/
@ManagedBean
@SessionScoped
public class EmailBean {

	@EJB
	private EMail email;
	private MessageMailBean messageMessageBean = new MessageMailBean();
	
        private String recipient;
	private String subject;
	private String message;

        private String loginId;
	private String password;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
        
         public MessageMailBean getMessageMessageBean() {
        return messageMessageBean;
    }

    public void setMessageMessageBean(MessageMailBean messageMessageBean) {
        this.messageMessageBean = messageMessageBean;
    }
	
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void clear()
	{
		this.recipient="";
		this.subject="";
		this.message="";
	}
        
	public String login()
	{
		try {
			email.connect(getLoginId(), getPassword());
		} catch (AuthenticationFailedException e) {
			addErrorMessage("The Username or password is incorrect.");
			return "login";
		}
		return "welcome";
	}
	
	public String sendMail() 
	{
		email.sendMessage(getRecipient(),  getLoginId()+"@yyuj.sci.csueastbay.edu", getSubject(), getMessage());
		clear();
		return "welcome";
	}
	
	public String checkMail()
	{
		messageMessageBean.setMessages(email.getAllMessages());
		return "checkMail";
	}
	
	public String logout()
	{
		email.disconnect(messageMessageBean.getDeletedMailsNumber());
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "login";
	}
	
	public void addErrorMessage(String error )
	{
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(error);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(null, message);
	}

   
	
	
	
}
