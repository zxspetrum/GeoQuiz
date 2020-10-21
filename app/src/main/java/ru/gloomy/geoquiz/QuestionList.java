package ru.gloomy.geoquiz;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionList {

    @SerializedName("quiz questions")
    @Expose
    private List<QuizQuestion> quizQuestions = null;

    public List<QuizQuestion> getQuizQuestions() {
        return quizQuestions;
    }

    public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
        this.quizQuestions = quizQuestions;
    }


}



