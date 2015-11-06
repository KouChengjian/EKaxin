package com.chenghui.ekaxin.ui;

import java.util.ArrayList;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.view.HeaderLayout.onRightImageButtonClickListener;


/**
 * @ClassName: SelectCardActivity
 * @Description: 名片模板选择
 * @author kcj
 * @date 
 */
public class SelectCardActivity extends BaseActivity {

	private static int     TOTAL_COUNT = 10;
    private RelativeLayout viewPagerContainer;
    private ViewPager      viewPager;
    
    private ArrayList<View> list;
	private ImageView imageView;
	private ImageView[] imageViews;

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_selectcard);
		initEditCardAllUi();
		//initView();
	}
	
	public void initEditCardAllUi(){
		initTopBarForBoth("选择模板", R.drawable.base_action_bar_add_bg_selector,
				new onRightImageButtonClickListener() {
					@Override
					public void onClick(View v) {
					}
				});
		viewPager = (ViewPager)findViewById(R.id.vp_selectcard);
        viewPagerContainer = (RelativeLayout)findViewById(R.id.rl_selectcard_viewpage);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOffscreenPageLimit(TOTAL_COUNT);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        viewPagerContainer.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return viewPager.dispatchTouchEvent(event);
			}
        });
        imageViews = new ImageView[TOTAL_COUNT];
        ViewGroup group = (ViewGroup)findViewById(R.id.ll_selectcard_identifying);
        for(int i=0; i < TOTAL_COUNT; i++){
        	imageView = new ImageView(this);
	    	imageView.setLayoutParams(new LayoutParams(12,12));
	    	imageViews[i] = imageView;
	    	if(i == 0){
	    		imageView.setBackgroundResource(R.drawable.item3);
	    	}else{
	    		imageView.setBackgroundResource(R.drawable.item4);
	    	}
	    	group.addView(imageView);
        }
	}
	
	class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return TOTAL_COUNT;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(SelectCardActivity.this);
            imageView.setImageResource(R.drawable.bcard_d0 + position);
            ((ViewPager)container).addView(imageView, position);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView((ImageView)object);
        }
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
        	for(int i = 0 ; i<imageViews.length; i++){
    			if(i == position){
    				imageViews[i].setBackgroundResource(R.drawable.item3);
    			}else{
    				imageViews[i].setBackgroundResource(R.drawable.item4);
    			}
    		}
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // to refresh frameLayout
            if (viewPagerContainer != null) {
                viewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }
}
