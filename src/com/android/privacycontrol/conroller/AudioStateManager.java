package com.android.privacycontrol.conroller;

import android.content.Context;
import android.media.AudioManager;

public class AudioStateManager
{
    public void setPhoneState(Context context, int ringerMode)
    {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        switch (ringerMode)
        {
            case -1:audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);break;
            case 1:audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);break;
        }
    }
}
