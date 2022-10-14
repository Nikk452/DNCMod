package net.nikk.dncmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.nikk.dncmod.DNCMod;
import net.nikk.dncmod.networking.Networking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CharCreationScreen5 extends Screen {
    final private String race;
    final private String firstName;
    private TexturedButtonWidget error_window;
    private boolean E1 = false;
    final private String lastName;
    final private String classname;
    private final int[] stats;
    private int skillsnum = 0;
    private int[] allskills = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    private String[] skillnames = {"Empty", "Empty", "Empty", "Empty"};
    private int[] skills = {-1,-1,-1,-1};
    private int[] skilltype = {-1,-1,-1,-1};
    private int[] skillcount = {0,0,0,0,0,0};
    private boolean complete= false;
    private final int[] stat_index;
    private Boolean allSlotsTaken = false;
    int extrastat;
    private TexturedButtonWidget complete_window;
    private ButtonWidget complete_button;
    private ArrayList<TexturedButtonWidget> skillsbuttons = new ArrayList<TexturedButtonWidget>();
    private ButtonWidget slot1;
    private ButtonWidget slot2;
    private ButtonWidget slot3;
    private ButtonWidget slot4;
    private ButtonWidget[] slots = {this.slot1,this.slot2,this.slot3,this.slot4};
    private ButtonWidget next_button;
    private ButtonWidget previous_button;

    protected CharCreationScreen5(String name1, String name2, String classname, String race, int[] stats,int extrastat,int[] stat_index) {
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
        this.next_button = new ButtonWidget(width/2+85, height/2+70, 75, 20, Text.literal("Next Page"), (button) -> {
            switch (this.classname) {
                case "Fighter" -> this.E1 = this.skillcount[0] <= 0 || this.skillcount[1] <= 0 || !this.allSlotsTaken;
                case "Wizard" -> this.E1 = this.skillcount[3] <= 1 || this.skillcount[4] <= 0 || !this.allSlotsTaken;
                case "Druid" ->
                        this.E1 = this.skillcount[0] <= 0 || this.skillcount[1] <= 0 || this.skillcount[4] <= 0 || !this.allSlotsTaken;
                case "Cleric" -> this.E1 = this.skillcount[4] <= 1 || this.skillcount[3] <= 0 || !this.allSlotsTaken;
                case "Sorcerer" -> this.E1 = this.skillcount[5] <= 0 || this.skillcount[4] <= 0 || !this.allSlotsTaken;
                case "Monk" -> this.E1 = this.skillcount[4] <= 1 || this.skillcount[1] <= 0 || !this.allSlotsTaken;
            }
            if(!E1) {
                this.complete = true;
            }});
        this.addDrawableChild(this.next_button);
        this.previous_button = new ButtonWidget(width/2-158, height/2+70, 75, 20, Text.literal("Previous Page"), (button) -> {
            this.client.setScreen(new CharCreationScreen4(this.firstName,this.lastName,this.classname,this.race,this.stats,this.extrastat,this.stat_index));});
        this.addDrawableChild(this.previous_button);
        //4 slots
        this.slots[0] = new ButtonWidget(x+collum*4-4, y+23+line*11/2+line, 75, 15, Text.literal("Empty"), (button) -> {
            this.emptySlot(0);});
        this.slots[1] = new ButtonWidget(x+collum*4-4, y+23+line*16/2+line, 75, 15, Text.literal("Empty"), (button) -> {
            this.emptySlot(1);});
        this.slots[2] = new ButtonWidget(x+collum*4-4, y+23+line*21/2+line, 75, 15, Text.literal("Empty"), (button) -> {
            this.emptySlot(2);});
        this.slots[3] = new ButtonWidget(x+collum*4-4, y+23+line*26/2+line, 75, 15, Text.literal("Empty"), (button) -> {
            this.emptySlot(3);});
        this.addDrawableChild(this.slots[0]);
        this.addDrawableChild(this.slots[1]);
        this.addDrawableChild(this.slots[2]);
        this.addDrawableChild(this.slots[3]);
        this.slots[0].active = false;
        this.slots[1].active = false;
        this.slots[2].active = false;
        this.slots[3].active = false;
        int[] tx = {12,14,16,12,14,16,18,20,12,14,12,14,16,18,20,22,12,14,16,18,20,12,14,16,18,20};
        int[] ts = {0,0,0,1,1,1,1,1,2,2,3,3,3,3,3,3,4,4,4,4,4,5,5,5,5,5};
        int[] ty = {20,20,20,30,30,30,30,30,40,40,50,50,50,50,50,50,60,60,60,60,60,70,70,70,70,70};
        String[] ids = {"grapleskill","mineskill","jumpskill","acrobaticsskill","stealthskill","stealingskill","lockpickskill","craftingskill","concentrationskill","enduranceskill","lore_arcaneskill","lore_divineskill","lore_primalskill","investigationskill","medicineskill","craftingknowledgeskill","healskill",
        "perceptionskill","appraiseskill","insightskill","meditationskill","magicaldeviceskill","persuasionskill","deceptionskill","intimidationskill","performanceskill"};
        String[] names = {"skills.dncmod.grapple","skills.dncmod.mine","skills.dncmod.jump","skills.dncmod.acrobatics","skills.dncmod.stealth","skills.dncmod.stealing","skills.dncmod.lockpick","skills.dncmod.crafting","skills.dncmod.concentration","skills.dncmod.endurance","skills.dncmod.lore_arcane",
                "skills.dncmod.lore_divine","skills.dncmod.lore_primal","skills.dncmod.investigation","skills.dncmod.medicine","skills.dncmod.crafting_knowledge","skills.dncmod.heal","skills.dncmod.perception","skills.dncmod.appraise","skills.dncmod.insight","skills.dncmod.meditation",
                "skills.dncmod.magical_device","skills.dncmod.persuasion","skills.dncmod.deception","skills.dncmod.intimidation","skills.dncmod.performance"};
        for(int i = 0;i<26;i++){
            int idx = i;
            int s_type = ts[i];
            String s_name = names[i];
            this.skillsbuttons.add(new TexturedButtonWidget(x+collum*tx[i]-4,y+23+line*ty[i]/4+line,16,16,0,0,16,new Identifier(DNCMod.MOD_ID,"textures/gui/"+ids[i]+".png"),16,16,(button) -> this.selectSlot(Text.translatable(s_name).toString(),idx,s_type)));
            this.addDrawableChild(this.skillsbuttons.get(i));
        }
        this.error_window = new TexturedButtonWidget(x+collum*8+4, y+20+line*3,194,160,0,0,0,new Identifier(DNCMod.MOD_ID,"textures/gui/uialarm.png"),194,160,(button) -> {
            this.error_window.active = false;
            this.E1 = false;
            this.next_button.active = true;
            this.previous_button.active = true;
            for(int i = 0;i<26;i++) this.skillsbuttons.get(i).active = true;
        });
        this.addDrawableChild(this.error_window);
        this.complete_button = new ButtonWidget(x -12 + collum * 14, y + 22 + line * 24, 75, 20, Text.literal("Complete"), (button) -> {
            NbtCompound nbt = new NbtCompound();
            nbt.putString("first_name",this.firstName);
            nbt.putString("last_name",this.lastName);
            nbt.putString("race",this.race);
            nbt.putIntArray("index",this.stat_index);
            nbt.putInt("extra_stat",this.extrastat);
            nbt.putString("class_name",this.classname);
            nbt.putIntArray("skills",this.skills);
            ClientPlayNetworking.send(Networking.CREATION_ID, PacketByteBufs.create().writeNbt(nbt));
            client.setScreen((Screen)null);
        });
        this.addDrawableChild(this.complete_button);
        this.complete_window = new TexturedButtonWidget(x+collum*8+4, y+line*1,194,260,0,0,0,new Identifier(DNCMod.MOD_ID,"textures/gui/uifrag.png"),194,260,(button) -> {
            this.complete = false;
            this.complete_window.active = false;
            this.next_button.active = true;
            this.previous_button.active = true;
            for(int i = 0;i<4;i++) this.slots[i].active = true;
            for(int i = 0;i<26;i++) this.skillsbuttons.get(i).active = true;
        });
        this.addDrawableChild(this.complete_window);

    }
    private void selectSlot(String name,int index,int type){
        for(int i=0;i<4;i++){
            if(this.skills[i] == -1){
                this.skillsnum += 1;
                this.skillnames[i] = name;
                this.slots[i].setMessage(Text.literal(this.skillnames[i]));
                this.slots[i].active = true;
                this.skills[i] = index;
                this.allskills[index] = 0;
                this.skillsbuttons.get(i).active = false;
                this.skilltype[i] = type;
                if(skillsnum==4)
                    this.allSlotsTaken= true;
                this.skillcount[type] += 1;
                break;
            }
        }
    }
    private void emptySlot(int index) {
        this.skillcount[this.skilltype[index]] -= 1;
        this.skillsbuttons.get(this.skills[index]).active = true;
        this.allskills[this.skills[index]] = -1;
        this.skills[index] = -1;
        this.skillnames[index] = "empty";
        this.slots[index].setMessage(Text.literal(this.skillnames[index]));
        this.slots[index].active = false;
        this.skillsnum -= 1;
        this.allSlotsTaken= false;
    }
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int backgroundWidth = 412;
        int backgroundHeight = 256;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        int line = backgroundHeight/30;
        int collum = backgroundWidth/30;
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
        this.previous_button.render(matrices,mouseX,mouseY,delta);
        this.next_button.render(matrices,mouseX,mouseY,delta);
        for(int i = 0;i<4;i++) this.slots[i].render(matrices,mouseX,mouseY,delta);
        for(int i = 0;i<26;i++) this.skillsbuttons.get(i).render(matrices,mouseX,mouseY,delta);
        //drawTexture(matrices, LocationX, LocationY, Z?... 1 , u is 0, v is 0,ActualImageWidth,ActualImageHeight,texturewidthScaled,textureheightScaled);
        //text drawing
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        MatrixStack textRendererMatrixStack = new MatrixStack();
        textRendererMatrixStack.scale(1.0F, 1.0F, 1.0F);
        textRenderer.draw(textRendererMatrixStack, "Character Creation", x+collum*12, y+20+line*2/3, 	15859709);
        textRenderer.draw(textRendererMatrixStack, Text.literal("Select your future skills:").styled(style -> style.withBold(false).withItalic(false)).append("").styled(style -> style.withBold(true).withItalic(true)).append("").append("").styled(style -> style.withBold(true).withItalic(true)).append(" "), x+collum*4, y+20+line*2+line, 	15859709);
        switch (this.classname) {
            case "Fighter" ->
                    textRenderer.draw(textRendererMatrixStack, Text.literal("(1 Strength, 1 Dexterity, 2 of any)"), x + collum * 4, y + 20 + line * 4 + line - 3, 15859709);
            case "Wizard" ->
                    textRenderer.draw(textRendererMatrixStack, Text.literal("(2 Intelligence, 1 Wisdom, 1 of any)"), x + collum * 4, y + 20 + line * 4 + line - 3, 15859709);
            case "Druid" ->
                    textRenderer.draw(textRendererMatrixStack, Text.literal("(1 Strength, 1 Dexterity, 1 Wisdom, 1 of any)"), x + collum * 4, y + 20 + line * 4 + line - 3, 15859709);
            case "Cleric" ->
                    textRenderer.draw(textRendererMatrixStack, Text.literal("(2 Wisdom, 1 Intelligence, 1 of any)"), x + collum * 4, y + 20 + line * 4 + line - 3, 15859709);
            case "Sorcerer" ->
                    textRenderer.draw(textRendererMatrixStack, Text.literal("(2 Charisma, 1 Wisdom, 1 of any)"), x + collum * 4, y + 20 + line * 4 + line - 3, 15859709);
            case "Monk" ->
                    textRenderer.draw(textRendererMatrixStack, Text.literal("(2 Wisdom, 1 Dexterity, 1 of any)"), x + collum * 4, y + 20 + line * 4 + line - 3, 15859709);
        }
        RenderSystem.setShaderColor(1.00f, 1.00f, 1.00f, 1.00f);

        List<Text> arr = List.of(new Text[]{Text.literal("[Strength] Skill: Grapple".formatted(Formatting.RED)), Text.literal("Grappling is a game mechanic that"),Text.literal("essentially means grabbing a dude")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(0).active,mouseX,mouseY,this.skillsbuttons.get(0).x,this.skillsbuttons.get(0).y,arr);
        arr = List.of(new Text[]{Text.literal("[Strength] Skill: Mine".formatted(Formatting.RED)), Text.literal("Mine lets you mine"),Text.literal("faster and more effective")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(1).active,mouseX,mouseY,this.skillsbuttons.get(1).x,this.skillsbuttons.get(1).y,arr);
        arr = List.of(new Text[]{Text.literal("[Strength] Skill: Jump".formatted(Formatting.RED)), Text.literal("Jump allows you to jump further"),Text.literal("and higher using your strength")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(2).active,mouseX,mouseY,this.skillsbuttons.get(2).x,this.skillsbuttons.get(2).y,arr);
        arr = List.of(new Text[]{Text.literal("[Dexterity] Skill: Acrobatics".formatted(Formatting.RED)), Text.literal("Acrobatics covers your attempt to stay"),Text.literal("on your feet in a tricky situation")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(3).active,mouseX,mouseY,this.skillsbuttons.get(3).x,this.skillsbuttons.get(3).y,arr);
        arr = List.of(new Text[]{Text.literal("[Dexterity] Skill: Stealth".formatted(Formatting.RED)), Text.literal("Stealth lets you conceal yourself from "),Text.literal("enemies, move unnoticed or spy on someone")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(4).active,mouseX,mouseY,this.skillsbuttons.get(4).x,this.skillsbuttons.get(4).y,arr);
        arr = List.of(new Text[]{Text.literal("[Dexterity] Skill: Stealing".formatted(Formatting.RED)), Text.literal("By using the stealing skill you will be able to"),Text.literal("take an item from another players inventory")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(5).active,mouseX,mouseY,this.skillsbuttons.get(5).x,this.skillsbuttons.get(5).y,arr);
        arr = List.of(new Text[]{Text.literal("[Dexterity] Skill: Lock Pick".formatted(Formatting.RED)), Text.literal("Lets you crack open locks on doors, chests"),Text.literal("vaults and etc based on their material")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(6).active,mouseX,mouseY,this.skillsbuttons.get(6).x,this.skillsbuttons.get(6).y,arr);
        arr = List.of(new Text[]{Text.literal("[Dexterity] Skill: Crafting".formatted(Formatting.RED)), Text.literal("Crafting is one of the two craft"),Text.literal("based skills that are used")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(7).active,mouseX,mouseY,this.skillsbuttons.get(7).x,this.skillsbuttons.get(7).y,arr);
        arr = List.of(new Text[]{Text.literal("[Constitution] Skill: Concentration".formatted(Formatting.RED)), Text.literal("Concentration is used when a player takes damage"),Text.literal("and they have a concentration spell or ability active")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(8).active,mouseX,mouseY,this.skillsbuttons.get(8).x,this.skillsbuttons.get(8).y,arr);
        arr = List.of(new Text[]{Text.literal("[Constitution] Skill: Endurance".formatted(Formatting.RED)), Text.literal("Make an Endurance check to stave off ill effects and"),Text.literal("to push yourself beyond normal physical limits")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(9).active,mouseX,mouseY,this.skillsbuttons.get(9).x,this.skillsbuttons.get(9).y,arr);
        arr = List.of(new Text[]{Text.literal("[Intelligence] Skill: Lore Arcane".formatted(Formatting.RED)), Text.literal("Arcana measures your ability to recall lore"),Text.literal("about spells, magic items, eldritch symbols etc")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(10).active,mouseX,mouseY,this.skillsbuttons.get(10).x,this.skillsbuttons.get(10).y,arr);
        arr = List.of(new Text[]{Text.literal("[Intelligence] Skill: Lore Divine".formatted(Formatting.RED)), Text.literal("Divine")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(11).active,mouseX,mouseY,this.skillsbuttons.get(11).x,this.skillsbuttons.get(11).y,arr);
        arr = List.of(new Text[]{Text.literal("[Intelligence] Skill: Lore Primal".formatted(Formatting.RED)), Text.literal("Primal")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(12).active,mouseX,mouseY,this.skillsbuttons.get(12).x,this.skillsbuttons.get(12).y,arr);
        arr = List.of(new Text[]{Text.literal("[Intelligence] Skill: Investigation".formatted(Formatting.RED)), Text.literal("When you look around for clues and"),Text.literal("make deductions based on those clues")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(13).active,mouseX,mouseY,this.skillsbuttons.get(13).x,this.skillsbuttons.get(13).y,arr);
        arr = List.of(new Text[]{Text.literal("[Intelligence] Skill: Medicine".formatted(Formatting.RED)), Text.literal("Medicine lets you try to stabilize a"),Text.literal("dying companion or diagnose an illness")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(14).active,mouseX,mouseY,this.skillsbuttons.get(14).x,this.skillsbuttons.get(14).y,arr);
        arr = List.of(new Text[]{Text.literal("[Intelligence] Skill: Craft Knowledge".formatted(Formatting.RED)), Text.literal("Craft Knowledge is one of the two"),Text.literal("craft based skills that are used")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(15).active,mouseX,mouseY,this.skillsbuttons.get(15).x,this.skillsbuttons.get(15).y,arr);
        arr = List.of(new Text[]{Text.literal("[Wisdom] Skill: Heal".formatted(Formatting.RED)), Text.literal("Heal is a skill that lets you heal"),Text.literal("someone when they are injured")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(16).active,mouseX,mouseY,this.skillsbuttons.get(16).x,this.skillsbuttons.get(16).y,arr);
        arr = List.of(new Text[]{Text.literal("[Wisdom] Skill: Perception".formatted(Formatting.RED)), Text.literal("Your Perception lets you spot, hear, or"),Text.literal("otherwise detect the presence of something")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(17).active,mouseX,mouseY,this.skillsbuttons.get(17).x,this.skillsbuttons.get(17).y,arr);
        arr = List.of(new Text[]{Text.literal("[Wisdom] Skill: Appraise".formatted(Formatting.RED)), Text.literal("Appraise lets you to identify"),Text.literal("the real value of an object")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(18).active,mouseX,mouseY,this.skillsbuttons.get(18).x,this.skillsbuttons.get(18).y,arr);
        arr = List.of(new Text[]{Text.literal("[Wisdom] Skill: Insight".formatted(Formatting.RED)), Text.literal("Insight is the ability to determine"),Text.literal("the true intentions of a creature"),Text.literal("such as when searching out a lie"),Text.literal("or predicting someone’s next move")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(19).active,mouseX,mouseY,this.skillsbuttons.get(19).x,this.skillsbuttons.get(19).y,arr);
        arr = List.of(new Text[]{Text.literal("[Wisdom] Skill: Meditation".formatted(Formatting.RED)), Text.literal("You can gather your mind to become"),Text.literal("more accurate and focused and"),Text.literal("increase your knowledge")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(20).active,mouseX,mouseY,this.skillsbuttons.get(20).x,this.skillsbuttons.get(20).y,arr);
        arr = List.of(new Text[]{Text.literal("[Charisma] Skill: Use Magical Device".formatted(Formatting.RED)), Text.literal("Lets you use various magical"),Text.literal("devices depending on your skill")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(21).active,mouseX,mouseY,this.skillsbuttons.get(21).x,this.skillsbuttons.get(21).y,arr);
        arr = List.of(new Text[]{Text.literal("[Charisma] Skill: Persuasion".formatted(Formatting.RED)), Text.literal("When you attempt to influence"),Text.literal("someone or a group of people with"),Text.literal("tact, social graces, or good nature")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(22).active,mouseX,mouseY,this.skillsbuttons.get(22).x,this.skillsbuttons.get(22).y,arr);
        arr = List.of(new Text[]{Text.literal("[Charisma] Skill: Deception".formatted(Formatting.RED)), Text.literal("Deception lets you"),Text.literal("convincingly hide the truth")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(23).active,mouseX,mouseY,this.skillsbuttons.get(23).x,this.skillsbuttons.get(23).y,arr);
        arr = List.of(new Text[]{Text.literal("[Charisma] Skill: Intimidation".formatted(Formatting.RED)),Text.literal("When you attempt to influence"),Text.literal("someone through overt threats,"),Text.literal("hostile actions, and physical violence")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(24).active,mouseX,mouseY,this.skillsbuttons.get(24).x,this.skillsbuttons.get(24).y,arr);
        arr = List.of(new Text[]{Text.literal("[Charisma] Skill: Performance".formatted(Formatting.RED)), Text.literal("Performance determines how well"),Text.literal("you can delight an audience with"),Text.literal("music, dance, acting, storytelling"),Text.literal("or some other form of entertainment")});
        renderSkillTooltips(matrices,this.skillsbuttons.get(25).active,mouseX,mouseY,this.skillsbuttons.get(25).x,this.skillsbuttons.get(25).y,arr);
        RenderSystem.setShaderColor(1.00f, 1.00f, 1.00f, 1.00f);
        if(this.E1){
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            this.next_button.active = false;
            this.previous_button.active = false;
            for(int i = 0;i<26;i++) this.skillsbuttons.get(i).active = false;
            this.error_window.render(matrices,mouseX,mouseY,delta);
            this.error_window.active = true;
            textRenderer.draw(textRendererMatrixStack, Text.literal("ERROR!").formatted(Formatting.BOLD), x + collum * 14 + 5, y + 28 + line * 8, 16121850);
            textRenderer.draw(textRendererMatrixStack, "WRONG SKILLS", x + collum * 13+4, y + 25 + line * 12, 16121850);textRenderer.draw(textRendererMatrixStack, "FOR YOUR CLASS", x + collum * 13-4, y + 20 + line * 14, 16121850);
        }
        if(this.complete){
            RenderSystem.setShaderColor(0.90f, 0.90f, 0.90f, 0.90f);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            this.next_button.active = false;
            this.previous_button.active = false;
            for(int i = 0;i<26;i++) this.skillsbuttons.get(i).active = false;
            for(int i = 0;i<4;i++) this.slots[i].active = false;
            this.complete_window.render(matrices,mouseX,mouseY,delta);
            this.complete_button.render(matrices,mouseX,mouseY,delta);
            this.complete_window.active = true;
            String[] extravalue = {"","","","","","",""};
            extravalue[this.extrastat+1] = Objects.equals(this.race, "Elf") ? " +2":"";
            textRenderer.draw(textRendererMatrixStack, Text.literal("Complete?").formatted(Formatting.BOLD), x + collum * 14-6, y + 24 + line, 16121850);
            textRenderer.draw(textRendererMatrixStack, "[Name: "+this.firstName+" "+this.lastName+"]", x + collum * 10 +6, y + 25 + line * 3, 16121850);
            textRenderer.draw(textRendererMatrixStack, "["+this.race+" "+this.classname+" Lv.0]", x + collum * 10 +6, y + 20 + line * 6, 16121850);
            textRenderer.draw(textRendererMatrixStack, Text.literal("Strength- ").append(String.valueOf(this.stats[this.stat_index[0]])).append(extravalue[1]), x + collum * 10 +6, y + 15 + line * 9, 16121850);
            textRenderer.draw(textRendererMatrixStack, Text.literal("Dexterity- ").append(String.valueOf(this.stats[this.stat_index[1]])).append(extravalue[2]), x + collum * 10 +6, y + 10 + line * 11, 16121850);
            textRenderer.draw(textRendererMatrixStack, Text.literal("Constitution- ").append(String.valueOf(this.stats[this.stat_index[2]])).append(extravalue[3]), x + collum * 10 +6, y + 5 + line * 13, 16121850);
            textRenderer.draw(textRendererMatrixStack, Text.literal("Intelligence- ").append(String.valueOf(this.stats[this.stat_index[3]])).append(extravalue[4]), x + collum * 10 +6, y + line * 15, 16121850);
            textRenderer.draw(textRendererMatrixStack, Text.literal("Wisdom- ").append(String.valueOf(this.stats[this.stat_index[4]])).append(extravalue[5]), x + collum * 10 +6, y -5 + line * 17, 16121850);
            textRenderer.draw(textRendererMatrixStack, Text.literal("Charisma- ").append(String.valueOf(this.stats[this.stat_index[5]])).append(extravalue[6]), x + collum * 10 +6, y -10 + line * 19, 16121850);
            textRenderer.draw(textRendererMatrixStack, "["+this.skillnames[0]+", "+this.skillnames[1]+", ", x + collum * 10 +6, y - 15 + line * 22, 16121850);
            textRenderer.draw(textRendererMatrixStack, this.skillnames[2]+", "+this.skillnames[3]+"]", x + collum * 10 +6, y - 20 + line * 24, 16121850);
        }
    }
    private void renderSkillTooltips(MatrixStack matrices,Boolean active,int mouseX,int mouseY, int x, int y, List<Text> arr){
        if(!this.allSlotsTaken && active){
            if(mouseX >= x && mouseX< x +15 && mouseY >= y && mouseY<y+15){
                renderTooltip(matrices, arr,x,y);

            }
        }
    }
    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
}
