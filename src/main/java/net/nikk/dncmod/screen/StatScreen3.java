package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.util.Locale;

public class StatScreen3 extends Screen {
    PlayerEntity player = MinecraftClient.getInstance().player;
    IEntityDataSaver playerD = (IEntityDataSaver)player;
    int backgroundWidth = 412;
    int backgroundHeight = 256;
    int line = backgroundHeight/30;
    int collum = backgroundWidth/30;
    protected StatScreen3() {
        super(Text.literal("Stat2"));
    }
    @Override
    protected void init() {
        int stats[] = {0,0,0,0,0,0};
        this.addDrawableChild(new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> {
            this.client.setScreen(new CharCreationScreen1("","","","", stats,0,new int[]{0,1,2,3,4,5}));}));
        this.addDrawableChild(new ButtonWidget(width/2-158, height/2+70, 75, 20, Text.literal("Previous Page"), (button) -> {
            this.client.setScreen(new StatScreen2());}));
    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight,412,256);
        //drawTexture(matrices, LocationX, LocationY, Z?... 1 , u is 0, v is 0,ActualImageWidth,ActualImageHeight,texturewidthScaled,textureheightScaled);
        //text drawing
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        MatrixStack textRendererMatrixStack = new MatrixStack();
        textRendererMatrixStack.scale(1.0F, 1.0F, 1.0F);
        textRenderer.draw(textRendererMatrixStack, "Status", width/2-18, y+20+line*2/3, 	15859709);
        textRenderer.draw(textRendererMatrixStack, Text.literal("Information".toUpperCase(Locale.ROOT)).styled(style -> style.withUnderline(true)), x+collum*4, y+20+line*2+line, 	15859709);
        textRenderer.draw(textRendererMatrixStack, Text.literal("[").append(Text.literal("NAME")).append(": "+player.getDisplayName().toString()+"]"), x+collum*4, y+20+line*4+line, 	15859709);
        textRenderer.draw(textRendererMatrixStack, "[HEALTH: "+(double) Math.round(player.getHealth()*10)/10+"/"+player.getMaxHealth()+"]", x+collum*4, y+20+line*6+line, 	15859709);
        textRenderer.draw(textRendererMatrixStack, "[LEVEL: "+player.experienceLevel+"/"+Math.round(player.experienceProgress*player.getNextLevelExperience())+"/"+player.getNextLevelExperience()+"]", x+collum*4, y+20+line*8+line, 15859709);
        textRenderer.draw(textRendererMatrixStack, "[HIT DICE :"+playerD.getCharacterData().getInt("dice")+"]", x+collum*4, y+20+line*10+line, 	15859709);
        textRenderer.draw(textRendererMatrixStack, "[BONUS STATS: "+(int)Math.floor((player.experienceLevel+1)/5)+"]", x+collum*4, y+20+line*12+line, 	15859709);
        textRenderer.draw(textRendererMatrixStack, "[KI POINTS: "+playerD.getCharacterData().getInt("ki")+"]", x+collum*4, y+20+line*14+line, 	15859709);
        super.render(matrices, mouseX, mouseY, delta);
    }
}

