package com.example.it.firebasetest.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.it.firebasetest.Interface.ItemClickListener;
import com.example.it.firebasetest.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtFoodName;
    public TextView txtFoodDescription;
    public TextView txtFoodPrice;
    public ImageView imgFood;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodViewHolder(View itemView) {
        super(itemView);

        txtFoodName = itemView.findViewById(R.id.food_name);
        txtFoodDescription = itemView.findViewById(R.id.food_description);
        txtFoodPrice = itemView.findViewById(R.id.food_price);
        imgFood = itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
