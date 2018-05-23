package com.shra1.scrollzzdemo.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;

/**
 * Created by Shrawan WABLE on 2/27/2018.
 */
public class MyHorizontalScrollView extends HorizontalScrollView {

   public OnScrollChangedListener mOnScrollChangedListener;

   public MyHorizontalScrollView(Context context) {
      super(context);
   }

   public MyHorizontalScrollView(Context context, AttributeSet attrs) {
      super(context, attrs);
   }

   public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   @Override
   protected void onScrollChanged(int l, int t, int oldl, int oldt) {
      super.onScrollChanged(l, t, oldl, oldt);

      if (mOnScrollChangedListener != null) {
         mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
      }
   }

   public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener){
      this.mOnScrollChangedListener = onScrollChangedListener;
   }

   public interface OnScrollChangedListener{
      void onScrollChanged(int l, int t, int oldl, int oldt);
   }

}
