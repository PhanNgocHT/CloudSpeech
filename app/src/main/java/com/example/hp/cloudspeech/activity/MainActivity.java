package com.example.hp.cloudspeech.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.cloudspeech.R;
import com.example.hp.cloudspeech.model.IntentAction;
import com.example.hp.cloudspeech.model.KeySort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Dictionary {

    private static final String TAG = "tag_input";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getData();
    }

    private void initView() {
        txtSpeechInput = (TextView) findViewById(R.id.txt_speech_input);
        btnSpeak = (ImageButton) findViewById(R.id.btn_speak);
        btnSpeak.setOnClickListener(this);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data!=null) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_speak:
                promptSpeechInput();
                break;
            default:
                break;
        }
    }

    private void getData() {
        //String data=txtSpeechInput.getText().toString();
        String data="tắt ti vi điều khiển bóng đèn cho tôi và điều khiển ti vi bật công tắc";
        String dataControl="tăng độ sáng đến bảy mươi phần trăm và giảm độ sáng đến tám mươi phần trăm";
       // parse(data);
       // setContent(data);
        setContentControl(dataControl);
    }

    private HashMap<String, String> parse(String input) {
        HashMap<String, String> hashMap = new HashMap<>();
        ArrayList intent = new ArrayList();
        ArrayList object = new ArrayList();
        for (String a : action) {
            if (input.contains(a)) {
                intent.add(a);
            }
        }
        for (String b : oj) {
            if (input.contains(b)) {
                object.add(b);
            }
        }
        for (int i = 0; i < intent.size(); i++) {
            hashMap.put(intent.get(i).toString(), object.get(i).toString());
        }

        for (String key : hashMap.keySet()) {
            Log.d(TAG, "Intent: "+key+" Object: "+hashMap.get(key));
        }
        return hashMap;
    }

    private ArrayList<IntentAction> setContent(String input) {
        ArrayList<IntentAction> arrContent=new ArrayList<>();
        ArrayList<KeySort> keySortIntent=new ArrayList<>();
        ArrayList<KeySort> keySortObject=new ArrayList<>();
        keySortIntent=setKeySort(action, keySortIntent, input);
        keySortObject=setKeySort(oj, keySortObject, input);
        int minSize=0;
        if (keySortIntent.size()<keySortObject.size()) {
            minSize=keySortIntent.size();
        }else {
            minSize=keySortObject.size();
        }
        for (int i=0; i<minSize; i++) {
            IntentAction intentAction=new IntentAction(keySortIntent.get(i).getValue().toString(), keySortObject.get(i).getValue().toString());
            arrContent.add(intentAction);
            Log.d(TAG, "Intent: "+intentAction.getIntent()+"\tObject: "+intentAction.getAction());
        }
       return arrContent;
    }

    private ArrayList<IntentAction> setContentControl(String input) {
        ArrayList<IntentAction> arrContentControl=new ArrayList<>();
        ArrayList<KeySort> keySortActionControl=new ArrayList<>();
        ArrayList<KeySort> keySortValue=new ArrayList<>();
        ArrayList<KeySort> keySortLevel=new ArrayList<>();
        keySortActionControl=setKeySort(actionControl, keySortActionControl, input);
        keySortValue=setKeySort(value, keySortValue, input);
        keySortLevel=setKeySort(level, keySortLevel, input);
        int minSize=0;
        if (keySortActionControl.size()<keySortValue.size()) {
            minSize=keySortActionControl.size();
        } else {
            minSize=keySortValue.size();
        }
        if (minSize>keySortLevel.size()) {
            minSize=keySortLevel.size();
        }
        for (int i=0; i<minSize; i++) {
            IntentAction intentAction=new IntentAction(keySortActionControl.get(i).getValue(), keySortValue.get(i).getValue(), keySortLevel.get(i).getValue());
            arrContentControl.add(intentAction);
            Log.d(TAG, "ActionControl: "+intentAction.getActionControl()+"\tValue: "+intentAction.getValue()+"\tLevel: "+intentAction.getLevel());
        }
        return arrContentControl;
    }

    private ArrayList<KeySort> setKeySort(String arrContent[], ArrayList<KeySort> keySorts, String input) {
        String contents=input;
        for (int i=0; i<arrContent.length; i++) {
            String content=arrContent[i].toString();
            if (contents.contains(content)) {
                int result=contents.indexOf(content);
                String firstContents=firstContents(result, contents);
                String betweenContents=betweenContentsToSpaceContents(content, result, contents);
                String lastContents=lastContents(content, result, contents);
                contents=firstContents.concat(betweenContents);
                contents=contents.concat(lastContents);
                keySorts.add(new KeySort(result, content));
                i=-1;
            }
        }
        sort(keySorts);
        return keySorts;
    }

    public void sort(ArrayList<KeySort> keySorts) {
        Comparator<KeySort> comparator=new Comparator<KeySort>() {
            @Override
            public int compare(KeySort keySort, KeySort t1) {
                return Integer.compare(keySort.getKey(), t1.getKey());
            }
        };
        Collections.sort(keySorts, comparator);
    }

    private String firstContents(int result, String contents) {
        String firstContent="";
        if (result>0) {
            firstContent=contents.substring(0, result-1);
        }
        return firstContent;
    }

    private String betweenContentsToSpaceContents(String content, int result, String contents) {
        String space="";
        String betweenContent=contents.substring(result,result+content.length()-1);
        for (int j=0; j<betweenContent.length(); j++) {
            space+=" ";
        }
        return space;
    }

    private String lastContents(String content, int result, String contents) {
        String lastContent="";
        if (result<contents.length()-content.length()) {
            lastContent = contents.substring(result + content.length());
        }
        return lastContent;
    }
}
