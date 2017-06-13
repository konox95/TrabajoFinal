package konox.actividad3;

import android.Manifest;
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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import konox.libreria1.MiPin;


public class Main3ActivityDrawer extends AppCompatActivity implements OnMapReadyCallback,LocationListener, DirectionCallback {

    //QBAdmin qbAdmin;
    main3ActivityDrawerController main3ActivityDrawerController;
    MapaFragment mapa;
    NuevoSpotFragment nuevoSpotFragment;
    PerfilFragment perfil;
    pinFragment pin;
    galeriaFragment galeriaFrag;
    FragmentManager fm;
    NavigationView navigationView;
    MenuItem perfilNav, spotNav, mapaNav, cerrarSesion, galeriaMenu;
    TextView txtEmail, txtName;
    GoogleMap mMap;
    Button btn;
    ImageView imgMenu;
    LocationManager mLocationManager;
    Location miUltimaPosicion=null;
    Marker markerMiPosicion;
    String serverKey = "AIzaSyC6ylYPdkjpTZyNtDSWGjwZIDE6jWKhNzk";

    private Button btnRequestDirection;
    private GoogleMap googleMap;
    private LatLng origin;
    private LatLng destination;

    double latitud=0, longitud=0;
    private static final int[] COLORS = new int[]{R.color.AMARILLO, R.color.colorAccent, R.color.BLANCO, R.color.GRISLIGHT};

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
        galeriaMenu = navigationView.getMenu().getItem(5);

        //FRAGMENTS
        mapa = (MapaFragment) fm.findFragmentById(R.id.frMapa);
        nuevoSpotFragment = (NuevoSpotFragment) fm.findFragmentById(R.id.frSpotNuevo);
        perfil = (PerfilFragment) fm.findFragmentById(R.id.frPerfil);
        pin = (pinFragment) fm.findFragmentById(R.id.fragment_pin);
        galeriaFrag = (galeriaFragment)fm.findFragmentById(R.id.fragment_galeria);

        //OnClick
        nuevoSpotFragment.btnNewSpot.setOnClickListener(main3ActivityDrawerController);
        pin.imgBtnClose.setOnClickListener(main3ActivityDrawerController);
        pin.imgBtnFavorites.setOnClickListener(main3ActivityDrawerController);
        perfil.btnEdit.setOnClickListener(main3ActivityDrawerController);
        perfil.btnGuardarPerfil.setOnClickListener(main3ActivityDrawerController);
        pin.btnEmpezar.setOnClickListener(main3ActivityDrawerController);

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
        txtName=(TextView)this.findViewById(R.id.txtName1);
        imgMenu = (ImageView) this.findViewById(R.id.imgmenu);
        txtEmail.setText(DataHolder.instance.email);
        txtName.setText(DataHolder.instance.perfilNombre);
        Log.v("NOMBRE", txtName.toString());
        String imageUrl = DataHolder.instance.urlImage;
        Picasso.with(this).load(imageUrl).transform(new RoundedCornersTransformation(290, 290)).rotate(90).into(imgMenu);

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
        transaction.hide(galeriaFrag);

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
        }else if (frgm == 6) {
            transaction.show(galeriaFrag);
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
            DataHolder.instance.longitud = loc.getLongitude();
            DataHolder.instance.latitud = loc.getLatitude();
        }
    }

    public void startDirection(MiPin pin, double longitud, double latitud){

        Log.v("ENTRA_MAPA", "ENTRA METODO --------------------------");



        origin = new LatLng(longitud, latitud);
        destination = new LatLng(pin.dbLongitud, pin.dbLatitud);

        GoogleDirection.withServerKey(serverKey)
                .from(new LatLng(latitud, longitud))
                .to(new LatLng(pin.dbLatitud, pin.dbLongitud))
                .transportMode(TransportMode.WALKING)
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
