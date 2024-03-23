package com.example.springkmsmybatis3.model;

public class HashedString {

    String str;

    private HashedString(String str) { this.str = str; }

    public static HashedString to(String str) { return new HashedString(str); }

    @Override
    public String toString() {
        return str;
    }
}
