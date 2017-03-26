package jgfe.gfx;

import java.io.BufferedInputStream;
import java.io.InputStream;

import javax.sound.sampled.*;

import jgfe.util.Resource;

public class SoundClip extends Resource{
    private Clip clip;
    private FloatControl gainControl;

    public SoundClip(String path){
	load(path);
    }

    public void play(){
	if(clip == null) return;
	stop();
	clip.setFramePosition(0);
	while(!clip.isRunning()){
	    clip.start();
	}
    }

    public void stop(){
	if(clip.isRunning()){
	    clip.stop();
	}
    }

    public void close(){
	stop();
	clip.drain();
	clip.close();
    }

    public void loop(){
	clip.loop(Clip.LOOP_CONTINUOUSLY);
	while(!clip.isRunning()){
	    clip.start();
	}
    }

    public void setVolume(float value){
	gainControl.setValue(value);
    }

    @Override
    public Resource load(String path){
	try{
	    InputStream audioSrs = getClass().getResourceAsStream(path);
	    InputStream bufferedIn = new BufferedInputStream(audioSrs);
	    AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
	    AudioFormat baseFormat = ais.getFormat();
	    AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
		    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
	    AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);

	    clip = AudioSystem.getClip();
	    clip.open(dais);

	    gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);

	}
	catch(Exception e){
	    e.printStackTrace();
	}
	
	return this;
    }
}
