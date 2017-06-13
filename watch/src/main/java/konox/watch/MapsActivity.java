package konox.watch;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.wearable.view.DismissOverlayView;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import konox.libreria1.MiPin;

public class MapsActivity extends Activity implements OnMapReadyCallback,
        GoogleMap.OnMapLongClickListener, LocationListener, DirectionCallback {

    /**
     * Overlay that shows a short help text when first launched. It also provides an option to
     * exit the app.
     */
    private DismissOverlayView mDismissOverlay;

    /**
     * The map. It is initialized when the map has been fully loaded and is ready to be used.
     *
     * @see #onMapReady(com.google.android.gms.maps.GoogleMap)
     */
    public GoogleMap mMap;
    LocationManager mLocationManager;
    Location miUltimaPosicion=null;
    MapsActivityController controller;
    double latitud=0, longitud=0;

    String usr = "user@text.com";
    String pwd = "12345678";
    String serverKey = "AIzaSyC6ylYPdkjpTZyNtDSWGjwZIDE6jWKhNzk";

    private GoogleMap googleMap;
    private LatLng origin;
    private LatLng destination;

    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        // Set the layout. It only contains a MapFragment and a DismissOverlay.
        setContentView(R.layout.activity_maps);
        DataHolder.instance.initQBAdmin(this);
        controller = new MapsActivityController(this);

        // Retrieve the containers for the root of the layout and the map. Margins will need to be
        // set on them to account for the system window insets.
        final FrameLayout topFrameLayout = (FrameLayout) findViewById(R.id.root_container);
        final FrameLayout mapFrameLayout = (FrameLayout) findViewById(R.id.map_container);

        // Set the system view insets on the containers when they become available.
        topFrameLayout.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                // Call through to super implementation and apply insets
                insets = topFrameLayout.onApplyWindowInsets(insets);

                FrameLayout.LayoutParams params =
                        (FrameLayout.LayoutParams) mapFrameLayout.getLayoutParams();

                // Add Wearable insets to FrameLayout container holding map as margins
                params.setMargins(
                        insets.getSystemWindowInsetLeft(),
                        insets.getSystemWindowInsetTop(),
                        insets.getSystemWindowInsetRight(),
                        insets.getSystemWindowInsetBottom());
                mapFrameLayout.setLayoutParams(params);

                return insets;
            }
        });

        // Obtain the DismissOverlayView and display the introductory help text.
        mDismissOverlay = (DismissOverlayView) findViewById(R.id.dismiss_overlay);
        mDismissOverlay.setIntroText(R.string.intro_text);
        mDismissOverlay.showIntroIfNecessary();



        // Obtain the MapFragment and set the async listener to be notified when the map is ready.
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void sendLatLong(){
        Location loc=getMiUltimaPosicion();
        if(loc!=null) {
            this.latitud = loc.getLatitude();
            this.longitud = loc.getLongitude();
            DataHolder.instance.longitud = loc.getLongitude();
            DataHolder.instance.latitud = loc.getLatitude();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        Log.v("ENTRA_PINES" ,"ENTRA EN PINESSSSSSSSSSSSSSSSS ------------------- "+DataHolder.instance.qbAdmin);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        } else {
            // Show rationale and request permission.
        }


    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // Display the dismiss overlay with a button to exit this activity.
        mDismissOverlay.show();
    }

    public Location getMiUltimaPosicion(){
        try {
            int MIN_TIME_BW_UPDATES=1000;//MILISEGUNDOS DE REFRESCO
            int MIN_DISTANCE_CHANGE_FOR_UPDATES=10;//METROS DE PRECISION

            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                showSettingsAlert();
            } else {
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    //mLocationManager.requestLocationUpdates( LocationManager.NETWORK_PROVIDER,  MIN_TIME_BW_UPDATES,  MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (mLocationManager != null) {
                        miUltimaPosicion = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        /*if (location != null) {
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }*/
                    }
                }
                //get the location by gps
                if (isGPSEnabled) {
                    if (miUltimaPosicion == null) {
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {
                            miUltimaPosicion = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return miUltimaPosicion;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                MapsActivity.this.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        miUltimaPosicion=location;
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void startDirection(MiPin pin, double longitud, double latitud){

        Log.v("ENTRA_MAPA", "ENTRA METODO -------------------------- " + pin.dbLatitud + " " + pin.dbLongitud + " " + longitud + " " + latitud);

        origin = new LatLng(longitud, latitud);
        destination = new LatLng(pin.dbLongitud, pin.dbLatitud);

        GoogleDirection.withServerKey(serverKey)
                .from(new LatLng(latitud, longitud))
                .to(new LatLng(pin.dbLatitud, pin.dbLongitud))
                .transportMode(TransportMode.DRIVING)
                .execute(this);

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Log.v("ENTRA_MAPA", "ENTRE SUCCESS ------- " + direction.getStatus());

        if(direction.isOK()) {
            Log.v("ENTRA_MAPA", "ENTRE SUCCESS");
            try{
                List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(this, stepList, 5, Color.RED, 3, Color.BLUE);
                for (PolylineOptions polylineOption : polylineOptionList) {
                    mMap.addPolyline(polylineOption);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {

    }
}
