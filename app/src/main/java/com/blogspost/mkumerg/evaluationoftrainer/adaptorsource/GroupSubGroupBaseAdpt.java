package com.blogspost.mkumerg.evaluationoftrainer.adaptorsource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blogspost.mkumerg.evaluationoftrainer.R;

import java.util.ArrayList;

/**
 * Created by mkumerg on 10/7/15.
 */
public class GroupSubGroupBaseAdpt extends BaseAdapter {

    private ArrayList<GroupSubGroupSet> sets;
    private ArrayList<GroupSubGroupSet> ratingSet;
    int index=0;

    private LayoutInflater l_Inflater;

    public GroupSubGroupBaseAdpt(Context context, ArrayList<GroupSubGroupSet> gsbSet, ArrayList<GroupSubGroupSet> gsbRatingSet, int index) {
        sets = gsbSet;
        ratingSet = gsbRatingSet;
        if(ratingSet != null && (ratingSet.size() > 0))
        {
            for(int i = 0; i < sets.size(); i++)
            {
                sets.get(i).setRating1(ratingSet.get(i+index).getRating1());
                sets.get(i).setRating2(ratingSet.get(i + index).getRating2());
                sets.get(i).setRating3(ratingSet.get(i + index).getRating3());
                sets.get(i).setRating4(ratingSet.get(i + index).getRating4());
                sets.get(i).setRating5(ratingSet.get(i + index).getRating5());
            }
        }
        this.index = index;
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
        final ViewHolder holder;
        if(convertView == null){
            convertView = l_Inflater.inflate(R.layout.evaluation_parameter,null);
            holder = new ViewHolder();
            holder.subGroup = (TextView)convertView.findViewById(R.id.tvSubgroup);
            holder.property = (TextView)convertView.findViewById(R.id.tvProperty);
            holder.radioGroup = (RadioGroup)convertView.findViewById(R.id.evaluation);
            holder.radio1 = (RadioButton)convertView.findViewById(R.id.radio1);
            holder.radio2 = (RadioButton)convertView.findViewById(R.id.radio2);
            holder.radio3 = (RadioButton)convertView.findViewById(R.id.radio3);
            holder.radio4 = (RadioButton)convertView.findViewById(R.id.radio4);
            holder.radio5 = (RadioButton)convertView.findViewById(R.id.radio5);

            holder.radio1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v,holder.selectedIndex);
                }
            });

            holder.radio2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v,holder.selectedIndex);
                }
            });

            holder.radio3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v,holder.selectedIndex);
                }
            });

            holder.radio4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v,holder.selectedIndex);
                }
            });

            holder.radio5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRadioButtonClicked(v,holder.selectedIndex);
                }
            });

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.selectedIndex = position;

//        Log.d("trineeName", "In Base Adaptor" + traineeDetailses.get(position).getName());

        holder.subGroup.setText(sets.get(position).getSubGroup());
        holder.property.setText(sets.get(position).getProperty());

        if(sets.get(position).getRating1() == 1)
        {
            holder.radio1.setChecked(true);
        }
        else if(sets.get(position).getRating2() == 1)
        {
            holder.radio2.setChecked(true);
        }
        else if(sets.get(position).getRating3() == 1)
        {
            holder.radio3.setChecked(true);
        }
        else if(sets.get(position).getRating4() == 1)
        {
            holder.radio4.setChecked(true);
        }
        else if(sets.get(position).getRating5() == 1)
        {
            holder.radio5.setChecked(true);
        }

        return convertView;
    }
//    private OnCheckedChangedListener checkChangedListener = new OnCheckedChangedListener()

    private void onRadioButtonClicked(View view, int index)
    {
        boolean checked = ((RadioButton)view).isChecked();

        switch (view.getId())
        {
            case R.id.radio1:
                if(checked)
                {
                    sets.get(index).setRating1(1);
                    sets.get(index).setRating2(0);
                    sets.get(index).setRating3(0);
                    sets.get(index).setRating4(0);
                    sets.get(index).setRating5(0);
                }
                break;
            case R.id.radio2:
                if(checked)
                {
                    sets.get(index).setRating1(0);
                    sets.get(index).setRating2(1);
                    sets.get(index).setRating3(0);
                    sets.get(index).setRating4(0);
                    sets.get(index).setRating5(0);
                }
                break;
            case R.id.radio3:
                if(checked)
                {
                    sets.get(index).setRating1(0);
                    sets.get(index).setRating2(0);
                    sets.get(index).setRating3(1);
                    sets.get(index).setRating4(0);
                    sets.get(index).setRating5(0);
                }
                break;
            case R.id.radio4:
                if(checked)
                {
                    sets.get(index).setRating1(0);
                    sets.get(index).setRating2(0);
                    sets.get(index).setRating3(0);
                    sets.get(index).setRating4(1);
                    sets.get(index).setRating5(0);
                }
                break;
            case R.id.radio5:
                if(checked)
                {
                    sets.get(index).setRating1(0);
                    sets.get(index).setRating2(0);
                    sets.get(index).setRating3(0);
                    sets.get(index).setRating4(0);
                    sets.get(index).setRating5(1);
                }
                break;
        }
    }

    static class ViewHolder{
        TextView subGroup;
        TextView property;
        RadioGroup radioGroup;
        RadioButton radio1;
        RadioButton radio2;
        RadioButton radio3;
        RadioButton radio4;
        RadioButton radio5;
        int selectedIndex;
    }
}
