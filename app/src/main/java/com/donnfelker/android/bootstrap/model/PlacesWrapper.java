package com.donnfelker.android.bootstrap.model;

import java.util.List;

/**
 * Created by guillaume on 1/13/16.
 */
public class PlacesWrapper {

  public Data getData() {
    return data;
  }

  public void setData(Data data) {
    this.data = data;
  }

  Data data;

  public class Data {

    public List<Place> getPlaces() {
      return places;
    }

    public void setPlaces(List<Place> places) {
      this.places = places;
    }

    List<Place> places;

    public class Place {

      String id;
      String name;

      public String getId() {
        return id;
      }

      public void setId(String id) {
        this.id = id;
      }

      public String getName() {
        return name;
      }

      public void setName(String name) {
        this.name = name;
      }
    }
  }
}
