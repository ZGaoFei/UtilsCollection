package com.example.utillibrary.view;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.example.utillibrary.R;


public class DatePickerShowDialog extends AlertDialog implements DialogInterface.OnClickListener,
        DatePicker.OnDateChangedListener {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";

    private DatePicker mDatePicker;

    private OnDateSetListener mDateSetListener;

    public DatePickerShowDialog(Context context, OnDateSetListener listener, int year, int month, int day) {
        super(context);
        init(listener);
    }

    public DatePickerShowDialog(Context context, boolean cancelable, OnCancelListener cancelListener, OnDateSetListener listener) {
        super(context, cancelable, cancelListener);
        init(listener);
    }

    public DatePickerShowDialog(Context context, @StyleRes int themeResId, OnDateSetListener listener) {
        super(context, themeResId);
        init(listener);
    }

    private void init(OnDateSetListener listener) {
        final Context themeContext = getContext();
        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.date_picker_layout, null);
        setView(view);

        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.ok), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.cancel), this);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
        mDatePicker.init(year, monthOfYear, dayOfMonth, this);

        mDateSetListener = listener;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mDateSetListener != null) {
                    mDatePicker.clearFocus();
                    mDateSetListener.onDateSet(mDatePicker, mDatePicker.getYear(),
                            mDatePicker.getMonth(), mDatePicker.getDayOfMonth());
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mDatePicker.init(year, monthOfYear, dayOfMonth, this);
    }

    public void setOnDateSetListener(@Nullable OnDateSetListener listener) {
        mDateSetListener = listener;
    }

    public void updateDate(int year, int month, int dayOfMonth) {
        mDatePicker.updateDate(year, month, dayOfMonth);
    }

    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        state.putInt(YEAR, mDatePicker.getYear());
        state.putInt(MONTH, mDatePicker.getMonth());
        state.putInt(DAY, mDatePicker.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int year = savedInstanceState.getInt(YEAR);
        final int month = savedInstanceState.getInt(MONTH);
        final int day = savedInstanceState.getInt(DAY);
        mDatePicker.init(year, month, day, this);
    }

    public interface OnDateSetListener {
        void onDateSet(DatePicker view, int year, int month, int dayOfMonth);
    }
}
