package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
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
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, 8, 6, 4210752, false);
        context.drawText(this.textRenderer, this.playerInventoryTitle, 8, (this.backgroundHeight - 166), 4210752, false);
    }

    @Override
    public void render(DrawContext matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        matrices.drawTexture(SPELL_BOOK_TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }
}
