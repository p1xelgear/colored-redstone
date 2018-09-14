package pyre.coloredredstone.util;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

public enum EnumColor implements IStringSerializable {
    BLACK(0, "black", "BLACK", Colors.BLACK, TextFormatting.BLACK),
    RED(1, "red", "RED", Colors.RED, TextFormatting.DARK_RED),
    GREEN(2, "green", "GREEN", Colors.GREEN, TextFormatting.DARK_GREEN),
    BROWN(3, "brown", "BROWN", Colors.BROWN, TextFormatting.GOLD),
    BLUE(4, "blue", "BLUE", Colors.BLUE, TextFormatting.DARK_BLUE),
    PURPLE(5, "purple", "PURPLE", Colors.PURPLE, TextFormatting.DARK_PURPLE),
    CYAN(6, "cyan", "CYAN", Colors.CYAN, TextFormatting.DARK_AQUA),
    LIGHT_GRAY(7, "light_gray", "LIGHT GRAY", Colors.LIGHT_GRAY, TextFormatting.GRAY),
    GRAY(8, "gray", "GRAY", Colors.GRAY, TextFormatting.DARK_GRAY),
    PINK(9, "pink", "PINK", Colors.PINK, TextFormatting.LIGHT_PURPLE),
    LIME(10, "lime", "LIME", Colors.LIME, TextFormatting.GREEN),
    YELLOW(11, "yellow", "YELLOW", Colors.YELLOW, TextFormatting.YELLOW),
    LIGHT_BLUE(12, "light_blue", "LIGHT BLUE", Colors.LIGHT_BLUE, TextFormatting.BLUE),
    MAGENTA(13, "magenta", "MAGENTA", Colors.MAGENTA, TextFormatting.AQUA),
    ORANGE(14, "orange", "ORANGE", Colors.ORANGE, TextFormatting.GOLD),
    WHITE(15, "white", "WHITE", Colors.WHITE, TextFormatting.WHITE);

    private static final EnumColor[] META_LOOKUP = new EnumColor[values().length];

    private final int meta;
    private final String name;
    private final String displayName;
    private final Colors.RGBColor[] shades;
    private final TextFormatting chatColor;

    EnumColor(int meta, String name, String displayName, Colors.RGBColor[] shades, TextFormatting chatColor) {
        this.meta = meta;
        this.name = name;
        this.displayName = displayName;
        this.shades = shades;
        this.chatColor = chatColor;
    }

    public static EnumColor byMetadata(int meta)
    {
        if (meta < 0 || meta >= META_LOOKUP.length)
        {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }


    public int getMetadata() {
        return this.meta;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Colors.RGBColor[] getShades() {
        return shades;
    }

    public TextFormatting getChatColor() {
        return chatColor;
    }

    static
    {
        for (EnumColor enumColor : values())
        {
            META_LOOKUP[enumColor.getMetadata()] = enumColor;
        }
    }
}
