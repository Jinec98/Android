package com.example.pc.myapplication5;

/**
 * Created by pc on 2018/11/6.
 */

public class Student {
    public int ID = -1;
    public String Name;
    public String Number;
    public String Class;

    public Student(){super();}
    public Student(String Class, String Number, String Name){
        this.Class = Class;
        this.Number = Number;
        this.Name = Name;
    }

    public String getName(){
        return this.Name;
    }

    public String getNumber(){
        return this.Number;
    }

    public String getClas(){
        return  this.Class;
    }

    public int getID(){
        return this.ID;
    }
}
