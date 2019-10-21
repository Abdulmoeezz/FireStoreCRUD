package com.example.firestorecrud;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    ListActivity listActivity;
    Context context;
    List<Model> modelList;
    String id,title,description;

    public CustomAdapter(ListActivity listActivity, List<Model> modelList) {
        this.listActivity = listActivity;

        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);
        viewHolder.setOnViewHolderClickListener(new ViewHolder.clickListener() {
            @Override
            public void onItemClick(View v, int position) {

                Toast.makeText(listActivity,modelList.get(position).getTile(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onlongItemClick(View v, final int position) {

                AlertDialog.Builder builder=new AlertDialog.Builder(listActivity);
                String[] options ={"Update","Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                    if(which==0){
                       id=modelList.get(position).getId().trim();
                       title=modelList.get(position).getTile().trim();
                       description=modelList.get(position).getDescription().trim();

                       Intent intent=new Intent(listActivity,MainActivity.class);
                       intent.putExtra("id",id);
                        intent.putExtra("title",title);
                        intent.putExtra("description",description);
                        listActivity.startActivity(intent);

                    }
                    if(which==1){
                        listActivity.DeleteData(position);

                    }


                    }
                });

builder.show();







            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mtitle.setText(modelList.get(position).getTile().trim());
        holder.mdescription.setText(modelList.get(position).getDescription().trim());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
