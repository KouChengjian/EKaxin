package com.chenghui.ekaxin.view.list;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.view.SpaceListViewFooter;
import com.chenghui.ekaxin.view.SpaceListViewHeader;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class SpaceListView extends ListView implements OnScrollListener{
	
	private float mLastY = -1;                // 坐标Y
	private Scroller mScroller;               // 用于回滚
	private OnScrollListener mScrollListener; // 滚动监听

	private SpaceListViewListener mListViewListener; // 该接口以触发刷新和加载更多。

	private SpaceListViewHeader mHeaderView;   // 下拉界面
	private TextView mHeaderTimeView;
	private RelativeLayout mHeaderViewContent; // 显示header的大小
	private int mHeaderViewHeight;             // 下拉的高度     
	private boolean mEnablePullRefresh = true; // 启用刷新
	private boolean mPullRefreshing = false;   // 取消

	private SpaceListViewFooter mFooterView;  // 上拉界面
	private boolean mEnablePullLoad;
	private boolean mPullLoading;
	private boolean mIsFooterReady = false;
	
	private int mTotalItemCount;  // 总的列表项，用来检测是ListView的底部

	// 滑动 是上滑还是下拉
	private int mScrollBack;
	private final static int SCROLLBACK_HEADER = 0;
	private final static int SCROLLBACK_FOOTER = 1;

	private final static int SCROLL_DURATION = 400;     // 滑动持续时间
	private final static int PULL_LOAD_MORE_DELTA = 50; // 当上拉大于50px是 加载更多
	private final static float OFFSET_RADIO = 1.8f;     // support iOS like pull feature.
													 

	public SpaceListView(Context context) {
		super(context);
		initWithContext(context);
	}

	public SpaceListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWithContext(context);
	}

	public SpaceListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initWithContext(context);
	}
	
	private void initWithContext(Context context) {
		mScroller = new Scroller(context, new DecelerateInterpolator());
		// XListView need the scroll event, and it will dispatch the event to user's listener (as a proxy).
		super.setOnScrollListener(this);

		// 初始化header view
		mHeaderView = new SpaceListViewHeader(context);
		mHeaderViewContent = (RelativeLayout) mHeaderView.findViewById(R.id.xlistview_header_content);
		mHeaderTimeView = (TextView) mHeaderView.findViewById(R.id.xlistview_header_time);
		addHeaderView(mHeaderView);

		// init footer view
		mFooterView = new SpaceListViewFooter(context);

		// 当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时，所要调用的回调函数的接口类
		mHeaderView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() 
		{
			@Override
			public void onGlobalLayout() 
			{
				// 获取上部分的高度  也就是布局的高度
				mHeaderViewHeight = mHeaderViewContent.getHeight();
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		// make sure XListViewFooter is the last footer view, and only add once.
		if (mIsFooterReady == false) {
			mIsFooterReady = true;
			addFooterView(mFooterView);
		}
		super.setAdapter(adapter);
	}
	
	/**
	 * enable or disable pull down refresh feature.
	 * 启动和禁止下拉刷新的可能
	 * @param enable
	 */
	public void setPullRefreshEnable(boolean enable) {
		mEnablePullRefresh = enable;
		if (!mEnablePullRefresh) { // disable, hide the content
			mHeaderViewContent.setVisibility(View.INVISIBLE);
		} else {
			mHeaderViewContent.setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * enable or disable pull up load more feature.
	 * 启动和禁止上拉刷新的可能
	 * @param enable
	 */
	public void setPullLoadEnable(boolean enable) {
		mEnablePullLoad = enable;
		if (!mEnablePullLoad) {
			mFooterView.hide();
			mFooterView.setOnClickListener(null);
		} else {
			mPullLoading = false;
			mFooterView.show();
			mFooterView.setState(SpaceListViewFooter.STATE_NORMAL);
			// both "pull up" and "click" will invoke load more.
			mFooterView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}
	
	/**
	 * stop refresh, reset header view.
	 * 停止刷新，恢复header view
	 */
	public void stopRefresh() {
		if (mPullRefreshing == true) {
			mPullRefreshing = false;
			resetHeaderHeight();
			mHeaderView.setState(SpaceListViewHeader.STATE_NORMAL);
		}
	}

	/**
	 * stop load more, reset footer view.
	 * 停止加载更多，恢复footer view
	 */
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(SpaceListViewHeader.STATE_NORMAL);
		}
	}
	
	/**
	 * set last refresh time
	 * 设置最后刷新的时间
	 * 
	 * @param time
	 */
	public void setRefreshTime(String time) {
		mHeaderTimeView.setText(time);
	}
	
	private void invokeOnScrolling() {
		if (mScrollListener instanceof OnXScrollListener) {
			OnXScrollListener l = (OnXScrollListener) mScrollListener;
			l.onXScrolling(this);
		}
	}
	
	/**
	 *  开始加载更多
	 */
	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(SpaceListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}
	
	/**
	 *  更新header 的高度
	 */
	private void updateHeaderHeight(float delta) {
		mHeaderView.setVisiableHeight((int) delta+ mHeaderView.getVisiableHeight());
		if (mEnablePullRefresh && !mPullRefreshing) 
		{ 
			// 未处于刷新状态，更新箭头 +300
			if (mHeaderView.getVisiableHeight() > mHeaderViewHeight ) 
			{
				mHeaderView.setState(SpaceListViewHeader.STATE_READY);
			} 
			else 
			{
				mHeaderView.setState(SpaceListViewHeader.STATE_NORMAL);
			}
		}
		setSelection(0); // scroll to top each time
	}
	
	
	/**
	 * reset header view's height.
	 * 恢复header 的高度   +   300
	 */
	private void resetHeaderHeight() {
		int height = mHeaderView.getVisiableHeight();
		if (height == 0) // not visible.
			return;
		// refreshing and header isn't shown fully. do nothing.
		if (mPullRefreshing && height <= mHeaderViewHeight) {
			return;
		}
		int finalHeight = 0; // default: scroll back to dismiss header.
		// is refreshing, just scroll back to show all the header.
		if (mPullRefreshing && height > mHeaderViewHeight) {
			finalHeight = mHeaderViewHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		mScroller.startScroll(0, height, 0, finalHeight - height    ,
				SCROLL_DURATION);
		// trigger computeScroll
		invalidate();
	}
	
	/**
	 *   更新上拉的高度
	 */
	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (mEnablePullLoad && !mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
													// more.
				mFooterView.setState(SpaceListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(SpaceListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);

//		setSelection(mTotalItemCount - 1); // scroll to bottom
	}
	
	/**
	 *  恢复上拉的高度
	 */
	private void resetFooterHeight() {
		int bottomMargin = mFooterView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			mScroller.startScroll(0, bottomMargin, 0, -bottomMargin,
					SCROLL_DURATION);
			invalidate();
		}
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mLastY == -1) {
			mLastY = ev.getRawY();
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 获取点击的坐标
			mLastY = ev.getRawY();
			break;
			
		case MotionEvent.ACTION_MOVE:
			// 移动的距离减去开始的  获取相对的距离
			final float deltaY = ev.getRawY() - mLastY;
			// 获取最后的距离
			mLastY = ev.getRawY();
			// 下拉
			if (getFirstVisiblePosition() == 0
					&& (mHeaderView.getVisiableHeight() > 0 || deltaY > 0)) {
				// the first item is showing, header has shown or pull down.
				updateHeaderHeight(deltaY / OFFSET_RADIO);
				invokeOnScrolling();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1
					&& (mFooterView.getBottomMargin() > 0 || deltaY < 0)) {
				// last item, already pulled up or want to pull up.
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;
		default:
			mLastY = -1; // reset
			if (getFirstVisiblePosition() == 0) {
				// invoke refresh
				if (mEnablePullRefresh
						&& mHeaderView.getVisiableHeight() > mHeaderViewHeight ) 
				{
					mPullRefreshing = true;
					mHeaderView.setState(SpaceListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) 
					{
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			} else if (getLastVisiblePosition() == mTotalItemCount - 1) {
				// invoke load more.
				if (mEnablePullLoad
				    && mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA
				    && !mPullLoading) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		mTotalItemCount = totalItemCount;
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount,
					totalItemCount);
		}
	}
	
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			if (mScrollBack == SCROLLBACK_HEADER) {
				mHeaderView.setVisiableHeight(mScroller.getCurrY());
			} else {
				mFooterView.setBottomMargin(mScroller.getCurrY());
			}
			postInvalidate();
			invokeOnScrolling();
		}
		super.computeScroll();
	}
	
	public void setSpaceListViewListener(SpaceListViewListener l) {
		mListViewListener = l;
	}
	
	/**
	 * you can listen ListView.OnScrollListener or this one. it will invoke
	 * onXScrolling when header/footer scroll back.
	 */
	public interface OnXScrollListener extends OnScrollListener {
		public void onXScrolling(View view);
	}
	
	/**
	 * implements this interface to get refresh/load more event.
	 */
	public interface SpaceListViewListener {
		public void onRefresh();

		public void onLoadMore();
	}

}
