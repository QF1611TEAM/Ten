package com.musketeer.ten.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.musketeer.ten.Beans.NovelBeanList;
import com.musketeer.ten.R;
import com.musketeer.ten.adapters.NovelAdapter;
import com.musketeer.ten.constants.HttpConstant;
import com.musketeer.ten.events.NovelEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kevin on 2016/9/20.
 */
public class NovelFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    public static final String TAG = NovelFragment.class.getSimpleName();
    @BindView(R.id.novel_data_shi)
    ImageView mNovelDataShi;
    @BindView(R.id.novel_data_ge)
    ImageView mNovelDataGe;
    @BindView(R.id.novel_week)
    ImageView mNovelWeek;
    @BindView(R.id.novel_month)
    ImageView mNovelMonth;
    @BindView(R.id.novel_Viewpager)
    ViewPager mNovelViewpager;
    private NovelAdapter adapter;
    private List<NovelBeanList.ResultBean> Results;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.novelfragment, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpView();
        initView();

    }
    private void initView() {
        List<NovelViewpagerFragment> data = new ArrayList<>();
        for (int i = 0; i <10; i++) {
            NovelViewpagerFragment viewpagerFragment = new NovelViewpagerFragment();
            Bundle bundle = new Bundle();
//            int id = Results.get(i).getId();
            bundle.putInt("id",i);
            viewpagerFragment.setArguments(bundle);
            data.add(viewpagerFragment);
        }
        adapter = new NovelAdapter(getFragmentManager(),data);
        mNovelViewpager.setAdapter(adapter);
        mNovelViewpager.addOnPageChangeListener(this);
    }
    private void setUpView() {
        RequestParams params = new RequestParams(HttpConstant.NOVELLIST_URL);
        x.http().get(params, new Callback.CommonCallback<NovelBeanList>() {
            @Override
            public void onSuccess(NovelBeanList result) {
                Log.e(TAG, "onSuccess: 美文"+result);
                Results=result.getResult();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e(TAG, "onError: 美文" + ex.getMessage());
                Toast.makeText(getActivity(), "网络故障了，下拉刷新试试。。。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    //在页面滑动的时候
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    /**
     * 在页面被选中的时候,滑动结束，处于屏幕中间的页面发生变化了
     * @param position   当前处于屏幕中间的页面
     */
    @Override
    public void onPageSelected(int position) {
        Log.e(TAG, "onPageSelected: "+position);
        Toast.makeText(getActivity(),"当前页面:"+position,Toast.LENGTH_SHORT).show();
    }
   /**
    * 在滑动状态改变的时候
    * 滑动状态：① 静止                            0
    *           ② 在外力的作用下进行滚动           1
    *           ③ 在不受外力的作用下进行滚动        2
    */
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
