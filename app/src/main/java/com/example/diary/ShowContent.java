/**显示一条日记内容*/
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

/**
 * @author dhx
 */
public class ShowContent extends AppCompatActivity {
    private EditText tittle,author,date,content;
    private SQLiteDatabase dbDiary;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        //获取传递过来的intent对象
        final Intent intent=getIntent();

        dbHelper=new MyDatabaseHelper(this,"diary.db",null,1);
        dbDiary=dbHelper.getWritableDatabase();

        //加载界面元素
        tittle=(EditText) findViewById(R.id.tittle);
        author=(EditText) findViewById(R.id.author);
        date=(EditText) findViewById(R.id.date);
        content=(EditText) findViewById(R.id.content);
        Button buttonSave=(Button) findViewById(R.id.saveAuthor);
        Button buttonCancel=(Button) findViewById(R.id.cancelAuthor);
        Button buttonDelete=(Button) findViewById(R.id.delete);

        //依据intent加载传递的data
        tittle.setText(intent.getStringExtra("tittle"));
        author.setText(intent.getStringExtra("author"));
        date.setText(intent.getStringExtra("date"));
        content.setText(intent.getStringExtra("content"));

        //save点击事件，更新数据库内容
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= intent.getStringExtra("diaryId");
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

                dbDiary.update("diary",values,"id=?",new String[]{id});
                Toast.makeText(ShowContent.this,"更新成功!",Toast.LENGTH_SHORT).show();
                //返回界面
                Intent intent=new Intent(ShowContent.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //点击取消按钮，返回主活动页面
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowContent.this,MainActivity.class);
                startActivity(intent);
            }
        });

        //点击删除按钮，删除日记
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id= intent.getStringExtra("diaryId");
                //根据id删除日记
                dbDiary.delete("diary","id=?",new String[]{id});
                Toast.makeText(ShowContent.this,"删除成功!",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ShowContent.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}