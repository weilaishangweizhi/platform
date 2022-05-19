package com.hollysmart.platformsdk.dialog;

import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

public class CaiPopupWindow extends PopupWindow {

    public CaiPopupWindow(View mMenuView, int matchParent, int matchParent1) {
        super(mMenuView, matchParent,matchParent1);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }


    @Override
    public void showAsDropDown(View anchorView, int xoff, int yoff) {

        if(Build.VERSION.SDK_INT ==  Build.VERSION_CODES.N) {
            int[] a = new int[2];
            anchorView.getLocationInWindow(a);
            showAtLocation(anchorView, Gravity.NO_GRAVITY, xoff, a[1] + anchorView.getHeight() + yoff);
        } else {
            super.showAsDropDown(anchorView, xoff, yoff);
        }
    }

}