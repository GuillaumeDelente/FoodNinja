package io.foodninja.android.model;

import java.util.List;

/**
 * Created by guillaume on 1/13/16.
 */
public class DishesWrapper {

  Data data;

  public List<Data.Sightings> getSightings() {
    return data.sightings;
  }

  public class Data {

    List<Sightings> sightings;

    public class Sightings {

      public String getImageUrl() {
        return current_review.thumb_590;
      }

      public String getName() {
        return current_review.item.name;
      }

      public int getHearts() {
        return current_review.ribbons_count;
      }

      CurrentReview current_review;

      public class CurrentReview {

        String thumb_590;
        Item item;
        int ribbons_count;

        public class Item {

          String name;

        }
      }
    }
  }
}
