package me.zheroandre.factioncollectors.utils;

import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String format(double doubleVar) {
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(0);
        return format.format(doubleVar);
    }

    public static String formatLetters(double doubleVar) {
        if (doubleVar < 1000.0D)
            return format(doubleVar);
        if (doubleVar < 1000000.0D)
            return format(doubleVar / 1000.0D) + "K";
        if (doubleVar < 1.0E9D)
            return format(doubleVar / 1000000.0D) + "M";
        if (doubleVar < 1.0E12D)
            return format(doubleVar / 1.0E9D) + "B";
        if (doubleVar < 1.0E15D)
            return format(doubleVar / 1.0E12D) + "T";
        if (doubleVar < 1.0E18D)
            return format(doubleVar / 1.0E15D) + "Q";
        return String.valueOf(doubleVar);
    }

}
