package de.kevtv.kevin.minecraft_spigot_user_verifier.Helper;

import java.util.Random;

public class Helper {

    public static String getVerifyCode() {

        String capitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String smallChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String values = capitalChars + smallChars + numbers;

        Random random = new Random();

        StringBuilder verifyCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            verifyCode.append(values.charAt(random.nextInt(values.length())));
        }

        return verifyCode.toString();

    }

}
