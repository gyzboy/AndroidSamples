package com.gyz.androidopensamples.changeskin.view;

/**
 * Created by geminiwen on 16/6/15.
 */
public interface Skinnable {
    /**
     * called by activity when UiMode changed
     */
    void applyDayNight();

    boolean isSkinnable();

    void setSkinnable(boolean skinnable);
}
