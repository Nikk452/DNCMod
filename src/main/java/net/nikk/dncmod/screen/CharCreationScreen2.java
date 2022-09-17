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
    private String firstName;
    private String lastName;
    private String classname;
    private int[] stats;
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
        int backgroundHeight = 256;
        int line = backgroundHeight/30;
        int collum = backgroundWidth/30;
        int y = (height - backgroundHeight) / 2;
        int x = (width - backgroundWidth) / 2;
        this.addDrawableChild(new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> {
            this.client.setScreen(new CharCreationScreen3(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extrastat,this.stat_index));}));
        this.addDrawableChild(new ButtonWidget(width/2-158, height/2+70, 75, 20, Text.literal("Previous Page"), (button) -> {
            this.client.setScreen(new CharCreationScreen1(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extrastat,this.stat_index));}));
        this.classeswheel = new ButtonWidget(x+collum*25/2, height/2+30, 90, 20, Text.literal("Next Page"), (button) -> {
            this.switchClass();});
        this.addDrawableChild(this.classeswheel);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
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
        switch(this.classname){
            case "Fighter":
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/fighter.png"));
                drawTexture(matrices, x+collum*25/2, y+20+line*5+line, 0, 0, 90, 90,90,90);
                textRenderer.draw(textRendererMatrixStack, "This class will require ", x+collum*23/2+3, y+20+line*19+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "you to have either 15", x+collum*23/2+3, y+20+line*21+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "strength or dexterity.", x+collum*23/2+3, y+20+line*23+line, 	15859709);
                this.classeswheel.setMessage(Text.literal("Fighter Class"));
                break;
            case "Wizard":
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/wizard.png"));
                drawTexture(matrices, x+collum*25/2, y+20+line*5+line, 0, 0, 90, 90,90,90);
                textRenderer.draw(textRendererMatrixStack, "This class will require", x+collum*23/2+3, y+20+line*19+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "you to have 15 Intelligence", x+collum*21/2+3, y+20+line*21+line, 	15859709);
                this.classeswheel.setMessage(Text.literal("Wizard Class"));
                break;
            case "Druid":
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/druid.png"));
                drawTexture(matrices, x+collum*25/2, y+20+line*5+line, 0, 0, 90, 90,90,90);
                textRenderer.draw(textRendererMatrixStack, "This class will require ", x+collum*23/2+3, y+20+line*19+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "you to have 15 Intelligence", x+collum*21/2+3, y+20+line*21+line, 	15859709);this.classeswheel.setMessage(Text.literal("Druid Class"));
                this.classeswheel.setMessage(Text.literal("Druid Class"));
                break;
            case "Cleric":
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/cleric.png"));
                drawTexture(matrices, x+collum*25/2, y+20+line*5+line, 0, 0, 90, 90,90,90);
                textRenderer.draw(textRendererMatrixStack, "This class will require ", x+collum*23/2+3, y+20+line*19+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "you to have 15 Wisdom", x+collum*23/2+3, y+20+line*21+line, 	15859709);this.classeswheel.setMessage(Text.literal("Cleric Class"));
                this.classeswheel.setMessage(Text.literal("Cleric Class"));
                break;
            case "Sorcerer":
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/sorcerer.png"));
                drawTexture(matrices, x+collum*25/2, y+20+line*5+line, 0, 0, 90, 90,90,90);
                textRenderer.draw(textRendererMatrixStack, "This class will require ", x+collum*23/2+3, y+20+line*19+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "you to have 15 Charisma", x+collum*22/2+3, y+20+line*21+line, 	15859709);this.classeswheel.setMessage(Text.literal("Cleric Class"));
                this.classeswheel.setMessage(Text.literal("Sorcerer Class"));
                break;
            case "Monk":
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/monk.png"));
                drawTexture(matrices, x+collum*25/2, y+20+line*5+line, 0, 0, 90, 90,90,90);
                textRenderer.draw(textRendererMatrixStack, "This class will require ", x+collum*23/2+3, y+20+line*19+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "you to have either 15", x+collum*23/2+3, y+20+line*21+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "wisdom or dexterity.", x+collum*23/2+3, y+20+line*23+line, 	15859709);
                this.classeswheel.setMessage(Text.literal("Monk Class"));
                break;
            default:
                this.classname = "Fighter";
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/fighter.png"));
                drawTexture(matrices, x+collum*25/2, y+20+line*5+line, 0, 0, 90, 90,90,90);
                textRenderer.draw(textRendererMatrixStack, "This class will require ", x+collum*23/2+3, y+20+line*19+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "you to have either 15", x+collum*23/2+3, y+20+line*21+line, 	15859709);
                textRenderer.draw(textRendererMatrixStack, "strength or dexterity.", x+collum*23/2+3, y+20+line*23+line, 	15859709);
                this.classeswheel.setMessage(Text.literal("Fighter Class"));
                break;
        }
        super.render(matrices, mouseX, mouseY, delta);
    }
    private void switchClass(){
        switch(this.classname){
            case "Fighter":
                this.classname = "Wizard";
                break;
            case "Wizard":
                this.classname = "Druid";
                break;
            case "Druid":
                this.classname = "Cleric";
                break;
            case "Cleric":
                this.classname = "Sorcerer";
                break;
            case "Sorcerer":
                this.classname = "Monk";
                break;
            case "Monk":
                this.classname = "Fighter";
                break;
        }
    }
}

