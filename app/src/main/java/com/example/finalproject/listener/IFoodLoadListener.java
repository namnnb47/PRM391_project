package com.example.finalproject.listener;

import com.example.finalproject.model.Food;

import java.util.List;

public interface IFoodLoadListener {
    void onFoodLoadSuccess(List<Food> foodList);
    void onFoodLoadFailed(String message);

}
