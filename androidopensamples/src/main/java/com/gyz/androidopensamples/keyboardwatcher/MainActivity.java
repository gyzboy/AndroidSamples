package com.gyz.androidopensamples.keyboardwatcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gyz.androidopensamples.R;

public class MainActivity extends AppCompatActivity implements KeyboardWatcher.OnKeyboardToggleListener {
    private TextView textView;
    private KeyboardWatcher keyboardWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboardwatcher_main);
        textView = (TextView) findViewById(R.id.textView);
        keyboardWatcher = new KeyboardWatcher(this);
        keyboardWatcher.setListener(this);
    }

    @Override
    protected void onDestroy() {
        keyboardWatcher.destroy();
        super.onDestroy();
    }

    @Override
    public void onKeyboardShown(int keyboardSize) {
        textView.setText(String.format("Shown\nkeyboard size: %dpx", keyboardSize));
    }

    @Override
    public void onKeyboardClosed() {
        textView.setText("Closed");
    }
}