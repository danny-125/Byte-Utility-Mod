package me.danny125.byteutilitymod.math;

public class Vector3 {
    double x,y,z;
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public double getZ(){
        return this.z;
    }
    public void setX(double xCoord){
        this.x = xCoord;
    }
    public void setY(double yCoord){
        this.y = yCoord;
    }
    public void setZ(double zCoord){
        this.z = zCoord;
    }
}
