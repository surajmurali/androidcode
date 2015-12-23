package jsonmodels;

/**
 * Created by jiffler on 23/12/15.
 */
public class JSONDataStore {
    private String mKey, mJsonData, mTimeStamp;

    public JSONDataStore() {

    }

    public JSONDataStore(String mKey, String mTimeStamp, String mJsonData) {
        this.mKey = mKey;
        this.mTimeStamp = mTimeStamp;
        this.mJsonData = mJsonData;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public void setTimeStamp(String mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public String getJsonData() {
        return mJsonData;
    }

    public void setJsonData(String mJsonData) {
        this.mJsonData = mJsonData;
    }
}
