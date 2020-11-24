/**添加一条日记内容*/
package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContent extends AppCompatActivity {
    private EditText tittle,author,date,content;
    private SQLiteDatabase dbDiary;
    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper=new MyDatabaseHelper(this,"diary.db",null,1);
        dbDiary=dbHelper.getWritableDatabase();

        //加载界面元素
        setContentView(R.layout.activity_add_content);
        tittle=(EditText) findViewById(R.id.tittle);
        author=(EditText) findViewById(R.id.author);
        date=(EditText) findViewById(R.id.date);
        content=(EditText) findViewById(R.id.content);
        Button buttonSave=(Button) findViewById(R.id.saveAuthor);
        Button buttonCancel=(Button) findViewById(R.id.cancelAuthor);

        //预加载作者信息
        Intent intent=getIntent();
        author.setText(intent.getStringExtra("authorName"));

        //保存至数据库
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTittle=tittle.getText().toString();
                String strAuthor=author.getText().toString();
                String strDate=date.getText().toString();
                String strContent=content.getText().toString();
                //开始组装数据
                ContentValues values=new ContentValues();
                values.put("tittle",strTittle);
                values.put("content",strContent);
                values.put("date",strDate);
                values.put("author",strAuthor);
                dbDiary.insert("diary",null,values);
                Toast.makeText(AddContent.this,"保存成功!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddContent.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //返回主活动页面
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddContent.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}