package com.chenghui.ekaxin.ui.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.chenghui.ekaxin.R;
import com.chenghui.ekaxin.ui.FragmentBase;
import com.chenghui.ekaxin.ui.MainActivity;
import com.chenghui.ekaxin.view.RadialMenuWidget;
import com.chenghui.ekaxin.view.RadialMenuWidget.RadialMenuEntry;
import com.chenghui.ekaxin.view.SearchDevicesView;

/**
 * 一键交换      (旧)
 * 
 * @ClassName: ExchangeFragment
 * @Description: TODO
 * @author kcj
 * @date 2014-10-10 9:20
 */
public class ExchangFragment extends FragmentBase {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_exchange, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initMenu();
		// initImgView();
		initBroadcastReceiver();
	}

	SearchDevicesView search_device_view;

	private void initView() {
		initTopBarForOnlyTitle("一键交换");
		// 雷达搜索
		search_device_view = (SearchDevicesView) findViewById(R.id.search_device_view);
		search_device_view.setWillNotDraw(false);
		// search_device_view.setVisibility(View.VISIBLE);
	}

	boolean menuSign = false;
	private RelativeLayout ll;
	private RadialMenuWidget PieMenu;

	public void initMenu() {
		ll = (RelativeLayout) findViewById(R.id.layout_menu);
		PieMenu = new RadialMenuWidget(getActivity());
		PieMenu.setAnimationSpeed(0L);
		int xLayoutSize = ll.getWidth();
		int yLayoutSize = ll.getHeight();
		PieMenu.setSourceLocation(xLayoutSize, yLayoutSize);
		PieMenu.setIconSize(15, 30);
		PieMenu.setTextSize(13);
		PieMenu.setCenterCircle(new Close());
		PieMenu.addMenuEntry(new Friend());
		PieMenu.addMenuEntry(new Search());
		ll.addView(PieMenu);
		menuSign = true;
	}

	private void initImgView() {
		/**  
		CircleImageView[] imgView = new CircleImageView[6];
		imgView[0] = (CircleImageView) findViewById(R.id.imageView1);
		imgView[1] = (CircleImageView) findViewById(R.id.imageView2);
		imgView[2] = (CircleImageView) findViewById(R.id.imageView3);
		imgView[3] = (CircleImageView) findViewById(R.id.imageView4);
		imgView[4] = (CircleImageView) findViewById(R.id.imageView5);
		imgView[5] = (CircleImageView) findViewById(R.id.imageView6);

		Bitmap btm1 = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.head);
		Bitmap mBitmap = Bitmap.createScaledBitmap(btm1, 100, 100, true);
		imgView[0].setImageBitmap(mBitmap);

		Animation anim = AnimationUtils.loadAnimation(getActivity(),
				R.anim.unzoom_in);
		imgView[0].startAnimation(anim);*/
	}

	public class Close implements RadialMenuEntry {
		public String getName() {
			return "Close";
		}

		public String getLabel() {
			return null;
		}

		public int getIcon() {
			return R.drawable.add;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			// ((RelativeLayout) PieMenu.getParent()).removeView(PieMenu);
			// Log.e("TAG","lose Menu Activated");
		}
	}

	public class Search implements RadialMenuEntry {
		public String getName() {
			return "SearchFriend";
		}

		public String getLabel() {
			return "搜索";
		}

		public int getIcon() {
			return R.drawable.ic_action_search;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			// Log.e("TAG","Menu #1 Activated - No Children");
			menuSign = false;
			Animation anim = AnimationUtils.loadAnimation(getActivity(),
					R.anim.appear_bottom_right_out);
			ll.startAnimation(anim);
			anim.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
				}
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
				}
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					((RelativeLayout) PieMenu.getParent()).removeView(PieMenu);
					search_device_view.setVisibility(View.VISIBLE);
					Animation anims =AnimationUtils.loadAnimation(getActivity(),R.anim.in_translate_top);
					ll.startAnimation(anims);
					anims.setAnimationListener(new AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onAnimationRepeat(Animation animation) {
							// TODO Auto-generated method stub
						}
						@Override
						public void onAnimationEnd(Animation animation) {
							// TODO Auto-generated method stub
							search_device_view.setSearching(true);
							//initImgView();
						}
					});
				}
			});
			
			
		}
	}

	public class Friend implements RadialMenuEntry {
		public String getName() {
			return "Friend";
		}

		public String getLabel() {
			return "好友";
		}

		public int getIcon() {
			return R.drawable.ic_action_refresh;
		}

		private List<RadialMenuEntry> children = new ArrayList<RadialMenuEntry>(
				Arrays.asList(new NewFriend(), new NoAddFriend(),
						new Blacklist()));

		public List<RadialMenuEntry> getChildren() {
			return children;
		}

		public void menuActiviated() {
			Log.e("TAG", "Menu #2 Activated - Children");
		}
	}

	public static class NewFriend implements RadialMenuEntry {
		public String getName() {
			return "NewFriend";
		}

		public String getLabel() {
			return "新朋友";
		}

		public int getIcon() {
			return 0;
			// return R.drawable.icon_set_press;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("IconOnly Menu Activated");
		}
	}

	public static class NoAddFriend implements RadialMenuEntry {
		public String getName() {
			return "NoAddFriend";
		}

		public String getLabel() {
			return "未添加";
		}

		public int getIcon() {
			return 0;
			// return R.drawable.icon_set_press;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("StringAndIcon Menu Activated");
		}
	}

	public static class Blacklist implements RadialMenuEntry {
		public String getName() {
			return "Blacklist";
		}

		public String getLabel() {
			return "黑名单";
		}

		public int getIcon() {
			return 0;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("StringOnly Menu Activated");
		}
	}

	void refreshMenu() {
		if (MainActivity.index == 2) {
			if (!menuSign) {
				menuSign = true;
				search_device_view.setVisibility(View.GONE);
				ll.addView(PieMenu);
			}
			//Log.e("TAG", "打开");
		} else if (MainActivity.index == 0 || MainActivity.index == 1
				|| MainActivity.index == 3 || MainActivity.index == 4) {
			if (menuSign) {
				menuSign = false;
				((RelativeLayout) PieMenu.getParent()).removeView(PieMenu);
			}
			//Log.e("TAG", "关闭");
		}
	}

	public void initBroadcastReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("action.refreshMenu");
		getActivity().registerReceiver(mRefreshMenuBroadcastReceiver,
				intentFilter);
	}

	private BroadcastReceiver mRefreshMenuBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("action.refreshMenu")) {
				//refreshMenu();
			}
		}
	};
}
