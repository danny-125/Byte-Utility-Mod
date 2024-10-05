package me.danny125.byteutilitymod.modules.combat;

import me.danny125.byteutilitymod.BYTE;
import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.settings.BooleanSetting;
import me.danny125.byteutilitymod.settings.NumberSetting;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {
    public BooleanSetting team = new BooleanSetting("Team", false);
    public BooleanSetting block = new BooleanSetting("Block", false);

    public NumberSetting range = new NumberSetting("Range", 4, 1, 6, 1,"blocks");
    public NumberSetting aps = new NumberSetting("APS (1.8)",10,1.0,20.0,1.0,"attacks");

    public BooleanSetting timedhits = new BooleanSetting("TimedHits", true);

    public static List<Entity> targets;

    public KillAura() {
        super("KillAura", 0, CATEGORY.COMBAT,false);
        this.addSettings(range, aps, team, block,timedhits);
    }

    public void onDisable() {
        if(mc.currentScreen == null) {
            mc.options.useKey.setPressed(false);
            hasstopped = true;
        }
    }

    public long lastMS = System.currentTimeMillis();

    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                reset();
            }
            return true;
        }

        return false;
    }

    public boolean hasstopped = true;

    public void onEvent(Event e) {
        if (e instanceof TickEvent) {
            TickEvent event = (TickEvent) e;

            if(mc.player == null || !this.toggled){
                return;
            }

            targets = (List<Entity>) mc.world.getOtherEntities(mc.player,mc.player.getBoundingBox().expand(range.getValue())).stream()
                    .filter(Entity.class::isInstance).collect(Collectors.toList());
            targets = targets.stream().filter(
                            entity -> entity.distanceTo(mc.player) < range.getValue() && entity != mc.player)
                    .collect(Collectors.toList());
            targets.sort(Comparator
                    .comparingDouble(entity -> ((Entity) entity).distanceTo(mc.player)));
            if (targets.isEmpty()) {
                if (!hasstopped) {
                    mc.options.useKey.setPressed(false);
                }
                hasstopped = true;
            }
            if (!targets.isEmpty()) {
                hasstopped = false;
                Entity entity = targets.get(0);

                    if (BYTE.INSTANCE.isModuleToggled("AntiBots")) {
                        if (entity.isInvisible()) {
                            return;
                        }
                        if (!entity.isOnGround() && entity.getMovement().getY() == 0 || !entity.isOnGround() && entity.getMovement().getY() == 0) {
                            return;
                        }
                        if(entity.getDisplayName() != null) {
                            if (entity.getDisplayName().getString().isEmpty()) {
                                return;
                            }
                        }else{
                            return;
                        }
                    }

                    if (!entity.isAlive()) {
                        mc.options.useKey.setPressed(false);
                        return;
                    }
                    if(!(entity instanceof PlayerEntity) && !(entity instanceof MobEntity)) {
                        return;
                    }
                    if (mc.player.isTeammate(entity) && !team.isToggled()) {
                        return;
                    }

                    if (hasTimeElapsed(1000 / Math.round(aps.getValue()), true) && !timedhits.isToggled() || mc.player.getAttackCooldownProgress(0.5f) == 1.0f && timedhits.isToggled()) {
                        float originalYaw = mc.player.getYaw();
                        float originalPitch = mc.player.getPitch();
                        mc.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, entity.getPos());
                        mc.interactionManager.attackEntity(mc.player,entity);
                        mc.player.swingHand(Hand.MAIN_HAND);
                        mc.player.setYaw(originalYaw);
                        mc.player.setPitch(originalPitch);
                    }
                }
            }

        }
}