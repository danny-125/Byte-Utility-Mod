package me.danny125.byteutilitymod.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.OptionalDouble;

public class ESPRenderer {

    public static void renderESP(Box box, MatrixStack matrixStack, VertexConsumerProvider.Immediate vertexConsumers) {
        RenderSystem.disableDepthTest();
        renderBox(matrixStack, vertexConsumers,box,new Color(0, 255, 0, 255));
        vertexConsumers.draw();
        RenderSystem.enableDepthTest();
    }

    public static RenderLayer getDebugLines() {
        return RenderLayer.of(
                "debug_lines",                              // Layer name
                VertexFormats.POSITION_COLOR,               // Vertex format for positions and colors
                VertexFormat.DrawMode.DEBUG_LINES,                // Draw mode for lines (similar to DEBUG_LINES)
                256,                                        // Buffer size
                false,                                      // Affects outline
                true,                                       // Use depth testing
                RenderLayer.MultiPhaseParameters.builder()
                        .lineWidth(new RenderLayer.LineWidth(OptionalDouble.of(2.0)))  // Optional: Set line width
                        .layering(RenderLayer.NO_LAYERING)                            // Disable special layering
                        .transparency(RenderLayer.NO_TRANSPARENCY)                    // No transparency
                        .writeMaskState(RenderLayer.ALL_MASK)                         // Write mask state
                        .build(false)
        );
    }

    private static void renderBox(MatrixStack matrixStack, VertexConsumerProvider.Immediate vertexConsumers, Box box, Color color) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
        float red = color.getRed() / 255.0F;
        float green = color.getGreen() / 255.0F;
        float blue = color.getBlue() / 255.0F;
        float alpha = color.getAlpha() / 255.0F;

        Matrix4f matrix = matrixStack.peek().getPositionMatrix();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        addLine(vertexConsumer, matrix, box.minX, box.minY, box.minZ, box.maxX, box.minY, box.minZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.minX, box.minY, box.minZ, box.minX, box.minY, box.maxZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.maxX, box.minY, box.minZ, box.maxX, box.minY, box.maxZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.minX, box.minY, box.maxZ, box.maxX, box.minY, box.maxZ, red, green, blue, alpha);

        addLine(vertexConsumer, matrix, box.minX, box.maxY, box.minZ, box.maxX, box.maxY, box.minZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.minX, box.maxY, box.minZ, box.minX, box.maxY, box.maxZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.maxX, box.maxY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.minX, box.maxY, box.maxZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);

        addLine(vertexConsumer, matrix, box.minX, box.minY, box.minZ, box.minX, box.maxY, box.minZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.maxX, box.minY, box.minZ, box.maxX, box.maxY, box.minZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.minX, box.minY, box.maxZ, box.minX, box.maxY, box.maxZ, red, green, blue, alpha);
        addLine(vertexConsumer, matrix, box.maxX, box.minY, box.maxZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);

        RenderSystem.setShaderColor(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private static void addLine(VertexConsumer vertexConsumer, Matrix4f matrix, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        vertexConsumer.vertex(matrix, (float) x1, (float) y1, (float) z1)
                .color(red, green, blue, alpha)
                .normal((float) 0, (float) 0, (float) 1);
        vertexConsumer.vertex(matrix, (float) x2, (float) y2, (float) z2)
                .color(red, green, blue, alpha)
                .normal((float) 0, (float) 0, (float) 1);
    }
}