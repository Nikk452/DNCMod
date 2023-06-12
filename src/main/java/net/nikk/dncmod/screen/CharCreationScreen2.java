package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
public class CharCreationScreen2 extends Screen {
    private final String race;
    private final String firstName;
    private final String lastName;
    private String classname;
    private final int[] stats;
    private final int[] stat_index;
    int extrastat;
    private ButtonWidget classeswheel;

    protected CharCreationScreen2(String name1, String name2, String classname, String race, int[] stats,int extrastat,int[] stat_index) {
        super(Text.literal("Stat2"));
        this.firstName = name1;
        this.lastName = name2;
        this.classname = classname;
        this.race = race;
        this.stats = stats;
        this.extrastat = extrastat;
        this.stat_index = stat_index;
    }

    @Override
    protected void init() {
        int backgroundWidth = 412;
        int collum = backgroundWidth/30;
        int x = (width - backgroundWidth) / 2;
        this.addDrawableChild(new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> this.client.setScreen(new CharCreationScreen3(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extrastat,this.stat_index))));
        this.addDrawableChild(new ButtonWidget(width/2-158, height/2+70, 75, 20, Text.literal("Previous Page"), (button) -> this.client.setScreen(new CharCreationScreen1(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extrastat,this.stat_index,false))));
        this.classeswheel = new ButtonWidget(x+collum*25/2, height/2+30, 90, 20, Text.literal("Next Page"), (button) -> this.switchClass());
        this.addDrawableChild(this.classeswheel);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
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
        //drawTexture(matrices, LocationX, LocationY, Z?... 1 , u is 0, v is 0,ActualImageWidth,ActualImageHeight,texturewidthScaled,textureheightScaled);
        //text drawing
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        MatrixStack textRendererMatrixStack = new MatrixStack();
        textRendererMatrixStack.scale(1.0F, 1.0F, 1.0F);
        int line = backgroundHeight/30;
        int collum = backgroundWidth/30;
        textRenderer.draw(textRendererMatrixStack, "Character Creation", x+collum*12, y+20+line*2/3, 	15859709);
        textRenderer.draw(textRendererMatrixStack, Text.literal("Pick a ").styled(style -> style.withBold(false).withItalic(false)).append("class").styled(style -> style.withBold(true).withItalic(true)).append(", any ").append("class").styled(style -> style.withBold(true).withItalic(true)).append(" from the following options:"), x+collum*4, y+20+line*3+line, 	15859709);
        RenderSystem.setShaderColor(1.00f, 1.00f, 1.00f, 1.00f);
        switch (this.classname) {
            case "Fighter" -> DrawClass("fighter",matrices,textRendererMatrixStack,new int[]{x,collum,y,line},new int[]{23,23,23},new int[]{19,21,23},new Text[]{Text.literal("This class will require"),Text.literal("you to have either 15"),Text.literal("strength or dexterity.")},new int[]{0,1,2});
            case "Wizard" -> DrawClass("wizard",matrices,textRendererMatrixStack,new int[]{x,collum,y,line},new int[]{23,21},new int[]{19,21},new Text[]{Text.literal("This class will require"),Text.literal("you to have 15 Intelligence")},new int[]{0,1});
            case "Druid" -> DrawClass("druid",matrices,textRendererMatrixStack,new int[]{x,collum,y,line},new int[]{23,21},new int[]{19,21},new Text[]{Text.literal("This class will require"),Text.literal("you to have 15 Intelligence")},new int[]{0,1});
            case "Cleric" -> DrawClass("cleric",matrices,textRendererMatrixStack,new int[]{x,collum,y,line},new int[]{23,23},new int[]{19,21},new Text[]{Text.literal("This class will require"),Text.literal("you to have 15 Wisdom")},new int[]{0,1});
            case "Sorcerer" -> DrawClass("sorcerer",matrices,textRendererMatrixStack,new int[]{x,collum,y,line},new int[]{23,22},new int[]{19,21},new Text[]{Text.literal("This class will require"),Text.literal("you to have 15 Charisma")},new int[]{0,1});
            case "Monk" -> DrawClass("monk",matrices,textRendererMatrixStack,new int[]{x,collum,y,line},new int[]{23,23,23},new int[]{19,21,23},new Text[]{Text.literal("This class will require"),Text.literal("you to have either 15"),Text.literal("wisdom or dexterity.")},new int[]{0,1,2});
            default -> {
                this.classname = "Fighter";
                DrawClass("fighter",matrices,textRendererMatrixStack,new int[]{x,collum,y,line},new int[]{23,23,23},new int[]{19,21,23},new Text[]{Text.literal("This class will require"),Text.literal("you to have either 15"),Text.literal("strength or dexterity.")},new int[]{0,1,2});
            }
        }
        super.render(matrices, mouseX, mouseY, delta);
    }
    private void DrawClass(String id,MatrixStack matrices,MatrixStack textRendererMatrixStack,int[] pic,int[] textX,int[] textY,Text[] texts,int[] max){
        RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/"+id+".png"));
        drawTexture(matrices, pic[0] + pic[1] * 25 / 2, pic[2] + 20 + pic[3] * 5 + pic[3], 0, 0, 90, 90, 90, 90);
        for (int i:max) textRenderer.draw(textRendererMatrixStack, texts[i], pic[0] + pic[1] * textX[i] / 2 + 3, pic[2] + 20 + pic[3] * textY[i] + pic[3], 15859709);
        this.classeswheel.setMessage(Text.literal(id.substring(0,1).toUpperCase() + id.substring(1).toLowerCase()+" Class"));
    }
    private void switchClass(){
        switch (this.classname) {
            case "Fighter" -> this.classname = "Wizard";
            case "Wizard" -> this.classname = "Druid";
            case "Druid" -> this.classname = "Cleric";
            case "Cleric" -> this.classname = "Sorcerer";
            case "Sorcerer" -> this.classname = "Monk";
            case "Monk" -> this.classname = "Fighter";
        }
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}

