package ru.gloomy.geoquiz.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import ru.gloomy.geoquiz.R;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private String[] mDataset;

// Предоставляем ссылку на представления для каждого элемента данных
// Для сложных элементов данных может потребоваться более одного представления для каждого элемента, и
// вы предоставляете доступ ко всем представлениям для элемента данных в держателе представления
    public static class MyViewHolder extends RecyclerView.ViewHolder {
    // в этом случае каждый элемент данных - это просто строка
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Предоставляем подходящий конструктор (зависит от типа набора данных)
    public Adapter(String[] myDataset) {
        mDataset = myDataset;
    }
    // Создаем новые представления (вызываемые менеджером по расположению)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // создаем новое view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_test, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Заменить содержимое представления (вызываемого менеджером по расположению)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

// - получить элемент из вашего набора данных в этой позиции
// - заменяем содержимое представления этим элементом
        holder.textView.setText(mDataset[position]);

    }

    // Возвращаем размер вашего набора данных (вызывается менеджером по расположению)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
