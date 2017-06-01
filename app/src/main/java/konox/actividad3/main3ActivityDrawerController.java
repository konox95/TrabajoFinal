package konox.actividad3;

import android.content.SharedPreferences;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
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
    ArrayList<String> arrFotos= new ArrayList<String>();

    String urlImg;


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
            LatLng current = new LatLng(main3ActivityDrawer.latitud,
                    main3ActivityDrawer.longitud);


            Marker tempmar = main3ActivityDrawer.mMap.addMarker(new MarkerOptions().position(current).
                    title(main3ActivityDrawer.nuevoSpotFragment.editTextSpot.getText().toString()));

            MiPin pinTemp = new MiPin(main3ActivityDrawer.latitud,
                    main3ActivityDrawer.longitud, main3ActivityDrawer.nuevoSpotFragment.editTextSpot.getText().toString());
            tempmar.setTag(pinTemp);


        }
    }

    @Override
    public void fotosubida(boolean blUpload, QBFile qbFile) {
        if (blUpload){

            //Conseguir el radioButton seleccionado
            int id = main3ActivityDrawer.nuevoSpotFragment.rdbtipo.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) main3ActivityDrawer.nuevoSpotFragment.rdbtipo.findViewById(id);
            int id2 = main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.getCheckedRadioButtonId();
            RadioButton radioButton2 = (RadioButton) main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.findViewById(id2);


            String chBanco = null, chBarandilla = null, chBowl = null, chCajon = null, chEscalera = null, chRampa = null;

            if (main3ActivityDrawer.nuevoSpotFragment.cbBanco.isChecked()){
                chBanco = main3ActivityDrawer.nuevoSpotFragment.cbBanco.getText().toString();
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbBarandilla.isChecked()){
                chBarandilla = main3ActivityDrawer.nuevoSpotFragment.cbBarandilla.getText().toString();
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbBowl.isChecked()){
                chBowl = main3ActivityDrawer.nuevoSpotFragment.cbBowl.getText().toString();
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbCajon.isChecked()){
                chCajon = main3ActivityDrawer.nuevoSpotFragment.cbCajon.getText().toString();
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbEscalera.isChecked()){
                chEscalera = main3ActivityDrawer.nuevoSpotFragment.cbEscalera.getText().toString();
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbRampa.isChecked()){
                chRampa = main3ActivityDrawer.nuevoSpotFragment.cbRampa.getText().toString();
            }

            urlImg = qbFile.getPublicUrl();

            Log.v("URL_PUBLIC", "URL -----------" + urlImg);
            arrFotos.add(urlImg);

            Log.v("subirFoto", "La foto ha sido subida " + urlImg);

            DataHolder.instance.qbAdmin.insertarPines(main3ActivityDrawer.longitud, main3ActivityDrawer.latitud,
                    main3ActivityDrawer.nuevoSpotFragment.editTextSpot.getText().toString(),
                    main3ActivityDrawer.nuevoSpotFragment.editTextDesc.getText().toString(),
                    radioButton.getText().toString(), radioButton2.getText().toString(),
                    chBanco, chBarandilla, chBowl, chCajon, chEscalera, chRampa, arrFotos);
        }  else {
            Log.v("noSubida", "La foto no se ha subido");
        }
    }

    @Override
    public void cambiarContraseñas(boolean cambiada, QBUser user) {
        if(cambiada){
            Toast.makeText(main3ActivityDrawer, "Contraseña cambiada", Toast.LENGTH_SHORT).show();
            //Log.v("pwd", "Contraseña----------------" + user.getPassword());
        }else{
            Toast.makeText(main3ActivityDrawer, "Fallo al cambiar la contraseña", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(final View view) {
        if (view.getId() == main3ActivityDrawer.nuevoSpotFragment.btnNewSpot.getId()) {
            main3ActivityDrawer.sendLatLong();

            try {
                DataHolder.instance.qbAdmin.subirFoto(main3ActivityDrawer.nuevoSpotFragment.mPath);
            } catch (QBResponseException e) {
                e.printStackTrace();
            }
        }

        if (view.getId() == main3ActivityDrawer.perfil.btnEdit.getId()) {

            if (main3ActivityDrawer.perfil.etContraseñaPerfil.getText().toString().equals("")) {
                Toast.makeText(view.getContext(), "Contraseña vacía", Toast.LENGTH_SHORT).show();
            } else {
                DataHolder.instance.qbAdmin.cambiarContraseña(main3ActivityDrawer.perfil.etContraseñaPerfil.getText().toString());
            }
        }
    }
}