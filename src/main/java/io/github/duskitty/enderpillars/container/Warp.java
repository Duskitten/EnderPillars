package io.github.duskitty.enderpillars.container;

public class Warp {

    public  String UNID;
    public  String NAME;
    public  String ID;
    public  double X;
    public  double Y;
    public  double Z;
    public  float YAW;
    public float PITCH;


    public Warp(String randomID,String name, String id, double x, double y, double z, float yaw, float pitch) {
        UNID = randomID;
        NAME = name;
        ID = id;
        X = x;
        Y = y;
        Z = z;
        YAW = yaw;
        PITCH = pitch;
    }

    public String getUniqueID(){ return this.UNID; }

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
