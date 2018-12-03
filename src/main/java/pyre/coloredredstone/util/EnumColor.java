package pyre.coloredredstone.util;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

public enum EnumColor implements IStringSerializable {
    RED(0, "red", "RED", "dyeRed", Colors.RED, TextFormatting.DARK_RED),
    BLACK(1, "black", "BLACK", "dyeBlack", Colors.BLACK, TextFormatting.DARK_GRAY),
    GREEN(2, "green", "GREEN", "dyeGreen", Colors.GREEN, TextFormatting.DARK_GREEN),
    BROWN(3, "brown", "BROWN", "dyeBrown", Colors.BROWN, TextFormatting.GOLD),
    BLUE(4, "blue", "BLUE", "dyeBlue", Colors.BLUE, TextFormatting.BLUE),
    PURPLE(5, "purple", "PURPLE", "dyePurple", Colors.PURPLE, TextFormatting.DARK_PURPLE),
    CYAN(6, "cyan", "CYAN", "dyeCyan", Colors.CYAN, TextFormatting.DARK_AQUA),
    LIGHT_GRAY(7, "light_gray", "LIGHT GRAY", "dyeLightGray", Colors.LIGHT_GRAY, TextFormatting.GRAY),
    GRAY(8, "gray", "GRAY", "dyeGray", Colors.GRAY, TextFormatting.DARK_GRAY),
    PINK(9, "pink", "PINK", "dyePink", Colors.PINK, TextFormatting.LIGHT_PURPLE),
    LIME(10, "lime", "LIME", "dyeLime", Colors.LIME, TextFormatting.GREEN),
    YELLOW(11, "yellow", "YELLOW", "dyeYellow", Colors.YELLOW, TextFormatting.YELLOW),
    LIGHT_BLUE(12, "light_blue", "LIGHT BLUE", "dyeLightBlue", Colors.LIGHT_BLUE, TextFormatting.BLUE),
    MAGENTA(13, "magenta", "MAGENTA", "dyeMagenta", Colors.MAGENTA, TextFormatting.DARK_PURPLE),
    ORANGE(14, "orange", "ORANGE", "dyeOrange", Colors.ORANGE, TextFormatting.GOLD),
    WHITE(15, "white", "WHITE", "dyeWhite", Colors.WHITE, TextFormatting.WHITE);

    private static final EnumColor[] META_LOOKUP = new EnumColor[values().length];

    private final int meta;
    private final String name;
    private final String displayName;
    private final String oreDictionaryDyeEntry;
    private final Colors.RGBColor[] shades;
    private final TextFormatting chatColor;

    EnumColor(int meta, String name, String displayName, String oreDictionaryDyeEntry, Colors.RGBColor[] shades, TextFormatting chatColor) {
        this.meta = meta;
        this.name = name;
        this.displayName = displayName;
        this.oreDictionaryDyeEntry = oreDictionaryDyeEntry;
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

    public static String getNameByMetadata(int meta){
        return byMetadata(meta).getName();
    }


    public int getMetadata() {
        return this.meta;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getOreDictionaryDyeEntry() {
        return oreDictionaryDyeEntry;
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
