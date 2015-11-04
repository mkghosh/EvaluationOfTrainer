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
public class SessionBaseAdpt extends BaseAdapter {

    private ArrayList<SessionDetails> sessions;

    private LayoutInflater l_Inflater;

    public SessionBaseAdpt(Context context, ArrayList<SessionDetails> trainerDetailses) {
        sessions = trainerDetailses;
        l_Inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Object getItem(int position) {
        return sessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = l_Inflater.inflate(R.layout.single_session_item,null);
            holder = new ViewHolder();
            holder.trainerName = (TextView)convertView.findViewById(R.id.textview_trainer_name);
            holder.trackName = (TextView)convertView.findViewById(R.id.textview_track_name);
            holder.groupName = (TextView)convertView.findViewById(R.id.textview_group_name);
            holder.workDate = (TextView)convertView.findViewById(R.id.session_date);
            holder.comment = (TextView)convertView.findViewById(R.id.textview_comment);
            holder.courseName = (TextView)convertView.findViewById(R.id.textview_course_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.trainerName.setText(sessions.get(position).getTrainerName());
        holder.trackName.setText(sessions.get(position).getTrackName());
        holder.groupName.setText(sessions.get(position).getGroupName());
        holder.workDate.setText(sessions.get(position).getWorkDate());
        holder.courseName.setText(sessions.get(position).getCourseName());
        holder.comment.setText(sessions.get(position).getComment());
        return convertView;
    }

    static class ViewHolder{
        TextView trainerName;
        TextView trackName;
        TextView groupName;
        TextView workDate;
        TextView comment;
        TextView courseName;
    }
}
