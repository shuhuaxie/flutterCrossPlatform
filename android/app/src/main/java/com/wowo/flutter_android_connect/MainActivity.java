package com.wowo.flutter_android_connect;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    private static final String CHANNEL = "samples.flutter.dev/battery";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                new MethodChannel(getFlutterView(), CHANNEL)
                        .invokeMethod("android", "Hello World!", new MethodChannel.Result() {

                            @Override
                            public void success(Object o) {
                                if(o instanceof String){
                                    Log.e("xie", "flutter result :" + o);
                                }
                            }

                            @Override
                            public void error(String s, String s1, Object o) {

                            }

                            @Override
                            public void notImplemented() {

                            }
                        });
            }
        }, 5000);

        new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
                new MethodChannel.MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                        // Note: this method is invoked on the main thread.
                        if (call.method.equals("getBatteryLevel")) {
                            int batteryLevel = getBatteryLevel();

                            if (batteryLevel != -1) {
                                result.success(batteryLevel);
                            } else {
                                result.error("UNAVAILABLE", "Battery level not available.", null);
                            }
                        } else {
                            result.notImplemented();
                        }

                    }
                });
    }

    private int getBatteryLevel() {
        return 10;
    }
}
