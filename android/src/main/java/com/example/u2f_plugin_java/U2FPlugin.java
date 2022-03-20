package com.example.u2f_plugin_java;

import androidx.annotation.NonNull;

import com.example.u2f_plugin_java.utils.Settings;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** U2fPluginJavaPlugin */
public class U2FPlugin implements FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private MethodChannel channel;

    private GoogleApiClient googleApiClient;
    private GoogleSignInAccount googleSignInAccount;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), Settings.START_CLASS_U2F);
        channel.setMethodCallHandler(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(Constants.SERVER_CLIENT_ID)
                .build();

        // build client with access to Google Sign-In API and the options specified by gso
//        googleApiClient = new GoogleApiClient.Builder(this)
//                        .enableAutoManage(this, this)
//                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                        .build();
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        if (call.method == "getAuth") {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method == "checkAuth") {

        } else {
            result.notImplemented();
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }
}
