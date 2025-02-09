package com.example.dailymenu;

import java.util.List;

public class CatigoryResponse {
    private List<Catigory> categories;

    public CatigoryResponse(List<Catigory> catigoryList) {
        this.categories = catigoryList;
    }

    public List<Catigory> getCatigoryList() {
        return categories;
    }

    public void setCatigoryList(List<Catigory> catigoryList) {
        this.categories = catigoryList;
    }
}
