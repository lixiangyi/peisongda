package com.cwq.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cwq.library.util.EditInputMoneyFilter;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/2/23 0023.
 */

public class EditNumberView extends LinearLayout implements View.OnTouchListener {
    private static final String TAG = "ChangeNumber测试";

    private ImageView iv_left;
    private ImageView iv_right;
    private EditText et_content;

    //是否释放
    private boolean isReleased = true;
    private int leftCount = 0;
    private int rightCount = 0;

    //默认数据更改单位
    private double updateStep = 0.01;
    private DecimalFormat df;
    private EditInputMoneyFilter filter;

    public interface OnClickViewListener {
        void onDataChange(String value);
    }

    private OnClickViewListener mListener;


    public EditNumberView(Context context) {
        this(context, null);
    }

    public EditNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.editnumberlayout, this);

        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        et_content = (EditText) findViewById(R.id.et_content);

        df = new DecimalFormat("##0.00");

        iv_left.setOnTouchListener(this);
        iv_right.setOnTouchListener(this);

        filter = new EditInputMoneyFilter();
        //限制只能输入两位小数的数值
        et_content.setFilters(new InputFilter[]{filter});

    }


    public void setOnClickViewListener(OnClickViewListener listener) {
        mListener = listener;
    }


    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1://左侧
                    if (leftCount != 0 && !isReleased) {
                        stepUpdateContent(1);
                    }
                    if (!isReleased) {
                        handler.postDelayed(new MyRunnable(1), 100);
                        leftCount++;
                    }
                    break;
                case 2://右侧
                    if (rightCount != 0 && !isReleased) {
                        stepUpdateContent(2);
                    }
                    if (!isReleased) {
                        handler.postDelayed(new MyRunnable(2), 100);
                        rightCount++;
                    }
                    break;
            }
            return false;
        }
    });


    class MyRunnable implements Runnable {
        private int flag;

        public MyRunnable(int i) {
            flag = i;
        }

        @Override
        public void run() {
            Message message = new Message();
            message.what = flag;
            handler.sendMessage(message);
        }
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isReleased = false;
                if (view.getId() == R.id.iv_left) {
                    leftCount = 0;
                    stepUpdateContent(1);
                    handler.postDelayed(new MyRunnable(1), 100);
                } else if (view.getId() == R.id.iv_right) {
                    rightCount = 0;
                    stepUpdateContent(2);
                    handler.postDelayed(new MyRunnable(2), 100);
                }
                break;
            case MotionEvent.ACTION_UP:
                isReleased = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                isReleased = true;
                break;
        }
        return true;
    }


    /**
     * 更新显示数值
     */
    private void stepUpdateContent(int flag) {
        if (TextUtils.isEmpty(getContent()))
            return;

        double value = Double.valueOf(getContent());

        if (1 == flag) {//左侧减法
            double temp = value - updateStep;
            if (temp >= 0) {
                setContent(temp);
                if (mListener != null)
                    mListener.onDataChange(getContent());
            } else {
                Log.i(TAG, "已经不能再减啦！");
            }

        } else if (2 == flag) {//右侧加法
            setContent(value + updateStep);
            if (mListener != null)
                mListener.onDataChange(getContent());
        }
    }


    /**
     * 设置默认显示内容
     *
     * @param content
     */
    public void setContent(double content) {
        et_content.setText(df.format(content));
    }


    /**
     * 获取当前显示内容
     *
     * @return
     */
    public String getContent() {
        return et_content.getText().toString().trim();
    }


    /**
     * 设置每次更新的单位值
     *
     * @param step
     */
    public void setUpdateStepValue(double step) {
        if (step > 0)
            updateStep = step;
    }


    /**
     * 获取当前更新的单位值
     *
     * @return
     */
    public double getUpdateStepValue() {
        return updateStep;
    }


    /**
     * 返回EditText
     *
     * @return
     */
    public EditText getEditText() {
        return et_content;
    }


    public ImageView getLeftImageView() {
        return iv_left;
    }


    public ImageView getRightImageView() {
        return iv_right;
    }


    /**
     * 设置可输入小数点位数
     *
     * @param length
     */
    public void setPointLength(int length) {
        if (length > 0) {
            filter.setPointLength(length);
            et_content.setFilters(new InputFilter[]{filter});
            String temp = "##0.";
            for (int i = 0; i < length; i++) {
                temp = temp + "0";
            }
            df = new DecimalFormat(temp);
        }
    }
}
