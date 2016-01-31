package com.donnfelker.android.bootstrap.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.donnfelker.android.bootstrap.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import pl.tajchert.nammu.PermissionListener;

/**
 * Created by guillaume on 1/17/16.
 */
public class LocationPermissionActivity extends AppCompatActivity {

  final PermissionCallback permissionLocationCallback = new PermissionCallback() {
    @Override
    public void permissionGranted() {
      startActivity(new Intent(LocationPermissionActivity.this, MainActivity.class));
      finish();
    }

    @Override
    public void permissionRefused() {
    }
  };

  @OnClick(R.id.grant_access)
  void onGrantAccessClicked() {
    Nammu.askForPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, permissionLocationCallback);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_location_permission);
    ButterKnife.bind(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    Nammu.permissionCompare(new PermissionListener() {
      @Override
      public void permissionsChanged(String permissionRevoke) {
        //Toast is not needed as always either permissionsGranted() or permissionsRemoved() will be called
        //Toast.makeText(MainActivity.this, "Access revoked = " + permissionRevoke, Toast.LENGTH_SHORT).show();
      }

      @Override
      public void permissionsGranted(String permissionGranted) {
        if (Nammu.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
          startActivity(new Intent(LocationPermissionActivity.this, MainActivity.class));
          finish();
        }
      }

      @Override
      public void permissionsRemoved(String permissionRemoved) {
      }
    });
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }
}
