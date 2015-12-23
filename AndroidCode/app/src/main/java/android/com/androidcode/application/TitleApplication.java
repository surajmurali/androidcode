package android.com.androidcode.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by jiffler on 23/12/15.
 */
public class TitleApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getContext() {
        return mContext;
    }
}