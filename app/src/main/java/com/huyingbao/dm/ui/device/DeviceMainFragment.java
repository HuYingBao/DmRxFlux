package com.huyingbao.dm.ui.device;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.huyingbao.dm.R;
import com.huyingbao.rxflux2.base.fragment.BaseFragment;
import com.huyingbao.rxflux2.constant.ActionsKeys;
import com.huyingbao.dm.ui.devicelist.model.Device;

import butterknife.BindView;

/**
 * 设备详情主界面
 * Created by liujunfeng on 2017/5/31.
 */
public class DeviceMainFragment extends BaseFragment {
    private static final String ARRAY_TITLE[] = new String[]{"基本信息", "维修记录", "设备保养", "备品记录", "巡检记录"};
    protected int mCurrentPosition = -1;
    protected Fragment[] mFragments;
    @BindView(R.id.vp_contaner)
    ViewPager mVpContaner;
    @BindView(R.id.tabl_titles)
    TabLayout mTblTitles;
    private Device mDevice;

    public static DeviceMainFragment newInstance(Device device) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ActionsKeys.DEVICE, device);
        DeviceMainFragment fragment = new DeviceMainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initInjector() {
        mFragmentComponent.inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_device_main;
    }

    @Override
    public void afterCreate(Bundle savedInstanceState) {
        mDevice = getArguments().getParcelable(ActionsKeys.DEVICE);
        initActionBar(mDevice.getName());
        initMainPage();
        recover(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(ActionsKeys.POSITION, mCurrentPosition);
        super.onSaveInstanceState(outState);
    }

    /**
     * 初始化滑动页面
     */
    private void initMainPage() {
        //初始化fragment
        mFragments = new Fragment[ARRAY_TITLE.length];
        // viewpager每次切换的时候，会重新创建当前界面及左右界面三个界面，每次切换都要重新oncreate,
        // 所以只要设置viewPager setOffscreenPageLimit即可避免这个问题,表示三个界面之间来回切换都不会重新加载
        mVpContaner.setAdapter(getFragmentPagerAdapter());//左右滑动fragment适配器
        mVpContaner.setOffscreenPageLimit(ARRAY_TITLE.length);
        mVpContaner.addOnPageChangeListener(getPageChangeListener());

        mTblTitles.setupWithViewPager(mVpContaner); //tab跟随viewpager
        mTblTitles.setTabMode(TabLayout.MODE_FIXED);

        // 设置TabLayout间隔线
        LinearLayout linearLayout = (LinearLayout) mTblTitles.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(mContext.getResources().getDrawable(R.drawable.ic_divider));
    }

    /**
     * 恢复数据
     *
     * @param savedInstanceState
     */
    private void recover(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            setPosition(savedInstanceState.getInt(ActionsKeys.POSITION));
        } else {
            setPosition(0);
        }
    }

    /**
     * 设置当前页面位置
     *
     * @param position
     */
    private void setPosition(int position) {
        // 位置非法
        if (position < 0 || position > ARRAY_TITLE.length - 1) return;
        mVpContaner.setCurrentItem(mCurrentPosition, false);
        mCurrentPosition = position;
    }

    /**
     * 获取adapter
     *
     * @return
     */
    private PagerAdapter getFragmentPagerAdapter() {
        return new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = mFragments[position % mFragments.length];
                if (fragment != null) return fragment;
                switch (position) {
                    case 0:
                        fragment = DeviceDetailFragment.newInstance(mDevice);
                        break;
                    case 1:
                        fragment = DeviceRepairListFragment.newInstance(mDevice);
                        break;
                    case 2:
                        fragment = DeviceMaintenanceMainFragment.newInstance(mDevice);
                        break;
                    case 3:
                        fragment = DevicePartListFragment.newInstance(mDevice);
                        break;
                    case 4:
                        fragment = DeviceInspectionListFragment.newInstance(mDevice);
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return ARRAY_TITLE.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return ARRAY_TITLE[position % ARRAY_TITLE.length];
            }
        };
    }

    /**
     * 获取页面改变监听器
     *
     * @return
     */
    @NonNull
    private ViewPager.OnPageChangeListener getPageChangeListener() {
        return new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }
}
