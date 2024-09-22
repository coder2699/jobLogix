package com.personal.jobhive.entities;
import java.util.List;

public class ChartData {
    private int[] data;
    private List<String> categories;

    public ChartData(int[] data, List<String> categories) {
        this.data = data;
        this.categories = categories;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}