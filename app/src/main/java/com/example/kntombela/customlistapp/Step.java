package com.example.kntombela.customlistapp;

import android.util.Log;
import android.widget.Button;

import java.io.Serializable;

/**
 * Created by kntombela on 2016/12/22.
 */

public class Step implements Serializable {

    //Class Members
    private String stepNumber = "";
    private String stepHeader = "";
    private String stepSummary = "";
    private String stepDetail = "";
    final static String logTag = Step.class.getName();

    Button btnComment;
    Button btnStatus;
    Button btnAssign;

    /*********** Constructors ******************/
    //Default Constructor
    Step (){

    }

    public Step (String StepNumber, String StepHeader, String StepSummary){

        //Set class members upto summary level
        setStepNumber(StepNumber);
        setStepHeader(StepHeader);
        setStepSummary(StepSummary);
    }

    public Step (String StepNumber, String StepHeader, String StepSummary, String StepDetail){

        //Set class members upto detail level
        setStepNumber(StepNumber);
        setStepHeader(StepHeader);
        setStepSummary(StepSummary);
        setStepDetail(StepDetail);
    }

    /*********** Setters For Class Members ******************/

    public void setStepNumber(String StepNumber)
    {
        this.stepNumber = StepNumber;
    }

    public void setStepSummary(String StepSummary){

        this.stepSummary = StepSummary;
    }

    public void setStepHeader(String StepHeader){

        this.stepHeader = StepHeader;
    }

    public void setStepDetail(String StepDetail){

        this.stepDetail = StepDetail;
    }

    /*********** Getters For Class Members ******************/
    public String getStepNumber(){

        return this.stepNumber;
    }

    public String getStepHeader(){

        return this.stepHeader;
    }

    public String getStepSummary(){

        return this.stepSummary;
    }

    public String getStepDetail(){

        return this.stepDetail;
    }

    /*********** Class Methods ******************/
    static void ActionAssign(){
        Log.i(logTag, "Assign Button Pressed.");
    }

    static void ActionComment(){
        Log.i(logTag, "Comment Button Pressed.");
    }

    static void ActionStatus(){
        Log.i(logTag, "Status Button Pressed.");
    }

}
