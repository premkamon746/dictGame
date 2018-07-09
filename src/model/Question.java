package model;

import java.util.ArrayList;

public class Question {
    private String answer = "";
    private String question = "";
    ArrayList<String> choice = new ArrayList<String>();

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setChoice(ArrayList<String> choicr) {
        this.choice = choice;
    }

    public ArrayList<String> getItem() {
        return choice;
    }
}
