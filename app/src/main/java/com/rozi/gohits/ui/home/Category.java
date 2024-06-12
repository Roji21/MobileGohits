package com.rozi.gohits.ui.home;

public class Category {
    private String title;
    private int pic;

    public Category(String title){
        this.title = title;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }
}
