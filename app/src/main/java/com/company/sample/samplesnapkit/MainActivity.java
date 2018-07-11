package com.company.sample.samplesnapkit;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.snapchat.kit.sdk.SnapLogin;
import com.snapchat.kit.sdk.core.controller.LoginStateController;
import com.snapchat.kit.sdk.core.models.MeData;
import com.snapchat.kit.sdk.core.models.UserDataResponse;
import com.snapchat.kit.sdk.login.networking.FetchUserDataCallback;

public class MainActivity
        extends AppCompatActivity implements LoginStateController.OnLoginStateChangedListener,
        LoginStateController.OnLoginStartListener {
    private Context usageContext;
    private ConstraintLayout layout;
    private View mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usageContext = this; // or getApplicationContext();

        layout = findViewById(R.id.login_layout);

        LoginStateController controller = SnapLogin.getLoginStateController(usageContext);
        controller.addOnLoginStateChangedListener(this);
        controller.addOnLoginStartListener(this);

        mLoginButton = SnapLogin.getButton(usageContext, layout);

        final TextView view = findViewById(R.id.resultView);
        final Button button = findViewById(R.id.fetchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = SnapLogin.isUserLoggedIn(usageContext);
                SnapLogin.fetchUserData(usageContext, "me{displayName}", null, new FetchUserDataCallback() {
                    @Override
                    public void onSuccess(@Nullable UserDataResponse userDataResponse) {
                        if (userDataResponse == null || userDataResponse.getData() == null
                                || userDataResponse.getData().getMe() == null) {
                            return;
                        }

                        MeData data = userDataResponse.getData().getMe();
                        view.setText(data.getDisplayName());
                    }

                    @Override
                    public void onFailure(boolean isNetworkError, int statusCode) {
                        String setText = isNetworkError + ", " + statusCode;
                        view.setText(setText);
                    }
                });
            }
        });
    }

    @Override
    public void onLoginSucceeded() {

    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onLogout() {

    }

    @Override
    public void onLoginStart() {

    }
}
