package com.android.privacycontrol.conroller;

import android.content.Context;
import android.media.AudioManager;

public class AudioStateManager
{
    AudioManager audioManager;

    public AudioStateManager(Context context)
    {
      audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void changeRingerMode(int ringerMode)
    {
        switch (ringerMode)
        {
            case -1:audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);break;
            case 1:audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);break;
        }
    }

    public int getRingerMode()
    {
        return audioManager.getRingerMode();
    }

    public void resetRingerMode(int ringerMode)
    {
        audioManager.setRingerMode(ringerMode);
    }
}
