package com.example.kntombela.customlistapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

    ListView list;
    StepAdapter adapter;
    public ArrayList<Step> StepSummaryList = new ArrayList<Step>();
    Fragment thisFragment = this;

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
        list = (ListView) rootView.findViewById(R.id.lstSummary);

        /*Add Data to ArrayList
        setListData();

        /**************** Attach Array List to Adapter ********
        adapter = new StepAdapter(getActivity(), StepSummaryList);
        list.setAdapter(adapter);*/

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

        private Step [] getStepListFromJson(String JSonString)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String JsonArrayName = "step";
            final String colStepID = "stepID";
            final String colStepNumber = "stepNumber";
            final String colStepTitle = "stepTitle";
            final String colStepSummary = "stepSummary";
            final String colStepDetail = "stepDetail";
            JSONObject stepJson = new JSONObject(JSonString);
            JSONArray step = stepJson.getJSONArray(JsonArrayName);



            Step[] resultObj = new Step[step.length()];
            for(int i = 0; i < step.length(); i++) {

                JSONObject d = step.getJSONObject(i);
                resultObj[i] = new Step("IMP " + d.getString(colStepNumber), d.getString(colStepTitle),
                        d.getString(colStepSummary), d.getString(colStepDetail));

            }

            return resultObj;
        }

        @Override
        protected Step[] doInBackground(Void... params) {

            try {

                /* If there's no zip code, there's nothing to look up. */
                if (params.length == 0) {
                    return null;
                }

                URL url = new URL("http://192.168.8.101/bcp_connect/get_steps.php");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                StepJsonStr = buffer.toString();
                Log.v(LogTag, "Step JSon: " + StepJsonStr);
                return getStepListFromJson(StepJsonStr);

            } catch (IOException e) {
                Log.e(LogTag, "Error ", e);
                // If the code didn't successfully get the bcp data, there's no point in attemping
                // to parse it.
                return null;
            } catch (JSONException e) {
                Log.e(LogTag, e.getMessage(), e);
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LogTag, "Error closing stream", e);
                    }
                }
            }

            // This will only happen if there was an error getting or parsing the department json.
            return null;
        }

        @Override
        protected void onPostExecute(Step[] result) {

            if (result != null) {
                StepSummaryList.clear();
                for(Step StepObj : result) {
                    StepSummaryList.add(StepObj);
                }
                // Attach adapter
                adapter = new StepAdapter(getActivity(), StepSummaryList, StepSummaryFragment.this);
                list.setAdapter(adapter);

            }
        }

    }

}
