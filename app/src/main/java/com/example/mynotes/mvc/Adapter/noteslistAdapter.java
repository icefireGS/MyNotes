package com.example.mynotes.mvc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Interface.ClickCallBack;


import java.text.SimpleDateFormat;
import java.util.List;

public class noteslistAdapter extends ArrayAdapter {
    private int resourceId;
    private ClickCallBack mcall;

    public noteslistAdapter(Context context, int resource, List objects, ClickCallBack callback){
        super(context, resource, objects);
        mcall=callback;
        resourceId=resource;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        View view;
        ViewHolder holder;
        Note item=(Note) getItem(position);
        if(convertView==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            holder=new ViewHolder();
            holder.clicklayout=(LinearLayout) view.findViewById(R.id.item_click);
            holder.titleText=(TextView) view.findViewById(R.id.notetitle);
            holder.dateText=(TextView) view.findViewById(R.id.notetime);
            holder.puid=(TextView) view.findViewById(R.id.puid);
            view.setTag(holder);
        } else {
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }

        SimpleDateFormat time = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
        String lasttime=time.format(item.getTime());
        holder.titleText.setText(item.getTitle());
        holder.dateText.setText(lasttime);
        holder.puid.setText(String.valueOf(item.getTime()));

        holder.clicklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mcall.onItemClick(position);
            }
        });

        return view;
    }

    class ViewHolder{
        LinearLayout clicklayout;
        TextView titleText;
        TextView dateText;
        TextView puid;
    }
}
