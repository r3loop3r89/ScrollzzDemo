package com.shra1.scrollzzdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shra1.scrollzzdemo.customviews.MyHorizontalScrollView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
   Context mCtx;
   ProgressDialog progressDialog;
   TableRow topHeadingRow;
   TableRow XYRow;
   private TableLayout tableTopLeft;
   private MyHorizontalScrollView topHorizontalScroll;
   private TableLayout tableTopHeadings;
   private ScrollView midMainVerticalScroll;
   private TableLayout tableHeadingColumns;
   private MyHorizontalScrollView midCommonHorizontalScroll;
   private ScrollView midCommonVerticalScroll;
   private TableLayout tableXYScrollableContent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      mCtx = this;

      progressDialog = new ProgressDialog(this);

      initViews();

      topHeadingRow = getNewRow();


      midCommonHorizontalScroll.setOnScrollChangedListener(new MyHorizontalScrollView.OnScrollChangedListener() {
         @Override
         public void onScrollChanged(int l, int t, int oldl, int oldt) {
            topHorizontalScroll.scrollTo(l, t);
         }
      });

      topHorizontalScroll.setOnScrollChangedListener(new MyHorizontalScrollView.OnScrollChangedListener() {
         @Override
         public void onScrollChanged(int l, int t, int oldl, int oldt) {
            midCommonHorizontalScroll.scrollTo(l, t);
         }
      });


      getTheString(7, 50, new StringCallback() {
         @Override
         public void onComplete(String str) {

            try {
               JSONObject mainObject = new JSONObject(str);
               JSONArray mainArray = mainObject.getJSONArray("values");

               for (int i = 0; i < mainArray.length(); i++) {
                  JSONObject subObject = mainArray.getJSONObject(i);
                  JSONArray subArray = subObject.getJSONArray("values");

                  XYRow = getNewRow();

                  for (int j = 0; j < subArray.length(); j++) {

                     if (i == 0 && j == 0) {
                        //get the first object
                        fillTheTopLeftElement(subArray.getString(j));

                     } else if (i == 0 && j != 0) {
                        //get the first row (header row)
                        fillTheTableTopHeadings(subArray.getString(j));

                     } else if (i != 0 && j == 0) {
                        //get the first column (header column)
                        fillTheTableHeadingColumns(subArray.getString(j));

                     } else {
                        fillTheTableXYScrollableContent(subArray.getString(j));
                     }

                  }
                  tableXYScrollableContent.addView(XYRow);
               }
               tableTopHeadings.addView(topHeadingRow);


            } catch (Exception e) {
               e.printStackTrace();
            }

         }
      });

   }

   private void fillTheTableXYScrollableContent(String string) {
      TextView tv = getDefaultTextView();
      tv.setText(string);
      XYRow.addView(tv);
   }

   private void fillTheTableHeadingColumns(String string) {
      TableRow row = getNewRow();
      TextView tv = getDefaultTextView();
      tv.setTypeface(null, Typeface.BOLD);
      tv.setText(string);
      row.addView(tv);
      tableHeadingColumns.addView(row);
   }

   private void fillTheTableTopHeadings(String string) {
      TextView tv = getDefaultTextView();
      tv.setText(string);
      tv.setTypeface(null, Typeface.BOLD);
      topHeadingRow.addView(tv);
   }

   private void fillTheTopLeftElement(String string) {
      TableRow row = getNewRow();
      TextView tv = getDefaultTextView();
      tv.setText(string);
      row.addView(tv);
      tv.setTypeface(null, Typeface.BOLD);
      tableTopLeft.removeAllViews();
      tableTopLeft.addView(row);
   }

   private TextView getDefaultTextView() {
      TextView textView = new TextView(mCtx);
      textView.setLayoutParams(new TableRow.LayoutParams((int) mCtx.getResources().getDimension(R.dimen.cell_width), (int) mCtx.getResources().getDimension(R.dimen.cell_height)));
      textView.setGravity(Gravity.CENTER);
      textView.setSingleLine(true);
      textView.setEllipsize(TextUtils.TruncateAt.END);
      textView.setBackgroundDrawable(mCtx.getResources().getDrawable(R.drawable.tv_bg));
      textView.setPadding(10, 5, 10, 5);
      return textView;
   }

   private TableRow getNewRow() {
      TableRow row = new TableRow(mCtx);
      row.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
      return row;
   }

   private void getTheString(int columns, int rows, final StringCallback c) {

      new AsyncTask<Integer, Void, String>() {
         @Override
         protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
         }

         @Override
         protected String doInBackground(Integer... ints) {
            int columns = ints[0];
            int rows = ints[1];

            JSONArray mainArray = new JSONArray();

            for (int i = 0; i < rows; i++) {
               JSONArray subArray = new JSONArray();
               for (int j = 0; j < columns; j++) {
                  subArray.put("" + Math.random());
               }
               mainArray.put(subArray);
            }

            Gson gson = new Gson();


            /*JSONArray mainArray = new JSONArray();


            JSONArray firstObjectArray = new JSONArray();
            firstObjectArray.put("Tipsters");
            firstObjectArray.put("Game 1");
            firstObjectArray.put("Margin");
            firstObjectArray.put("Game 2");
            firstObjectArray.put("Game 3");

            JSONArray secondObjectArray = new JSONArray();
            secondObjectArray.put("Shra1");
            secondObjectArray.put("7");
            secondObjectArray.put("8");
            secondObjectArray.put("9");
            secondObjectArray.put("10");

            JSONArray thirdObjectArray = new JSONArray();
            thirdObjectArray.put("Sagar");
            thirdObjectArray.put("12");
            thirdObjectArray.put("13");
            thirdObjectArray.put("14");
            thirdObjectArray.put("15");

            JSONArray fourthObjectArray = new JSONArray();
            fourthObjectArray.put("Shashank");
            fourthObjectArray.put("17");
            fourthObjectArray.put("18");
            fourthObjectArray.put("19");
            fourthObjectArray.put("20");

            mainArray.put(firstObjectArray);
            mainArray.put(secondObjectArray);
            mainArray.put(thirdObjectArray);
            mainArray.put(fourthObjectArray);


            Gson gson = new Gson();*/

            return gson.toJson(mainArray);

         }

         @Override
         protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing()) {
               progressDialog.dismiss();
            }
            c.onComplete(s);
         }
      }.execute(columns, rows);

   }

   private void initViews() {
      tableTopLeft = (TableLayout) findViewById(R.id.tableTopLeft);
      topHorizontalScroll = (MyHorizontalScrollView) findViewById(R.id.topHorizontalScroll);
      tableTopHeadings = (TableLayout) findViewById(R.id.tableTopHeadings);
      midMainVerticalScroll = (ScrollView) findViewById(R.id.midMainVerticalScroll);
      tableHeadingColumns = (TableLayout) findViewById(R.id.tableHeadingColumns);
      midCommonHorizontalScroll = (MyHorizontalScrollView) findViewById(R.id.midCommonHorizontalScroll);
      //midCommonVerticalScroll = (ScrollView) findViewById(R.id.midCommonVerticalScroll);
      tableXYScrollableContent = (TableLayout) findViewById(R.id.tableXYScrollableContent);
   }

   interface StringCallback {
      public void onComplete(String string);
   }
}
