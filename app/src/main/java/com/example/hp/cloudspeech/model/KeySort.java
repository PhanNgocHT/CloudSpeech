package com.example.hp.cloudspeech.model;

/**
 * Created by hp on 12/29/2017.
 */

public class KeySort {
    private int key;
    private String value;

    public KeySort(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
