package networkservice;

import java.util.ArrayList;

import jsonmodels.Title;
import retrofit.Call;
import retrofit.http.GET;
/**
 * Created by jiffler on 23/12/15.
 */
public interface FetchTitleService {
    @GET("maclir/f715d78b49c3b4b3b77f/raw/8854ab2fe4cbe2a5919cea97d71b714ae5a4838d/items.json")
    Call<ArrayList<Title>> listTitles();
}
