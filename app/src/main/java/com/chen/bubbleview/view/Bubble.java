/*
 * Copyright (C) 2015 Jared Luo
 * jaredlam86@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chen.bubbleview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.chen.bubbleview.utils.DimenUtil;
import com.chen.bubbleview.utils.LogUtils;

public class Bubble extends AppCompatImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;

    private static final int COLORDRAWABLE_DIMENSION = 2;

    private BitmapShader mBitmapShader;

    private Bitmap mBitmap;

    private float mDrawableRadius;
    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private int mBitmapWidth;
    private int mBitmapHeight;

    private int mRandomHeight;

    public boolean isV() {
        return isV;
    }

    public void setV(boolean v) {
        isV = v;
    }

    //    是否认证
    private boolean isV;

    private Bitmap ver;

    public Bitmap getVer() {
        return ver;
    }

    public void setVer(Bitmap ver) {
        this.ver = ver;
    }

    public int getmRandomHeight() {
        return mRandomHeight;
    }

    public void setmRandomHeight(int mRandomHeight) {
        this.mRandomHeight = mRandomHeight;
    }

    private boolean mReady;

    private boolean mSetupPending;

    private int index;

    private Rect rect;
    private Paint mPaint;

    private Context mContext;

    private int mX;
    private int mY;

    private String mImageUrl;

    private String mContent;

    private Paint mVer = new Paint();


    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private boolean mBorderOverlay;

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
        setup();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public int getmX() {
        return mX;
    }

    public void setmX(int mX) {
        this.mX = mX;
    }

    public int getmY() {
        return mY;
    }

    public void setmY(int mY) {
        this.mY = mY;
    }

    private int mWidth;

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    private boolean mIsMoving;

    public Bubble(Context context) {
        super(context);
        init(context);
    }

    public Bubble(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Bubble(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mContext = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getmWidth(), getmWidth());
//        setMeasuredDimension(100, 100);
        LogUtils.e("bubbleview宽度:" + getmWidth());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    private void setup() {

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//
        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);
//
//
        mBitmapHeight = getmWidth();
        mBitmapWidth = getmWidth();

        mBorderRect.set(0, 0, getWidth(), getHeight());
//        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2, (mBorderRect.width() - mBorderWidth) / 2);

        mDrawableRect.set(mBorderRect);
//        if (!mBorderOverlay) {
//            mDrawableRect.inset(mBorderWidth, mBorderWidth);
//        }
        mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {


        canvas.drawCircle(getmWidth() / 2, getmWidth() / 2, mDrawableRadius, mBitmapPaint);

        int width = getmWidth();
//        int height = getmWidth();
//        LogUtils.e("宽:" + width + "/" + height);
//        canvas.drawCircle(width / 2, height / 2, width / 2, mPaint);
//
        int lenWidth = (int) (Math.sin(Math.PI * 45 / 180) * (width / 2));
        int circleX = width / 2 + lenWidth;
        int circleY = width / 2 - lenWidth;
//
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(circleX, circleY, DimenUtil.dip2px(mContext, 7) + 1, mPaint);

//
        mPaint.setColor(Color.GRAY);
        canvas.drawCircle(circleX, circleY, DimenUtil.dip2px(mContext, 7), mPaint);
//
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(DimenUtil.sp2px(mContext, 7));
        canvas.drawText(mContent + "'", circleX - DimenUtil.dip2px(mContext, 7) / 2, circleY + DimenUtil.dip2px(mContext, 7) / 2, mPaint);

        if (isV) {
            int isVX = width / 2 + lenWidth - getVer().getWidth() / 2;
            int isVY = width - 2 - lenWidth + getVer().getHeight() / 2;
            canvas.drawBitmap(getVer(), isVX, isVY, null);
        }
    }


    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    public float getTextMeasureWidth() {
        Rect bounds = new Rect();
//        getPaint().getTextBounds(getText().toString(), 0, getText().toString().length(), bounds);
        return bounds.width();
    }

    public void setCircleColor(int colorRes) {
        int color = getResources().getColor(colorRes);
        mPaint.setColor(color);
    }


}
