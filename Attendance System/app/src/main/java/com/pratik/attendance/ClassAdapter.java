package com.pratik.attendance;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.ConnectException;
import java.util.ArrayList;

class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.classViewHolder> {

    ArrayList<Class_Item> classItems;
    Context context;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{

        void onClick(int position);
    }

    public ClassAdapter(Context context,ArrayList<Class_Item> classItems) {
        this.classItems = classItems;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class classViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    TextView className;
    TextView subjectName;
        public classViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            className = itemView.findViewById(R.id.class_tv);
            subjectName = itemView.findViewById(R.id.subject_tv);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);  //view will appear after long press
        }

        //long press menu
        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            //action method are on enroll class
            menu.add(getAdapterPosition(),0,0,"EDIT");
            menu.add(getAdapterPosition(),1,0,"DELETE");
        }
    }


    @NonNull
    @Override
    public classViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,parent,false);

        return new classViewHolder(itemView,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull classViewHolder holder, int position) {
        holder.className.setText(classItems.get(position).getClassName());
        holder.subjectName.setText(classItems.get(position).getSubjectName());
    }

    @Override
    public int getItemCount() {
        return classItems.size();
    }
}
