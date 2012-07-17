package com.android.privacycontrol.conroller;

import android.content.Context;
import android.media.AudioManager;

public class AudioStateManager
{
    public void setPhoneState(Context context, int ringerMode)
    {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }
}
