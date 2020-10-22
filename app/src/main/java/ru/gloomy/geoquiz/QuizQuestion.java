package ru.gloomy.geoquiz;

import android.widget.ArrayAdapter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizQuestion {


    private String question;
    private List<String> answers = null;
    private Long trueAnswer;
    private String variant;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers(ArrayAdapter adapter) {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public Long getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(Long trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

}