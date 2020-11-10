package ru.gloomy.geoquiz;



import java.util.List;


public class QuizQuestion {


    private String question;
    private List<String> answers = null;
    private int trueAnswer;
    private String variant;

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

    public int getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(int trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

}