package com.blogspost.mkumerg.evaluationoftrainer.adaptorsource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blogspost.mkumerg.evaluationoftrainer.R;

import java.util.ArrayList;

/**
 * Created by mkumerg on 10/19/15.
 */
public class TrainerBaseAdapt extends BaseAdapter {
    private ArrayList<TrainerDetails> trainers;

    private LayoutInflater l_Inflater;

    public TrainerBaseAdapt(Context context, ArrayList<TrainerDetails> trainerDetailses) {
        trainers = trainerDetailses;
        l_Inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return trainers.size();
    }

    @Override
    public Object getItem(int position) {
        return trainers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = l_Inflater.inflate(R.layout.single_trainer_view,null);
            holder = new ViewHolder();
            holder.trainerName = (TextView)convertView.findViewById(R.id.tv_trainer_name);
            holder.trainerDes = (TextView)convertView.findViewById(R.id.tv_trainer_des);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.trainerName.setText(trainers.get(position).getTrainerName());
        holder.trainerDes.setText(trainers.get(position).getTrainerDescription());
        return convertView;
    }

    static class ViewHolder{
        TextView trainerName;
        TextView trainerDes;
    }
}
