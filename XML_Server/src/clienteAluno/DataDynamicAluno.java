package clienteAluno;

import java.util.ArrayList;

public class DataDynamicAluno {
	
	private ArrayList<String> questions;
	private int counter;

	public DataDynamicAluno() {
		questions = new ArrayList<String>();
		counter = 0;
	}
	
	public void addQuestion(String question) {
		questions.add(question);
	}
	
	public String getIdOfQuestion(int index) {
		return questions.get(index);
	}
	
	public int getIndexQuestion() {
		return this.counter;
	}
	
	public void questionAnswered() {
		counter++;
	}
	
	

}
