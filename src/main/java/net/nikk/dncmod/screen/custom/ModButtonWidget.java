package net.nikk.dncmod.screen.custom;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ModButtonWidget extends ButtonWidget {
    public ModButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION_SUPPLIER);
    }
}
