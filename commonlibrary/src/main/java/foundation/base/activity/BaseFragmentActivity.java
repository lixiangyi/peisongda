package foundation.base.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseArray;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wfpb on 15/7/6.
 * FragmentActivity
 */
public abstract class BaseFragmentActivity extends BaseActivity {

    protected FragmentManager _fragmentManager;
    private ChangeListener changeListener;


    protected int _index = 0;

    protected Bundle bundle = new Bundle();
    protected ArrayList<Class<? extends Fragment>> _fragmentClasses = new ArrayList<>();
    protected List<Fragment> _fragments = new ArrayList<>();

    protected abstract ArrayList<Class<? extends Fragment>> fragmentClasses();

    protected abstract int containerViewId();

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("**********", "notnull_Base_onNewIntent");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        _fragmentManager = getSupportFragmentManager();
        _fragmentClasses = this.fragmentClasses();
        Assert.assertTrue("_fragmentClasses.size() == 0", _fragmentClasses.size() != 0);
        for (int i = 0; i < _fragmentClasses.size(); i++) {
            if (_fragmentManager.findFragmentByTag(i + "") != null) {
                _fragments.add(_fragmentManager.findFragmentByTag(i + ""));
                Log.i("**********", "notnull_Base");
            } else {
                _fragments.add(null);
                Log.i("**********", "null_Base");
            }
        }
        selectPage(this.getSelectedPage());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        _fragmentManager = getSupportFragmentManager();
        _fragmentClasses = this.fragmentClasses();
        Assert.assertTrue("_fragmentClasses.size() == 0", _fragmentClasses.size() != 0);
        for (int i = 0; i < _fragmentClasses.size(); i++) {
            if (_fragmentManager.findFragmentByTag(i + "") != null) {
                _fragments.add(_fragmentManager.findFragmentByTag(i + ""));
                Log.i("**********", "notnull_Base");
            } else {
                _fragments.add(null);
                Log.i("**********", "null_Base");
            }
        }
        selectPage(this.getSelectedPage());
    }

    public void selectPage(int index) {
        getBundle();
        Log.i("**********", "selectPage_Base");
        this.setFragmentSelection(index);
    }

    public int getSelectedPage() {
        return _index;
    }

    public void setSelectedPage(int index) {
        _index = index;
    }

    public void setFragmentSelection(int index) {
        if (changeListener != null) {
            changeListener.changeIndex(index);
        }
        FragmentTransaction transaction = _fragmentManager.beginTransaction();
        setSelectedPage(index);
        for (int i = 0; i < _fragments.size(); i++) {
            if (i == index) {
                continue;
            }

            Fragment fragment = _fragments.get(i);
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
        Fragment fragment = _fragments.get(index);

        if (fragment == null) {
            Class<? extends Fragment> clazz = _fragmentClasses.get(index);
            try {
                    fragment = clazz.newInstance();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("position", index);
                    fragment.setArguments(bundle1);
                    Assert.assertTrue("fragment == null", fragment != null);
                    transaction.add(containerViewId(), fragment, "" + index);
                    _fragments.set(index, fragment);

            } catch (Exception e) {
                Assert.assertTrue("clazz.newInstance() exception", false);
            }
        } else {
                fragment = _fragmentManager.findFragmentByTag("" + _index);
                if (refreshes.get(_index, false)) {
                    Log.i("**********", "refreshes_Base");
                    transaction.detach(fragment);
                    transaction.attach(fragment);
                }
                if(fragment!=null){
                    transaction.show(fragment);
                }
            }


        transaction.commitAllowingStateLoss();

    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
        Log.i("**********","setBundle_base");
    }
    public Bundle getBundle(){
        return  bundle;
    }

    public interface ChangeListener {
        void changeIndex(int index);
    }
    private SparseArray<Boolean> refreshes = new SparseArray<>();

    /**
     * 设置想要刷新的fragment
     *
     * @param index   fragment的位置
     * @param refresh 是否刷新
     */
    protected void setRefresh(int index, boolean refresh) {
        refreshes.put(index, refresh);
    }
}
