package android.com.androidcode.activities;

import android.com.androidcode.R;
import android.com.androidcode.Util.IntentHelper;
import android.com.androidcode.adapter.TitleAdapter;
import android.com.androidcode.database.DatabaseHelper;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import android.com.androidcode.jsonmodels.JSONDataStore;
import android.com.androidcode.jsonmodels.Title;
import android.com.androidcode.networkservice.FetchTitleService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends BaseActivity {
    private ArrayList<Title>titles;
    private ListView titlesListView;
    private TitleAdapter titleAdapter;
    private final String titleContentId="title";
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
                    saveTitleContent();
                } else {
                    System.out.println("failed Response");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                progressDialog.dismiss();

                loadOffline();
            }
        });
    }
    public void initializeTitlesListWithData(){
        titleAdapter=new TitleAdapter(mContext,titles);
        titlesListView.setAdapter(titleAdapter);
        titlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentHelper.addObjectForKey(titles.get(position), "title");
                Intent intent = new Intent(mContext, TitleDetailsActivity.class);
                startActivity(intent);
            }
        });
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
            fetchTitlesFromServer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void saveTitleContent(){
        save(new java.util.Date().getTime(),new Gson().toJson(titles),titleContentId);
    }
    public void loadOffline(){
            new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }
                @Override
                protected String doInBackground( String... params ) {
                    DatabaseHelper databaseHelper = new DatabaseHelper();
                    if (databaseHelper.containsKey(titleContentId)){
                        JSONDataStore dataStore = databaseHelper
                                .getJsonData(titleContentId);
                        return dataStore.getJsonData();
                    }
                    else
                        return null;
                }
                protected void onPostExecute(String response) {
                    if (response != null) {
                        Gson gson=new Gson();
                        titles=gson.fromJson(response,new TypeToken<ArrayList<Title>>(){}.getType());
                        initializeTitlesListWithData();
                    }
                }
            }.execute();
        }


    public void save(final long timeStamp,final String  jsonData,final String databaseId){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground( Void... voids ) {
                JSONDataStore dataStore=new JSONDataStore(databaseId,String.valueOf(timeStamp),jsonData);
                DatabaseHelper databaseHelper = new DatabaseHelper();
                if (databaseHelper.containsKey(databaseId))
                    databaseHelper.updateJsonData(dataStore);
                else
                    databaseHelper.addJsonData(dataStore);
                return null;
            }
        }.execute();

    }

}
