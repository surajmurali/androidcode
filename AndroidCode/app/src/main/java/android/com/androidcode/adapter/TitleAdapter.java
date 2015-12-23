package android.com.androidcode.adapter;

import android.com.androidcode.R;
import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import jsonmodels.Title;

/**
 * Created by jiffler on 23/12/15.
 */
public class TitleAdapter extends BaseAdapter {
    private ArrayList<Title> titles;
    private LayoutInflater inflater = null;
    Context mContext;
    public TitleAdapter(Context context,ArrayList<Title>titles) {
        super();
        this.titles=titles;
        this.mContext=context;
        inflater=(LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(titles==null)return 0;
        return titles.size();
    }

    @Override
    public Title getItem(int position) {
        return titles.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        TitleViewHolder holder;
        Title title = getItem(position);
        if (convertView == null) {
            vi = inflater.inflate(R.layout.title_list_item, null);
            holder = new TitleViewHolder();
            holder.title=(TextView)vi.findViewById(R.id.title);
            holder.description=(TextView)vi.findViewById(R.id.description);
            vi.setTag(holder);
        } else {
            holder = (TitleViewHolder) vi.getTag();
        }
        String toUpper=title.getTitle().substring(0,1).toUpperCase()+title.getTitle().substring(1);
        holder.title.setText(toUpper);
        holder.description.setText(getDecoratedDescription(title.getDescription()));
        return vi;
    }
    public static class TitleViewHolder {
        public TextView title,description;
    }
    public SpannableString getDecoratedDescription(String description){
        description=description.substring(0,1).toUpperCase()+description.substring(1);
        SpannableString spannableString=new SpannableString(description);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 0, 1, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,1, 0);
        return spannableString;
    }

}
