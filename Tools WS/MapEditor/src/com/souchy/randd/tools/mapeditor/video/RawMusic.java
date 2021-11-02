package com.souchy.randd.tools.mapeditor.video;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.audio.OpenALMusic;
import com.badlogic.gdx.video.VideoDecoder;
import com.badlogic.gdx.backends.lwjgl3.audio.OpenALLwjgl3Audio;

public class RawMusic extends OpenALMusic {

    private final VideoDecoder decoder;
    private final ByteBuffer backBuffer;

    public RawMusic(VideoDecoder decoder, ByteBuffer buffer, int channels, int sampleRate) {
        //super((OpenALAudio) Gdx.audio, null);
    	super((OpenALLwjgl3Audio) Gdx.audio, null);

        this.decoder = decoder;
        this.backBuffer = buffer;

        setup(channels, sampleRate);
    }

    @Override
    public int read(byte[] buffer) {
        int sizeNeeded = buffer.length;
        int currentIndex = 0;

        while (sizeNeeded > 0) {
            if (backBuffer.remaining() > 0) {
                int numBytes = Math.min(backBuffer.remaining(), sizeNeeded);
                backBuffer.get(buffer, currentIndex, numBytes);
                currentIndex += numBytes;
                sizeNeeded -= numBytes;
            } else {
                // We need to fill the buffer;
                backBuffer.rewind();
                decoder.updateAudioBuffer();
            }
        }

        return buffer.length;
    }

    @Override
    public void reset() {
    }
    
}