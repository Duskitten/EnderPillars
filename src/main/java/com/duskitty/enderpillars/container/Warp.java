package com.duskitty.enderpillars.container;

public class Warp {

    public static String NAME;
    public static String ID;
    public static double X;
    public static double Y;
    public static double Z;
    public static float YAW;
    public static float PITCH;


    public Warp(String name, String id, double x, double y, double z, float yaw, float pitch) {
        NAME = name;
        ID = id;
        X = x;
        Y = y;
        Z = z;
        YAW = yaw;
        PITCH = pitch;
    }

    public String getName(){
       return this.NAME;
    }

    public String getDimensionId(){
        return this.ID;
    }

    public double getX(){
        return this.X;
    }

    public double getY(){
        return this.Y;
    }

    public double getZ(){
        return this.Z;
    }

    public float getYaw(){
        return this.YAW;
    }
    public float getPitch(){
        return this.PITCH;
    }

}
