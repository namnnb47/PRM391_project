package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.finalproject.adapter.FoodAdapter;
import com.example.finalproject.listener.ICartLoadListener;
import com.example.finalproject.listener.IFoodLoadListener;
import com.example.finalproject.model.Cart;
import com.example.finalproject.model.Food;
import com.example.finalproject.util.SpaceItemDecoration;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IFoodLoadListener, ICartLoadListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.main_layout)
    RelativeLayout main_layout;

    @BindView(R.id.badge)
    NotificationBadge badge;

    @BindView(R.id.btnCart)
    FrameLayout btnCart;

    ICartLoadListener cartLoadListener;
    IFoodLoadListener foodLoadListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadFoodFromFirebase();

    }

    private void init() {
        ButterKnife.bind(this);

        foodLoadListener = this;
        cartLoadListener = this;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpaceItemDecoration());
    }

    private void loadFoodFromFirebase() {
        List<Food> foodList = new ArrayList<>();
        FirebaseDatabase.getInstance()
                .getReference("food")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot foodSnapshot : snapshot.getChildren()) {
                                Food food = foodSnapshot.getValue(Food.class);
                                food.setFoodId(foodSnapshot.getKey());
                                foodList.add(food);
                            }
                            foodLoadListener.onFoodLoadSuccess(foodList);
                        } else {
                            foodLoadListener.onFoodLoadFailed("Can not get food menu");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        foodLoadListener.onFoodLoadFailed(error.getMessage());
                    }
                });
    }

    @Override
    public void onCartLoadSuccess(List<Cart> cartList) {

    }

    @Override
    public void onCartLoadFailed(String message) {

    }

    @Override
    public void onFoodLoadSuccess(List<Food> foodList) {
        FoodAdapter adapter = new FoodAdapter(this, foodList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFoodLoadFailed(String message) {
        Snackbar.make(main_layout, message, Snackbar.LENGTH_LONG).show();
    }
}