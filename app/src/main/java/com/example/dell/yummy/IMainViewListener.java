package com.example.dell.yummy;

public interface IMainViewListener {

    void addFragment(int screenId);
    void addActivity(int screen);
    void addActivityInfo(int screen,String name,int wallet);

}
