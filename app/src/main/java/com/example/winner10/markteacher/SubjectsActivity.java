package com.example.winner10.markteacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class SubjectsActivity extends AppCompatActivity {

    RecyclerView subjectList;
    AdpaterSubject  mAdapter;
    StringRes sr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        sr = ((StringRes)getApplicationContext());
        setTitle("Subjects List");

        subjectList = (RecyclerView)findViewById(R.id.subjectList);
        mAdapter = new AdpaterSubject(SubjectsActivity.this, sr.getSubjects());
        subjectList.setAdapter(mAdapter);
        subjectList.setLayoutManager(new LinearLayoutManager(SubjectsActivity.this));
    }
}
