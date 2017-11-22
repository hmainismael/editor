package org.ulco;

public class ID {
    private static ID instance;
    private int id = 0;

    private ID()
    {}

    public static ID getInstance(){
        if (instance == null)
        {
            instance = new ID();
        }
        return instance;
    }

    public int incrementId(){
        return ++id;
    }

    public int getId(){
        return id;
    }
}