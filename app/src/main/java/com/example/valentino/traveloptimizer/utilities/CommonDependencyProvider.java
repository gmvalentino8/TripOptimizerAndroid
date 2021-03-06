package com.example.valentino.traveloptimizer.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Class used to retrieve dependencies in app activities and fragments.
 *
 * Used as a form of dependency injection such that classes can be more easily mocked.
 */
public class CommonDependencyProvider {

    private static AppHelper appHelper = null;

    public AppHelper getAppHelper() {
        return appHelper;
    }

    public AppHelper getAppHelper(Context context) {
        if (appHelper == null) {
            appHelper = new AppHelper(context);
        }
        return appHelper;
    }

    @NonNull
    public AlertDialog.Builder getAlertDialogBuilder(Context currentContext) {
        return new AlertDialog.Builder(currentContext);
    }

    @NonNull
    public ProgressDialog getProgressDialog(Context currentContext) {
        return new ProgressDialog(currentContext);
    }


}
