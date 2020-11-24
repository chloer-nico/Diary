package com.example.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dhx
 */
public class MainActivity extends AppCompatActivity {
    /**
     * notes：listView的实例对象列表
     * */
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase dbDiary;
    private String authorName,authorSex,authorAge;
    List<Note> notes=new ArrayList<>();



    @Override
    /**
     * 创建菜单
     **/
    public boolean onCreateOptionsMenu(Menu menu) {
        //给当前menu分配资源文件preference
        getMenuInflater().inflate(R.menu.preference,menu);
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //首先读取sharedPreferences文件，不存在则新建一个默认作者为dhx的pref文件
        SharedPreferences pref=getSharedPreferences("authorInfo",0);
        if(pref==null){
            SharedPreferences.Editor editor=getSharedPreferences("authorInfo",0).edit();
            editor.putString("author","dhx");
            editor.putString("sex","男");
            editor.putString("age","21");
            editor.apply();
        }
        //不为空时读取其中的作者信息,没有的话用默认值替代
        authorName=pref.getString("author","zpy");
        authorSex=pref.getString("sex","女");
        authorAge=pref.getString("age","20");

        //打开数据库
        dbHelper=new MyDatabaseHelper(this,"diary.db",null,1);
        dbDiary=dbHelper.getWritableDatabase();
        //listView的适配器
        final NoteAdapter adapter = null;
        //一个id对应一个note对象,以便后续传递在listView的点击事件中传递点击的note实例
//        final Map<Integer,Note> map= new HashMap<Integer,Note>();

        //设置布局以及按钮
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonAdd=(Button)findViewById(R.id.add);
        Button buttonRefresh=(Button)findViewById(R.id.refresh);
        final ListView listView=(ListView)findViewById(R.id.listView);


        //点击新添按钮添加一篇日记
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddContent.class);
                //将作者信息传递下去
                intent.putExtra("authorName",authorName);
                startActivity(intent);
            }
        });

        //点击刷新按钮查询数据库内容，加入notes链表中
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=dbDiary.query("diary",null,null,null,null,null,null);
                //不能在循环体内部构造list，否则每次都会创建一个，最后内存会不足，每次应该在循环体里面清空
                notes.clear();


                while (cursor.moveToNext()){
                    String tittle=cursor.getString(cursor.getColumnIndex("tittle"));
                    String author=cursor.getString(cursor.getColumnIndex("author"));
                    String date=cursor.getString(cursor.getColumnIndex("date"));
                    String content=cursor.getString(cursor.getColumnIndex("content"));
                    int id=cursor.getInt(cursor.getColumnIndex("id"));
                    Note note=new Note(id,tittle,content,date,author);
                    notes.add(note);

                }
                cursor.close();

                //更新notes列表，显示在listView中,调用addNote方法
                addNote(adapter,notes,listView);
            }

        });

        //listView的点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.e("点击事件position", String.valueOf(position));
//                Log.e("map中的对应id", String.valueOf(map.get(position+1).getId()));

                //转到showContent中显示单篇日记的内容
                Intent intent=new Intent(MainActivity.this,ShowContent.class);
                //为下一个showContent活动传递点击的note的数据
//                Note note=map.get(position+1);
                Note note=new Note();
                note=notes.get(position);
//                Log.e("position为",String.valueOf(position));
//                Log.e("notes[position]的id",String.valueOf(note.getId()));
                intent.putExtra("diaryId", Integer.toString(note.getId()));
                intent.putExtra("tittle",note.getTittle());
                intent.putExtra("author", note.getAuthor());
                intent.putExtra("date", note.getDate());
                intent.putExtra("content", note.getContent());
                startActivity(intent);
            }
        });

        //菜单界面，用于维护作者信息

    }

    /**
     * 刷新notes列表，显示在listView中,为listView设置适配器
     */
    public void addNote(NoteAdapter adapter,List<Note> notes,ListView listView){
        adapter=new NoteAdapter(MainActivity.this,R.layout.note_item,notes);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    /**
     * 菜单的响应事件
     **/
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //点击作者信息，转到作者界面
            case R.id.authorMenu:
                Intent intent=new Intent(MainActivity.this,PreferenceForAuthor.class);
                //把读取到的作者信息传递给下一个活动
                intent.putExtra("authorName",authorName);
                intent.putExtra("authorSex",authorSex);
                intent.putExtra("authorAge",authorAge);
                startActivity(intent);
                break;
            //点击显示作者信息出一个toast
            case R.id.authorInfo:
                Toast.makeText(MainActivity.this,"作者为："+authorName+"，性别："+authorSex+"，年龄"+authorAge,Toast.LENGTH_SHORT).show();
                break;
            default:
        }
        return true;
    }

}

