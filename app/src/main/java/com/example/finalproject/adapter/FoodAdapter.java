package com.example.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.model.Food;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyFoodViewHolder> {


    private Context context;
    private List<Food> foodList;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }


    @NonNull
    @Override
    public MyFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyFoodViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyFoodViewHolder holder, int position) {
        Glide.with(context).load(foodList.get(position).getFoodImage()).into(holder.imageView);
        holder.txtFoodPrice.setText(new StringBuilder("$").append(foodList.get(position).getFoodPrice()));
        holder.txtFoodName.setText(new StringBuilder().append(foodList.get(position).getFoodName()));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyFoodViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtFoodName)
        TextView txtFoodName;
        @BindView(R.id.txtFoodPrice)
        TextView txtFoodPrice;

        private Unbinder unbinder;

        public MyFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
