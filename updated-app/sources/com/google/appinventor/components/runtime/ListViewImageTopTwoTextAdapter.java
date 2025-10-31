package com.google.appinventor.components.runtime;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import com.google.appinventor.components.runtime.ListAdapterWithRecyclerView;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.google.appinventor.components.runtime.util.TextViewUtil;
import com.google.appinventor.components.runtime.util.ViewUtil;
import com.google.appinventor.components.runtime.util.YailDictionary;
import java.io.IOException;
import java.util.List;

public class ListViewImageTopTwoTextAdapter extends ListAdapterWithRecyclerView {
    private int imageHeight;
    private int imageWidth;
    private int textDetailColor;
    private String textDetailFont;
    private float textDetailSize;
    private int textMainColor;
    private String textMainFont;
    private float textMainSize;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ListViewImageTopTwoTextAdapter(ComponentContainer container, List<Object> data, int textMainColor2, float textMainSize2, String textMainFont2, int textDetailColor2, float textDetailSize2, String textDetailFont2, int backgroundColor, int selectionColor, int radius, int imageWidth2, int imageHeight2) {
        super(container, data, backgroundColor, selectionColor, (float) radius);
        this.container = container;
        this.textMainColor = textMainColor2;
        this.textMainSize = textMainSize2;
        this.textMainFont = textMainFont2;
        this.textDetailColor = textDetailColor2;
        this.textDetailSize = textDetailSize2;
        this.textDetailFont = textDetailFont2;
        this.imageWidth = imageWidth2;
        this.imageHeight = imageHeight2;
    }

    public ListAdapterWithRecyclerView.RvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout cardView = createCardView(parent);
        int idCard = cardView.getId();
        ImageView imageView = new ImageView(this.container.$context());
        int idImage = ViewCompat.generateViewId();
        imageView.setId(idImage);
        LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(this.imageWidth, this.imageHeight);
        layoutParamsImage.setMargins(0, 0, 0, 15);
        imageView.setLayoutParams(layoutParamsImage);
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
        LinearLayout linearLayoutTexts = new LinearLayout(this.container.$context());
        LinearLayout.LayoutParams layoutParamslinearTexts = new LinearLayout.LayoutParams(-2, -2);
        linearLayoutTexts.setLayoutParams(layoutParamslinearTexts);
        linearLayoutTexts.setOrientation(1);
        linearLayoutTexts.setGravity(1);
        linearLayoutTexts.addView(textViewFirst);
        linearLayoutTexts.addView(textViewSecond);
        LinearLayout linearLayoutMain = new LinearLayout(this.container.$context());
        LinearLayout.LayoutParams layoutParams = layoutParams2;
        LinearLayout.LayoutParams layoutParamslinearMain = new LinearLayout.LayoutParams(-1, -2);
        linearLayoutMain.setLayoutParams(layoutParamslinearMain);
        linearLayoutMain.setOrientation(1);
        linearLayoutMain.setGravity(1);
        linearLayoutMain.addView(imageView);
        linearLayoutMain.addView(linearLayoutTexts);
        cardView.addView(linearLayoutMain);
        LinearLayout.LayoutParams layoutParams3 = layoutParamslinearTexts;
        LinearLayout linearLayout = linearLayoutMain;
        LinearLayout linearLayout2 = linearLayoutTexts;
        LinearLayout.LayoutParams layoutParams4 = layoutParamslinearMain;
        int i = idSecond;
        TextView textView = textViewSecond;
        return new ImageTopTwoTextRvViewHolder(cardView, idCard, idFirst, idSecond, idImage);
    }

    public void onBindViewHolder(ListAdapterWithRecyclerView.RvViewHolder holder, int position) {
        ImageTopTwoTextRvViewHolder imageTwoTextHolder = (ImageTopTwoTextRvViewHolder) holder;
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
        String imageName = "";
        if (dictItem.containsKey(Component.LISTVIEW_KEY_IMAGE)) {
            imageName = dictItem.get(Component.LISTVIEW_KEY_IMAGE).toString();
        }
        imageTwoTextHolder.textViewFirst.setText(first);
        imageTwoTextHolder.textViewSecond.setText(second);
        try {
            ViewUtil.setImage(imageTwoTextHolder.imageView, MediaUtil.getBitmapDrawable(this.container.$form(), imageName));
        } catch (IOException ioe) {
            Log.e("ListView", "onBindViewHolder Unable to load image " + imageName + ": " + ioe.getMessage());
        }
        updateCardViewColor(imageTwoTextHolder.cardView, position);
    }

    public class ImageTopTwoTextRvViewHolder extends ListAdapterWithRecyclerView.RvViewHolder {
        public CardView cardView;
        public ImageView imageView;
        public TextView textViewFirst;
        public TextView textViewSecond;

        public /* bridge */ /* synthetic */ void onClick(View view) {
            super.onClick(view);
        }

        public ImageTopTwoTextRvViewHolder(View view, int idCard, int idFirst, int idSecond, int idImage) {
            super(view);
            this.cardView = view.findViewById(idCard);
            this.textViewFirst = (TextView) view.findViewById(idFirst);
            this.textViewSecond = (TextView) view.findViewById(idSecond);
            this.imageView = (ImageView) view.findViewById(idImage);
        }
    }
}
