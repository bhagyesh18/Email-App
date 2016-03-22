package bean;

import java.util.Date;
/*

Author Bhagyesh-zi2736 & Heliun5533
*/
public class MailData {

	private int mailNumber;
	private boolean selected;
	private String from;
	private String subject;
	private Date date;
	private String message;
	private boolean markedForDelete;
	
	public MailData() {}
	
	public MailData(String from, String subject, Date date, String message) {
		super();
		this.from = from;
		this.subject = subject;
		this.date = date;
		this.message = message;
	}

	public int getMailNumber() {
		return mailNumber;
	}

	public void setMailNumber(int mailNumber) {
		this.mailNumber = mailNumber;
	}

	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isMarkedForDelete() {
		return markedForDelete;
	}

	public void setMarkedForDelete(boolean markedForDelete) {
		this.markedForDelete = markedForDelete;
	}

	public String toString()
	{
		return "Sender: "+from
				+"<br/><br/>Subject: "+subject
				+"<br/><br/>Date: "+date
				+"<br/><br/><br/>"+message;
	}
}
