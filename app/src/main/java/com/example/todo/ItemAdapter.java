package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    public interface OnLongClickListener{
        void OnItemLong(int position);
    }
    public interface OnClickListener{
        void OnClick(int position);
    }
    List<String> items;
    OnLongClickListener olcl;
    OnClickListener ocl;
    public ItemAdapter(List<String> items, OnLongClickListener olcl, OnClickListener ocl) {
        this.items = items;
        this.olcl = olcl;
        this.ocl = ocl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todo = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todo);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView word;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            word = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String s) {
            word.setText(s);
            word.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    olcl.OnItemLong(getAdapterPosition());
                    return true;
                }
            });
            word.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ocl.OnClick(getAdapterPosition());
                }
            });
        }
    }
}
