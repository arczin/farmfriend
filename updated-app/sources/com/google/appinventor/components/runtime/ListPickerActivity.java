package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.appinventor.components.runtime.util.AnimationUtil;

public class ListPickerActivity extends AppInventorCompatActivity implements AdapterView.OnItemClickListener {
    static int backgroundColor;
    static int itemColor;
    MyAdapter adapter;
    private String closeAnim = "";
    private ListView listView;
    EditText txtSearchBox;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        styleTitleBar();
        LinearLayout viewLayout = new LinearLayout(this);
        viewLayout.setOrientation(1);
        Intent myIntent = getIntent();
        if (myIntent.hasExtra(ListPicker.LIST_ACTIVITY_ANIM_TYPE)) {
            this.closeAnim = myIntent.getStringExtra(ListPicker.LIST_ACTIVITY_ANIM_TYPE);
        }
        if (myIntent.hasExtra(ListPicker.LIST_ACTIVITY_ORIENTATION_TYPE)) {
            String orientation = myIntent.getStringExtra(ListPicker.LIST_ACTIVITY_ORIENTATION_TYPE).toLowerCase();
            if (orientation.equals("portrait")) {
                setRequestedOrientation(1);
            } else if (orientation.equals("landscape")) {
                setRequestedOrientation(0);
            }
        }
        if (myIntent.hasExtra(ListPicker.LIST_ACTIVITY_TITLE)) {
            setTitle(myIntent.getStringExtra(ListPicker.LIST_ACTIVITY_TITLE));
        }
        if (myIntent.hasExtra(ListPicker.LIST_ACTIVITY_ARG_NAME)) {
            String[] items = getIntent().getStringArrayExtra(ListPicker.LIST_ACTIVITY_ARG_NAME);
            this.listView = new ListView(this);
            this.listView.setOnItemClickListener(this);
            this.listView.setScrollingCacheEnabled(false);
            itemColor = myIntent.getIntExtra(ListPicker.LIST_ACTIVITY_ITEM_TEXT_COLOR, -1);
            backgroundColor = myIntent.getIntExtra(ListPicker.LIST_ACTIVITY_BACKGROUND_COLOR, -16777216);
            viewLayout.setBackgroundColor(backgroundColor);
            this.adapter = new MyAdapter(this, items);
            this.listView.setAdapter(this.adapter);
            String showFilterBar = myIntent.getStringExtra(ListPicker.LIST_ACTIVITY_SHOW_SEARCH_BAR);
            this.txtSearchBox = new EditText(this);
            this.txtSearchBox.setSingleLine(true);
            this.txtSearchBox.setWidth(-2);
            this.txtSearchBox.setPadding(10, 10, 10, 10);
            this.txtSearchBox.setHint("Search list...");
            if (!isClassicMode()) {
                this.txtSearchBox.setBackgroundColor(-1);
            }
            if (showFilterBar == null || !showFilterBar.equalsIgnoreCase("true")) {
                this.txtSearchBox.setVisibility(8);
            }
            this.txtSearchBox.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    ListPickerActivity.this.adapter.getFilter().filter(cs);
                }

                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                public void afterTextChanged(Editable arg0) {
                }
            });
        } else {
            setResult(0);
            finish();
            AnimationUtil.ApplyCloseScreenAnimation((Activity) this, this.closeAnim);
        }
        viewLayout.addView(this.txtSearchBox);
        viewLayout.addView(this.listView);
        setContentView(viewLayout);
        viewLayout.requestLayout();
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        getWindow().setSoftInputMode(3);
    }

    /* JADX WARNING: type inference failed for: r5v0, types: [android.widget.AdapterView<?>, android.widget.AdapterView] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onItemClick(android.widget.AdapterView<?> r5, android.view.View r6, int r7, long r8) {
        /*
            r4 = this;
            android.widget.Adapter r0 = r5.getAdapter()
            java.lang.Object r0 = r0.getItem(r7)
            java.lang.String r0 = (java.lang.String) r0
            android.content.Intent r1 = new android.content.Intent
            r1.<init>()
            java.lang.String r2 = com.google.appinventor.components.runtime.ListPicker.LIST_ACTIVITY_RESULT_NAME
            r1.putExtra(r2, r0)
            java.lang.String r2 = com.google.appinventor.components.runtime.ListPicker.LIST_ACTIVITY_RESULT_INDEX
            int r3 = r7 + 1
            r1.putExtra(r2, r3)
            r4.closeAnim = r0
            r2 = -1
            r4.setResult(r2, r1)
            r4.finish()
            java.lang.String r2 = r4.closeAnim
            com.google.appinventor.components.runtime.util.AnimationUtil.ApplyCloseScreenAnimation((android.app.Activity) r4, (java.lang.String) r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.ListPickerActivity.onItemClick(android.widget.AdapterView, android.view.View, int, long):void");
    }

    public void onBackPressed() {
        AnimationUtil.ApplyCloseScreenAnimation((Activity) this, this.closeAnim);
        super.onBackPressed();
    }

    private static class MyAdapter extends ArrayAdapter<String> {
        private final Context mContext;

        public MyAdapter(Context context, String[] items) {
            super(context, 17367040, items);
            this.mContext = context;
        }

        public long getItemId(int position) {
            return (long) ((String) getItem(position)).hashCode();
        }

        /* JADX WARNING: type inference failed for: r1v5, types: [android.view.View] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.view.View getView(int r5, android.view.View r6, android.view.ViewGroup r7) {
            /*
                r4 = this;
                r0 = r6
                android.widget.TextView r0 = (android.widget.TextView) r0
                if (r0 != 0) goto L_0x0016
                android.content.Context r1 = r4.mContext
                android.view.LayoutInflater r1 = android.view.LayoutInflater.from(r1)
                r2 = 17367043(0x1090003, float:2.5162934E-38)
                r3 = 0
                android.view.View r1 = r1.inflate(r2, r7, r3)
                r0 = r1
                android.widget.TextView r0 = (android.widget.TextView) r0
            L_0x0016:
                java.lang.Object r1 = r4.getItem(r5)
                java.lang.CharSequence r1 = (java.lang.CharSequence) r1
                r0.setText(r1)
                int r1 = com.google.appinventor.components.runtime.ListPickerActivity.itemColor
                r0.setTextColor(r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.ListPickerActivity.MyAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
        }
    }
}
