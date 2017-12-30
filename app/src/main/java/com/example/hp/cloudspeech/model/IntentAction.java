package com.example.hp.cloudspeech.model;

/**
 * Created by hp on 12/29/2017.
 */

public class IntentAction {
    private String intent;
    private String action;

    public IntentAction(String intent, String action) {
        this.intent = intent;
        this.action = action;
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
}

