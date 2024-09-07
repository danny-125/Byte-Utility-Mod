package me.danny125.byteutilitymod.util;

import me.danny125.byteutilitymod.math.Vector3;
import net.minecraft.entity.Entity;

public class ByteEntity {
    public boolean removed;
    public Entity e;

    public ByteEntity(Entity entity){
        this.e = entity;
    }

    public Vector3 getPosition() {
        return new Vector3(e.getX(),e.getY(),e.getZ());
    }

    public Entity toEntity(){
        return this.e;
    }

    public void removeEntity(){
        this.removed = true;
    }

    public boolean isRemoved(){
        return removed;
    }
}
