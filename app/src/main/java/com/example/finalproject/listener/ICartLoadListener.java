package com.example.finalproject.listener;

import com.example.finalproject.model.Cart;


import java.util.List;

public interface ICartLoadListener {
    void onCartLoadSuccess(List<Cart> cartList);
    void onCartLoadFailed(String message);
}
