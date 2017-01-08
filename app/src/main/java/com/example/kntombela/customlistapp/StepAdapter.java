package com.example.kntombela.customlistapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by kntombela on 2016/12/22.
 */

public class StepAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declaration Of Class Members *********/
    private Activity activity;
    private StepSummaryFragment fragment;
    private ArrayList data;
    private static LayoutInflater inflater = null;
    public Resources res;
    Step step = null;
    int i = 0;

    /*************  Class Constructor *****************/
    public StepAdapter(Activity a, ArrayList d, StepSummaryFragment f) {

        /********** Take passed values **********/
        activity = a;
        data = d ;
        fragment = f;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /*************  Class Constructor *****************/
    public StepAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data = d ;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = (LayoutInflater)activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    //Onclick Listeners For Step Actions
    private View.OnClickListener assignBtnListener = new View.OnClickListener(){
        public void onClick(View v) {
            Step.ActionAssign();
        }};

    private View.OnClickListener commentBtnListener = new View.OnClickListener(){
        public void onClick(View v) {
            Step.ActionComment();
        }};

    private View.OnClickListener statusBtnListener = new View.OnClickListener(){
        public void onClick(View v) {
            Step.ActionStatus();
        }};

    @Override
    public int getCount() {

        //Return size of array
        if(data.size() <= 0)
            return 1;
        else
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView txtStepCounter;
        public TextView txtStepHeader;
        public TextView txtStepSummary;
        public Button btnAssign;
        public Button btnComment;
        public Button btnStatus;

        //Add Buttons

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView == null){

            /****** Inflate list item layout file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.listitem_step_summary, null);

            /****** View Holder Object to contain list item file elements ******/
            holder = new ViewHolder();
            holder.txtStepCounter = (TextView) vi.findViewById(R.id.txtStepCounter);
            holder.txtStepHeader = (TextView)vi.findViewById(R.id.txtStepHeader);
            holder.txtStepSummary = (TextView)vi.findViewById(R.id.txtStepSummary);
            holder.btnAssign = (Button) vi.findViewById(R.id.btnAssign);
            holder.btnComment = (Button) vi.findViewById(R.id.btnComment);
            holder.btnStatus = (Button) vi.findViewById(R.id.btnStatus);

            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        }
        else

            holder = (ViewHolder) vi.getTag();

        if(data.size()<=0)
        {
            //If there is no data set the header of the list item to "No Data"
            holder.txtStepHeader.setText("No Data");
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            step = null;
            step = (Step) data.get(position);
            Log.i("StepAdapter",step.getStepNumber() + ", " + step.getStepHeader() + ", " + step.getStepSummary());

            /************  Set Model values in Holder elements ***********/
            holder.txtStepHeader.setText(step.getStepHeader());
            holder.txtStepCounter.setText(step.getStepNumber());
            holder.txtStepSummary.setText(step.getStepSummary());
            holder.btnComment.setOnClickListener(commentBtnListener);
            holder.btnAssign.setOnClickListener(assignBtnListener);
            holder.btnStatus.setOnClickListener(statusBtnListener);
            holder.btnComment.setTag(getItem(position));
            holder.btnComment.setTag(getItem(position));
            holder.btnComment.setTag(getItem(position));

            /******** Set Item Click Listener for LayoutInflater for each row *******/
            vi.setOnClickListener(new OnItemClickListener(position));

        }
        return vi;
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {

            /****Call  onItemClick Method inside StepSummaryFragment****/
            fragment.onItemClick(mPosition);
        }
    }

}
