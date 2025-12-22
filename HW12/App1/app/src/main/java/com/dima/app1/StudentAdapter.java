package com.dima.app1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.VH> {
    private List<Student> data = new ArrayList<>();

    void setStudents(List<Student> list) {
        data = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View v = android.view.LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int pos) {
        holder.tv.setText(data.get(pos).toString());
    }

    @Override
    public int getItemCount() { return data.size(); }

    static class VH extends RecyclerView.ViewHolder {
        android.widget.TextView tv;
        VH(android.view.View v) { super(v); tv = v.findViewById(android.R.id.text1); }
    }
}