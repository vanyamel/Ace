package boggle.tts;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Speaker {
    private static final Speaker instance = new Speaker();
    private final Voice voice;
    public boolean enabled;

    public static Speaker getInstance() {
        return instance;
    }

    private Speaker() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        this.voice = VoiceManager.getInstance().getVoice("kevin16");
        this.enabled = false;
    }

    public void init() {
        voice.allocate();
    }

    public void deinit() {
        voice.deallocate();
    }

    public void speak(String str) {
        if (!enabled) return;

        voice.speak(str);
    }
}
