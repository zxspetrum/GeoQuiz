package ru.gloomy.geoquiz;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.NewViewHolder> {
    private List<QuizQuestion> listQuestionList = new ArrayList<>();

    public NewAdapter (List<QuizQuestion> mAnswers){
        listQuestionList = mAnswers;
    }
    public void dataSetChanged (List<QuizQuestion>listQuestionList){
        this.listQuestionList = listQuestionList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent,false);
        return new NewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder holder, int position) {

        holder.listQuestion.setText(listQuestionList.get(position).getQuestion());
    }

    @Override
    public int getItemCount() {
        return listQuestionList.size();
    }

    public static class NewViewHolder extends RecyclerView.ViewHolder{

        TextView listQuestion;
        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
             listQuestion = itemView.findViewById(R.id.rvAnswers);
        }
    }
}
