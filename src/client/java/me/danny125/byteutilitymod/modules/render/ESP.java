package me.danny125.byteutilitymod.modules.render;

import com.mojang.blaze3d.systems.RenderSystem;
import me.danny125.byteutilitymod.event.Event;
import me.danny125.byteutilitymod.event.RenderEvent;
import me.danny125.byteutilitymod.event.TickEvent;
import me.danny125.byteutilitymod.event.ViewBobbingEvent;
import me.danny125.byteutilitymod.settings.BooleanSetting;
import me.danny125.byteutilitymod.modules.Module;
import me.danny125.byteutilitymod.util.wurst.EntityUtils;
import me.danny125.byteutilitymod.util.wurst.RegionPos;
import me.danny125.byteutilitymod.util.wurst.RenderUtils;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class ESP extends Module {

    //Credit to Wurst Client for ESP Module
    //Deadass wouldve made it myself but microsoft is a bunch of cucks who like to write shit code

    private final ArrayList<PlayerEntity> players = new ArrayList<>();

    public BooleanSetting boxes = new BooleanSetting("Boxes", true);
    public BooleanSetting tracers = new BooleanSetting("Tracers", true);

    public ESP() {
        super("PlayerESP", 0, CATEGORY.RENDER, false);
        this.addSettings(boxes,tracers);
    }
    public Vec3d getLookVec(float yaw,float pitch)
    {
        float radPerDeg = MathHelper.RADIANS_PER_DEGREE;
        float pi = MathHelper.PI;

        float adjustedYaw = -MathHelper.wrapDegrees(yaw) * radPerDeg - pi;
        float cosYaw = MathHelper.cos(adjustedYaw);
        float sinYaw = MathHelper.sin(adjustedYaw);

        float adjustedPitch = -MathHelper.wrapDegrees(pitch) * radPerDeg;
        float nCosPitch = -MathHelper.cos(adjustedPitch);
        float sinPitch = MathHelper.sin(adjustedPitch);

        return new Vec3d(sinYaw * nCosPitch, sinPitch, cosYaw * nCosPitch);
    }
    public Vec3d getClientLookVec(float partialTicks)
    {
        float yaw = mc.player.getYaw(partialTicks);
        float pitch = mc.player.getPitch(partialTicks);
        return getLookVec(yaw,pitch);
    }
    private void renderTracers(MatrixStack matrixStack, float partialTicks,
                               RegionPos region)
    {
        if(players.isEmpty())
            return;

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.begin(
                VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

        Vec3d regionVec = region.toVec3d();
        Vec3d start = getClientLookVec(partialTicks)
                .add(RenderUtils.getCameraPos()).subtract(regionVec);

        for(PlayerEntity e : players)
        {
            Vec3d end = EntityUtils.getLerpedBox(e, partialTicks).getCenter()
                    .subtract(regionVec);

            float r, g, b;


                float f = mc.player.distanceTo(e) / 20F;
                r = MathHelper.clamp(2 - f, 0, 1);
                g = MathHelper.clamp(f, 0, 1);
                b = 0;


            bufferBuilder
                    .vertex(matrix, (float)start.x, (float)start.y, (float)start.z)
                    .color(r, g, b, 0.5F);

            bufferBuilder
                    .vertex(matrix, (float)end.x, (float)end.y, (float)end.z)
                    .color(r, g, b, 0.5F);
        }

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    private void renderBoxes(MatrixStack matrixStack, float partialTicks,
                             RegionPos region)
    {
        float extraSize = 0.1f;

        for(PlayerEntity e : players)
        {
            matrixStack.push();

            Vec3d lerpedPos = EntityUtils.getLerpedPos(e, partialTicks)
                    .subtract(region.toVec3d());
            matrixStack.translate(lerpedPos.x, lerpedPos.y, lerpedPos.z);

            matrixStack.scale(e.getWidth() + extraSize,
                    e.getHeight() + extraSize, e.getWidth() + extraSize);

            // set color
            //if(WURST.getFriends().contains(e.getName().getString()))
            //    RenderSystem.setShaderColor(0, 0, 1, 0.5F);
            //else
            //{
                float f = mc.player.distanceTo(e) / 20F;
                RenderSystem.setShaderColor(2 - f, f, 0, 0.5F);
            //}

            Box bb = new Box(-0.5, 0, -0.5, 0.5, 1, 0.5);
            RenderUtils.drawOutlinedBox(bb, matrixStack);

            matrixStack.pop();
        }
    }

    @Override
    public void onEvent(Event e) {
        super.onEvent(e);

        if(!this.toggled || mc.world == null){
            return;
        }

        if(e instanceof ViewBobbingEvent){
            if(tracers.isToggled()){
                e.cancel();
            }
        }

        if(e instanceof TickEvent){
            PlayerEntity player = mc.player;
            ClientWorld world = mc.world;

            players.clear();
            Stream<AbstractClientPlayerEntity> stream = world.getPlayers()
                    .parallelStream().filter(en -> !en.isRemoved() && en.getHealth() > 0)
                    .filter(en -> en != player)
                    .filter(en -> Math.abs(en.getY() - mc.player.getY()) <= 1e6);

            players.addAll(stream.collect(Collectors.toList()));
        }

        if (e instanceof RenderEvent) {
            RenderEvent renderEvent = (RenderEvent) e;

            MatrixStack matrixStack = renderEvent.getMatrixStack();
            float partialTicks = renderEvent.getTickDelta();

            PlayerEntity playerEntity = mc.player;

            if (mc.world == null || playerEntity == null) {
                return; // Avoid null reference crashes
            }

            Vec3d cameraPos = mc.gameRenderer.getCamera().getPos();

                // GL settings
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDisable(GL11.GL_DEPTH_TEST);

                matrixStack.push();

                RegionPos region = RenderUtils.getCameraRegion();
                RenderUtils.applyRegionalRenderOffset(matrixStack, region);

                // draw boxes
                if(boxes.isToggled()) {
                    renderBoxes(matrixStack, partialTicks, region);
                }
                if(tracers.isToggled()){
                    renderTracers(matrixStack,partialTicks,region);
                }

                matrixStack.pop();

                // GL resets
                RenderSystem.setShaderColor(1, 1, 1, 1);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDisable(GL11.GL_BLEND);
        }
    }
}