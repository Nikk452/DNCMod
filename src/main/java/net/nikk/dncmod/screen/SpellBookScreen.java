package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;

public class SpellBookScreen extends HandledScreen<SpellBookScreenHandler> {


    private static final Identifier SPELL_BOOK_TEXTURE = new Identifier(DNCMod.MOD_ID, "textures/gui/container/hopper.png");

    public SpellBookScreen(SpellBookScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        //this.backgroundWidth = 256;
        //this.backgroundHeight = 181;
        this.backgroundHeight = 133;
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, 8.0F, 6.0F, 4210752);
        this.textRenderer.draw(matrices, this.playerInventoryTitle, 8.0F, (float)(this.backgroundHeight - 166), 4210752);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SPELL_BOOK_TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}
