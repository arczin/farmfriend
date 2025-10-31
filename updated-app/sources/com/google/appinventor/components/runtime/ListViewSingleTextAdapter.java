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

public class ListViewSingleTextAdapter extends ListAdapterWithRecyclerView {
    private int textMainColor;
    private String textMainFont;
    private float textMainSize;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ListViewSingleTextAdapter(ComponentContainer container, List<Object> data, int textMainColor2, float textMainSize2, String textMainFont2, int textDetailColor, float textDetailSize, String textDetailFont, int backgroundColor, int selectionColor, int radius, int imageWidth, int imageHeight) {
        super(container, data, backgroundColor, selectionColor, (float) radius);
        this.container = container;
        this.textMainColor = textMainColor2;
        this.textMainSize = textMainSize2;
        this.textMainFont = textMainFont2;
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
        LinearLayout linearLayout1 = new LinearLayout(this.container.$context());
        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        linearLayout1.setOrientation(0);
        linearLayout1.addView(textViewFirst);
        cardView.addView(linearLayout1);
        return new SingleTextRvViewHolder(cardView, idCard, idFirst);
    }

    public void onBindViewHolder(ListAdapterWithRecyclerView.RvViewHolder holder, int position) {
        SingleTextRvViewHolder singleTextHolder = (SingleTextRvViewHolder) holder;
        Object o = this.items.get(position);
        YailDictionary dictItem = new YailDictionary();
        if (!(o instanceof YailDictionary)) {
            dictItem.put(Component.LISTVIEW_KEY_MAIN_TEXT, o.toString());
        } else if (((YailDictionary) o).containsKey(Component.LISTVIEW_KEY_MAIN_TEXT)) {
            dictItem = (YailDictionary) o;
        } else {
            dictItem.put(Component.LISTVIEW_KEY_MAIN_TEXT, o.toString());
        }
        singleTextHolder.textViewFirst.setText(dictItem.get(Component.LISTVIEW_KEY_MAIN_TEXT).toString());
        updateCardViewColor(singleTextHolder.cardView, position);
    }

    public class SingleTextRvViewHolder extends ListAdapterWithRecyclerView.RvViewHolder {
        public CardView cardView;
        public TextView textViewFirst;

        public /* bridge */ /* synthetic */ void onClick(View view) {
            super.onClick(view);
        }

        public SingleTextRvViewHolder(View view, int idCard, int idFirst) {
            super(view);
            this.cardView = view.findViewById(idCard);
            this.textViewFirst = (TextView) view.findViewById(idFirst);
        }
    }
}
