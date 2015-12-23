package android.com.androidcode.activities;

import android.app.ProgressDialog;
import android.com.androidcode.R;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class BaseActivity extends AppCompatActivity {
    protected OkHttpClient okHttpClient;
    protected Context mContext;
    protected Retrofit retrofit;
    protected ProgressDialog progressDialog;
    protected String baseUrl="https://gist.githubusercontent.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext=this;
        initOKHttpClient();
        initRetrofit();
        initProgressDialog();
    }
    public void initProgressDialog(){
        progressDialog= new ProgressDialog(mContext);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
    }
    public void initOKHttpClient(){
        okHttpClient= new OkHttpClient();
        okHttpClient.setReadTimeout(20000, TimeUnit.MILLISECONDS);
    }
    public void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
