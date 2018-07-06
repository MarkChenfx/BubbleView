package com.chen.bubbleview.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


import com.chen.bubbleview.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;


public class BubbleLayout extends ViewGroup {

    public static final int DEFAULT_PADDING = 0;

    private int padding = DEFAULT_PADDING;

    private double mRadiansPiece = 2 * Math.PI / 6;
    private int mRandomRadians = 0;


    public interface onItemClickListener {
        void onItemClick(View view, int pos);
    }

    private Context mContext;


    private onItemClickListener mOnItemClick;

    public void setmOnItemClick(onItemClickListener onItemClick) {
        this.mOnItemClick = onItemClick;
    }


    private int mBaseHeight = 0;

    //行数量
    private List<Integer> mPointsLine = new ArrayList<>();


    public BubbleLayout(Context context) {
        this(context, null);
    }

    public BubbleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

//        mRandomRadians = (int) Math.PI;

    }


    /**
     * @param changed
     * @param l
     * @param t
     * @param r       宽
     * @param b       高
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mPointsLine.clear();
//
        List<Bubble> sortResult;
        sortResult = sort();

        Rect baseRect = null;

        LogUtils.e("onLayout:" + "changed:" + changed + "/" + l + "/" + t + "/" + r + "/" + b);

        int currentRadians = mRandomRadians;
        for (int index = 0; index < sortResult.size(); index++) {
            final Bubble bubbleView = sortResult.get(index);

            if (bubbleView != null) {
//                半径
                int radius = bubbleView.getmWidth() / 2;
//                int radius = mViewWidth.get(i);
                if (index == 0) {

                    baseRect = getBounds(currentRadians, currentRadians, bubbleView
                            .getmWidth(), bubbleView.getmWidth());
                    bubbleView.layout(baseRect.left, baseRect.top, baseRect.right, baseRect.bottom);
//                    if (!mPointsLine.contains(index))


                    mPointsLine.add(index, index);

//                    LogUtils.e("第一行:" + mPointsLine.size());

                    int ra = ( baseRect.bottom - baseRect.top)/2;
                    insertXY(bubbleView, currentRadians + bubbleView.getmWidth() / 2,
                            currentRadians + bubbleView.getmWidth() / 2);
//                    insertXY(bubbleView,ra + currentRadians , currentRadians + ra);
                    bubbleView.setRect(baseRect);
                } else {
                    int baseCenterX = baseRect.left + baseRect.width() / 2;
                    int baseCenterY = baseRect.top + baseRect.width() / 2;


//                    LogUtils.e("数据" + index + "baseRectleft:" + baseRect.left + "/" + baseRect
// .top + "/" + baseRect.width());

                    int[] center = getRadianPoint(sortResult, baseRect, r, radius, index,
                            bubbleView);

                    insertXY(bubbleView, center[0], center[1]);

                    baseRect = getBounds(center[0] - radius, center[1] - radius, bubbleView
                            .getmWidth(), bubbleView.getMeasuredHeight());
//                    Rect rect = getBounds(center[0] - radius, center[1] - radius, child
// .getmWidth(), child.getMeasuredHeight());
                    bubbleView.layout(baseRect.left, baseRect.top, baseRect.right, baseRect.bottom);
                    bubbleView.setRect(baseRect);

                }
            }
        }

        MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();


        if (sortResult.size() > 0) {
            if (sortResult.get(sortResult.size() - 1).getmY() > mBaseHeight) {
                mBaseHeight = sortResult.get(sortResult.size() - 1).getmY();
            }
        }
        if (baseRect != null) {
            if (baseRect.bottom > mBaseHeight)
                mBaseHeight = baseRect.bottom;
        }

//        if (mBaseHeight==0){
//            if(mPointsLine.size()>0){
//                mBaseHeight = mPointsLine.size()*(DimenUtil.dip2px(mContext,93));
//            }
//        }
//
        lp.height = mBaseHeight + 200;
//        lp.height = b;

        LogUtils.e("bubbleLayout高度:" + mBaseHeight);
//        setLayoutParams(lp);
    }

    public int getmBaseHeight() {
        return mBaseHeight;
    }

    /**
     * 记录XY 坐标
     *
     * @param index
     * @param X
     * @param Y
     */
    private void insertXY(Bubble index, int X, int Y) {
        index.setmX(X);
        index.setmY(Y);
//        LogUtils.e("添加坐标:" + index.getText() + "/" + X + '/' + Y);
    }

    /**
     * 根据上一个圆的位置计算圆心
     *
     * @param baseRect    上一个圆心
     * @param ScreenWidth 屏幕宽度
     * @param radius      半径
     * @return
     */
    private int[] getRadianPoint(List<Bubble> sortResult, Rect baseRect, int ScreenWidth, int
            radius, int index, Bubble bubbleView) {

        int resultX = 0;
        int resultY = 0;

        int circleRight = baseRect.right + 2 * radius + padding;
        int pos;
        if (circleRight > ScreenWidth) {
            resultX = radius;
//            // TODO: 17/5/20 换行
            //获取上一行view个数
            int upLineCount = index;
//            //两点之间的距离
            double finalDistance = ScreenWidth;
//            //最短距离index
            int minIndex = 0;
//            //换行后的X坐标

            //上一行第一个view的位置
            pos = mPointsLine.get(mPointsLine.size() - 1);

            int maxLength = 0;
            if (mPointsLine.size() > 1) {
                upLineCount = index - mPointsLine.get(mPointsLine.size() - 1);
            }


            for (int i = 0; i < upLineCount; i++) {
                int len = sortResult.get(i + pos).getmY()
                        + (sortResult.get(i + pos).getmWidth() / 2);

                if (len > maxLength) {
                    maxLength = len;
                }
            }

            for (int i = 0; i < upLineCount; i++) {

                int oldX = sortResult.get(i + pos).getmX();
                int oldY = sortResult.get(i + pos).getmY();

                double oldRadius  = sortResult.get(i+pos).getmWidth()/2;

                double checkMath = Math.sqrt(Math.pow(radius+oldRadius,2)- Math.pow(resultX - oldX,2));

                double  newY =  Math.abs((double) oldY + checkMath);

                boolean isMinY = true;

                if (newY < finalDistance && (checkMath !=0)) {

                    for (int j = 0; j < upLineCount; j++){
                        int oldXJ = sortResult.get(j+pos).getmX();
                        int oldYJ = sortResult.get(j+pos).getmY();
                        double oldRadiusJ  = sortResult.get(j+pos).getmWidth()/2;

                        int ab =  (int) Math.ceil((Math.pow(resultX - oldXJ,2)+ Math.pow(newY - oldYJ,2)));

                        if(ab < (int) Math.ceil(Math.pow(oldRadiusJ + radius,2)))
                            isMinY = false;

                        if (!isMinY)
                            break;
                    }

                    if (isMinY) {
                        finalDistance = newY;
                        minIndex = pos + i;
                    }
                }
            }


            Bubble mBa = sortResult.get(minIndex);

            int oldX = mBa.getmWidth() / 2 + mBa.getRect().left;
            int oldY = mBa.getmWidth() / 2 + mBa.getRect().top;


            if(resultX == oldX){
                resultY = mBa.getRect().bottom + (baseRect.bottom - baseRect.top) / 2 ;

            }else {
                resultY = (int) finalDistance;
            }

            if (resultY > mBaseHeight)
                mBaseHeight = resultY;
            mPointsLine.add(mPointsLine.size(), index);

        } else {
            pos = mPointsLine.get(mPointsLine.size() - 1);

            if(pos ==0){
                resultY = radius ;
            }else {
//                resultY = radius + sortResult.get(pos).getRect().top;


                //算法开始
                resultX = radius;
//            // TODO: 17/5/20 换行
                //获取上一行view个数
                int upLineCount = index;
//            //两点之间的距离
                double finalDistance = ScreenWidth;
//            //最短距离index
                int minIndex = 0;
//            //换行后的X坐标

                //上一行第一个view的位置
                 pos = mPointsLine.get(mPointsLine.size() - 1);

                int maxLength = 0;
                if (mPointsLine.size() > 1) {
                    upLineCount = index - mPointsLine.get(mPointsLine.size() - 1);
                }


                for (int i = 0; i < upLineCount; i++) {
                    int len = sortResult.get(i + pos).getmY()
                            + (sortResult.get(i + pos).getmWidth() / 2);

                    if (len > maxLength) {
                        maxLength = len;
                    }
                }

                LogUtils.e("上一行view数量:"+upLineCount);

                LogUtils.e("换行:" + index+"上一行第一个view:"+pos +" /"+mPointsLine.size());
                //找出最短距离

                LogUtils.e("oldview: =============换行================="+index);

                for (int i = 0; i < upLineCount; i++) {

                    int oldX = sortResult.get(i + pos).getmX();
                    int oldY = sortResult.get(i + pos).getmY();

                    double oldRadius  = sortResult.get(i+pos).getmWidth()/2;

                    LogUtils.e("oldview:"+(i+pos)+"/x:"+oldX+"/Y："+oldY+"/"+oldRadius+"/"+radius);

                    double checkMath = Math.sqrt(Math.pow(radius+oldRadius,2)- Math.pow(resultX - oldX,2));

                    double  newY =  Math.abs((double) oldY + checkMath);
                    LogUtils.e("oldview:"+(i+pos)+"/oldY:"+oldY+"/radius："+radius+"/oldRadius："+oldRadius+"/resultX："+resultX+"/oldX："+oldX);
                    LogUtils.e("oldview: distance："+newY);

                    boolean isMinY = true;

                    if (newY < finalDistance && (checkMath !=0)) {

                        for (int j = 0; j < upLineCount; j++){
                            int oldXJ = sortResult.get(j+pos).getmX();
                            int oldYJ = sortResult.get(j+pos).getmY();
                            double oldRadiusJ  = sortResult.get(j+pos).getmWidth()/2;

                            int ab =  (int) Math.ceil((Math.pow(resultX - oldXJ,2)+ Math.pow(newY - oldYJ,2)));

                            LogUtils.e("oldview:boolean :ab"+ ab +"/"+ (int) Math.ceil(Math.pow(oldRadiusJ + radius,2)));
                            if(ab < (int) Math.ceil(Math.pow(oldRadiusJ + radius,2)))
                                isMinY = false;

                            LogUtils.e("oldviewJ:"+(j+pos)+"/resultX:"+resultX+"/oldXJ："+oldXJ+"/newY："+newY+"/oldYJ："+oldYJ+"/oldRadiusJ："+oldRadiusJ+"/radius："+radius);
                            LogUtils.e("oldviewJ A:"+(int)Math.pow(resultX - oldXJ,2)+"/B:"+(int)Math.ceil(Math.pow(newY - oldYJ,2))+"、"+ (int)Math.pow(oldRadiusJ + radius,2));
                            LogUtils.e("oldviewJ isMinY:"+isMinY);

                            if (!isMinY)
                                break;
                        }

                        if (isMinY) {
                            finalDistance = newY;
                            minIndex = pos + i;
                            LogUtils.e("oldview 最短距离:position:" + (i + pos));
                            LogUtils.e("oldview 最短距离:" + finalDistance + "/" + newY);
                        }
                    }
                }

//            finalDistance = 180;


                for (int d : mPointsLine) {
                    LogUtils.e("行数情况:" + d);
                }
//

                Bubble mBa = sortResult.get(minIndex);
//            BubbleView mBa = sortResult.get(minIndex + mPointsLine.get(mPointsLine.size() - 1));
//            LogUtils.e("上一行view:" + upLineCount + "当前行位置:" + minIndex + " 当前view内容:" + mBa
// .getText().toString()+"/ distance"+finalDistance);

                int oldX = mBa.getmWidth() / 2 + mBa.getRect().left;
                int oldY = mBa.getmWidth() / 2 + mBa.getRect().top;

                LogUtils.e("finaloldview:"+minIndex+"/x:"+oldX+"/Y："+oldY+"/"+radius);


                LogUtils.e("oldview resultx:"+resultX +"/："+oldX);
                if(resultX == oldX){
                    resultY = mBa.getRect().bottom + (baseRect.bottom - baseRect.top) / 2 ;

                    LogUtils.e("oldview resltY:"+resultY);


                }else {
//                double cos = Math.abs(resultX - oldX) / (finalDistance);
//
//                double sin = Math.sqrt(1 - Math.pow(cos, 2));
//
//                resultY = (int) ((sin * finalDistance) + (baseRect.bottom - baseRect.top)) / 2 + oldY;
//
//                LogUtils.e("oldview resltY:"+resultY);
                    resultY = (int) finalDistance;

                }


            }

            resultX = baseRect.right + padding + radius;

            if (resultY > mBaseHeight)
                mBaseHeight = resultY;
        }
        return new int[]{resultX, resultY};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        int widthMeasure = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasure = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        //计算宽度 高度 //wrap_content测量模式下会使用到:
        //存储最后计算出的宽度，
        int maxLineWidth = 0;
        //存储最后计算出的高度
        int totalHeight = 0;
        //存储当前行的宽度
        int curLineWidth = 0;
        //存储当前行的高度
        int curLineHeight = 0;

        // 得到内部元素的个数
        int count = getChildCount();

        //存储子View
        Bubble child = null;
        //存储子View的LayoutParams
        MarginLayoutParams params = null;
        //子View Layout需要的宽高(包含margin)，用于计算是否越界
        int childWidth;
        int childHeight;

        //遍历子View 计算父控件宽高
        for (int i = 0; i < count; i++) {
            child = sort().get(i);
            //如果gone，不测量了
            if (View.GONE == child.getVisibility()) {
                continue;
            }
            //先测量子View
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            //获取子View的LayoutParams，(子View的LayoutParams的对象类型，取决于其ViewGroup的generateLayoutParams()
            // 方法的返回的对象类型，这里返回的是MarginLayoutParams)
//            params = (MarginLayoutParams) child.getLayoutParams();
            //子View需要的宽度 为 子View 本身宽度+marginLeft + marginRight
//            childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            childWidth = child.getmWidth();
//            childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            childHeight = child.getmWidth() + child.getmRandomHeight();
            LogUtils.i("子View Layout需要的宽高(包含margin)：childWidth:" + childWidth + "   ," +
                    "childHeight:" + childHeight);

            //如果当前的行宽度大于 父控件允许的最大宽度 则要换行
            //父控件允许的最大宽度 如果要适配 padding 这里要- getPaddingLeft() - getPaddingRight()
            //即为测量出的宽度减去父控件的左右边距
            if (curLineWidth + childWidth > widthMeasure - getPaddingLeft() - getPaddingRight()) {
                //通过比较 当前行宽 和以前存储的最大行宽,得到最新的最大行宽,用于设置父控件的宽度
                maxLineWidth = Math.max(maxLineWidth, curLineWidth);
                //父控件的高度增加了，为当前高度+当前行的高度
                totalHeight += curLineHeight;
                //换行后 刷新 当前行 宽高数据： 因为新的一行就这一个View，所以为当前这个view占用的宽高(要加上View 的 margin)
                curLineWidth = childWidth;
                curLineHeight = childHeight;
            } else {
                //不换行：叠加当前行宽 和 比较当前行高:
                curLineWidth += childWidth;
                curLineHeight = Math.max(curLineHeight, childHeight);
            }
            //如果已经是最后一个View,要比较当前行的 宽度和最大宽度，叠加一共的高度
            if (i == count - 1) {
                maxLineWidth = Math.max(maxLineWidth, curLineWidth);
                totalHeight += childHeight;
            }
        }

        LogUtils.i("系统测量允许的尺寸最大值：widthMeasure:" + widthMeasure + "   ,heightMeasure:" +
                heightMeasure);
        LogUtils.i("经过我们测量实际的尺寸(不包括父控件的padding)：maxLineWidth:" + maxLineWidth + "   ," +
                "totalHeight:" + totalHeight);

        LogUtils.e("onMeasure:" + widthMeasureSpec + " /" + heightMeasureSpec);


        setMeasuredDimension(
                widthMode != MeasureSpec.EXACTLY ? maxLineWidth + getPaddingLeft() +
                        getPaddingRight() : widthMeasure,
                heightMode != MeasureSpec.EXACTLY ? totalHeight + getPaddingTop() +
                        getPaddingBottom() : heightMeasure);
//        setMeasuredDimension(widthSize, heightSize);
//        LogUtils.e("onMeasureDimension:" + widthSize + " /" + heightSize);

//
//        int count = getChildCount();
//        for (int i = 0; i < count; ++i)
//        {
//            View childView = getChildAt(i);
//            measureChild(childView, widthMeasureSpec,mScreenHeight);
//        }

    }


    private int getRandomBetween(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private Rect getBounds(int left, int top, int width, int height) {
        return new Rect(left, top, left + width, top + height);
    }


    private List<Bubble> sort() {
        List<Bubble> allBubbleChild = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != null && view instanceof Bubble) {
                allBubbleChild.add((Bubble) view);
            }
        }
        return allBubbleChild;
    }


    private static class MyHandler extends Handler {
        private WeakReference<Bubble> mBubble;

        public MyHandler(Bubble layout) {
            this.mBubble = new WeakReference<>(layout);
        }

        @Override
        public void handleMessage(Message msg) {
//            Bubble layout = mBubble.get();
//            int count = layout.getChildCount();
//            for (int i = 0; i < count && layout.mBubbleInfos.size() > 0; i++) {
//
//                BubbleInfo bubbleInfo = layout.mBubbleInfos.get(i);
//
//                List<BubbleInfo> overlapList = layout.hasOverlap(bubbleInfo);
//
//                Point overlapPoint = layout.ifOverlapBounds(bubbleInfo);
//                if (overlapPoint != null) {
//                    layout.reverseIfOverlapBounds(bubbleInfo);
//                } else if (overlapList.size() > 0) {
//                    layout.dealWithOverlap();
//                }
//                layout.moveBubble(bubbleInfo);
//            }
        }
    }

    ;


    private double getRandomRadians() {
        return Math.random() * 2 * Math.PI;
    }

    private double getRadians(float[] fromPoint, float[] toPoint) {
        return Math.atan2(toPoint[1] - fromPoint[1], toPoint[0] - fromPoint[0]);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


}
