package com.example.diary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.diary.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.diary.Note;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private int resourceId;

    public NoteAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Note> objects) {
        super(context, textViewResourceId, objects);
        resourceId=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获取当前note实例
        Note note=getItem(position);
        View view;
        ViewHolder viewHolder;
        //convertView用于缓存，提高listView的效率
        if(convertView==null){
            //缓存为空时才加载布局
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            //缓存空时，创建一个用于缓存的实例
            viewHolder=new ViewHolder();
            //提示信息
            viewHolder.hintTittle=(TextView)view.findViewById(R.id.hintTittle);
            viewHolder.hintAuthor=(TextView)view.findViewById(R.id.hintAuthor);
            viewHolder.hintDate=(TextView)view.findViewById(R.id.hintDate);
            viewHolder.hintContent=(TextView)view.findViewById(R.id.hintContent);

            viewHolder.tittle=(TextView)view.findViewById(R.id.NoteTittle);
            viewHolder.author=(TextView)view.findViewById(R.id.NoteAuthor);
            viewHolder.date=(TextView)view.findViewById(R.id.NoteDate);
            viewHolder.content=(TextView)view.findViewById(R.id.NoteContent);
            //将viewHolder保存在view中
            view.setTag(viewHolder);
        }
        else {
            //否则重用convertView，以此达到了不会重复加载布局
            view=convertView;
            //重用viewHolder,重新获取viewHolder
            viewHolder=(ViewHolder)view.getTag();
        }
        //提示信息
        viewHolder.hintTittle.setText("标题");
        viewHolder.hintAuthor.setText("作者");
        viewHolder.hintDate.setText("日期");
        viewHolder.hintContent.setText("内容");
        //日记内容
        viewHolder.tittle.setText(note.getTittle());
        viewHolder.author.setText(note.getAuthor());
        viewHolder.date.setText(note.getDate());
        viewHolder.content.setText(note.getContent());
        return view;
    }

    //创建一个内部类ViewHolder,用于对控件的实例进行缓存
    public class ViewHolder{
        TextView hintTittle,hintAuthor,hintDate,hintContent;
        TextView tittle,author,date,content;
    }
}

