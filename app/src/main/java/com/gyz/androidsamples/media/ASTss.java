package com.gyz.androidsamples.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import static android.speech.tts.TextToSpeech.QUEUE_ADD;
import static android.speech.tts.TextToSpeech.QUEUE_FLUSH;

public class ASTss extends Activity implements TextToSpeech.OnInitListener {

    private static final String TAG = ASTss.class.getSimpleName();
    private TextToSpeech mTTS;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //检查TTS数据是否已经安装并且可用
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTTS.shutdown();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            switch (resultCode) {
                case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
                    //这个返回结果表明TTS Engine可以用
                {
                    mTTS = new TextToSpeech(ASTss.this, ASTss.this);
                    Log.v(TAG, "TTS Engine is installed!");

                }

                break;
                case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
                    //需要的语音数据已损坏
                case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
                    //缺少需要语言的语音数据
                case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
                    //缺少需要语言的发音数据
                {
                    //这三种情况都表明数据有错,重新下载安装需要的数据
                    Log.v(TAG, "Need language stuff:" + resultCode);
                    Intent dataIntent = new Intent();
                    dataIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(dataIntent);

                }
                break;
                case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
                    //检查失败
                default:
                    Log.v(TAG, "Got a failure. TTS apparently not available");
                    break;
            }
        } else {
            //其他Intent返回的结果
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTTS.setLanguage(Locale.getDefault());
            // 如果不支持所设置的语言
            if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                    && result != TextToSpeech.LANG_AVAILABLE) {
                Toast.makeText(ASTss.this,
                        "TTS暂时不支持这种语言的朗读！", Toast.LENGTH_SHORT).show();
            }
            mTTS.speak("请向中间缓慢转头", QUEUE_ADD, null);
            mTTS.speak("请向中间缓慢转头", QUEUE_ADD, null);
            mTTS.speak("请向中间缓慢转头", QUEUE_ADD, null);
            mTTS.speak("请向中间缓慢转头", QUEUE_ADD, null);
            mTTS.speak("请向中间缓慢转头", QUEUE_ADD, null);
            mTTS.speak("请向中间缓慢转头", QUEUE_ADD, null);
        }
    }
}
