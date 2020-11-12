package ru.gloomy.geoquiz;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionList {

    @SerializedName("questions")
    @Expose
    private List<QuizQuestion> quizQuestions = null;

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public int setQuizQuestions() {
        this.quizQuestions = quizQuestions;
        return 0;
    }


}



