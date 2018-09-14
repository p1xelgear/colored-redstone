package pyre.coloredredstone.util;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

public enum EnumColor implements IStringSerializable {
    BLACK(0, "black", Colors.BLACK, TextFormatting.BLACK),
    RED(1, "red", Colors.RED, TextFormatting.DARK_RED),
    GREEN(2, "green", Colors.GREEN, TextFormatting.DARK_GREEN),
    BROWN(3, "brown", Colors.BROWN, TextFormatting.GOLD),
    BLUE(4, "blue", Colors.BLUE, TextFormatting.DARK_BLUE),
    PURPLE(5, "purple", Colors.PURPLE, TextFormatting.DARK_PURPLE),
    CYAN(6, "cyan", Colors.CYAN, TextFormatting.DARK_AQUA),
    LIGHT_GRAY(7, "light_gray", Colors.LIGHT_GRAY, TextFormatting.GRAY),
    GRAY(8, "gray", Colors.GRAY, TextFormatting.DARK_GRAY),
    PINK(9, "pink", Colors.PINK, TextFormatting.LIGHT_PURPLE),
    LIME(10, "lime", Colors.LIME, TextFormatting.GREEN),
    YELLOW(11, "yellow", Colors.YELLOW, TextFormatting.YELLOW),
    LIGHT_BLUE(12, "light_blue", Colors.LIGHT_BLUE, TextFormatting.BLUE),
    MAGENTA(13, "magenta", Colors.MAGENTA, TextFormatting.AQUA),
    ORANGE(14, "orange", Colors.ORANGE, TextFormatting.GOLD),
    WHITE(15, "white", Colors.WHITE, TextFormatting.WHITE);

    private static final EnumColor[] META_LOOKUP = new EnumColor[values().length];

    private final int meta;
    private final String name;
    private final Colors.RGBColor[] shades;
    private final TextFormatting chatColor;

    EnumColor(int meta, String name, Colors.RGBColor[] shades, TextFormatting chatColor) {
        this.meta = meta;
        this.name = name;
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
