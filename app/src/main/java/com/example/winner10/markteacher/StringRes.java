package com.example.winner10.markteacher;

import android.app.Application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Winner 10 on 4/12/2017.
 */

public class StringRes extends Application {

    private String HOSTNAME = "http://192.168.0.103/mark_php/";
    private List<Subjects> subjects = new ArrayList<>();

    public void setHOSTNAME(String HOSTNAME) {
        this.HOSTNAME = HOSTNAME;
    }

    public String getHOSTNAME() {
        return HOSTNAME;
    }

//    public List<String> getSubjects() {
//        return subjects;
//    }
//
//    public void setSubjects(List<String> subjects) {
//        this.subjects = subjects;
//    }
//    public void addSubject(String subjectName){
//        this.subjects.add(subjectName);
//    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }

    public void addSubject(Subjects subjectName) {
        this.subjects.add(subjectName);
    }
}
