package com.example.valentino.traveloptimizer.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;
import com.example.valentino.traveloptimizer.R;
import com.example.valentino.traveloptimizer.fragments.SelectCityFragment;
import com.example.valentino.traveloptimizer.fragments.ViewTripsFragment;
import com.example.valentino.traveloptimizer.utilities.CommonDependencyProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String username;
    private CognitoUser user;
    private AlertDialog userDialog;
    private ProgressDialog waitDialog;

    private CommonDependencyProvider provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCommonDependencyProvider(new CommonDependencyProvider());
        setContentView(R.layout.activity_main);

        if (provider.getAppHelper() == null) {
            provider.getAppHelper(this);
        }

        if (findViewById(R.id.content) != null) {
            if (savedInstanceState != null) {
                return;
            }
            ViewTripsFragment testFragment = new ViewTripsFragment();
            getFragmentManager().beginTransaction().add(R.id.content, testFragment).commit();

        }

        // Get the user name
        username = provider.getAppHelper().getCurrUser();
        user = provider.getAppHelper().getPool().getUser(username);
        getDetails();

        ImageButton signoutButton = findViewById(R.id.signoutButton);
        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    public void setCommonDependencyProvider(CommonDependencyProvider provider) {
        this.provider = provider;
    }

    public void signOut() {
        user.signOut();
    }

    // Get user details from CIP service
    private void getDetails() {
        provider.getAppHelper().getPool().getUser(username).getDetailsInBackground(detailsHandler);
    }

    GetDetailsHandler detailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            closeWaitDialog();
            // Store details in the AppHandler
            provider.getAppHelper().setUserDetails(cognitoUserDetails);
            // Trusted devices?
            handleTrustedDevice();
        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            showDialogMessage("Could not fetch user details!", provider.getAppHelper().formatException(exception), true);
        }
    };

    private void handleTrustedDevice() {
        CognitoDevice newDevice = provider.getAppHelper().getNewDevice();
        if (newDevice != null) {
            provider.getAppHelper().newDevice(null);
            trustedDeviceDialog(newDevice);
        }
    }

    private void trustedDeviceDialog(final CognitoDevice newDevice) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remember this device?");
        //final EditText input = new EditText(UserActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        //input.setLayoutParams(lp);
        //input.requestFocus();
        //builder.setView(input);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    //String newValue = input.getText().toString();
                    showWaitDialog("Remembering this device...");
                    updateDeviceStatus(newDevice);
                    userDialog.dismiss();
                } catch (Exception e) {
                    // Log failure
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                } catch (Exception e) {
                    // Log failure
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void updateDeviceStatus(CognitoDevice device) {
        device.rememberThisDeviceInBackground(trustedDeviceHandler);
    }

    GenericHandler trustedDeviceHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            // Close wait dialog
            closeWaitDialog();
        }

        @Override
        public void onFailure(Exception exception) {
            closeWaitDialog();
            showDialogMessage("Failed to update device status", provider.getAppHelper().formatException(exception), true);
        }
    };

    private void showWaitDialog(String message) {
        closeWaitDialog();
        waitDialog = new ProgressDialog(this);
        waitDialog.setTitle(message);
        waitDialog.show();
    }

    private void showDialogMessage(String title, String body, final boolean exit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    if(exit) {
                        exit();
                    }
                } catch (Exception e) {
                    // Log failure
                    if(exit) {
                        exit();
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void closeWaitDialog() {
        try {
            waitDialog.dismiss();
        }
        catch (Exception e) {
            //
        }
    }

    private void exit () {
        Intent intent = new Intent();
        if(username == null)
            username = "";
        intent.putExtra("name",username);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void logout(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        signOut();
        startActivity(intent);
    }



}
