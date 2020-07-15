package com.zk.mvp.mvp.view.activity.login;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.zk.mvp.R;
import com.zk.mvp.adapter.GuideViewPagerAdapter;
import com.zk.mvp.base.BaseActivity;
import com.zk.mvp.base.BaseContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.blankj.utilcode.util.SizeUtils.px2dp;

/**
 * ViewPager2引导页
 */
public class GuideActivity extends BaseActivity {
    private static final String TAG = "GuideActivity";
    @BindView(R.id.view_pager2)
    ViewPager2 viewPager2;
    @BindView(R.id.ll_guide_sing)
    LinearLayout ll;

    private GuideViewPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initWindow() {

    }

    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initViews() {
        adapter = new GuideViewPagerAdapter(this);
    }

    List<Integer> img;
    @Override
    protected void initDatas() {
        viewPager2.setAdapter(adapter);
        //禁止滚动true为可以滑动false为禁止
        viewPager2.setUserInputEnabled(true);
        //设置垂直滚动ORIENTATION_VERTICAL，横向的为
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        //切换到指定页，是否展示过渡中间页
//        viewPager2.setCurrentItem(1,true);
        //滑动监听
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.i(TAG, "onPageScrolled: "+position+"--->"+positionOffset+"--->"+positionOffsetPixels );
//                switchPageHandler.removeMessages(position + 1);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Log.i(TAG, "onPageSelected: "+position);
//                switchIcon(position);
//                switchPageHandler.sendEmptyMessageDelayed(position + 1 == img.size() ? position : position + 1,5000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                Log.i(TAG, "onPageScrollStateChanged: "+state);
            }
        });

        img = new ArrayList<>();
//        img.add(R.drawable.img1);
//        img.add(R.drawable.img2);
//        img.add(R.drawable.img3);
        adapter.setImgs(img);
        switchIcon(0);

    }

    /**
     * 切换小圆点
     * @param index
     */
    public void switchIcon(int index){
        ll.removeAllViews();
        for (int i = 0; i < img.size(); i++) {
            ImageView iv = new ImageView(this);
            if (i == index) {
                iv.setBackgroundResource(R.drawable.page_white);
            }else {
                iv.setBackgroundResource(R.drawable.page_gray);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(px2dp(10),0,px2dp(10),0);
            iv.setLayoutParams(lp);
            ll.addView(iv);
            Log.i(TAG, "switchIcon: "+i);
        }
    }

    /**
     * 切换页面
     */
    @SuppressLint("HandlerLeak")
    Handler switchPageHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
                viewPager2.setCurrentItem(msg.what,true);
                switchIcon(msg.what);
        }
    };
}
