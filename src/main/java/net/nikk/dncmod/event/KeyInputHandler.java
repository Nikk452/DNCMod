package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.CharCreationScreen1;
import net.nikk.dncmod.screen.StatScreen1;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DNC = "key.category.dncmod.dnc";
    public static final String KEY_OPEN_MENU = "key.dncmod.openmenu";

    public static KeyBinding menukey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(menukey.wasPressed()) {
                ClientPlayNetworking.send(Networking.REFRESH_STATS_ID, PacketByteBufs.create());
                NbtCompound nbt = ((IEntityDataSaver) client.player).getCharacterData().getCompound("dncmod.chart");
                client.player.sendMessage(Text.literal("Pressed C"+" "+nbt.getBoolean("created"))
                        .fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), false);
                if(!nbt.getBoolean("created")){
                    int[] stats = nbt.getIntArray("stats").length==6?nbt.getIntArray("stats"): new int[]{0, 0, 0, 0, 0, 0};
                    client.setScreen(new CharCreationScreen1("","", "fighter","",stats,0,new int[]{0,1,2,3,4,5}));
                }else{
                    client.setScreen(new StatScreen1());
                }
            }
        });
    }

    public static void register() {
        menukey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_MENU,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                KEY_CATEGORY_DNC
        ));

        registerKeyInputs();
    }
}
