package android.com.androidcode.Util.urlimageviewhelper;

import android.graphics.Bitmap;

public class LruBitmapCache extends LruCache<String, Bitmap> {
    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
}
