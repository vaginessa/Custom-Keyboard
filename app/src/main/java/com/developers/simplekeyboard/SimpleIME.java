package com.developers.simplekeyboard;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class SimpleIME extends InputMethodService implements KeyboardView.OnKeyboardActionListener{
    private KeyboardView kv;
    private Keyboard keyboard;
    BluetoothSPP bt;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    private boolean caps = false;
    public SimpleIME() {
    }


    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {
        Log.e("IME","OnKey");
        InputConnection ic =   getCurrentInputConnection();
        //playClick(i);

        if(ic != null){
            switch(i){
                case Keyboard.KEYCODE_DELETE :
                    ic.deleteSurroundingText(1, 0);
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    caps = !caps;
                    keyboard.setShifted(caps);
                    kv.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_DONE:
                    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                default:
                    char code = (char)i;
                    if(Character.isLetter(code) && caps){
                        code = Character.toUpperCase(code);
                        Log.d("Bluetooth", "Done 1");
                    }
                    ic.commitText(String.valueOf(code),1);
                    Log.d("Bluetooth", "Done 2");
            }
        }

    }

    @Override
    public void onText(CharSequence charSequence) {
        Log.e("IME","OnText");
        //InputConnection ic = getCurrentInputConnection();
        //ic.commitText(String.valueOf(65),1);
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }
    public void printLine(String message){
        Log.e("IME","It can runnnnnnn");
        onKey(65,null);
    }


    private void playClick(int keyCode){
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(keyCode){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }


}
