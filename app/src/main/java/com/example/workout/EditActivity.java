package com.example.workout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kongzue.dialog.v3.MessageDialog;

import org.litepal.LitePal;


public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final EditText title = findViewById(R.id.title);
        final EditText content = findViewById(R.id.content);
        Button saveBtn = findViewById(R.id.save_data);
        final Intent intent = getIntent();
        if (intent.hasExtra("EditTextId")){
            int data = intent.getIntExtra("EditTextId",1);
            Text text1 = LitePal.find(Text.class,data);
            title.setText(text1.getTitle());
            content.setText(text1.getContent());
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Text text = new Text();
                if (TextUtils.isEmpty(title.getText()) | TextUtils.isEmpty(content.getText())){
                    MessageDialog.show(EditActivity.this,"提示","请输入内容","确定");
                }else {
                    text.setTitle(title.getText().toString());
                    text.setContent(content.getText().toString());
                    if (intent.hasExtra("EditTextId")){
                        text.update(intent.getIntExtra("EditTextId",1));
                    }else {
                        text.save();
                    }
                    Intent intent = new Intent(EditActivity.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}
