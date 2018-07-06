package com.chen.bubbleview;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chen.bubbleview.bean.FindMusicComments;
import com.chen.bubbleview.utils.LogUtils;
import com.chen.bubbleview.view.Bubble;
import com.chen.bubbleview.view.BubbleLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bubblelayout)
    BubbleLayout mBubblelayout;
    @BindView(R.id.activity_main)
    RelativeLayout mActivityMain;

    private String[] mHeads = { "https://ad.12306.cn/res/delivery/0001/2017/08/31/201708311634229711.jpg"};


    private List<FindMusicComments.DataBean.ListBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//         mBubblelayout = (BubbleLayout) findViewById(R.id.bubblelayout);
        initData();
        addData();

//        showDialog("教UI设计---Android系统弹窗样式展示");
//        showDialog("教UI设计---Android系统弹窗样式展示" +
//                "换行数据展示哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈" +
//                "哈哈哈哈哈哈哈哈");
    }

    private void initData() {

        for (int i = 0; i < 20; i++) {

            FindMusicComments.DataBean.ListBean mCircleBean = new FindMusicComments.DataBean
                    .ListBean();

            mCircleBean.setHeadImg(mHeads[0]);

            mCircleBean.setIdentifyStatus(1);

            mData.add(mCircleBean);
        }
    }

    private BubbleLayout.onItemClickListener mItemClikListener;


    private String mLastId;

    private void addData() {

        for (int i = 0; i < mData.size(); i++) {

            FindMusicComments.DataBean.ListBean mCircleBean = mData.get(i);

            mLastId = mCircleBean.getId();
            final Bubble bubbleView = new Bubble(this);
            bubbleView.setIndex(i);
            bubbleView.setV(mCircleBean.getIdentifyStatus() == 1);
            bubbleView.setmRandomHeight(getRandomBetween(0, 100));

            final int finalI = i;
            bubbleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClikListener != null)
                        mItemClikListener.onItemClick(view, finalI);
                }
            });

            int listCount = mCircleBean.getListenCount();

            int width;
            if (listCount > 30) {
                width = (int) (144 + 1.5 * 30 * 3);
            } else {
                width = (int) (144 + 1.5 * i * 3);
            }

            bubbleView.setmWidth(width);

            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.certify);

            bubbleView.setVer(zoomImage(bitmap, bubbleView.getmWidth() / 6, bubbleView.getmWidth
                    () / 6));

            //因为版本原因，这个不兼容
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                bubbleView.setmImageUrl(mCircleBean.getHeadImg());
            } else {
//                bubbleView.setmImageUrl(mCircleBean.getHeadImg());
                bubbleView.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            }

            final int imageWidth = width;

            LogUtils.e("头像地址:" + mCircleBean.getHeadImg());
            Glide.with(MainActivity.this).load(mCircleBean.getHeadImg()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap>
                        glideAnimation) {
                    LogUtils.e("图片加载成功");
                    bubbleView.setImageBitmap(zoomImage(resource, imageWidth, imageWidth));
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    LogUtils.e("error:"+e.toString());
                }
            });
            bubbleView.setmContent(mCircleBean.getSecond() + "");
            bubbleView.setmContent(i + "");
            mBubblelayout.addView(bubbleView);
        }


    }

    private int getRandomBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    /**
     * 按比例缩放图片
     *
     * @param bgimage
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                   double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // 缩放图片动作
        if (scaleHeight > scaleWidth) {
            matrix.postScale(scaleHeight, scaleHeight);
        } else {
            matrix.postScale(scaleWidth, scaleWidth);
        }
//        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showDialog(String s){
        new AlertDialog.Builder(this)
                .setMessage(s)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();



    }

}
