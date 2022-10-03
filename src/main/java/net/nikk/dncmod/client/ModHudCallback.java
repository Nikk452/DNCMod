package net.nikk.dncmod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;

public class ModHudCallback implements HudRenderCallback{

    @Override
    public void onHudRender(MatrixStack matrices, float tickDelta) {
        final Identifier EXP_BAR = new Identifier(DNCMod.MOD_ID,
                "textures/gui/xp_bar_ui.png");
        final Identifier FULL_EXP_BAR = new Identifier(DNCMod.MOD_ID,
                "textures/gui/full_xp_bar_ui.png");
        final Identifier HEALTH_BAR = new Identifier(DNCMod.MOD_ID,
                "textures/gui/health_bar_ui.png");
        final Identifier MANA_BAR = new Identifier(DNCMod.MOD_ID,
                "textures/gui/mana_bar_ui.png");
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        int scaledHeight = client.getWindow().getScaledHeight();
        int scaledWidth = client.getWindow().getScaledWidth();
        int x = scaledWidth / 2 - 91;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
        int i = client.player.getNextLevelExperience();
        int k;
        int l;
        if (i > 0) {
            k = (int)(client.player.experienceProgress * 183.0F);
            l = scaledHeight - 32 + 3;
            DrawableHelper.drawTexture(matrices, x, l, 0, 64, 182, 5,256, 256);
            if (k > 0) {
                DrawableHelper.drawTexture(matrices, x, l, 0, 69, k, 5,256, 256);
            }
        }

        client.getProfiler().pop();
        if (client.player.experienceLevel > 0) {
            client.getProfiler().push("expLevel");
            String string = "" + client.player.experienceLevel;
            k = (scaledWidth - textRenderer.getWidth(string)) / 2;
            l = scaledHeight - 31 - 4;
            textRenderer.draw(matrices, string, (float)(k + 1), (float)l, 0);
            textRenderer.draw(matrices, string, (float)(k - 1), (float)l, 0);
            textRenderer.draw(matrices, string, (float)k, (float)(l + 1), 0);
            textRenderer.draw(matrices, string, (float)k, (float)(l - 1), 0);
            textRenderer.draw(matrices, string, (float)k, (float)l, 8453920);
            client.getProfiler().pop();
        }
    }
}
