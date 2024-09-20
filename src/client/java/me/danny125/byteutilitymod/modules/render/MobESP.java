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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
public class MobESP extends Module {
    //Credit to Wurst Client for ESP Module
    public final MinecraftClient MC = MinecraftClient.getInstance();
    private final ArrayList<LivingEntity> mobs = new ArrayList<>();
    private VertexBuffer mobBox;

    public BooleanSetting boxes = new BooleanSetting("Boxes", true);
    public BooleanSetting tracers = new BooleanSetting("Tracers", true);

    public MobESP() {
        super("MobESP", 0, CATEGORY.RENDER, false);
        this.addSettings(boxes,tracers);
    }

    private void renderBoxes(MatrixStack matrixStack, float partialTicks,
                             RegionPos region)
    {
        float extraSize = 0.1f;
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        for(LivingEntity e : mobs)
        {
            matrixStack.push();
            Vec3d lerpedPos = EntityUtils.getLerpedPos(e, partialTicks)
                    .subtract(region.toVec3d());
            matrixStack.translate(lerpedPos.x, lerpedPos.y, lerpedPos.z);
            matrixStack.scale(e.getWidth() + extraSize,
                    e.getHeight() + extraSize, e.getWidth() + extraSize);
            float f = MC.player.distanceTo(e) / 20F;
            RenderSystem.setShaderColor(2 - f, f, 0, 0.5F);
            Matrix4f viewMatrix = matrixStack.peek().getPositionMatrix();
            Matrix4f projMatrix = RenderSystem.getProjectionMatrix();
            ShaderProgram shader = RenderSystem.getShader();
            mobBox.bind();
            mobBox.draw(viewMatrix, projMatrix, shader);
            VertexBuffer.unbind();
            matrixStack.pop();
        }
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
        float yaw = MC.player.getYaw(partialTicks);
        float pitch = MC.player.getPitch(partialTicks);
        return getLookVec(yaw,pitch);
    }
    private void renderTracers(MatrixStack matrixStack, float partialTicks,
                               RegionPos region)
    {
        if(mobs.isEmpty())
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

        for(LivingEntity e : mobs)
        {
            Vec3d end = EntityUtils.getLerpedBox(e, partialTicks).getCenter()
                    .subtract(regionVec);

            float f = MC.player.distanceTo(e) / 20F;
            float r = MathHelper.clamp(2 - f, 0, 1);
            float g = MathHelper.clamp(f, 0, 1);

            bufferBuilder
                    .vertex(matrix, (float)start.x, (float)start.y, (float)start.z)
                    .color(r, g, 0, 0.5F);

            bufferBuilder
                    .vertex(matrix, (float)end.x, (float)end.y, (float)end.z)
                    .color(r, g, 0, 0.5F);
        }

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    @Override
    public void onEnable() {
        super.onEnable();
        mobBox = new VertexBuffer(VertexBuffer.Usage.STATIC);
        Box bb = new Box(-0.5, 0, -0.5, 0.5, 1, 0.5);
        RenderUtils.drawOutlinedBox(bb, mobBox);
    }
    @Override
    public void onDisable() {
        super.onDisable();
        if(mobBox != null)
            mobBox.close();
    }
    @Override
    public void onEvent(Event e) {
        super.onEvent(e);

        if(!this.toggled || MC.world == null){
            return;
        }

        if(e instanceof ViewBobbingEvent){
            if(tracers.isToggled()){
                e.cancel(false);
            }
        }

        if(e instanceof TickEvent){
            mobs.clear();
            Stream<LivingEntity> stream = StreamSupport
                    .stream(MC.world.getEntities().spliterator(), false)
                    .filter(LivingEntity.class::isInstance).map(en -> (LivingEntity)en)
                    .filter(en -> !(en instanceof PlayerEntity))
                    .filter(en -> !en.isRemoved() && en.getHealth() > 0);
            mobs.addAll(stream.collect(Collectors.toList()));
        }
        if (e instanceof RenderEvent) {
            RenderEvent renderEvent = (RenderEvent) e;
            MatrixStack matrixStack = renderEvent.getMatrixStack();
            float partialTicks = renderEvent.getTickDelta();
            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity playerEntity = client.player;
            if (client.world == null || playerEntity == null) {
                return; // Avoid null reference crashes
            }
            Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
            //for (Entity entity : client.world.getEntities()) {
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
            if(tracers.isToggled()) {
                renderTracers(matrixStack, partialTicks, region);
            }
            matrixStack.pop();
            // GL resets
            RenderSystem.setShaderColor(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            //}
        }
    }
}