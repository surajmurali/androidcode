package android.com.androidcode.Util;
import java.util.Hashtable;
/**
 * Created by jiffler on 23/12/15.
 */
public class IntentHelper {
    private static IntentHelper _instance;
    private Hashtable<String, Object> _hash;
    private IntentHelper() {
        _hash = new Hashtable<String, Object>();
    }

    private static IntentHelper getInstance() {
        if (_instance == null) {
            _instance = new IntentHelper();
        }
        return _instance;
    }

    public static void addObjectForKey(Object object, String key) {
        // First trying to remove if anything is there and then adding it
        IntentHelper helper = getInstance();
        Object data = helper._hash.get(key);
        if (data != null) {
            helper._hash.remove(key);
        }
        getInstance()._hash.put(key, object);
    }

    public static Object getObjectForKey(String key) {
        IntentHelper helper = getInstance();
        Object data = helper._hash.get(key);
        helper._hash.remove(key);
        helper = null;
        return data;
    }
}
