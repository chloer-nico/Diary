package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author dhx
 * 用于处理作者的preference信息
 */
public class PreferenceForAuthor extends AppCompatActivity {
    private EditText authorName,authorSex,authorAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent=getIntent();
        setContentView(R.layout.activity_preference_for_author);

        authorName=(EditText)findViewById(R.id.authorName);
        authorSex=(EditText)findViewById(R.id.authorSex);;
        authorAge=(EditText)findViewById(R.id.authorAge);;
        Button buttonSave=(Button)findViewById(R.id.saveAuthor);
        Button buttonCancel=(Button)findViewById(R.id.cancelAuthor);

        //加载EditText
            authorName.setText(intent.getStringExtra("authorName"));
            authorAge.setText(intent.getStringExtra("authorAge"));
            authorSex.setText(intent.getStringExtra("authorSex"));

        //修改pref
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("authorInfo",0).edit();
                editor.clear();
                String strName=authorName.getText().toString();
                String strSex=authorSex.getText().toString();
                String strAge=authorAge.getText().toString();

                editor.putString("author",strName);
                editor.putString("sex",strSex);
                editor.putString("age",strAge);
                editor.apply();

                Toast.makeText(PreferenceForAuthor.this,"修改成功!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(PreferenceForAuthor.this,MainActivity.class);
                startActivity(intent);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PreferenceForAuthor.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}