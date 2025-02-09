package com.example.dailymenu;

public class IngridentItem {
    private String ingrediant;
    private String measure;
    private String ingrediantImage;

    public IngridentItem(String ingrediant, String measure, String ingrediantImage) {
        this.ingrediant = ingrediant;
        this.measure = measure;
        this.ingrediantImage = ingrediantImage;
    }

    public String getIngrediant() {
        return ingrediant;
    }

    public void setIngrediant(String ingrediant) {
        this.ingrediant = ingrediant;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngrediantImage() {
        return ingrediantImage;
    }

    public void setIngrediantImage(String ingrediantImage) {
        this.ingrediantImage = ingrediantImage;
    }
}
