package bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/*
Author Bhagyesh-zi2736 & Heliun5533
*/
public class MessageMailBean {

	private List<MailData> messages = new ArrayList<MailData>();
	private MailData mailData;
	private List<Integer> deletedMailsNumber = new ArrayList<Integer>();

	public List<MailData> getMessages() {
		return messages;
	}

	public void setMessages(List<MailData> messages) {
		this.messages.clear();
		for(MailData mailData : messages) {
			boolean isDeletedMail = false;
			for(Integer deletedMailNumber : deletedMailsNumber) {
				if(mailData.getMailNumber() == deletedMailNumber) {
					isDeletedMail = true;
					break;
				}
			}
			if(!isDeletedMail)
				this.messages.add(mailData);
		}
		
	}
	
	public MailData getMailData() {
		return mailData;
	}

	public void setMailData(MailData mailData) {
		this.mailData = mailData;
	}
	
	 public void display(MailData mailData) {
		    this.mailData = mailData;
		    System.out.println("for display mail = " + this.mailData);
	}
	 
	public List<Integer> getDeletedMailsNumber() {
		return deletedMailsNumber;
	}

	public void setDeletedMailsNumber(List<Integer> deletedMailsNumber) {
		this.deletedMailsNumber = deletedMailsNumber;
	}

	public void deleteMail()  {
		
		    Iterator <MailData> itr=messages.iterator();
		    while(itr.hasNext()) {
		    	MailData data = (MailData)itr.next();
		      if(data.isSelected()) {
		        int i = messages.indexOf(data);
		        deletedMailsNumber.add(data.getMailNumber());
		        if (i != -1) {itr.remove();}
		      }
		    }
		  }
}
