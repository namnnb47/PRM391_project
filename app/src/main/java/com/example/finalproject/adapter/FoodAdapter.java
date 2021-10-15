package com.example.finalproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.model.Food;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class FoodAdapter extends FirebaseRecyclerAdapter<Food, FoodAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FoodAdapter(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Food model) {
        holder.foodName.setText(model.getFoodName());
        holder.foodDesc.setText(model.getFoodDesc());
        holder.foodPrice.setText(model.getFoodPrice());

        Glide.with(holder.foodImage.getContext())
                .load(model.getFoodImage())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.foodImage);

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView foodImage;
        TextView foodName, foodDesc, foodPrice;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = (CircleImageView) itemView.findViewById(R.id.foodImage);
            foodName = (TextView) itemView.findViewById(R.id.foodName);
            foodDesc = (TextView) itemView.findViewById(R.id.foodDesc);
            foodPrice = (TextView) itemView.findViewById(R.id.foodPrice);


        }
    }
}
