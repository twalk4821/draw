import React from 'react';
import ReactNative, {
  View,
  UIManager,
  requireNativeComponent,
  PanResponder,
  PixelRatio,
  TouchableWithoutFeedback,
} from 'react-native';
import PropTypes from 'prop-types';

const NativeCanvasView = requireNativeComponent('CanvasView');

const Commands = Object.freeze({
  PATH_START: 0,
  PATH_MOVE: 1,
  PATH_END: 2,
  CREATE_DROP: 3,
});

const Modes = Object.freeze({
  SKETCH: 0,
  DROP: 1,
});

class CanvasView extends React.Component {
  _handle;
  _ref;
  _panResponder;

  constructor(props) {
    super(props);
    this._createPanResponder();
  }

  _createPanResponder = () => {
    this._panResponder = PanResponder.create({
      onStartShouldSetPanResponder: () => true,
      onPanResponderStart: (event, gestureState) => {
        UIManager.dispatchViewManagerCommand(
          this._handle,
          Commands.PATH_START,
          [],
        );
      },
      onPanResponderMove: (event, gestureState) => {
        const {moveX: x, moveY: y} = gestureState;
        UIManager.dispatchViewManagerCommand(this._handle, Commands.PATH_MOVE, [
          {
            x: PixelRatio.getPixelSizeForLayoutSize(x),
            y: PixelRatio.getPixelSizeForLayoutSize(y),
          },
        ]);
      },
      onPanResponderEnd: (event, gestureState) => {
        const {moveX: x, moveY: y} = gestureState;
        UIManager.dispatchViewManagerCommand(this._handle, Commands.PATH_END, [
          {
            x: PixelRatio.getPixelSizeForLayoutSize(x),
            y: PixelRatio.getPixelSizeForLayoutSize(y),
          },
        ]);
      },
    });
  };

  _createDrop(event) {
    const {locationX: x, locationY: y} = event.nativeEvent;
    UIManager.dispatchViewManagerCommand(this._handle, Commands.CREATE_DROP, [
      {
        x: PixelRatio.getPixelSizeForLayoutSize(x),
        y: PixelRatio.getPixelSizeForLayoutSize(y),
      },
    ]);
  }

  _renderSketchCanvas() {
    const {style} = this.props;
    return (
      <View {...style} {...this._panResponder.panHandlers}>
        <NativeCanvasView
          {...style}
          ref={ref => {
            this._handle = ReactNative.findNodeHandle(ref);
          }}
        />
      </View>
    );
  }

  _renderDropCanvas() {
    const {style} = this.props;
    return (
      <TouchableWithoutFeedback {...style} onPress={e => this._createDrop(e)}>
        <NativeCanvasView
          {...style}
          ref={ref => {
            this._handle = ReactNative.findNodeHandle(ref);
          }}
        />
      </TouchableWithoutFeedback>
    );
  }

  render() {
    const {mode} = this.props;
    if (mode === Modes.DROP) {
      return this._renderDropCanvas();
    } else {
      return this._renderSketchCanvas();
    }
  }
}

NativeCanvasView.propTypes = {
  style: PropTypes.object,
  onStartShouldSetPanResponder: PropTypes.func,
  mode: PropTypes.number,
};

export default {
  CanvasView,
  Commands,
  Modes,
};
