package loginAluno;

public class ClientServerAluno {
	
	public int questionIndex;
	private String firstName;
	private String lastName;
	private String number;
	private String birthdayDate;
	private long timeConnect;


	public ClientServerAluno(String firstName,String lastName, String number, String birthdayDate, long timeConnect) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.number = number;
		this.birthdayDate = birthdayDate;
		this.timeConnect = timeConnect;
		questionIndex = 0;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getStudentNumber() {
		return this.number;
	}
	
	public String getBirthday() {
		return this.birthdayDate;
	}
	
	public int getCurrentQuestionIndex() {
		return this.questionIndex;
	}
	
	public void increaseCurrentQuestionIndex() {
		this.questionIndex++;
	}
	
	public void setCurrentCquestionIndex(int index) {
		this.questionIndex = index;
	}
	
	public long getTimeConnectMillis() {
		return this.timeConnect;
	}
	
	public long getTimeConnectMinuts() {
		return ((System.currentTimeMillis() - getTimeConnectMillis())/1000)/60;
	}	
}
