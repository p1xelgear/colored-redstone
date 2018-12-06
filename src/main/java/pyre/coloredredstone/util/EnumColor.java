package pyre.coloredredstone.util;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import pyre.coloredredstone.ColoredRedstone;

public enum EnumColor implements IStringSerializable {
    RED(0, "red", Reference.MOD_ID + ".color.red", "dyeRed", Colors.RED, TextFormatting.DARK_RED),
    BLACK(1, "black", Reference.MOD_ID + ".color.black", "dyeBlack", Colors.BLACK, TextFormatting.DARK_GRAY),
    GREEN(2, "green", Reference.MOD_ID + ".color.green", "dyeGreen", Colors.GREEN, TextFormatting.DARK_GREEN),
    BROWN(3, "brown", Reference.MOD_ID + ".color.brown", "dyeBrown", Colors.BROWN, TextFormatting.GOLD),
    BLUE(4, "blue", Reference.MOD_ID + ".color.blue", "dyeBlue", Colors.BLUE, TextFormatting.BLUE),
    PURPLE(5, "purple",Reference.MOD_ID + ".color.purple", "dyePurple", Colors.PURPLE, TextFormatting.DARK_PURPLE),
    CYAN(6, "cyan", Reference.MOD_ID + ".color.cyan", "dyeCyan", Colors.CYAN, TextFormatting.DARK_AQUA),
    LIGHT_GRAY(7, "light_gray", Reference.MOD_ID + ".color.light_gray", "dyeLightGray", Colors.LIGHT_GRAY, TextFormatting.GRAY),
    GRAY(8, "gray", Reference.MOD_ID + ".color.gray", "dyeGray", Colors.GRAY, TextFormatting.DARK_GRAY),
    PINK(9, "pink", Reference.MOD_ID + ".color.pink", "dyePink", Colors.PINK, TextFormatting.LIGHT_PURPLE),
    LIME(10, "lime", Reference.MOD_ID + ".color.lime", "dyeLime", Colors.LIME, TextFormatting.GREEN),
    YELLOW(11, "yellow", Reference.MOD_ID + ".color.yellow", "dyeYellow", Colors.YELLOW, TextFormatting.YELLOW),
    LIGHT_BLUE(12, "light_blue", Reference.MOD_ID + ".color.light_blue", "dyeLightBlue", Colors.LIGHT_BLUE, TextFormatting.BLUE),
    MAGENTA(13, "magenta", Reference.MOD_ID + ".color.magenta", "dyeMagenta", Colors.MAGENTA, TextFormatting.DARK_PURPLE),
    ORANGE(14, "orange", Reference.MOD_ID + ".color.orange", "dyeOrange", Colors.ORANGE, TextFormatting.GOLD),
    WHITE(15, "white", Reference.MOD_ID + ".color.white", "dyeWhite", Colors.WHITE, TextFormatting.WHITE);

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
        return ColoredRedstone.proxy.localize(displayName);
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
