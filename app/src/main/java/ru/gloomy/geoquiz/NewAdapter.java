package ru.gloomy.geoquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> mAnswer;
    private OnAdapterClickListener mListener;

    public NewAdapter(Context context, List<String> mAnswer, OnAdapterClickListener mListener) {
this.mAnswer=mAnswer;
this.mContext=context;
this.mListener=mListener;

 }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item,parent, false);
        RecyclerView.ViewHolder holder = new NamesViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NamesViewHolder viewHolder = (NamesViewHolder) holder;

        viewHolder.name.setText(mAnswer.get(position));
        viewHolder.itemView.setTag(mAnswer.get(position));
    }

    @Override
    public int getItemCount() {

        if(mAnswer ==null) return 0;
        return mAnswer.size();
    }
    class NamesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        public NamesViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.rvAnswers);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            itemView.setOnClickListener(this);
        }
    }

    interface OnAdapterClickListener{
        void OnAdapterClick(int position, String name);

    }
}