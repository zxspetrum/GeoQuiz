package ru.gloomy.geoquiz;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public  class AdapterRecyclerView extends RecyclerView.Adapter <AdapterRecyclerView.ViewHolder> {

    private List<String> mAnswers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // передача данных в конструктор
    AdapterRecyclerView(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mAnswers = data;
    }

    // данный код увеличивает контейнер RecyclerView в случае необходимости
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // связывает данные с TextView в каждой строке
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String answers = mAnswers.get(position);
        holder.myTextView.setText(answers);
    }

    // общее количество строк
    @Override
    public int getItemCount() {
        return mAnswers.size();
    }


    // сохраняет и перерабатывает просмотры по мере их прокрутки за пределы экрана
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvAnswers);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // метод получения данных при клике
    String getItem(int id) {
        return mAnswers.get(id);
    }

    // обработчик кликов
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // activity будет реализовывать этот метод для ответа на события щелчка
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
