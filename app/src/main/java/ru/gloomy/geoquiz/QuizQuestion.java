package ru.gloomy.geoquiz;



import java.util.List;


public class QuizQuestion {


    private String question;
    private List<String> answers = null;
    private String trueAnswer;
    private int variant;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public String  setTrueAnswer() {
        return trueAnswer;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }


}