package konox.actividad3;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import konox.libreria1.MiPin;
import konox.libreria1.QBAdmin;
import konox.libreria1.QBAdminListiner;


public class Main3ActivityDrawer extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    //QBAdmin qbAdmin;
    main3ActivityDrawerController main3ActivityDrawerController;
    MapaFragment mapa;
    NuevoSpotFragment nuevoSpotFragment;
    PerfilFragment perfil;
    pinFragment pin;
    FragmentManager fm;
    NavigationView navigationView;
    MenuItem perfilNav;
    MenuItem spotNav;
    MenuItem mapaNav;
    MenuItem cerrarSesion;
    TextView txtEmail;
    TextView txtName;
    GoogleMap mMap;
    Button btn;
    ImageView imgMenu;
    LocationManager mLocationManager;
    Location miUltimaPosicion=null;
    Marker markerMiPosicion;
    double latitud=0;
    double longitud=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3_drawer);
        fm = getSupportFragmentManager();
        main3ActivityDrawerController = new main3ActivityDrawerController(this);
        //qbAdmin=new QBAdmin(this,this);

        //MENU ITEMS
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Log.v("AA", navigationView + "");
        perfilNav = navigationView.getMenu().getItem(2);
        spotNav = navigationView.getMenu().getItem(1);
        mapaNav = navigationView.getMenu().getItem(0);
        cerrarSesion = navigationView.getMenu().getItem(3);




        //FRAGMENTS
        mapa = (MapaFragment) fm.findFragmentById(R.id.frMapa);
        nuevoSpotFragment = (NuevoSpotFragment) fm.findFragmentById(R.id.frSpotNuevo);
        perfil = (PerfilFragment) fm.findFragmentById(R.id.frPerfil);
        pin = (pinFragment) fm.findFragmentById(R.id.fragment_pin);

        nuevoSpotFragment.btnNewSpot.setOnClickListener(main3ActivityDrawerController);

        pin.imgBtnClose.setOnClickListener(main3ActivityDrawerController);

        perfil.btnEdit.setOnClickListener(main3ActivityDrawerController);

        perfil.btnGuardarPerfil.setOnClickListener(main3ActivityDrawerController);

        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.frMapa);
        supportMapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(main3ActivityDrawerController);



        cambiarFragmentDrawer(1);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        txtEmail=(TextView)this.findViewById(R.id.txtEmail);
        txtName=(TextView)this.findViewById(R.id.txtName);
        imgMenu = (ImageView) this.findViewById(R.id.imgmenu);
        txtEmail.setText(DataHolder.instance.email);
        txtName.setText(DataHolder.instance.name);
        String imageUrl = DataHolder.instance.urlImage;
        Picasso.with(this).load(imageUrl).into(imgMenu);


        getMenuInflater().inflate(R.menu.main3_activity_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void cambiarFragmentDrawer(int frgm) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(nuevoSpotFragment);
        transaction.hide(perfil);
        transaction.hide(mapa);
        transaction.hide(pin);

        if (frgm == 1) {
            transaction.show(mapa);

        } else if (frgm == 2) {
            transaction.show(nuevoSpotFragment);
        } else if (frgm == 3) {
            transaction.show(perfil);

            this.main3ActivityDrawerController.descargarImg();
        } else if(frgm ==4 ){
            transaction.show(mapa);

            transaction.show(pin);
        }else if(frgm ==5 ){
            transaction.show(mapa);
            transaction.hide(pin);
        }

        transaction.commit();

    }
    public Location getMiUltimaPosicion(){
        if ( Build.VERSION.SDK_INT >= 23 && (
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            showSettingsAlert();
            return  null;
        }

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
                        //mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (mLocationManager != null) {
                            miUltimaPosicion = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            /*if (location != null) {
                                lat = location.getLatitude();
                                lng = location.getLongitude();
                            }*/
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


    @Override
    public void onLocationChanged(Location location) {
        miUltimaPosicion=location;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                Main3ActivityDrawer.this.startActivity(intent);
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
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        } else {
            // Show rationale and request permission.
        }

        mMap.setOnMarkerClickListener(main3ActivityDrawerController);

        DataHolder.instance.qbAdmin.descargDatosPines();

    }

    public void sendLatLong(){
        Location loc=getMiUltimaPosicion();
        Log.v("LOC",loc+" !!!! ");
        if(loc!=null) {
            this.latitud = loc.getLatitude();
            this.longitud = loc.getLongitude();
        }
    }

}
