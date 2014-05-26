package com.depalma.whoswhere.activities;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.depalma.whoswhere.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {

	private static final float ZOOM_LEVEL = 13;
	private static ArrayList<LatLng> mohawk;
	ViewPager mViewPager;
	LocationClient myLocationClient;
	Location myCurrentLocation;
	Double currentLongitude;
	Double currentLatitude;
	GoogleMap map;

	private static int getVersionFromPackageManager(Context context) {
		PackageManager packageManager = context.getPackageManager();
		FeatureInfo[] featureInfos = packageManager
				.getSystemAvailableFeatures();
		if (featureInfos != null && featureInfos.length > 0) {
			for (FeatureInfo featureInfo : featureInfos) {
				// Null feature name means this feature is the open
				// gl es version feature.
				if (featureInfo.name == null) {
					if (featureInfo.reqGlEsVersion != FeatureInfo.GL_ES_VERSION_UNDEFINED) {
						return getMajorVersion(featureInfo.reqGlEsVersion);
					} else {
						return 1; // Lack of property means OpenGL ES
						// version 1
					}
				}
			}
		}
		return 1;
	}

	/**
	 * @see FeatureInfo#getGlEsVersion()
	 */
	private static int getMajorVersion(int glEsVersion) {
		return ((glEsVersion & 0xffff0000) >> 16);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (hasGoogleMaps()) {
			setContentView(R.layout.activity_main);
		} else {
			Toast.makeText(this, R.string.no_maps, Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);

		myLocationClient = new LocationClient(this, this, this);

		// Get a handle to the Map Fragment
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		mohawk = new ArrayList<LatLng>();
		mohawk.add(new LatLng(43.239660, -79.886347));
		mohawk.add(new LatLng(43.238835, -79.883430));
		mohawk.add(new LatLng(43.236980, -79.884430));
		mohawk.add(new LatLng(43.237434, -79.886374));
	}

	public void aboutUsSwitch() {
		Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
		startActivity(intent);
	}

	private boolean hasGoogleMaps() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

		if (status == ConnectionResult.SUCCESS
				&& getVersionFromPackageManager(this) >= 2) {
			return true;
		}
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.businessInquiries:
			Intent intented = new Intent(MainActivity.this,
					UpdateBarsActivity.class);
			startActivity(intented);
			return true;
		case R.id.aboutUs:
			Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
			startActivity(intent);
			return true;
		case R.id.deregister:
			Intent intente = new Intent(MainActivity.this,
					DeRegisterActivity.class);
			startActivity(intente);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main_activity_actions, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle connectionHint) {

		myCurrentLocation = myLocationClient.getLastLocation();
		currentLongitude = myCurrentLocation.getLongitude();
		currentLatitude = myCurrentLocation.getLatitude();

		LatLng yourMapLocation = new LatLng(currentLatitude, currentLongitude);

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(yourMapLocation,
				ZOOM_LEVEL));

		map.addMarker(new MarkerOptions().position(
				new LatLng(currentLatitude, currentLongitude)).title(
				"You are here!"));
		final LatLng myHouse = new LatLng(43.209391, -79.855738);
		Marker myHome = map.addMarker(new MarkerOptions().position(myHouse)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

		// Communicator.getCommunicator().
	}

	public boolean pnpoly(ArrayList<LatLng> building, LatLng check) {
		int i, j;
		boolean c = false;
		Double checkLong = check.longitude;
		Double checkLat = check.latitude;

		for (i = 0, j = building.size() - 1; i < building.size(); j = i++) {

			Double buildILong = building.get(i).longitude;
			Double buildILat = building.get(i).latitude;
			Double buildJLong = building.get(j).longitude;
			Double buildJLat = building.get(j).latitude;

			boolean bool1 = (buildILat >= checkLat) != (buildJLat >= checkLat);
			Double res = (buildJLong - buildILong) * (checkLat - buildILat)
					/ (buildJLat - buildILat) + buildILong;

			if (bool1 && checkLong <= res) {
				c = !c;
			}
		}
		return c;
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		myLocationClient.connect();
	}

	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		myLocationClient.disconnect();
		super.onStop();
	}

	public void checkStatus(View view) {
		myCurrentLocation = myLocationClient.getLastLocation();
		currentLongitude = myCurrentLocation.getLongitude();
		currentLatitude = myCurrentLocation.getLatitude();

		LatLng yourMapLocation = new LatLng(currentLatitude, currentLongitude);
		Toast.makeText(this,
				pnpoly(mohawk, yourMapLocation) ? "We are within" : "Nope",
				Toast.LENGTH_LONG).show();
	}

	public static String SERVERIP = "10.128.12.55";
	public static final int SERVERPORT = 43599;

	public void Connected(View view) {

	}

}
