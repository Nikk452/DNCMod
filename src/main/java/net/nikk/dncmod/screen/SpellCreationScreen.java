package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.custom.ModButtonWidget;
import net.nikk.dncmod.util.AttributeData;
import net.nikk.dncmod.util.IEntityDataSaver;

public class SpellCreationScreen extends Screen {
    PlayerEntity player = MinecraftClient.getInstance().player;
    NbtCompound nbt = ((IEntityDataSaver)player).getPersistentData();
    int backgroundWidth = 412;
    int backgroundHeight = 256;
    private float shade_color;
    private double anime;
    private boolean animate;
    private String type;
    private String target;
    private int level;
    private int duration;
    private int range;
    private int casting;
    private int effect;
    private int[] components;

    public SpellCreationScreen(Text title) {
        super(title);
        this.animate=true;
    }

    @Override
    protected void init() {
        if (this.animate){
            this.anime = 300d;
            this.shade_color = 0f;
            this.type="Buff";
            this.target="Self";
            this.level=1;
            this.duration=1;
            this.effect=0;
            this.range=5;
            this.casting=1;
            this.components=new int[]{};
        }else{this.anime = -40;}
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        int line = backgroundHeight/30;
        int collum = backgroundWidth/30;
        this.addDrawableChild(new PressableTextWidget(-5+(width-textRenderer.getWidth(Text.literal("Type: "+this.type)))/2,y+10+line*3,90,13, Text.of(""),(button) -> {
            this.type = this.type.equals("Buff")?"Offensive":(this.type.equals("Offensive")?"Healing":"Buff");
        }, textRenderer));
        this.addDrawableChild(new PressableTextWidget(x+collum*4-5,y+20+line*3+line,90,13, Text.of(""),(button) -> {
            this.level+=1;
        }, textRenderer));
        this.addDrawableChild(new PressableTextWidget(x+collum*25/2-5,y+20+line*3+line,90,13, Text.of(""),(button) -> {
            this.duration = this.duration==1?10:(this.duration==10?60:(this.duration==60?600:this.duration==600?3600:1));
        }, textRenderer));
        this.addDrawableChild(new PressableTextWidget(x+collum*43/2-5,y+20+line*3+line,90,13, Text.of(""),(button) -> {
            this.casting = this.casting==1?5:(this.casting==5?10:(this.casting==10?30:this.casting==30?60:1));
        }, textRenderer));
        this.addDrawableChild(new ModButtonWidget(x -12 + collum * 14, y + 22 + line * 24, 75, 20, Text.literal("Complete"), (button) -> {
            NbtCompound nbt = new NbtCompound();
            nbt.putString("type",this.type);
            nbt.putString("target",this.target);
            nbt.putInt("level",this.level);
            nbt.putInt("duration",this.duration);
            nbt.putInt("range",this.range);
            nbt.putInt("casting",this.casting);
            nbt.putInt("effect",this.effect);
            nbt.putIntArray("components",this.components);
            ClientPlayNetworking.send(Networking.SPELL_CREATE_C2S, PacketByteBufs.create().writeNbt(nbt));
            client.setScreen((Screen)null);
        }));
    }

    @Override
    public void render(DrawContext matrices, int mouseX, int mouseY, float delta) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (this.anime>y || this.anime>-35) {
            this.anime -= 5;
            this.shade_color = this.shade_color >= 1f ? 1f : this.shade_color + 0.013f;
            RenderSystem.setShaderColor(this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f);
            renderBackground(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f);
            matrices.drawTexture(new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"), x, Math.max((int) this.anime, y), 0, 0, backgroundWidth, backgroundHeight, 412, 256);
        }else{
            if(this.animate) this.animate = false;
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            renderBackground(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            matrices.drawTexture(new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"), x, y, 0, 0, backgroundWidth, backgroundHeight,412,256);
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            DrawContext textRendererMatrixStack = new DrawContext(MinecraftClient.getInstance(), matrices.getVertexConsumers());
            int line = backgroundHeight/30;
            int collum = backgroundWidth/30;
            Text[] texts = {
                    Text.literal("Primal Spell Creation"),
                    Text.literal("Type: "+this.type),
                    Text.literal("Spell Level: "+this.level),
                    Text.literal("Duration: "+(this.duration==1?"Instant":"Per "+this.duration+"s")),
                    Text.literal("Casting Time: "+this.casting+"s"),
                    Text.literal("Saving Throw:"),
                    Text.literal("Target: "+this.target),
                    Text.literal("Range: Per "+this.range+" Blocks"),
                    Text.literal("Components:"),
                    Text.literal("Buff Effect: Per Temp HP")

            };
            int[] locsX = {(width-textRenderer.getWidth(texts[0]))/2,(width-textRenderer.getWidth(texts[1]))/2,x+collum*4,x+collum*25/2,x+collum*43/2,x+collum*4,x+collum*26/2,x+collum*20,x+collum*4,x+collum*12};
            int[] locsY = {y+20+line*2/3, y+10+line*3, y+20+line*3+line, y+20+line*3+line, y+20+line*3+line, y+20+line*7+line, y+20+line*7+line, y+20+line*7+line, y+20+line*11+line, y+20+line*11+line};
            for(int i=0;i<texts.length;i++) textRendererMatrixStack.drawText(textRenderer, texts[i], locsX[i], locsY[i], 	15859709, false);
            super.render(matrices, mouseX, mouseY, delta);
        }
    }
}
