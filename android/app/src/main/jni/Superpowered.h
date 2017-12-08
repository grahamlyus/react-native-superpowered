#ifndef REACTNATIVESUPERPOWERED_SUPERPOWERED_H
#define REACTNATIVESUPERPOWERED_SUPERPOWERED_H

#include <math.h>
#include <pthread.h>

#include <SuperpoweredAdvancedAudioPlayer.h>
#include <SuperpoweredFilter.h>
#include <SuperpoweredRoll.h>
#include <SuperpoweredFlanger.h>
#include <AndroidIO/SuperpoweredAndroidAudioIO.h>

#define HEADROOM_DECIBEL 3.0f
static const float headroom = powf(10.0f, -HEADROOM_DECIBEL * 0.025f);

class Superpowered {
public:

	Superpowered(unsigned int samplerate, unsigned int buffersize, const char *path, int fileAoffset, int fileAlength, int fileBoffset, int fileBlength);
	~Superpowered();

	bool process(short int *output, unsigned int numberOfSamples);
	void onPlayPause();
	void onFxValue(int activeFx, float value);
	void onCrossfader(float value);

private:
    SuperpoweredAndroidAudioIO *audioSystem;
    SuperpoweredAdvancedAudioPlayer *playerA, *playerB;
    SuperpoweredRoll *roll;
    SuperpoweredFilter *filter;
    SuperpoweredFlanger *flanger;
    float *stereoBuffer;
    unsigned char activeFx;
    float crossValue, volA, volB;
};

#endif //REACTNATIVESUPERPOWERED_SUPERPOWERED_H
