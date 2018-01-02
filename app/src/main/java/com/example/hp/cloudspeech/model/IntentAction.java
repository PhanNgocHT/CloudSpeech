package com.example.hp.cloudspeech.model;

/**
 * Created by hp on 12/29/2017.
 */

public class IntentAction {
    private String intent;
    private String action;
    private String actionControl;
    private String value;
    private String level;

    public IntentAction(String intent, String action) {
        this.intent = intent;
        this.action = action;
    }

    public IntentAction(String actionControl, String value, String level) {
        this.actionControl = actionControl;
        this.value = value;
        this.level = level;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionControl() {
        return actionControl;
    }

    public void setActionControl(String actionControl) {
        this.actionControl = actionControl;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

