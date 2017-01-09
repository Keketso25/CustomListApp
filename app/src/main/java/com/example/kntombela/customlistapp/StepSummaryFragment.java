package com.example.kntombela.customlistapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.kntombela.customlistapp.utilities.NetworkUtils;
import com.example.kntombela.customlistapp.utilities.StepJsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepSummaryFragment extends Fragment {

    //Member Variables
    RecyclerView mStepList;
    ListAdapter mListAdaper;
    public ArrayList<Step> StepSummaryList = new ArrayList<Step>();

    public StepSummaryFragment() {
        // Required empty public constructor
    }

    public StepSummaryFragment getFragment(){
        return this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_summary, container, false);
        mStepList = (RecyclerView) rootView.findViewById(R.id.lstSummary);

        //Set Layout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mStepList.setLayoutManager(layoutManager);

        //Populate Listview using background task
        GetSteps getSteps = new GetSteps();
        getSteps.execute();

        return rootView;
    }


    public void onItemClick(int mPosition){

        startActivity(new Intent(getActivity(), StepDetailActivity.class).putExtra("Step",
                StepSummaryList.get(mPosition)));
    }

    public class GetSteps extends AsyncTask<Void, Void, Step[]> {

        //Variable Declaration
        private final String LogTag = GetSteps.class.getSimpleName();
        String StepJsonStr = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        @Override
        protected Step[] doInBackground(Void... params) {

            try {

                URL url = new URL("http://192.168.8.100/bcp_connect/get_steps.php");

                //Read inputstream from BCPDb
                StepJsonStr = NetworkUtils
                        .getResponseFromHttpUrl(url);

                //Return parsed Json object
                return StepJsonUtils.getStepListFromJson(StepJsonStr);

            } catch (Exception e) {
                Log.e(LogTag, "Error ", e);
                // If the code didn't successfully get the bcp data, there's no point in attemping
                // to parse it.
                return null;
            }
        }

        @Override
        protected void onPostExecute(Step[] result) {

            if (result != null) {
                StepSummaryList.clear();
                for(Step StepObj : result) {
                    StepSummaryList.add(StepObj);
                }
                // Attach adapter
                mListAdaper = new ListAdapter(getActivity(), StepSummaryList, StepSummaryFragment.this);
                mStepList.setAdapter(mListAdaper);

            }
        }
    }
}
