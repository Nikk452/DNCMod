package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.util.Arrays;

public class CharCreationScreen4 extends Screen {
    private String race;
    private String firstName;
    private String lastName;
    private String classname;
    private ButtonWidget next;
    private ButtonWidget previous_page;
    private ButtonWidget B1;
    private ButtonWidget B2;
    private ButtonWidget B3;
    private ButtonWidget B4;
    private ButtonWidget B5;
    private ButtonWidget B6;
    private ButtonWidget B7;
    private ButtonWidget B8;
    private ButtonWidget B9;
    private ButtonWidget B10;
    private ButtonWidget B11;
    private ButtonWidget B12;
    private TexturedButtonWidget error_window;
    private boolean E1 = false;
    private int[] stats;
    private int[] stat_ind = {0,1,2,3,4,5};
    int extra_stat;
    private ButtonWidget rollstats;
    NbtCompound nbt_stats = ((IEntityDataSaver)MinecraftClient.getInstance().player).getCharacterData().getCompound("dncmod.chart");
    protected CharCreationScreen4(String name1, String name2, String classname, String race, int[] stats,int extra_stat,int[] stat_index) {
        super(Text.literal("Stat2"));
        this.firstName = name1;
        this.lastName = name2;
        this.classname = classname;
        this.race = race;
        this.stats = Arrays.stream(nbt_stats.getIntArray("stats")).sum()==0?nbt_stats.getIntArray("stats"):stats;
        this.extra_stat = extra_stat;
        this.stat_ind = stat_index;
    }
    @Override
    protected void init() {
        int backgroundWidth = 412;
        int backgroundHeight = 256;
        int line = backgroundHeight/30;
        int collum = backgroundWidth/30;
        int y = (height - backgroundHeight) / 2;
        int x = (width - backgroundWidth) / 2;
        this.next = new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> {
            switch(this.classname){
                case "Fighter":
                    if(this.stats[stat_ind[0]]>=15 || this.stats[stat_ind[1]] >= 15)
                        this.E1 = false;
                    else this.E1 = true;
                    break;
                case "Wizard":
                    if(this.stats[stat_ind[3]]>=15)
                        this.E1 = false;
                    else this.E1 = true;
                    break;
                case "Druid":
                    if(this.stats[stat_ind[3]]>=15)
                        this.E1 = false;
                    else this.E1 = true;
                    break;
                case "Cleric":
                    if(this.stats[stat_ind[4]]>=15)
                        this.E1 = false;
                    else this.E1 = true;
                    break;
                case "Sorcerer":
                    if(this.stats[stat_ind[5]]>=15)
                        this.E1 = false;
                    else this.E1 = true;
                    break;
                case "Monk":
                    if(this.stats[stat_ind[4]]>=15 && this.stats[stat_ind[1]] >= 15)
                        this.E1 = false;
                    else this.E1 = true;
                    break;
            }
            if(!E1){
                this.client.setScreen(new CharCreationScreen5(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extra_stat,this.stat_ind));
            }});
        this.addDrawableChild(this.next);
        this.previous_page = new ButtonWidget(width/2-158, height/2+70, 75, 20, Text.literal("Previous Page"), (button) -> {
            this.client.setScreen(new CharCreationScreen3(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extra_stat,this.stat_ind));});
        this.addDrawableChild(this.previous_page);
        this.rollstats = new ButtonWidget(x+collum*19, y+20+line*5/2+line, 25, 20, Text.literal("Roll"), (button) -> {
            this.RollStats();});
        rollstats.active = Arrays.stream(this.stats).sum() == 0;
        this.addDrawableChild(this.rollstats);
        //12 buttons for up down
        this.B1 = new ButtonWidget(x+collum*5-4, y+23+line*11/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(0,-1);});
        this.addDrawableChild(this.B1);
        this.B2 = new ButtonWidget(x+collum*5-4, y+23+line*12/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(0,1);});
        this.addDrawableChild(this.B2);
        this.B3 = new ButtonWidget(x+collum*5-4, y+23+line*16/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(1,-1);});
        this.addDrawableChild(this.B3);
        this.B4 = new ButtonWidget(x+collum*5-4, y+23+line*17/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(1,1);});
        this.addDrawableChild(this.B4);
        this.B5 = new ButtonWidget(x+collum*5-4, y+23+line*21/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(2,-1);});
        this.addDrawableChild(this.B5);
        this.B6 = new ButtonWidget(x+collum*5-4, y+23+line*22/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(2,1);});
        this.addDrawableChild(this.B6);
        this.B7 = new ButtonWidget(x+collum*5-4, y+23+line*26/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(3,-1);});
        this.addDrawableChild(this.B7);
        this.B8 = new ButtonWidget(x+collum*5-4, y+23+line*27/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(3,1);});
        this.addDrawableChild(this.B8);
        this.B9 = new ButtonWidget(x+collum*5-4, y+23+line*31/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(4,-1);});
        this.addDrawableChild(this.B9);
        this.B10 = new ButtonWidget(x+collum*5-4, y+23+line*32/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(4,1);});
        this.addDrawableChild(this.B10);
        this.B11 = new ButtonWidget(x+collum*5-4, y+23+line*36/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(5,-1);});
        this.addDrawableChild(this.B11);
        this.B12 = new ButtonWidget(x+collum*5-4, y+23+line*37/2+line, 5, 4, Text.literal(""), (button) -> {
            this.moveStats(5,1);});
        this.addDrawableChild(this.B12);
        this.error_window = new TexturedButtonWidget(x+collum*8+4, y+20+line*3,194,160,0,0,0,new Identifier(DNCMod.MOD_ID,"textures/gui/uialarm.png"),194,160,(button) -> {
            this.error_window.active = false;
            this.E1 = false;
            this.next.active = true;
            this.previous_page.active = true;
            this.B1.active = true;
            this.B2.active = true;
            this.B3.active = true;
            this.B4.active = true;
            this.B5.active = true;
            this.B6.active = true;
            this.B7.active = true;
            this.B8.active = true;
            this.B9.active = true;
            this.B10.active = true;
            this.B11.active = true;
            this.B12.active = true;
        });
        this.addDrawableChild(this.error_window);
    }
    private void moveStats(int index,int direction){
        if(index == 0 && direction == -1){
            int holder = this.stat_ind[5];
            this.stat_ind[5] = this.stat_ind[index];
            this.stat_ind[index] = holder;
        }else if(index == 5 && direction == 1){
            int holder = this.stat_ind[0];
            this.stat_ind[0] = this.stat_ind[index];
            this.stat_ind[index] = holder;
        }else{
            int holder = this.stat_ind[index + direction];
            this.stat_ind[index+direction] = this.stat_ind[index];
            this.stat_ind[index] = holder;
        }
    }
    private void RollStats() {
        ClientPlayNetworking.send(Networking.ROLL_CREATION_ID, PacketByteBufs.create());
        rollstats.active = false;
        }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        if(!(Arrays.equals(nbt_stats.getIntArray("stats"), this.stats)))
            this.stats = nbt_stats.getIntArray("stats");
        int backgroundWidth = 412;
        int backgroundHeight = 256;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight,412,256);
        this.rollstats.render(matrices,mouseX,mouseY,delta);
        this.next.render(matrices,mouseX,mouseY,delta);
        this.previous_page.render(matrices,mouseX,mouseY,delta);
        this.B1.render(matrices,mouseX,mouseY,delta);
        this.B2.render(matrices,mouseX,mouseY,delta);
        this.B3.render(matrices,mouseX,mouseY,delta);
        this.B4.render(matrices,mouseX,mouseY,delta);
        this.B5.render(matrices,mouseX,mouseY,delta);
        this.B6.render(matrices,mouseX,mouseY,delta);
        this.B7.render(matrices,mouseX,mouseY,delta);
        this.B8.render(matrices,mouseX,mouseY,delta);
        this.B9.render(matrices,mouseX,mouseY,delta);
        this.B10.render(matrices,mouseX,mouseY,delta);
        this.B11.render(matrices,mouseX,mouseY,delta);
        this.B12.render(matrices,mouseX,mouseY,delta);
        //drawTexture(matrices, LocationX, LocationY, Z?... 1 , u is 0, v is 0,ActualImageWidth,ActualImageHeight,texturewidthScaled,textureheightScaled);
        //text drawing
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        MatrixStack textRendererMatrixStack = new MatrixStack();
        textRendererMatrixStack.scale(1.0F, 1.0F, 1.0F);
        int line = backgroundHeight/30;
        int collum = backgroundWidth/30;
        textRenderer.draw(textRendererMatrixStack, "Character Creation", x+collum*12, y+20+line*2/3, 	15859709);
        textRenderer.draw(textRendererMatrixStack, Text.literal("There are 6 ").styled(style -> style.withBold(false).withItalic(false)).append("stats ").styled(style -> style.withBold(true).withItalic(true)).append("on this ").append("page:").styled(style -> style.withBold(true).withItalic(true)).append(" "), x+collum*4, y+20+line*3+line, 	15859709);
        textRenderer.draw(textRendererMatrixStack, "Strength: "+ this.stats[stat_ind[0]], x+collum*6+6, y+20+line*12/2+line, 	15859709);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/strengthicon.png"));
        drawTexture(matrices, x+collum*5+1, y+20+line*11/2+line, 0, 0, 15, 15,15,15);
        textRenderer.draw(textRendererMatrixStack, "Dexterity: "+ this.stats[stat_ind[1]], x+collum*6+6, y+20+line*17/2+line, 	15859709);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/dexterityicon.png"));
        drawTexture(matrices, x+collum*5+1, y+20+line*16/2+line, 0, 0, 15, 15,15,15);
        textRenderer.draw(textRendererMatrixStack, "Constitution: "+ this.stats[stat_ind[2]], x+collum*6+6, y+20+line*22/2+line, 	15859709);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/constitutionicon.png"));
        drawTexture(matrices, x+collum*5+1, y+20+line*21/2+line, 0, 0, 15, 15,15,15);
        textRenderer.draw(textRendererMatrixStack, "Intelligence: "+ this.stats[stat_ind[3]], x+collum*6+6, y+20+line*27/2+line, 	15859709);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/intelligenceicon.png"));
        drawTexture(matrices, x+collum*5+1, y+20+line*26/2+line, 0, 0, 15, 15,15,15);
        textRenderer.draw(textRendererMatrixStack, "Wisdom: "+ this.stats[stat_ind[4]], x+collum*6+6, y+20+line*32/2+line, 	15859709);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/wisdomicon.png"));
        drawTexture(matrices, x+collum*5+1, y+20+line*31/2+line, 0, 0, 15, 15,15,15);
        textRenderer.draw(textRendererMatrixStack, "Charisma: "+ this.stats[stat_ind[5]], x+collum*6+6, y+20+line*37/2+line, 	15859709);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/charismaicon.png"));
        drawTexture(matrices, x+collum*5+1, y+20+line*36/2+line, 0, 0, 15, 15,15,15);
        RenderSystem.setShaderColor(1.00f, 1.00f, 1.00f, 1.00f);
        if(this.E1){
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            this.next.active = false;
            this.previous_page.active = false;
            this.B1.active = false;
            this.B2.active = false;
            this.B3.active = false;
            this.B4.active = false;
            this.B5.active = false;
            this.B6.active = false;
            this.B7.active = false;
            this.B8.active = false;
            this.B9.active = false;
            this.B10.active = false;
            this.B11.active = false;
            this.B12.active = false;
            this.error_window.render(matrices,mouseX,mouseY,delta);
            this.error_window.active = true;
            textRenderer.draw(textRendererMatrixStack, Text.literal("ERROR!").formatted(Formatting.BOLD), x + collum * 14 + 5, y + 28 + line * 8, 16121850);
            textRenderer.draw(textRendererMatrixStack, "WRONG STATS", x + collum * 13+4, y + 25 + line * 12, 16121850);textRenderer.draw(textRendererMatrixStack, "FOR YOUR CLASS", x + collum * 13-4, y + 20 + line * 14, 16121850);
        }
    }
}
