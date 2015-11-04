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
 * Created by mkumerg on 10/15/15.
 */
public class NameSubjectBaseAdaptor extends BaseAdapter {

    private ArrayList<TrainerNameAndSub> sets;

    private LayoutInflater l_Inflater;

    public NameSubjectBaseAdaptor(Context context, ArrayList<TrainerNameAndSub> gsbSet) {
        sets = gsbSet;
        l_Inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sets.size();
    }

    @Override
    public Object getItem(int position) {
        return sets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = l_Inflater.inflate(R.layout.trainer_name_subject,null);
            holder = new ViewHolder();
            holder.trainerName = (TextView)convertView.findViewById(R.id.speaker_name_to_evaluate);
            holder.sessionName = (TextView)convertView.findViewById(R.id.session_for_evaluation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.trainerName.setText(sets.get(position).getTrainerName());
        holder.sessionName.setText(sets.get(position).getSubject());
        return convertView;
    }

    static class ViewHolder{
        TextView trainerName;
        TextView sessionName;
    }
}
