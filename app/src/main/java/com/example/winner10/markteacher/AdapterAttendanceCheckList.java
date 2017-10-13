package com.example.winner10.markteacher;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdapterAttendanceCheckList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<AttendanceList> data = Collections.emptyList();
    List<String> present = new ArrayList<>();
    List<String> absent = new ArrayList<>();

    public AdapterAttendanceCheckList(Context context, List<AttendanceList> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.container_students_check_list, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final MyHolder myHolder = (MyHolder) holder;
        AttendanceList current = data.get(position);
        final String id = current.atrid;

        boolean check_status;

        if (current.status.equals("0")) {
            absent.add(id);
            check_status = false;
        } else {
            present.add(id);
            check_status = true;
        }

        myHolder.student.setChecked(check_status);
        myHolder.student.setText(current.studentid);

        myHolder.student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    absent.remove(id);
                    present.add(id);
                } else {
                    present.remove(id);
                    absent.add(id);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public List<AttendanceList> getList() {
        return data;
    }

    public String getString() {
        return data.toString();
    }

    public String getPresent(){
        return TextUtils.join(",",present);
    }

    public String getAbsent(){
        return TextUtils.join(",",absent);
    }

    class MyHolder extends RecyclerView.ViewHolder {

        CheckBox student;

        public MyHolder(View itemView) {
            super(itemView);
            student = (CheckBox) itemView.findViewById(R.id.student_checkbox);
        }

    }
}
