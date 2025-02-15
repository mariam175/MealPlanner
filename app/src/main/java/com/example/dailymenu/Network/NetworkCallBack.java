package com.example.dailymenu.Network;

import java.util.List;

public interface NetworkCallBack<T> {
    public void OnSuccess(List<T> meals);
    public void OnFailure(String err);
}
