package android.com.androidcode.activities;
import android.com.androidcode.R;
import android.com.androidcode.Util.IntentHelper;
import android.com.androidcode.Util.urlimageviewhelper.UrlImageViewHelper;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;

import android.com.androidcode.jsonmodels.Title;
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
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        UrlImageViewHelper.setUrlDrawable(imageView, title.getImage());
    }

    public SpannableString getDecoratedDescription(String description){
        description=description.substring(0,1).toUpperCase()+description.substring(1);
        SpannableString spannableString=new SpannableString(description);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, 1, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,1, 0);
        return spannableString;
    }
}
