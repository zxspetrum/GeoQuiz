package ru.gloomy.geoquiz;

import android.widget.ArrayAdapter;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizQuestion {

    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answers")
    @Expose
    private List<String> answers = null;
    @SerializedName("true_answer")
    @Expose
    private Long trueAnswer;
    @SerializedName("variant")
    @Expose
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