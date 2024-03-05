package com.izakaya.website.util;

import java.util.Random;

public class TempKey {

    public String getKey(int size, boolean lowerCheck) {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        do {
            int num = rnd.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            }
        } while (sb.length() < size);

        return lowerCheck ? sb.toString().toLowerCase() : sb.toString();
    }
}