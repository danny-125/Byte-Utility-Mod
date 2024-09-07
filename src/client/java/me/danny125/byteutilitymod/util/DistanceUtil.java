package me.danny125.byteutilitymod.util;

import me.danny125.byteutilitymod.math.Vector3;
import me.danny125.byteutilitymod.math.Vector3Distance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.concurrent.CopyOnWriteArrayList;

public class DistanceUtil {
    public static ByteEntity getClosest(PlayerEntity player, CopyOnWriteArrayList<ByteEntity> entityList,double range){
        ByteEntity closestEntity = null;
        PlayerEntity p = MinecraftClient.getInstance().player;

        Vector3 playerPos = new Vector3(player.getX(), player.getY(), player.getZ());

        for(ByteEntity entity : entityList){
            if(closestEntity == null){
                closestEntity = entity;
            }else{
                if(Vector3Distance.getDistance(entity.getPosition(), playerPos) < Vector3Distance.getDistance(closestEntity.getPosition(),playerPos)){
                    if (Vector3Distance.getDistance(playerPos,entity.getPosition()) < range) {
                        closestEntity = entity;
                    }
                }
            }
        }
        return closestEntity;
    }
}
