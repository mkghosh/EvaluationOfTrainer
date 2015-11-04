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
 * Created by mkumerg on 10/17/15.
 */
public class CourseNameAndDescriptionBaseAdpt extends BaseAdapter {

    private ArrayList<CourseNameAndDescription> sets;

    private LayoutInflater l_Inflater;

    public CourseNameAndDescriptionBaseAdpt(Context context, ArrayList<CourseNameAndDescription> gsbSet) {
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
            convertView = l_Inflater.inflate(R.layout.course_name_description,null);
            holder = new ViewHolder();
            holder.courseName = (TextView)convertView.findViewById(R.id.tv_course_name);
            holder.courseDes = (TextView)convertView.findViewById(R.id.tv_course_des);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.courseName.setText(sets.get(position).getCourseName());
        holder.courseDes.setText(sets.get(position).getCourseDescription());
        return convertView;
    }

    static class ViewHolder{
        TextView courseName;
        TextView courseDes;
    }
}
