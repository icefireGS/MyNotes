package com.example.mynotes.mvc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Bean.Note;


import java.text.SimpleDateFormat;
import java.util.List;

public class noteslistAdapter extends ArrayAdapter {
    private int resourceId;

    public noteslistAdapter(Context context, int resource, List objects){
        super(context, resource, objects);
        resourceId=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        ViewHolder holder;
        Note item=(Note) getItem(position);
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder();
            holder.titleText=(TextView) view.findViewById(R.id.notetitle);
            holder.dateText=(TextView) view.findViewById(R.id.notetime);
            view.setTag(holder);
        } else {
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }

        SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
        String lasttime=time.format(item.getTime());
        holder.titleText.setText(item.getTitle());
        holder.dateText.setText(lasttime);
        return view;
    }

    class ViewHolder{
        TextView titleText;
        TextView dateText;
    }
}
