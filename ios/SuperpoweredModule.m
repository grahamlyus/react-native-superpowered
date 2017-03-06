//
//  SuperpoweredModule.m
//  ReactNativeSuperpowered
//
//  Created by Mathieu Débit on 06/03/2017.
//  Copyright © 2017 Facebook. All rights reserved.
//

#import "SuperpoweredModule.h"
#import "React/RCTLog.h"
#import <AVFoundation/AVAudioSession.h>

@implementation SuperpoweredModule

RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(getVolume,
                 resolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
  float volume = [AVAudioSession sharedInstance].outputVolume;
  NSString* volumeString = [NSString stringWithFormat:@"%f", volume];
  
  if (volumeString) {
    resolve(volumeString);
  } else {
    reject(@"get_error", @"Error getting system volume", nil);
  }
  
}

@end
