import React from 'react';
import ReactNative, {
  SafeAreaView,
  Text,
  requireNativeComponent,
  UIManager,
  TouchableOpacity,
} from 'react-native';
const CanvasView = requireNativeComponent('CanvasView');
const App = () => {
  let _handle = null;

  const onTap = () => {
    UIManager.dispatchViewManagerCommand(_handle, 0, []);
  };
  return (
    <>
      <SafeAreaView>
        <Text>Hello</Text>
        <TouchableOpacity
          style={{width: 200, height: 200, backgroundColor: 'blue'}}
          onPress={onTap}>
          <CanvasView
            ref={ref => {
              _handle = ReactNative.findNodeHandle(ref);
            }}
          />
        </TouchableOpacity>
      </SafeAreaView>
    </>
  );
};

export default App;
