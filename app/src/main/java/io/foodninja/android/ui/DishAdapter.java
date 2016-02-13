package io.foodninja.android.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.foodninja.android.R;
import io.foodninja.android.model.DishesWrapper;

/**
 * Created by guillaume on 1/13/16.
 */
public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

  private Context context;
  private List<DishesWrapper.Data.Sightings> items = new ArrayList<>();

  public DishAdapter(Context context) {
    this.context = context;
  }

  @Override
  public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new DishViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.row_dish, parent, false));
  }

  @Override
  public void onBindViewHolder(final DishViewHolder holder, int position) {
    holder.imageView.setImageDrawable(null);
    holder.background.setVisibility(View.VISIBLE);
    final DishesWrapper.Data.Sightings item = items.get(position);
    Picasso.with(context)
        .load(item.getImageUrl())
        .noPlaceholder()
        .fit()
        .centerCrop()
        .into(holder.imageView, new Callback() {
          @Override
          public void onSuccess() {
            holder.background.setVisibility(View.GONE);
          }

          @Override
          public void onError() {

          }
        });
    holder.title.setText(item.getName());
    holder.hearts.setText(String.valueOf(item.getHearts()));
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  public void setItems(List<DishesWrapper.Data.Sightings> items) {
    this.items.clear();
    this.items.addAll(items);
    notifyDataSetChanged();
  }

  public List<DishesWrapper.Data.Sightings> getItems() {
    return items;
  }

  public void clear() {
    items.clear();
    notifyDataSetChanged();
  }

  class DishViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.background)
    ImageView background;
    @Bind(R.id.image_view)
    ImageView imageView;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.hearts)
    TextView hearts;

    public DishViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
