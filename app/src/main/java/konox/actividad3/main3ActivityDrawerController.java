package konox.actividad3;

import android.content.SharedPreferences;
import android.content.Intent;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import konox.libreria1.MiPin;
import konox.libreria1.QBAdmin;
import konox.libreria1.QBAdminListiner;

/**
 * Created by konox on 17/01/2017.
 */

public class main3ActivityDrawerController implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMarkerClickListener, QBAdminListiner {
    //QBAdmin qbAdmin;
    Main3ActivityDrawer main3ActivityDrawer;


    public main3ActivityDrawerController(Main3ActivityDrawer main3ActivityDrawer) {
        this.main3ActivityDrawer = main3ActivityDrawer;
        //qbAdmin = new QBAdmin(this, main3ActivityDrawer);
        DataHolder.instance.qbAdmin.setListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.v("MenuItems", R.id.perfil + " " + R.id.nuevoSpot + " " + R.id.mapaSpots + " " + item.getItemId());

        if (item.getItemId() == main3ActivityDrawer.perfilNav.getItemId()) {
            main3ActivityDrawer.cambiarFragmentDrawer(3);
            Log.v("AAA", "PEFIL");
            main3ActivityDrawer.onBackPressed();
        } else if (item.getItemId() == main3ActivityDrawer.spotNav.getItemId()) {
            main3ActivityDrawer.cambiarFragmentDrawer(2);
            Log.v("BBB", "NUEVOOOO");
            main3ActivityDrawer.onBackPressed();
        } else if (item.getItemId() == main3ActivityDrawer.mapaNav.getItemId()) {
            main3ActivityDrawer.cambiarFragmentDrawer(1);
            Log.v("CCCC", "MAPAAAA!!!!!!!!!");
            main3ActivityDrawer.onBackPressed();
        }else if(item.getItemId()==main3ActivityDrawer.cerrarSesion.getItemId()){
            Log.v("CCCC", "CERRRAR SESION!!!!!!!!!");

            SharedPreferences prefs = main3ActivityDrawer.getSharedPreferences("MIPROPS",0);
            SharedPreferences.Editor edit = prefs.edit();

            edit.putString("email",null);
            edit.putString("pwd", null);

            edit.commit();

            Intent inten = new Intent(main3ActivityDrawer,MainActivity.class);
            main3ActivityDrawer.startActivity(inten);
        }

        return true;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        MiPin pin = (MiPin) marker.getTag();
        Log.v("MAP", "NOMBRE DEL MARKER PINCHADO " + pin.sNombre);

        return false;
    }

    @Override
    public void sesionCreada(boolean creada) {

    }

    @Override
    public void logeado(boolean blLogeado, QBUser user) {

    }

    @Override
    public void registrado(boolean blRegistrado, QBUser user) {

    }

    @Override
    public void descargaPinesFinalizado(ArrayList<MiPin> pines) {
        for (int i = 0; i < pines.size(); i++) {
            LatLng current = new LatLng(pines.get(i).dbLatitud,
                    pines.get(i).dbLongitud);
            Marker tempmar = main3ActivityDrawer.mMap.addMarker(new MarkerOptions().position(current).
                    title(pines.get(i).sNombre));
            tempmar.setTag(pines.get(i));
        }
    }

    @Override
    public void insertarSpot(boolean blInsertado, QBCustomObject object) {
        if (blInsertado) {
            Log.v("Controlador", main3ActivityDrawer.latitud + " NuevoSpot " + main3ActivityDrawer.longitud);
            LatLng current = new LatLng(main3ActivityDrawer.latitud,
                    main3ActivityDrawer.longitud);


            Marker tempmar = main3ActivityDrawer.mMap.addMarker(new MarkerOptions().position(current).
                    title(main3ActivityDrawer.nuevoSpotFragment.editTextSpot.getText().toString()));

            MiPin pinTemp = new MiPin(main3ActivityDrawer.latitud, main3ActivityDrawer.longitud, main3ActivityDrawer.nuevoSpotFragment.editTextSpot.getText().toString());
            tempmar.setTag(pinTemp);


        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == main3ActivityDrawer.nuevoSpotFragment.btnNewSpot.getId()) {
            main3ActivityDrawer.sendLatLong();

            int id = main3ActivityDrawer.nuevoSpotFragment.rdbtipo.getCheckedRadioButtonId();

            RadioButton radioButton = (RadioButton) main3ActivityDrawer.nuevoSpotFragment.rdbtipo.findViewById(id);

            int id2 = main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.getCheckedRadioButtonId();

            RadioButton radioButton2 = (RadioButton) main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.findViewById(id2);



            DataHolder.instance.qbAdmin.insertarPines(main3ActivityDrawer.longitud, main3ActivityDrawer.latitud,
                    main3ActivityDrawer.nuevoSpotFragment.editTextSpot.getText().toString(),
                    main3ActivityDrawer.nuevoSpotFragment.editTextDesc.getText().toString(), radioButton.getText().toString(), radioButton2.getText().toString());

        }
    }
}
