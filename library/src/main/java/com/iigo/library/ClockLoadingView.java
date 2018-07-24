package com.iigo.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import static android.view.View.MeasureSpec.AT_MOST;

/**
 * @author SamLeung
 * @e-mail 729717222@qq.com
 * @github https://github.com/samlss
 * @csdn https://blog.csdn.net/Samlss
 * @description The clock loading view.
 */
public class ClockLoadingView extends View{
    private final static float MIN_PIN_MAX_ANGLE = (float) (2 * Math.PI);
    private final static float HOUR_PIN_ANGLE_INTERVAL = (float) (2 * Math.PI / 60);
    private final static int DEFAULT_COLOR = Color.BLUE;

    private int circleColor = DEFAULT_COLOR;
    private int centerColor = DEFAULT_COLOR;
    private int hourHandColor = DEFAULT_COLOR;
    private int minHandColor = DEFAULT_COLOR;

    private float circleRadius;

    private float circleStrokeWidth;
    private float pinStrokeWidth;

    private Path circlePath;

    private Paint circlePaint;
    private Paint centerPaint;
    private Paint hourHandPaint;
    private Paint minHandPaint;

    private ValueAnimator valueAnimator;

    private float centerX;
    private float centerY;

    private float currentMinHandAngle = 0;
    private float currentHourHandAngle = 0;

    public ClockLoadingView(Context context) {
        super(context);

        init();
    }

    public ClockLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        parseAttr(attrs);
        init();
    }

    public ClockLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttr(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        parseAttr(attrs);
        init();
    }

    private void parseAttr(AttributeSet attrs){
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClockLoadingView);
        circleColor = typedArray.getColor(R.styleable.ClockLoadingView_circle_color, DEFAULT_COLOR);
        hourHandColor = typedArray.getColor(R.styleable.ClockLoadingView_hour_hand_color, DEFAULT_COLOR);
        minHandColor = typedArray.getColor(R.styleable.ClockLoadingView_minute_hand_color, DEFAULT_COLOR);
        centerColor = typedArray.getColor(R.styleable.ClockLoadingView_center_color, DEFAULT_COLOR);
        typedArray.recycle();
    }

    private void init(){
        circlePath = new Path();

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.STROKE);

        centerPaint  = new Paint();
        centerPaint.setAntiAlias(true);
        centerPaint.setColor(centerColor);
        centerPaint.setStyle(Paint.Style.FILL);

        hourHandPaint = new Paint();
        hourHandPaint.setAntiAlias(true);
        hourHandPaint.setColor(hourHandColor);

        minHandPaint = new Paint();
        minHandPaint.setAntiAlias(true);
        minHandPaint.setColor(minHandColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int modeWidth  = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //没有指定宽高,使用了wrap_content,则手动指定宽高为MATCH_PARENT
        // (No width or height is specified, wrap_content is used, and the width and height are manually specified as MATCH_PARENT)
        if (modeWidth == AT_MOST && modeHeight == AT_MOST){
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        stop();

        int minSize = Math.min(w, h);

        circleStrokeWidth = minSize / 20f;
        pinStrokeWidth = circleStrokeWidth * 3 / 5f;

        circlePaint.setStrokeWidth(circleStrokeWidth);
        hourHandPaint.setStrokeWidth(pinStrokeWidth);
        minHandPaint.setStrokeWidth(pinStrokeWidth);

        circleRadius = (minSize) / 2 - 2 * circleStrokeWidth;

        centerX = w / 2;
        centerY = h / 2;

        circlePath.reset();
        circlePath.addCircle(w / 2, h / 2, circleRadius, Path.Direction.CW);

        setupAnimator();
    }

    private void setupAnimator(){
        valueAnimator = ValueAnimator.ofFloat(0, MIN_PIN_MAX_ANGLE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentMinHandAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                currentHourHandAngle = (currentHourHandAngle + HOUR_PIN_ANGLE_INTERVAL) % MIN_PIN_MAX_ANGLE;
            }
        });
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(centerX, centerY, pinStrokeWidth, centerPaint);
        canvas.drawPath(circlePath, circlePaint);
        float minEndX = (float) (circleRadius * Math.cos(currentMinHandAngle) + centerX);
        float minEndY = (float) (circleRadius * Math.sin(currentMinHandAngle) + centerY);

        float hourEndX = (float) ((circleRadius * 2 / 3) * Math.cos(currentHourHandAngle) + centerX);
        float hourEndY = (float) ((circleRadius * 2 / 3) * Math.sin(currentHourHandAngle) + centerY);

        canvas.drawLine(centerX, centerY, hourEndX, hourEndY, hourHandPaint);
        canvas.drawLine(centerX, centerY, minEndX, minEndY, minHandPaint);
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        circlePaint.setColor(circleColor);
        invalidate();
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
        centerPaint.setColor(centerColor);
        invalidate();
    }

    public void setHourHandColor(int hourHandColor) {
        this.hourHandColor = hourHandColor;
        hourHandPaint.setColor(hourHandColor);
        invalidate();
    }

    public void setMinHandColor(int minHandColor) {
        this.minHandColor = minHandColor;
        minHandPaint.setColor(minHandColor);
        invalidate();
    }

    public void start(){
        if (valueAnimator != null){
            valueAnimator.start();
        }
    }

    public void stop(){
        if (valueAnimator != null){
            valueAnimator.cancel();
        }
    }

    public void release(){
        stop();

        if (valueAnimator != null){
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.removeAllListeners();
        }
    }
}
