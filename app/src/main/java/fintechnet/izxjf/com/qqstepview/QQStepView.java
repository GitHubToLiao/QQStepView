package fintechnet.izxjf.com.qqstepview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by as on 2017/5/22.
 */

public class QQStepView extends View {
    private int mOurterColor =Color.BLUE;
    private int mInnerColor =Color.RED;
    private int mBorderWidth =10;
    private int mTextColor =Color.BLACK;
    private int mTextSize =15;

    private Paint mOurterRecPaint;
    private Paint mInnerColorPaint;
    private Paint mTextPaint;
    private float maxProgress =100;
    private float progress =0;
    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);

        mOurterColor =typedArray.getColor(R.styleable.QQStepView_mOuterColor,mOurterColor);
        mInnerColor =typedArray.getColor(R.styleable.QQStepView_mInnerColor,mInnerColor);
        mBorderWidth =typedArray.getDimensionPixelOffset(R.styleable.QQStepView_mBorderWidth,dpi2Px(mBorderWidth));
        mTextColor =typedArray.getColor(R.styleable.QQStepView_mTextColor,mTextColor);
        mTextSize =typedArray.getDimensionPixelSize(R.styleable.QQStepView_mTextSize,sp2Px(mTextSize));
        typedArray.recycle();
        //外圆画笔
        mOurterRecPaint = new Paint();
        mOurterRecPaint.setStrokeWidth(mBorderWidth);
        mOurterRecPaint.setAntiAlias(true);
        mOurterRecPaint.setColor(mOurterColor);
        mOurterRecPaint.setStyle(Paint.Style.STROKE);
        mOurterRecPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerColorPaint = new Paint();
        mInnerColorPaint.setStrokeWidth(mBorderWidth);
        mInnerColorPaint.setAntiAlias(true);
        mInnerColorPaint.setColor(mInnerColor);
        mInnerColorPaint.setStyle(Paint.Style.STROKE);
        mInnerColorPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width =MeasureSpec.getSize(widthMeasureSpec);
        int height =MeasureSpec.getSize(heightMeasureSpec);
        //宽和高哪个小用哪个
        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(mBorderWidth/2,mBorderWidth/2,getWidth()-mBorderWidth/2,getWidth()-mBorderWidth/2);
        canvas.drawArc(rectF,135,270,false,mOurterRecPaint);

        if(progress ==0)return;
        canvas.drawArc(rectF,135,(progress/maxProgress)*270,false,mInnerColorPaint);

        String text =progress+"%";
        Rect textRect = new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),textRect);


        int width =getWidth()/2 -textRect.width()/2;
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();

        int dy = (fontMetricsInt.bottom -fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int bastLinet =getHeight()/2 +dy;
        canvas.drawText(text,width,bastLinet,mTextPaint);
    }
    public void setProgress(float progress){
        this.progress =progress;
        invalidate();
    }
    public void setAnimatorProgress(float animatorProgress){
        if(animatorProgress ==0 ) return;
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0, (int) animatorProgress);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatorProgress = (int) animation.getAnimatedValue();
                setProgress((float) animatorProgress);
            }
        });
        valueAnimator.start();
    }
    public void setMaxProgress(int maxProgress){
        this.maxProgress = maxProgress;
    }
    public int dpi2Px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
    public int sp2Px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }
}
