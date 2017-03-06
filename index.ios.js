/**
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  NativeModules
} from 'react-native';

const SuperpoweredModule = NativeModules.SuperpoweredModule;

export default class ReactNativeSuperpowered extends Component {
  constructor(props) {
    super(props);

    this.state = {
      volume: null,
    }
  }

  componentDidMount() {
    this.getVolume();
  }

  async getVolume() {
    try {
      let volume = await SuperpoweredModule.getVolume();

      this.setState({ volume });
    } catch (e) {
      console.error(e);
    }
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Volume: { this.state.volume }
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('ReactNativeSuperpowered', () => ReactNativeSuperpowered);
