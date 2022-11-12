package net.nikk.dncmod.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.nikk.dncmod.networking.Networking;
import net.nikk.dncmod.screen.CharCreationScreen1;
import net.nikk.dncmod.util.AttributeData;
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
                if(!(client.currentScreen instanceof CharCreationScreen1)) {
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeBoolean(false);
                    ClientPlayNetworking.send(Networking.REFRESH_STATS_ID, buf);
                }
            }
        });
    }
    public static void registerKeyInputs2() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(testkey.wasPressed()) {
                PacketByteBuf bufs = PacketByteBufs.create();
                bufs.writeInt(AttributeData.getIndexOfLargest(((IEntityDataSaver)client.player).getPersistentData().getIntArray("classes")));
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
