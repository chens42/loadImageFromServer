package com.example.loadimagefromserver.app.common;

public interface HttpConstant {
    public final String BASE_URL = "http://192.168.2.12:8080%s";
    public final String IMAGE_URL = String.format(BASE_URL, "/images/%s");
    public static String URl_Fraction = String.format(BASE_URL, "/assets?page=%d");
}
