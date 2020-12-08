package ru.gloomy.geoquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class AdapterLearn extends RecyclerView.Adapter<AdapterLearn.ViewHolder> {

    private  List<String> mDataLearn;
    private  LayoutInflater mInflaterTitle;
    private ItemClickListener mClickListener;

    AdapterLearn(Context context, String[] mData) {
        this.mInflaterTitle = LayoutInflater.from(context);
        this.mDataLearn = Arrays.asList(mData);
    }
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflaterTitle.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder( AdapterLearn.ViewHolder holder, int position) {
        String title = mDataLearn.get(position);
      //  holder.mTextViewTitle.setText(title);
    }
    @Override
    public int getItemCount() {
        return mDataLearn.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTextViewTitle;

        ViewHolder(View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.rvTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    String getItem(int id) {
        return mDataLearn.get(id);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}