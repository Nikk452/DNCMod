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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.util.ArrayList;
import java.util.Arrays;

import static net.minecraft.util.math.MathHelper.floor;

public class CharCreationScreen4 extends Screen {
    private final String race;
    private final String firstName;
    private final String lastName;
    private final String classname;
    private ButtonWidget next;
    private ButtonWidget previous_page;
    private ArrayList<ButtonWidget> MyButtons = new ArrayList<ButtonWidget>();
    private TexturedButtonWidget error_window;
    private boolean E1 = false;
    private int[] stats;
    private int[] stat_ind;
    int extra_stat;
    private ButtonWidget rollstats;
    protected CharCreationScreen4(String name1, String name2, String classname, String race, int[] stats,int extra_stat,int[] stat_index) {
        super(Text.literal("Stat2"));
        this.firstName = name1;
        this.lastName = name2;
        this.classname = classname;
        this.race = race;
        this.stats = Arrays.stream(((IEntityDataSaver)MinecraftClient.getInstance().player).getPersistentData().getIntArray("stats")).sum()==0?stats:((IEntityDataSaver)MinecraftClient.getInstance().player).getPersistentData().getIntArray("stats");
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
                    this.E1 = this.stats[stat_ind[0]] < 15 && this.stats[stat_ind[1]] < 15;
                    break;
                case "Wizard":
                case "Druid":
                    this.E1 = this.stats[stat_ind[3]] < 15;
                    break;
                case "Cleric":
                    this.E1 = this.stats[stat_ind[4]] < 15;
                    break;
                case "Sorcerer":
                    this.E1 = this.stats[stat_ind[5]] < 15;
                    break;
                case "Monk":
                    this.E1 = this.stats[stat_ind[4]] < 15 || this.stats[stat_ind[1]] < 15;
                    break;
            }
            if(!E1){
                this.client.setScreen(new CharCreationScreen5(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extra_stat,this.stat_ind));
            }});
        this.addDrawableChild(this.next);
        this.previous_page = new ButtonWidget(width/2-158, height/2+70, 75, 20, Text.literal("Previous Page"), (button) -> {
            this.client.setScreen(new CharCreationScreen3(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extra_stat,this.stat_ind));});
        this.addDrawableChild(this.previous_page);
        this.rollstats = new ButtonWidget(x+collum*19, y+20+line*5/2+line, 25, 20, Text.literal("Roll"), (button) -> this.RollStats());
        rollstats.active = Arrays.stream(this.stats).sum() == 0;
        this.addDrawableChild(this.rollstats);
        //12 buttons for up down
        int[] BY = {11,12,16,17,21,22,26,27,31,32,36,37};
        for(int i = 0;i<12;i++){
            int dir = (i+2)%2==0?-1:1;
            int idx = floor(i/2);
            this.MyButtons.add(new ButtonWidget(x+collum*5-4, y + 23 + line * BY[i]/2 + line,5,4,Text.literal(""),(button) -> this.moveStats(idx,dir)));
            this.addDrawableChild(this.MyButtons.get(i));
        }
        this.error_window = new TexturedButtonWidget(x+collum*8+4, y+20+line*3,194,160,0,0,0,new Identifier(DNCMod.MOD_ID,"textures/gui/uialarm.png"),194,160,(button) -> {
            this.error_window.active = false;
            this.E1 = false;
            this.next.active = true;
            this.previous_page.active = true;
            for(int i = 0;i<12;i++) this.MyButtons.get(i).active = true;
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
        if(!(Arrays.equals(((IEntityDataSaver) MinecraftClient.getInstance().player).getPersistentData().getIntArray("stats"), this.stats)))
            this.stats = ((IEntityDataSaver)MinecraftClient.getInstance().player).getPersistentData().getIntArray("stats").length == 0? new int[]{0,0,0,0,0,0}:((IEntityDataSaver)MinecraftClient.getInstance().player).getPersistentData().getIntArray("stats");
        int backgroundWidth = 412;
        int backgroundHeight = 256;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
        renderBackground(matrices);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"));
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight,412,256);
        this.rollstats.render(matrices,mouseX,mouseY,delta);
        this.next.render(matrices,mouseX,mouseY,delta);
        this.previous_page.render(matrices,mouseX,mouseY,delta);
        for(int i = 0;i<12;i++) this.MyButtons.get(i).render(matrices,mouseX,mouseY,delta);
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
            for(int i = 0;i<12;i++) this.MyButtons.get(i).active = false;
            this.error_window.render(matrices,mouseX,mouseY,delta);
            this.error_window.active = true;
            textRenderer.draw(textRendererMatrixStack, Text.literal("ERROR!").formatted(Formatting.BOLD), x + collum * 14 + 5, y + 28 + line * 8, 16121850);
            textRenderer.draw(textRendererMatrixStack, "WRONG STATS", x + collum * 13+4, y + 25 + line * 12, 16121850);textRenderer.draw(textRendererMatrixStack, "FOR YOUR CLASS", x + collum * 13-4, y + 20 + line * 14, 16121850);
        }
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
