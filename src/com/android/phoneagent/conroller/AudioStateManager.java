package com.android.phoneagent.conroller;

import android.content.Context;
import android.media.AudioManager;
import com.android.phoneagent.entities.ContactState;

public class AudioStateManager
{
    AudioManager audioManager;

    public AudioStateManager(Context context)
    {
      audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    public void changeRingerMode(ContactState state)
    {
        switch (state)
        {
            case RESTRICTED:audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);break;
            case FAVOURITE:audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);break;
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
