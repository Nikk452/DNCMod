package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.util.IEntityDataSaver;

import java.io.File;

public class CharCreationScreen1 extends Screen {
    private TextFieldWidget textField1;
    private TextFieldWidget textField2;
    private boolean E1 = false;
    private boolean E2 = false;
    private boolean E3 = false;
    private boolean E4 = false;
    private String firstName;
    private String lastName;
    final private String classname;
    final private String race;
    private TexturedButtonWidget errorwindow;
    final private int[] stats;
    int extrastat;
    final private int[] stat_index;
    private ButtonWidget createCharButton;
    private double anime;
    private float shade_color;
    private Boolean animate;
    private boolean waiting = false;
    private String allowed_name = "";
    public CharCreationScreen1(String name1,String name2,String classname,String race, int[] stats,int extrastat,int[] stat_index,boolean animate) {
        super(Text.literal("Stat2"));
        this.firstName = name1;
        this.lastName = name2;
        this.classname = classname;
        this.race = race;
        this.stats = stats;
        this.extrastat = extrastat;
        this.stat_index = stat_index;
        this.animate = animate;
    }
    @Override
    protected void init() {
        if (this.animate){
        this.anime = 300d;
        this.shade_color = 0f;
        }else{this.anime = -40;}
        int backgroundWidth = 412;
        int backgroundHeight = 256;
        int line = backgroundHeight/30;
        int collum = backgroundWidth/30;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        this.textField1 = new TextFieldWidget(this.textRenderer, (int) (this.width / 2 - 51.5), y+18+line*8+line, 103, 12, Text.translatable(""));
        this.textField1.setMaxLength(12);
        this.addDrawableChild(this.textField1);
        this.textField1.setText(this.firstName);
        this.textField2 = new TextFieldWidget(this.textRenderer, (int) (this.width / 2 - 51.5), y+18+line*11+line, 103, 12, Text.translatable(""));
        this.textField2.setMaxLength(12);
        this.addDrawableChild(this.textField2);
        this.textField2.setText(this.lastName);
        this.createCharButton = new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> {
            this.createCharButton.active = false;
            this.textField1.setFocusUnlocked(false);
            this.textField2.setFocusUnlocked(false);
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeString(this.firstName+" "+this.lastName);
            this.waiting = true;
            ClientPlayNetworking.send(Networking.NEWNAMEC2S,buf);
        });
        this.addDrawableChild(this.createCharButton);
        //error pannel
        this.errorwindow = new TexturedButtonWidget(x+collum*8+4, y+20+line*3,194,160,0,0,0,new Identifier(DNCMod.MOD_ID,"textures/gui/uialarm.png"),194,160,(button) -> {
            this.errorwindow.active = false;
            this.E1 = false;
            this.E2 = false;
            this.E3 = false;
            this.E4 = false;
            this.textField1.setFocusUnlocked(true);
            this.textField2.setFocusUnlocked(true);
            this.createCharButton.active = true;
        });
        this.addDrawableChild(errorwindow);

    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if(this.waiting){
            this.allowed_name = ((IEntityDataSaver)client.player).getPersistentData().getString("allowed_name");
            if(this.allowed_name.length()>1){
                this.waiting = false;
                this.confirmName();
            }
        }
        int backgroundWidth = 412;
        int backgroundHeight = 256;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //texture drawing
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        if (this.anime>y || this.anime>-35) {
            this.anime -= 5;
            this.shade_color = this.shade_color >= 1f ? 1f : this.shade_color + 0.013f;
            RenderSystem.setShaderColor(this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f);
            renderBackground(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f, this.shade_color - 0.1f);
            RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"));
            drawTexture(matrices, x, Math.max((int) this.anime, y), 0, 0, backgroundWidth, backgroundHeight, 412, 256);
        }else{
            if(this.animate) this.animate = false;
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            renderBackground(matrices);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            RenderSystem.setShaderTexture(0, new Identifier(DNCMod.MOD_ID, "textures/gui/uifrag.png"));
            drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight, 412, 256);
            this.textField1.render(matrices, mouseX, mouseY, delta);
            this.textField2.render(matrices, mouseX, mouseY, delta);
            this.createCharButton.render(matrices, mouseX, mouseY, delta);
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            MatrixStack textRendererMatrixStack = new MatrixStack();
            textRendererMatrixStack.scale(1.0F, 1F, 1.0F);
            int line = backgroundHeight / 30;
            int collum = backgroundWidth / 30;
            textRenderer.draw(textRendererMatrixStack, "Character Creation", x + collum * 12, y + 20 + line * 2 / 3, 15859709);
            textRenderer.draw(textRendererMatrixStack, Text.literal("Choose a character name for this server,").styled(style -> style.withBold(true).withItalic(true)), x + collum * 4, y + 20 + line * 3 + line, 15859709);
            textRenderer.draw(textRendererMatrixStack, Text.literal("you will be seen as this name while on the server.").styled(style -> style.withItalic(true).withBold(true)), x + collum * 4, y + 20 + line * 5 + line, 15859709);
            textRenderer.draw(textRendererMatrixStack, "Pick a first name: ", x + collum * 4, y + 20 + line * 8 + line, 15859709);
            textRenderer.draw(textRendererMatrixStack, "Pick a last name: ", x + collum * 4, y + 20 + line * 11 + line, 15859709);
            if (this.E1 || this.E2 || this.E3 || this.E4) {
                RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                this.createCharButton.active = false;
                this.textField1.setFocusUnlocked(false);
                this.textField2.setFocusUnlocked(false);
                this.errorwindow.render(matrices, mouseX, mouseY, delta);
                this.errorwindow.active = true;
                textRenderer.draw(textRendererMatrixStack, Text.literal("ERROR!").formatted(Formatting.BOLD), x + collum * 14 + 5, y + 28 + line * 8, 16121850);
                if (this.E1) {
                    textRenderer.draw(textRendererMatrixStack, "THIS NAME IS", x + collum * 13 + 4, y + 25 + line * 12, 16121850);
                    textRenderer.draw(textRendererMatrixStack, "ALREADY TAKEN!", x + collum * 13, y + 20 + line * 14, 16121850);
                } else if (this.E2) {
                    textRenderer.draw(textRendererMatrixStack, "ALPHABETIC", x + collum * 13 + 4, y + 25 + line * 12, 16121850);
                    textRenderer.draw(textRendererMatrixStack, "LETTERS ONLY!", x + collum * 13, y + 20 + line * 14, 16121850);
                } else if (this.E3) {
                    textRenderer.draw(textRendererMatrixStack, "ONE OF THE", x + collum * 13 + 7, y + 25 + line * 12, 16121850);
                    textRenderer.draw(textRendererMatrixStack, "FIELDS ARE EMPTY!", x + collum * 13 - 7, y + 20 + line * 14, 16121850);
                }else if (this.E4) {
                    textRenderer.draw(textRendererMatrixStack, "THE NAME IS", x + collum * 13 + 4, y + 25 + line * 12, 16121850);
                    textRenderer.draw(textRendererMatrixStack, "WAY TOO LONG!", x + collum * 13, y + 20 + line * 14, 16121850);
                }
            }
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    private void confirmName(){
        this.firstName = textField1.getText();
        this.lastName = textField2.getText();
        String fullname = this.firstName +" "+ this.lastName;
        if((this.firstName+" "+this.lastName).length()<17){
            if(!this.firstName.equals("") && !this.lastName.equals("")){
                if (this.firstName != null && this.firstName.matches("^[a-zA-Z]*$") && this.lastName != null && this.lastName.matches("^[a-zA-Z]*$")){
                    if(this.allowed_name.equals("true")){
                        this.client.setScreen(new CharCreationScreen2(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extrastat,this.stat_index));
                    }else {this.E4 = false;this.E1=true;this.E2=false;this.E3=false;}
                }else {this.E4 = false;this.E2 = true;this.E3=false;this.E1=false;}
            }else {this.E4 = false;this.E3 = true;this.E2 = false;this.E1=false;}
        }else {this.E4 = true;this.E3 = false;this.E2=false;this.E1=false;}
    }
}
