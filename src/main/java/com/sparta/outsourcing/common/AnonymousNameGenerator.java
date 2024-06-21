package com.sparta.outsourcing.common;

import java.util.UUID;

public class AnonymousNameGenerator {
    public static String nameGenerate() {
        return "유동" + UUID.randomUUID().toString().substring(0, 7);
    }
}
