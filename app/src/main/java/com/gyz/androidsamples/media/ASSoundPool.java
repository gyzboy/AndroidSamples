package com.gyz.androidsamples.media;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gyz.androidsamples.R;

import java.util.HashMap;

/**
 * Created by guoyizhe on 2017/3/20.
 * 邮箱:gyzboy@126.com
 */

public class ASSoundPool extends Activity implements View.OnClickListener
{
    Button bomb, shot, arrow;
    // 定义一个SoundPool
    SoundPool soundPool;
    HashMap<Integer, Integer> soundMap =
            new HashMap<Integer, Integer>();
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soundpool);
        bomb = (Button) findViewById(R.id.bomb);
        shot = (Button) findViewById(R.id.shot);
        arrow = (Button) findViewById(R.id.arrow);
        // 设置最多可容纳10个音频流，音频的品质为5
        soundPool = new SoundPool(10
                , AudioManager.STREAM_SYSTEM, 5);
        // load方法加载指定音频文件，并返回所加载的音频ID。
        // 此处使用HashMap来管理这些音频流
        soundMap.put(1, soundPool.load(this, R.raw.bomb, 1));
        soundMap.put(2, soundPool.load(this, R.raw.shot, 1));
        soundMap.put(3, soundPool.load(this, R.raw.arrow, 1));
        bomb.setOnClickListener(this);
        shot.setOnClickListener(this);
        arrow.setOnClickListener(this);
    }

    // 重写OnClickListener监听器接口的方法
    @Override
    public void onClick(View source)
    {
        // 判断哪个按钮被单击
        switch (source.getId())
        {
            case R.id.bomb:
                soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1); //不放进map里播放不出声音
                break;
            case R.id.shot:
                soundPool.play(soundMap.get(2), 1, 1, 0, 0, 1);
                break;
            case R.id.arrow:
                soundPool.play(soundMap.get(3), 1, 1, 0, 0, 1);
                break;
        }
    }
}
