import React from 'react';
import {SafeAreaView, StyleSheet, Button} from 'react-native';
const {CanvasView, Modes} = require('react-native-canvas').default;

const style = StyleSheet.create({
  container: {
    width: 254,
    height: 252,
    borderColor: 'black',
    borderWidth: 2,
  },
  canvas: {
    flex: 1,
    width: 250,
    height: 250,
  },
});

class App extends React.Component {
  state = {
    mode: Modes.DROP,
  };

  render() {
    const {mode} = this.state;

    return (
      <>
        <SafeAreaView style={style.container}>
          <CanvasView style={style.canvas} mode={mode} />
        </SafeAreaView>
      </>
    );
  }
}

export default App;
