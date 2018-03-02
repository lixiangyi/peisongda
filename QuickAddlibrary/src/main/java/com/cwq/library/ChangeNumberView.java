package com.cwq.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/2/23 0023.
 */

public class ChangeNumberView extends LinearLayout implements View.OnTouchListener {
    private static final String TAG = "ChangeNumber测试";

    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_content;

    //是否释放
    private boolean isReleased = true;
    private int leftCount = 0;
    private int rightCount = 0;

    //默认数据更改单位
    private double updateStep = 0.01;
    private DecimalFormat df;

    public interface OnClickViewListener {

        void onClickContent(View view);

        void onDataChange(String value);
    }

    private OnClickViewListener mListener;


    public ChangeNumberView(Context context) {
        this(context, null);
    }

    public ChangeNumberView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChangeNumberView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(getContext()).inflate(R.layout.changenumberlayout, this);

        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        tv_content = (TextView) findViewById(R.id.tv_content);

        df = new DecimalFormat("##0.00");

        iv_left.setOnTouchListener(this);
        iv_right.setOnTouchListener(this);
        tv_content.setOnTouchListener(this);
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
                } else if (view.getId() == R.id.tv_content) {
                    if (mListener != null)
                        mListener.onClickContent(view);
                }
                break;
            case MotionEvent.ACTION_MOVE:
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
     * 设置默认显示内容，设置的数值不小于0
     *
     * @param content
     */
    public void setContent(double content) {
        if (content >= 0)
            tv_content.setText(df.format(content));
    }


    /**
     * 获取当前显示内容
     *
     * @return
     */
    public String getContent() {
        return tv_content.getText().toString().trim();
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


    public TextView getContentTextView() {
        return tv_content;
    }

    public ImageView getLeftImageView() {
        return iv_left;
    }


    public ImageView getRightImageView() {
        return iv_right;
    }
}
