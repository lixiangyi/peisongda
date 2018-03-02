/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     16-12-19 上午11:33
 * ********************************************************
 */
package foundation.widget.recyclerView.swiperefreshlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.common.library.R;

/**
 * 设定了颜色的自定义的SwipeRefreshLayout，用来统一颜色
 */
public class SuperSwipeRefreshLayout extends SwipeRefreshLayout {
    private final int mTouchSlop;
    private float startY;
    private float startX;
    private boolean mIsVpDragger;// 记录viewPager是否拖拽的标记
    private boolean isProceeConflict;// 是否初始冲突，如ViewPager

    public SuperSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public SuperSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeResources(R.color.black, R.color.black, R.color.black);
        mTouchSlop = ViewConfiguration.get(context)
                .getScaledTouchSlop();
    }

    /**
     * 设置是否处理冲突，默认不处理
     */
    public void setIsProceeConflict(boolean isProceeConflict) {
        this.isProceeConflict = isProceeConflict;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isProceeConflict) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // 记录手指按下的位置
                    startY = ev.getY();
                    startX = ev.getX();
                    // 初始化标记
                    mIsVpDragger = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                    if (mIsVpDragger) {
                        return false;
                    }

                    // 获取当前手指位置
                    float endY = ev.getY();
                    float endX = ev.getX();
                    float distanceX = Math.abs(endX - startX);
                    float distanceY = Math.abs(endY - startY);
                    // 如果X轴位移大于Y轴位移，那么将事件交给viewPager处理。
                    if (distanceX > mTouchSlop && distanceX > distanceY) {
                        mIsVpDragger = true;
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mIsVpDragger = false; // 初始化标记
                    break;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
