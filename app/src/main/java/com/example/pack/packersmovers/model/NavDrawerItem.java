package com.example.pack.packersmovers.model;

/**
 * Created by root on 4/2/16.
 */
public class NavDrawerItem {
    private String title;

    public NavDrawerItem(){}

    public NavDrawerItem(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

}
