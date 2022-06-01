package com.example.togaether;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import jp.wasabeef.glide.transformations.MaskTransformation;

public class MaskTransformation2 extends MaskTransformation {
    private static final Paint paint = new Paint();

    static {
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }
    Drawable maskD;
    Bitmap maskB;
    /**
     * @param maskId If you change the mask file, please also rename the mask file, or Glide will get
     *               the cache with the old mask. Because key() return the same values if using the
     *               same make file name. If you have a good idea please tell us, thanks.
     */
    public MaskTransformation2(int maskId) {
        super(maskId);
    }

    public MaskTransformation2(Drawable maskD) {
        super(0);
        this.maskD = maskD;
    }

    public MaskTransformation2(Context con, Bitmap maskB) {
        super(0);
        this.maskB = maskB;
        maskD = new BitmapDrawable(con.getResources(), maskB);
    }

    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                               @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);

        Drawable mask = maskD;

        bitmap.setDensity(toTransform.getDensity());

        Canvas canvas = new Canvas(bitmap);
        mask.setBounds(0, 0, width, height);
        mask.draw(canvas);
        canvas.drawBitmap(toTransform, 0, 0, paint);

        return bitmap;
    }

}
