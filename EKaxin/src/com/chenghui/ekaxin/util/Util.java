package com.chenghui.ekaxin.util;

import android.content.res.Resources;
import android.util.TypedValue;


/**
 * @ClassName: Util
 * @Description: JazzyView¹¤¾ß
 * @author kcj
 * @date 
 */
public class Util {

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}

}
