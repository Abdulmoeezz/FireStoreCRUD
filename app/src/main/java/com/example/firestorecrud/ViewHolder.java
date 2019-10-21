package com.example.firestorecrud;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView mtitle,mdescription;
    View mview;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mview=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mclickLsitener.onItemClick(view,getAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mclickLsitener.onlongItemClick(view,getAdapterPosition());
                return false;
            }
        });

        mtitle=itemView.findViewById(R.id.item_title);
        mdescription=itemView.findViewById(R.id.item_description);
    }

   private  ViewHolder.clickListener  mclickLsitener;

    public  interface clickListener{
       void  onItemClick(View v,int position);
       void  onlongItemClick(View v,int position);

    }


    public void  setOnViewHolderClickListener(ViewHolder.clickListener  clickListener){
        mclickLsitener=clickListener;
    }

}
