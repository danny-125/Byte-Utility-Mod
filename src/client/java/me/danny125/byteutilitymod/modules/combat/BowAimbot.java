package me.danny125.byteutilitymod.modules.combat;

import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.BooleanSetting;
import me.danny125.byteutilitymod.settings.NumberSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class BowAimbot extends Module {

    public BooleanSetting mobs = new BooleanSetting("Mobs", true);
    public BooleanSetting players = new BooleanSetting("Players", true);

    public NumberSetting range = new NumberSetting("Range",50.0,10.0,100.0,5.0,"Blocks");

    public BowAimbot() {
        super("BowAimbot",0,CATEGORY.COMBAT,false);
    }

    private float[] calculateLookAt(PlayerEntity player, Entity target) {
        double deltaX = target.getX() - player.getX();
        double deltaY = (target.getY() + target.getEyeHeight(player.getPose())) - (player.getY() + player.getEyeHeight(player.getPose()));
        double deltaZ = target.getZ() - player.getZ();

        double distance = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);

        float yaw = (float) (Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90.0F);
        float pitch = (float) -Math.toDegrees(Math.atan2(deltaY, distance));

        return new float[]{yaw, pitch};
    }

    @Override
    public void onEvent(Event e) {
        super.onEvent(e);
        if (e instanceof TickEvent) {
            PlayerEntity player = mc.player;

            if(!this.toggled || mc.player == null){
                return;
            }

            if (!player.isAlive() || player.isSpectator()) {
                return;
            }

            double AimbotRange = range.getValue();
            double maxAngleFromCrosshair = 15.0; // Max angle from crosshair in degrees
            Entity closestEntity = null;
            double closestDistanceSquared = AimbotRange * AimbotRange;
            double closestAngle = maxAngleFromCrosshair;

            List<Entity> nearbyEntities = mc.world.getOtherEntities(player, player.getBoundingBox().expand(AimbotRange));

            if (!nearbyEntities.isEmpty()) {
                for (Entity entity : nearbyEntities) {
                    // Check if the entity is valid based on settings
                    if (entity instanceof MobEntity && !mobs.isToggled()) {
                        continue;
                    }
                    if (entity instanceof PlayerEntity && !players.isToggled()) {
                        continue;
                    }

                    // Check if entity is visible (not behind walls)
                    if (!player.canSee(entity)) {
                        continue;
                    }

                    // Calculate angle between player view and entity
                    float[] lookAtAngles = calculateLookAt(player, entity);
                    float yawDiff = Math.abs(player.getYaw() - lookAtAngles[0]);
                    float pitchDiff = Math.abs(player.getPitch() - lookAtAngles[1]);

                    double angleFromCrosshair = Math.sqrt(yawDiff * yawDiff + pitchDiff * pitchDiff);

                    if (angleFromCrosshair < closestAngle) {
                        closestEntity = entity;
                        closestAngle = angleFromCrosshair;
                    } else {
                        // Check distance to fallback to closest by distance if no close crosshair match
                        double distanceSquared = player.squaredDistanceTo(entity);
                        if (distanceSquared < closestDistanceSquared) {
                            closestEntity = entity;
                            closestDistanceSquared = distanceSquared;
                        }
                    }
                }

                if (closestEntity != null) {
                    // Calculate the look-at angles for the closest entity
                    float[] targetLookAt = calculateLookAt(player, closestEntity);
                    // Apply these angles to the player's rotation to aim at the target
                    player.setYaw(targetLookAt[0]);
                    player.setPitch(targetLookAt[1]);
                }
            }
        }
    }
}