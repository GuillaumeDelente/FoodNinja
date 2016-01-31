package com.donnfelker.android.bootstrap.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.PlacesWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by guillaume on 1/13/16.
 */
public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> {

  private Context context;
  private List<PlacesWrapper.Data.Place> items = new ArrayList<>(0);
  private OnPlaceSelectedListener onItemClickListener;

  public PlacesAdapter(Context context, OnPlaceSelectedListener onItemClickListener) {
    this.context = context;
    this.onItemClickListener = onItemClickListener;
  }

  @Override
  public PlacesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new PlacesViewHolder(LayoutInflater.from(context)
        .inflate(android.R.layout.simple_selectable_list_item, parent, false));
  }

  @Override
  public void onBindViewHolder(PlacesViewHolder holder, int position) {
    final PlacesWrapper.Data.Place item = items.get(position);
    holder.text1.setText(item.getName());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (onItemClickListener != null) {
          onItemClickListener.onPlaceSelected(item);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public void setItems(List<PlacesWrapper.Data.Place> items) {
    this.items = items;
    notifyDataSetChanged();
  }

  public List<PlacesWrapper.Data.Place> getItems() {
    return items;
  }

  class PlacesViewHolder extends RecyclerView.ViewHolder {
    @Bind(android.R.id.text1)
    TextView text1;

    public PlacesViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.background));
    }
  }

  interface OnPlaceSelectedListener {
    void onPlaceSelected(PlacesWrapper.Data.Place place);
  }
}
