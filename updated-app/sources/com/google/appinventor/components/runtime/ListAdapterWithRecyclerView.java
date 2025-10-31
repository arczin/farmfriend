package com.google.appinventor.components.runtime;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.appinventor.components.runtime.util.YailDictionary;
import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapterWithRecyclerView extends RecyclerView.Adapter<RvViewHolder> implements Filterable {
    protected static final String LOG_TAG = "ListView";
    protected int backgroundColor;
    protected ClickListener clickListener;
    protected ComponentContainer container;
    protected final Filter filter = new Filter() {
        /* access modifiers changed from: protected */
        public Filter.FilterResults performFiltering(CharSequence charSequence) {
            String filterString;
            ListAdapterWithRecyclerView.this.lastQuery = charSequence.toString().toLowerCase();
            Filter.FilterResults results = new Filter.FilterResults();
            List<Object> filteredList = new ArrayList<>();
            ListAdapterWithRecyclerView.this.originalPositions = new ArrayList();
            if (ListAdapterWithRecyclerView.this.lastQuery == null || ListAdapterWithRecyclerView.this.lastQuery.length() == 0) {
                filteredList = new ArrayList<>(ListAdapterWithRecyclerView.this.originalItems);
                ListAdapterWithRecyclerView.this.items = new ArrayList(ListAdapterWithRecyclerView.this.originalItems);
            } else {
                for (int index = 0; index < ListAdapterWithRecyclerView.this.originalItems.size(); index++) {
                    Object item = ListAdapterWithRecyclerView.this.originalItems.get(index);
                    if (!(item instanceof YailDictionary)) {
                        filterString = item.toString();
                    } else if (((YailDictionary) item).containsKey(Component.LISTVIEW_KEY_MAIN_TEXT)) {
                        Object o = ((YailDictionary) item).get(Component.LISTVIEW_KEY_DESCRIPTION);
                        filterString = ((YailDictionary) item).get(Component.LISTVIEW_KEY_MAIN_TEXT).toString();
                        if (o != null) {
                            filterString = filterString + " " + o.toString();
                        }
                    } else {
                        filterString = item.toString();
                    }
                    if (filterString.toLowerCase().contains(ListAdapterWithRecyclerView.this.lastQuery)) {
                        filteredList.add(item);
                        ListAdapterWithRecyclerView.this.originalPositions.add(Integer.valueOf(index));
                    }
                }
            }
            results.count = filteredList.size();
            results.values = filteredList;
            return results;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            ListAdapterWithRecyclerView.this.items = new ArrayList((List) filterResults.values);
            ListAdapterWithRecyclerView.this.clearSelections();
            ListAdapterWithRecyclerView.this.notifyDataSetChanged();
        }
    };
    protected List<Object> items = new ArrayList();
    protected String lastQuery = "";
    protected List<Object> originalItems = new ArrayList();
    protected List<Integer> originalPositions = new ArrayList();
    protected float radius;
    protected List<Integer> selectedItems = new ArrayList();
    protected int selectionColor;

    public interface ClickListener {
        void onItemClick(int i, View view);
    }

    public ListAdapterWithRecyclerView(ComponentContainer container2, List<Object> data, int backgroundColor2, int selectionColor2, float radius2) {
        this.container = container2;
        this.backgroundColor = backgroundColor2;
        this.radius = radius2;
        this.selectionColor = selectionColor2;
        updateData(data);
    }

    public void updateData(List<Object> newItems) {
        this.originalItems = newItems;
        if (this.originalPositions.isEmpty()) {
            this.items = new ArrayList(newItems);
        } else {
            this.filter.filter(this.lastQuery);
        }
        clearSelections();
    }

    /* access modifiers changed from: protected */
    public CardView createCardView(ViewGroup parent) {
        CardView cardView = new CardView(this.container.$context());
        cardView.setContentPadding(15, 15, 15, 15);
        cardView.setPreventCornerOverlap(false);
        cardView.setMaxCardElevation(3.0f);
        cardView.setCardBackgroundColor(this.backgroundColor);
        cardView.setRadius(this.radius);
        cardView.setCardElevation(0.0f);
        ViewCompat.setElevation(cardView, 0.0f);
        cardView.setClickable(true);
        cardView.setId(ViewCompat.generateViewId());
        FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(-1, -2);
        params1.setMargins(0, 0, 0, 0);
        cardView.setLayoutParams(params1);
        return cardView;
    }

    /* access modifiers changed from: protected */
    public void updateCardViewColor(CardView cardView, int position) {
        if (this.selectedItems.contains(Integer.valueOf(position))) {
            cardView.setCardBackgroundColor(this.selectionColor);
        } else {
            cardView.setCardBackgroundColor(this.backgroundColor);
        }
    }

    public int getItemCount() {
        return this.items.size();
    }

    public void toggleSelection(int position) {
        if (!this.originalPositions.isEmpty()) {
            position = this.originalPositions.indexOf(Integer.valueOf(position));
        }
        if (!this.selectedItems.contains(Integer.valueOf(position))) {
            if (!this.selectedItems.isEmpty()) {
                int oldPosition = this.selectedItems.get(0).intValue();
                this.selectedItems.clear();
                notifyItemChanged(oldPosition);
            }
            this.selectedItems.add(Integer.valueOf(position));
            notifyItemChanged(position);
        }
    }

    public void changeSelections(int position) {
        if (!this.originalPositions.isEmpty()) {
            position = this.originalPositions.indexOf(Integer.valueOf(position));
        }
        if (this.selectedItems.contains(Integer.valueOf(position))) {
            this.selectedItems.remove(Integer.valueOf(position));
        } else {
            this.selectedItems.add(Integer.valueOf(position));
        }
        notifyItemChanged(position);
    }

    public void clearSelections() {
        this.selectedItems.clear();
    }

    abstract class RvViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RvViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (!ListAdapterWithRecyclerView.this.originalPositions.isEmpty()) {
                position = ListAdapterWithRecyclerView.this.originalPositions.get(position).intValue();
            }
            ListAdapterWithRecyclerView.this.clickListener.onItemClick(position, v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener2) {
        this.clickListener = clickListener2;
    }

    public Filter getFilter() {
        return this.filter;
    }
}
