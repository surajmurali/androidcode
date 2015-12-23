package android.com.androidcode.activities;

import android.com.androidcode.R;
import android.com.androidcode.adapter.TitleAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import jsonmodels.Title;
import networkservice.FetchTitleService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends BaseActivity {
    private ArrayList<Title>titles;
    private ListView titlesListView;
    private TitleAdapter titleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        fetchTitlesFromServer();
    }
    public void fetchTitlesFromServer(){
        final FetchTitleService fetchTitleService=retrofit.create(FetchTitleService.class);
        Call<ArrayList<Title>> call =fetchTitleService.listTitles();
        progressDialog.setMessage("Loading titles");
        progressDialog.show();
        call.enqueue(new Callback<ArrayList<Title>>() {
            @Override
            public void onResponse(Response<ArrayList<Title>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    titles = response.body();
                    initializeTitlesListWithData();
                }
                else{
                    System.out.println("failed Response");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();
            }
        });


    }

    public void initializeTitlesListWithData(){
        titleAdapter=new TitleAdapter(mContext,titles);
        titlesListView.setAdapter(titleAdapter);
    }
    public void initUi(){
        titlesListView=(ListView)findViewById(R.id.tittleListView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
