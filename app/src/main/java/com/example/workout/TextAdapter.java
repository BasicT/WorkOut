package com.example.workout;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {

    private List<Text> mTexts;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View textView;
        TextView textTitle;

        public ViewHolder(View view){
            super(view);
            textView = view;
            textTitle = (TextView) view.findViewById(R.id.title);
        }
    }

    public TextAdapter(List<Text> texts) {
        mTexts = texts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text,viewGroup,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Text text = mTexts.get(position);
                Intent intent = new Intent(v.getContext(),EditActivity.class);
                intent.putExtra("EditTextId",text.getId());
                v.getContext().startActivity(intent);
            }
        });
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = holder.getAdapterPosition();
                Text text = mTexts.get(position);
                LitePal.deleteAll(Text.class,"title = ?",text.getTitle());
                notifyDataSetChanged();
                return true;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Text text = mTexts.get(i);
        viewHolder.textTitle.setText(text.getTitle());
    }

    @Override
    public int getItemCount() {
        return mTexts.size();
    }
}
