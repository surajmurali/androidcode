package android.com.androidcode.activities;

import android.com.androidcode.Util.IntentHelper;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.com.androidcode.R;
import android.widget.TextView;

import jsonmodels.Title;
public class TitleDetailsActivity extends AppCompatActivity {
private Title title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_details);
        title=(Title) IntentHelper.getObjectForKey("title");
        initTitleDetails();
    }
    public void initTitleDetails(){
        String toUpper=title.getTitle().substring(0,1).toUpperCase()+title.getTitle().substring(1);
        ((TextView)findViewById(R.id.detailTitle)).setText(toUpper);
        ((TextView)findViewById(R.id.detailDescription)).setText(getDecoratedDescription(title.getDescription()));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_details, menu);
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
    public SpannableString getDecoratedDescription(String description){
        description=description.substring(0,1).toUpperCase()+description.substring(1);
        SpannableString spannableString=new SpannableString(description);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, 1, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,1, 0);
        return spannableString;
    }
}
