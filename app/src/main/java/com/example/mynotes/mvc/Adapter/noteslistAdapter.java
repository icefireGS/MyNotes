package com.example.mynotes.mvc.Adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mynotes.R;
import com.example.mynotes.mvc.Bean.Note;
import com.example.mynotes.mvc.Interface.ClickCallBack;


import java.text.SimpleDateFormat;
import java.util.List;

public class noteslistAdapter extends ArrayAdapter {
    private int resourceId;
    private boolean isdelstate;
    private ClickCallBack mcall;

    public noteslistAdapter(Context context, int resource, List objects, ClickCallBack callback){
        super(context, resource, objects);
        mcall=callback;
        resourceId=resource;
        isdelstate=false;
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
            holder.sc=(CheckBox) view.findViewById(R.id.sc_select);
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



        if(isdelstate){
            RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams) holder.clicklayout.getLayoutParams();
            lp.rightMargin=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55,this.getContext().getResources().getDisplayMetrics() );;
            holder.clicklayout.setLayoutParams(lp);
            holder.sc.setVisibility(View.VISIBLE);
            holder.clicklayout.setOnClickListener(null);

            holder.sc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        mcall.onCheckClick(position);
                    }else {
                        mcall.onCheckCancle(position);
                    }
                }
            });
        } else {
            RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams) holder.clicklayout.getLayoutParams();
            lp.rightMargin=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,this.getContext().getResources().getDisplayMetrics() );;;
            holder.clicklayout.setLayoutParams(lp);
            holder.sc.setVisibility(View.INVISIBLE);
            holder.clicklayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mcall.onItemClick(position);
                }
            });
        }

        return view;
    }

    class ViewHolder{
        LinearLayout clicklayout;
        TextView titleText;
        TextView dateText;
        TextView puid;
        CheckBox sc;
    }

    public void setDelState(boolean isdel){
        isdelstate=isdel;
    }
}
