package com.reactnativesuperpowered;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Build;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.IOException;

public class SuperpoweredModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
  // ReactContextBaseJavaModule
  SuperpoweredModule(final ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() { return "SuperpoweredModule"; }

  // LifecycleEventListener
  @Override
  public void onHostResume() {

  }

  @Override
  public void onHostPause() {

  }

  @Override
  public void onHostDestroy() {
  }

  // React Methods
  @ReactMethod
  public void init(Promise promise) {
    // Get the device's sample rate and buffer size to enable low-latency Android audio output, if available.
    String sampleRateString = null;
    String bufferSizeString = null;

    if (Build.VERSION.SDK_INT >= 17) {
      AudioManager audioManager = (AudioManager) getReactApplicationContext().getSystemService(Context.AUDIO_SERVICE);
      sampleRateString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE);
      bufferSizeString = audioManager.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER);
    }
    if (sampleRateString == null) sampleRateString = "44100";
    if (bufferSizeString == null) bufferSizeString = "512";

    // Files under res/raw are not zipped, just copied into the APK. Get the offset and length to know where our files are located.
    int[] file1 = getOffsetAndLength(R.raw.lycka);
    int[] file2 = getOffsetAndLength(R.raw.nuyorica);

    Superpowered(Integer.parseInt(sampleRateString), Integer.parseInt(bufferSizeString),
            getReactApplicationContext().getPackageResourcePath(),
            file1[0], file1[1], file2[0], file2[1]
    );

    promise.resolve("true");
  }

  @ReactMethod
  public void playPause(Promise promise) {
    onPlayPause();
    promise.resolve("true");
  }

  @ReactMethod
  public void fxValue(Integer activeFx, Float value, Promise promise) {
    onFxValue(activeFx, value);
    promise.resolve(null);
  }

  @ReactMethod
  public void crossFader(Float value, Promise promise) {
    onCrossfader(value);
    promise.resolve(null);
  }

  private int[] getOffsetAndLength (int id) {
    Resources resources = getReactApplicationContext().getResources();
    AssetFileDescriptor fd = resources.openRawResourceFd(id);
    int offset = (int) fd.getStartOffset();
    int length = (int) fd.getLength();
    try {
      fd.getParcelFileDescriptor().close();
    } catch (IOException e) {
      android.util.Log.d("", "Close error.");
    }

    return new int[] { offset, length };
  }

  // JNI
  private native void Superpowered(int samplerate, int buffersize, String apkPath, int file1Offset, int file1Length, int file2Offset, int file2Length);
  private native void onPlayPause();
  private native void onFxValue(int activeFx, float value);
  private native void onCrossfader(float value);

  static {
    System.loadLibrary("Superpowered");
  }
}
