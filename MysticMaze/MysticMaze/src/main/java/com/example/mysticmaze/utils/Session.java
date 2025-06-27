// Session.java
package com.example.mysticmaze.utils;

public class Session {
    private static int userId;

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }
}
