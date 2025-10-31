package com.google.appinventor.components.runtime;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import com.google.appinventor.components.runtime.ListAdapterWithRecyclerView;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.YailDictionary;
import java.util.List;

public class ListViewTwoTextAdapter extends ListAdapterWithRecyclerView {
    private int textDetailColor;
    private String textDetailFont;
    private float textDetailSize;
    private int textMainColor;
    private String textMainFont;
    private float textMainSize;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ListViewTwoTextAdapter(ComponentContainer container, List<Object> data, int textMainColor2, float textMainSize2, String textMainFont2, int textDetailColor2, float textDetailSize2, String textDetailFont2, int backgroundColor, int selectionColor, int radius, int imageWidth, int imageHeight) {
        super(container, data, backgroundColor, selectionColor, (float) radius);
        this.container = container;
        this.textMainColor = textMainColor2;
        this.textMainSize = textMainSize2;
        this.textMainFont = textMainFont2;
        this.textDetailColor = textDetailColor2;
        this.textDetailSize = textDetailSize2;
        this.textDetailFont = textDetailFont2;
    }

    public ListAdapterWithRecyclerView.RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = createCardView(parent);
        int idCard = cardView.getId();
        TextView textViewFirst = new TextView(this.container.$context());
        int idFirst = ViewCompat.generateViewId();
        textViewFirst.setId(idFirst);
        textViewFirst.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        textViewFirst.setTextSize(this.textMainSize);
        textViewFirst.setTextColor(this.textMainColor);
        TextViewUtil.setFontTypeface(this.container.$form(), textViewFirst, this.textMainFont, false, false);
        TextView textViewSecond = new TextView(this.container.$context());
        int idSecond = ViewCompat.generateViewId();
        textViewSecond.setId(idSecond);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        textViewSecond.setTextSize(this.textDetailSize);
        TextViewUtil.setFontTypeface(this.container.$form(), textViewSecond, this.textDetailFont, false, false);
        textViewSecond.setTextColor(this.textDetailColor);
        textViewSecond.setLayoutParams(layoutParams2);
        LinearLayout linearLayout2 = new LinearLayout(this.container.$context());
        LinearLayout.LayoutParams layoutParamslinear2 = new LinearLayout.LayoutParams(0, -2, 2.0f);
        linearLayout2.setLayoutParams(layoutParamslinear2);
        linearLayout2.setOrientation(1);
        linearLayout2.addView(textViewFirst);
        linearLayout2.addView(textViewSecond);
        LinearLayout linearLayout1 = new LinearLayout(this.container.$context());
        LinearLayout.LayoutParams layoutParamslinear1 = new LinearLayout.LayoutParams(-1, -2);
        linearLayout1.setLayoutParams(layoutParamslinear1);
        linearLayout1.setOrientation(0);
        linearLayout1.addView(linearLayout2);
        cardView.addView(linearLayout1);
        LinearLayout.LayoutParams layoutParams = layoutParamslinear1;
        LinearLayout linearLayout = linearLayout1;
        LinearLayout.LayoutParams layoutParams3 = layoutParamslinear2;
        return new TwoTextRvViewHolder(cardView, idCard, idFirst, idSecond);
    }

    public void onBindViewHolder(ListAdapterWithRecyclerView.RvViewHolder holder, int position) {
        TwoTextRvViewHolder twoTextHolder = (TwoTextRvViewHolder) holder;
        Object o = this.items.get(position);
        YailDictionary dictItem = new YailDictionary();
        if (!(o instanceof YailDictionary)) {
            dictItem.put(Component.LISTVIEW_KEY_MAIN_TEXT, o.toString());
        } else if (((YailDictionary) o).containsKey(Component.LISTVIEW_KEY_MAIN_TEXT)) {
            dictItem = (YailDictionary) o;
        } else {
            dictItem.put(Component.LISTVIEW_KEY_MAIN_TEXT, o.toString());
        }
        String first = dictItem.get(Component.LISTVIEW_KEY_MAIN_TEXT).toString();
        String second = "";
        if (dictItem.containsKey(Component.LISTVIEW_KEY_DESCRIPTION)) {
            second = dictItem.get(Component.LISTVIEW_KEY_DESCRIPTION).toString();
        }
        twoTextHolder.textViewFirst.setText(first);
        twoTextHolder.textViewSecond.setText(second);
        updateCardViewColor(twoTextHolder.cardView, position);
    }

    public class TwoTextRvViewHolder extends ListAdapterWithRecyclerView.RvViewHolder {
        public CardView cardView;
        public TextView textViewFirst;
        public TextView textViewSecond;

        public /* bridge */ /* synthetic */ void onClick(View view) {
            super.onClick(view);
        }

        public TwoTextRvViewHolder(View view, int idCard, int idFirst, int idSecond) {
            super(view);
            this.cardView = view.findViewById(idCard);
            this.textViewFirst = (TextView) view.findViewById(idFirst);
            this.textViewSecond = (TextView) view.findViewById(idSecond);
        }
    }
}
