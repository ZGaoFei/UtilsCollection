package com.example.utillibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.example.utillibrary.R;


public class IndicatorView extends View {

    private Context mContext;
    private Paint mActiveIndicatorPaint;
    private Paint mInactiveIndicatorPaint;
    private int mDiameter;
    private int mSize;
    private int mPosition;
    private int mIndicatorsCount;

    public IndicatorView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mContext = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.IndicatorView,
                defStyle, 0);

        int activeColor = typedArray.getColor(R.styleable.IndicatorView_activeColor,
                ContextCompat.getColor(context, R.color.white_dp));
        mActiveIndicatorPaint = new Paint();
        mActiveIndicatorPaint.setColor(activeColor);
        mActiveIndicatorPaint.setAntiAlias(true);

        int inactiveColor = typedArray.getColor(R.styleable.IndicatorView_inactiveColor,
                ContextCompat.getColor(context, R.color.base_color_title));
        mInactiveIndicatorPaint = new Paint();
        mInactiveIndicatorPaint.setColor(inactiveColor);
        mInactiveIndicatorPaint.setAntiAlias(true);

        int indicatorSize = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_indicatorSize,
                getResources().getDimensionPixelSize(R.dimen.dp_8));
        mDiameter = indicatorSize;
        mSize = mDiameter * 2;

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIndicatorsCount <= 1) {
            return;
        }
        for (int i = 0; i < mIndicatorsCount; i++) {
            if (i == mPosition) {
                canvas.drawCircle((float) (mDiameter + (mSize * mPosition)),
                        (float) mDiameter,
                        (float) mDiameter / 2,
                        mActiveIndicatorPaint);
            } else {
                canvas.drawCircle((float) (mDiameter + (mSize * i)),
                        (float) mDiameter,
                        (float) mDiameter / 2,
                        mInactiveIndicatorPaint);
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mSize * mIndicatorsCount;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = 2 * mDiameter + getPaddingTop() + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    public void setCurrentPage(int position) {
        mPosition = position;
        invalidate();
    }

    public void setPageIndicators(int size) {
        mIndicatorsCount = size;
        invalidate();
    }

    public void setInactiveIndicatorColor(@ColorRes int color) {
        mInactiveIndicatorPaint.setColor(ContextCompat.getColor(mContext, color));
        invalidate();
    }

    public void setActiveIndicatorColor(@ColorRes int color) {
        mActiveIndicatorPaint.setColor(ContextCompat.getColor(mContext, color));
        invalidate();
    }
}
