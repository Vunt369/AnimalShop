package com.example.petshop.utils;

import java.util.Arrays;

public class KeyHelper {
    public static String generatePalindrome(String[] usernames) {
        Arrays.sort(usernames);
        StringBuilder combined = new StringBuilder();
        for (String username : usernames) {
            combined.append(username).append("-");
        }
        return combined.toString();
    }
}
