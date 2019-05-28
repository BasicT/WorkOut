package com.example.workout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.util.BaseDialog;
import com.kongzue.dialog.v3.MessageDialog;

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
            textTitle =  view.findViewById(R.id.title);
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
                Text text = mTexts.get(holder.getAdapterPosition());
                Intent intent = new Intent(v.getContext(),EditActivity.class);
                intent.putExtra("EditTextId",text.getId());
                v.getContext().startActivity(intent);
            }
        });
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Text text = mTexts.get(holder.getAdapterPosition());
                    MessageDialog.show(MainActivity.getMainActivity(),"警示","是否删除本条记录","确定","取消")
                            .setOnOkButtonClickListener(new OnDialogButtonClickListener() {
                                @Override
                                public boolean onClick(BaseDialog baseDialog, View v) {
                                    LitePal.deleteAll(Text.class,"title = ?",text.getTitle());
                                    mTexts.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    notifyItemRangeChanged(holder.getAdapterPosition(),getItemCount());
                                    return false;
                                }
                            })
                            .setOnCancelButtonClickListener(new OnDialogButtonClickListener() {
                                @Override
                                public boolean onClick(BaseDialog baseDialog, View v) {
                                    return false;
                                }
                            });
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
