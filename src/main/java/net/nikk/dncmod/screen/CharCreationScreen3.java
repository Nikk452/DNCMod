package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.nikk.dncmod.DNCMod;

public class CharCreationScreen3 extends Screen {
    private String race;
    private final String firstName;
    private final String lastName;
    private final String classname;
    private final int[] stats;
    private final int[] stat_index;
    int extrastat;
    private ButtonWidget classeswheel;
    private ButtonWidget statswheel;
    protected CharCreationScreen3(String name1, String name2, String classname, String race, int[] stats,int extrastat,int[] stat_index) {
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
        this.addDrawableChild(new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> this.client.setScreen(new CharCreationScreen4(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extrastat,this.stat_index))));
        this.addDrawableChild(new ButtonWidget(width/2-158, height/2+70, 75, 20, Text.literal("Previous Page"), (button) -> this.client.setScreen(new CharCreationScreen2(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extrastat,this.stat_index))));
        this.classeswheel = new ButtonWidget(x+collum*25/2, height/2+30, 90, 20, Text.literal("Next Page"), (button) -> this.switchRace());
        this.addDrawableChild(this.classeswheel);
        this.statswheel = new ButtonWidget(width/2+85, height/2, 75, 20, Text.literal("Strength"), (button) -> this.extrastat = this.extrastat==5?0:this.extrastat+1);
        this.addDrawableChild(this.statswheel);
        this.statswheel.visible= false;
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
        textRenderer.draw(textRendererMatrixStack, Text.literal("Pick a ").styled(style -> style.withBold(false).withItalic(false)).append("race").styled(style -> style.withBold(true).withItalic(true)).append(", any ").append("race").styled(style -> style.withBold(true).withItalic(true)).append(" from the following options:"), x+collum*4, y+20+line*3+line, 	15859709);
        RenderSystem.setShaderColor(1.00f, 1.00f, 1.00f, 1.00f);
        loadClass(matrices,x,collum,y,line);
        Text[] stat_names = {Text.literal("Strength"),Text.literal("Dexterity"),Text.literal("Constitution"),Text.literal("Intelligence"),Text.literal("Wisdom"),Text.literal("Charisma")};
        this.statswheel.setMessage(stat_names[this.extrastat]);
        switch (this.race) {
            case "Human" -> {
                this.statswheel.visible = false;
                textRenderer.draw(textRendererMatrixStack, "This race will grant", x + collum * 23 / 2 + 3, y + 20 + line * 19 + line, 15859709);
                textRenderer.draw(textRendererMatrixStack, "the player a bonus Feat.", x + collum * 21 / 2 + 3, y + 20 + line * 21 + line, 15859709);
                drawEntity(x + collum * 32 / 2 - 4, y + 20 + line * 14 + line, 30, x + collum * 32 / 2 - 4 - mouseX, y + 20 + line * 14 + line - mouseY, this.client.player);
                this.classeswheel.setMessage(Text.literal("Human Race"));
            }
            case "Elf" -> {
                this.statswheel.visible = true;
                textRenderer.draw(textRendererMatrixStack, "Select A Stat", x + collum * 22 + 8, y + 20 + line * 11 + line, 15859709);
                textRenderer.draw(textRendererMatrixStack, "This race will grant the player", x + collum * 20 / 2 + 3, y + 20 + line * 19 + line, 15859709);
                textRenderer.draw(textRendererMatrixStack, "a +2 bonus to one", x + collum * 24 / 2 + 3, y + 20 + line * 21 + line, 15859709);
                textRenderer.draw(textRendererMatrixStack, "Stat of their choice.", x + collum * 23 / 2 + 6, y + 20 + line * 23 + line, 15859709);
                drawEntity(x + collum * 32 / 2 - 4, y + 20 + line * 14 + line, 30, x + collum * 32 / 2 - 4 - mouseX, y + 20 + line * 14 + line - mouseY, EntityType.ENDERMAN.create(MinecraftClient.getInstance().world));
                this.classeswheel.setMessage(Text.literal("Elven Race"));
            }
            case "Dwarf" -> {
                this.statswheel.visible = false;
                textRenderer.draw(textRendererMatrixStack, "This race will grant the player", x + collum * 20 / 2 + 3, y + 20 + line * 19 + line, 15859709);
                textRenderer.draw(textRendererMatrixStack, "bonus hit points equal to half", x + collum * 20 / 2 + 3, y + 20 + line * 21 + line, 15859709);
                textRenderer.draw(textRendererMatrixStack, "your con modifier rounded down.", x + collum * 19 / 2 + 3, y + 20 + line * 23 + line, 15859709);
                drawEntity(x + collum * 32 / 2 - 4, y + 20 + line * 14 + line, 30, x + collum * 32 / 2 - 4 - mouseX, y + 20 + line * 14 + line - mouseY, EntityType.CREEPER.create(MinecraftClient.getInstance().world));
                this.classeswheel.setMessage(Text.literal("Dwarf Race"));
            }
            default -> this.race = "Human";
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void switchRace(){
        switch (this.race) {
            case "Human" -> this.race = "Elf";
            case "Elf" -> this.race = "Dwarf";
            case "Dwarf" -> this.race = "Human";
        }
    }

    public static void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        float f = (float)Math.atan((double)(mouseX / 40.0F));
        float g = (float)Math.atan((double)(mouseY / 40.0F));
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate(x, y, 1050.0);
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0, 0.0, 1000.0);
        matrixStack2.scale((float)size, (float)size, (float)size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0F);
        quaternion.hamiltonProduct(quaternion2);
        matrixStack2.multiply(quaternion);
        float h = entity.bodyYaw;
        float i = entity.getYaw();
        float j = entity.getPitch();
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;
        entity.bodyYaw = 180.0F + f * 20.0F;
        entity.setYaw(180.0F + f * 40.0F);
        entity.setPitch(-g * 20.0F);
        entity.headYaw = entity.getYaw();
        entity.prevHeadYaw = entity.getYaw();
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, matrixStack2, immediate, 15728880));
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        entity.bodyYaw = h;
        entity.setYaw(i);
        entity.setPitch(j);
        entity.prevHeadYaw = k;
        entity.headYaw = l;
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }
    private void loadClass(MatrixStack matrices,int x, int collum, int y, int line){
        switch (this.classname) {
            case "Fighter" -> {
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/fighter.png"));
                drawTexture(matrices, x + collum * 25 / 2, y + 20 + line * 5 + line, 0, 0, 90, 90, 90, 90);
            }
            case "Wizard" -> {
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/wizard.png"));
                drawTexture(matrices, x + collum * 25 / 2, y + 20 + line * 5 + line, 0, 0, 90, 90, 90, 90);
            }
            case "Druid" -> {
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/druid.png"));
                drawTexture(matrices, x + collum * 25 / 2, y + 20 + line * 5 + line, 0, 0, 90, 90, 90, 90);
            }
            case "Cleric" -> {
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/cleric.png"));
                drawTexture(matrices, x + collum * 25 / 2, y + 20 + line * 5 + line, 0, 0, 90, 90, 90, 90);
            }
            case "Sorcerer" -> {
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/sorcerer.png"));
                drawTexture(matrices, x + collum * 25 / 2, y + 20 + line * 5 + line, 0, 0, 90, 90, 90, 90);
            }
            case "Monk" -> {
                RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/monk.png"));
                drawTexture(matrices, x + collum * 25 / 2, y + 20 + line * 5 + line, 0, 0, 90, 90, 90, 90);
            }
        }
    }
}
