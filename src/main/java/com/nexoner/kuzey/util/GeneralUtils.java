package com.nexoner.kuzey.util;

import java.text.NumberFormat;

public class GeneralUtils {
    public static boolean isMouseOver(double mouseX, double mouseY, int x, int y, int sizeX, int sizeY) {return (mouseX >= x && mouseX <= x + sizeX) && (mouseY >= y && mouseY <= y + sizeY);}

    public static String formatEnergyText(int energy, int maxEnergy){
        String formattedText = NumberFormat.getIntegerInstance().format(energy) + " / " + NumberFormat.getIntegerInstance().format(maxEnergy) + " FE";
        return formattedText;
    }

    public static int boolToInt(boolean bool){return bool ? 1:0;}
    public static boolean intToBool(int integer){return integer > 0;}
}