package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.networking.packet.ExampleS2CPacket;
import net.nikk.dncmod.screen.CharCreationScreen1;
import net.nikk.dncmod.screen.StatScreen1;
import net.nikk.dncmod.util.IEntityDataSaver;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DNC = "key.category.dncmod.dnc";
    public static final String KEY_OPEN_MENU = "key.dncmod.openmenu";
    public static final String KEY_TEST = "key.dncmod.test";


    public static KeyBinding menukey;
    public static KeyBinding testkey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(menukey.wasPressed()) {
                if(!(client.currentScreen instanceof CharCreationScreen1)) ClientPlayNetworking.send(Networking.REFRESH_STATS_ID, PacketByteBufs.create());
            }
        });
    }
    public static void registerKeyInputs2() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(testkey.wasPressed()) {
                PacketByteBuf bufs = PacketByteBufs.create();
                bufs.writeInt(5);
                ClientPlayNetworking.send(Networking.EXAMPLE_C2S, bufs);
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
        testkey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TEST,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                KEY_CATEGORY_DNC
        ));
        registerKeyInputs2();
    }
}
