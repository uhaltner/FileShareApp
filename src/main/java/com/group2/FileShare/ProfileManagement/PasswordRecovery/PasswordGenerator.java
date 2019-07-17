package com.group2.FileShare.ProfileManagement.PasswordRecovery;

import java.util.Random;

public class PasswordGenerator implements IPasswordGenerator{

    private static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/";

    public String generate(){

        String result = "";

        result += randomCharacter(ALPHA_CAPS);
        result += randomCharacter(ALPHA);
        result += randomCharacter(NUMERIC);
        result += randomCharacter(SPECIAL_CHARS);
        result += randomCharacter(ALPHA_CAPS);
        result += randomCharacter(ALPHA);
        result += randomCharacter(NUMERIC);
        result += randomCharacter(SPECIAL_CHARS);

        return result;
    }


    private char randomCharacter(String string){
        int i = new Random().nextInt(string.length());
        return string.charAt(i);
    }

}
