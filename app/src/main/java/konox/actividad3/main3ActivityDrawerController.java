package konox.actividad3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import konox.libreria1.MiPin;
import konox.libreria1.QBAdminListiner;

/**
 * Created by konox on 17/01/2017.
 */

public class main3ActivityDrawerController implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMarkerClickListener, QBAdminListiner {
    //QBAdmin qbAdmin;
    Main3ActivityDrawer main3ActivityDrawer;
    ArrayList<String> arrFotos = new ArrayList<String>();
    ArrayList<String> pines= new ArrayList<>();
    Boolean chBanco = false, chBarandilla = false, chBowl = false, chCajon = false, chEscalera = false, chRampa = false;

    String urlImg;


    public main3ActivityDrawerController(Main3ActivityDrawer main3ActivityDrawer) {
        this.main3ActivityDrawer = main3ActivityDrawer;
        //qbAdmin = new QBAdmin(this, main3ActivityDrawer);
        DataHolder.instance.qbAdmin.setListener(this);

    }

    public void descargarImg() {
        Log.v("PASE", "PASSSSSSSSSSSSSSSS");
        String imageUrl = DataHolder.instance.urlImage;
        Picasso.with(main3ActivityDrawer).load(imageUrl).transform(new RoundedCornersTransformation(290, 290)).rotate(90).into(main3ActivityDrawer.perfil.img);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.v("MenuItems", R.id.perfil + " " + R.id.nuevoSpot + " " + R.id.mapaSpots + " " + item.getItemId());

        if (item.getItemId() == main3ActivityDrawer.perfilNav.getItemId()) {
            main3ActivityDrawer.cambiarFragmentDrawer(3);
            main3ActivityDrawer.onBackPressed();
        } else if (item.getItemId() == main3ActivityDrawer.spotNav.getItemId()) {
            main3ActivityDrawer.cambiarFragmentDrawer(2);
            Log.v("BBB", "NUEVOOOO");
            main3ActivityDrawer.onBackPressed();
        } else if (item.getItemId() == main3ActivityDrawer.mapaNav.getItemId()) {
            main3ActivityDrawer.cambiarFragmentDrawer(1);
            Log.v("CCCC", "MAPAAAA!!!!!!!!!");
            main3ActivityDrawer.onBackPressed();

        }else if(item.getItemId()==R.id.cerrarSesion){
            Log.v("CCCC", "CERRRAR SESION!!!!!!!!!");

            SharedPreferences prefs = main3ActivityDrawer.getSharedPreferences("MIPROPS", 0);
            SharedPreferences.Editor edit = prefs.edit();

            edit.putString("email", null);
            edit.putString("pwd", null);


            edit.commit();

            DataHolder.instance.cerraSesion = true;

            main3ActivityDrawer.finish();

            Intent inten = new Intent(main3ActivityDrawer, MainActivity.class);
            main3ActivityDrawer.startActivity(inten);


        } else if (item.getItemId()==R.id.imagenes){

            main3ActivityDrawer.cambiarFragmentDrawer(6);
            Log.v("BBB", "GALERIAAAA");
            main3ActivityDrawer.onBackPressed();
        }

        return true;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        MiPin pin = (MiPin) marker.getTag();
        Log.v("MAP", "NOMBRE DEL MARKER PINCHADO " + pin.sNombre + pin.sDificultad);
        main3ActivityDrawer.pin.txt_nombre.setText(pin.sNombre);
        main3ActivityDrawer.pin.txt_tipo.setText(pin.sTipo);
        main3ActivityDrawer.pin.txt_descripcion.setText(pin.sDescripcion);
        main3ActivityDrawer.pin.txt_dificultad.setText(pin.sDificultad);
        main3ActivityDrawer.pin.txt_tipo.setText(pin.sTipo);

        if (pin.chBanco) {
            main3ActivityDrawer.pin.icBanco.setImageResource(R.mipmap.ic_done_black_24dp);
        }
        if (pin.chBarandilla) {
            main3ActivityDrawer.pin.icBarandilla.setImageResource(R.mipmap.ic_done_black_24dp);
        }
        if (pin.chBowl) {
            main3ActivityDrawer.pin.icBowl.setImageResource(R.mipmap.ic_done_black_24dp);
        }
        if (pin.chCajon) {
            main3ActivityDrawer.pin.icCajon.setImageResource(R.mipmap.ic_done_black_24dp);
        }
        if (pin.chEscalera) {
            main3ActivityDrawer.pin.icEscalera.setImageResource(R.mipmap.ic_done_black_24dp);
        }
        if (pin.chRampa) {
            main3ActivityDrawer.pin.icRampa.setImageResource(R.mipmap.ic_done_black_24dp);
        }

        DataHolder.instance.miPin = pin;

        if (pin.imgSpot.size() != 0) {
            String UrlFoto = pin.imgSpot.get(0);
            Picasso.with(main3ActivityDrawer)
                    .load(UrlFoto)
                    .transform(new RoundedCornersTransformation(290, 290))
                    //.resize(200,100)
                    .rotate(90)
                    .into(main3ActivityDrawer.pin.imgSpotPinFragment);
        }

        main3ActivityDrawer.cambiarFragmentDrawer(4);
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
        //Igualo el arrayList de pines al dataHolder
        DataHolder.instance.pi = pines;
        for (int i = 0; i < pines.size(); i++) {
            LatLng current = new LatLng(pines.get(i).dbLatitud,
                    pines.get(i).dbLongitud);
            Marker tempmar = main3ActivityDrawer.mMap.addMarker(new MarkerOptions().position(current)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
            tempmar.setTag(pines.get(i));

            main3ActivityDrawer.getMiUltimaPosicion();

            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(DataHolder.instance.latitud, DataHolder.instance.longitud));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);

            main3ActivityDrawer.mMap.moveCamera(center);
            main3ActivityDrawer.mMap.animateCamera(zoom);
        }
    }

    @Override
    public void insertarSpot(boolean blInsertado, QBCustomObject object) {
        if (blInsertado) {


            LatLng current = new LatLng(main3ActivityDrawer.latitud,
                    main3ActivityDrawer.longitud);


            Marker tempmar = main3ActivityDrawer.mMap.addMarker(new MarkerOptions().position(current)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

            //Conseguir el radioButton seleccionado
            int id = main3ActivityDrawer.nuevoSpotFragment.rdbtipo.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) main3ActivityDrawer.nuevoSpotFragment.rdbtipo.findViewById(id);
            int id2 = main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.getCheckedRadioButtonId();
            RadioButton radioButton2 = (RadioButton) main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.findViewById(id2);

            MiPin pinTemp = new MiPin(main3ActivityDrawer.latitud,
                    main3ActivityDrawer.longitud, main3ActivityDrawer.nuevoSpotFragment.editTextSpot.getText().toString(),
                    main3ActivityDrawer.nuevoSpotFragment.editTextDesc.getText().toString(), radioButton.getText().toString(),
                    radioButton2.getText().toString(), arrFotos, chBanco, chBarandilla, chBowl, chCajon, chEscalera, chRampa);

            DataHolder.instance.pi.add(pinTemp);
            tempmar.setTag(pinTemp);

            main3ActivityDrawer.nuevoSpotFragment.editTextDesc.setText("");
            main3ActivityDrawer.nuevoSpotFragment.editTextSpot.setText("");

            main3ActivityDrawer.nuevoSpotFragment.rdbtipo.clearCheck();
            main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.clearCheck();

            main3ActivityDrawer.nuevoSpotFragment.cbBanco.setChecked(false);
            main3ActivityDrawer.nuevoSpotFragment.cbBarandilla.setChecked(false);
            main3ActivityDrawer.nuevoSpotFragment.cbBowl.setChecked(false);
            main3ActivityDrawer.nuevoSpotFragment.cbCajon.setChecked(false);
            main3ActivityDrawer.nuevoSpotFragment.cbEscalera.setChecked(false);
            main3ActivityDrawer.nuevoSpotFragment.cbRampa.setChecked(false);
            main3ActivityDrawer.nuevoSpotFragment.img.setImageResource(R.drawable.transparente);

            Toast.makeText(main3ActivityDrawer, "SPOT INSERTADO", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(main3ActivityDrawer, "ERROR AL INSERTAR SPOT", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fotosubida(boolean blUpload, QBFile qbFile) {
        if (blUpload) {

            //Conseguir el radioButton seleccionado
            int id = main3ActivityDrawer.nuevoSpotFragment.rdbtipo.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) main3ActivityDrawer.nuevoSpotFragment.rdbtipo.findViewById(id);
            int id2 = main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.getCheckedRadioButtonId();
            RadioButton radioButton2 = (RadioButton) main3ActivityDrawer.nuevoSpotFragment.rdgDificultad.findViewById(id2);

            if (main3ActivityDrawer.nuevoSpotFragment.cbBanco.isChecked()) {
                chBanco = true;
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbBarandilla.isChecked()) {
                chBarandilla = true;
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbBowl.isChecked()) {
                chBowl = true;
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbCajon.isChecked()) {
                chCajon = true;
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbEscalera.isChecked()) {
                chEscalera = true;
            }

            if (main3ActivityDrawer.nuevoSpotFragment.cbRampa.isChecked()) {
                chRampa = true;
            }

            urlImg = qbFile.getPublicUrl();
            arrFotos.add(urlImg);


            DataHolder.instance.qbAdmin.insertarPines(main3ActivityDrawer.longitud, main3ActivityDrawer.latitud,
                    main3ActivityDrawer.nuevoSpotFragment.editTextSpot.getText().toString(),
                    main3ActivityDrawer.nuevoSpotFragment.editTextDesc.getText().toString(),
                    radioButton.getText().toString(), radioButton2.getText().toString(),
                    chBanco, chBarandilla, chBowl, chCajon, chEscalera, chRampa, arrFotos);
        } else {
            Log.v("noSubida", "La foto no se ha subido");
        }
    }

    @Override
    public void fotosubidaPerfil(boolean blUpload, QBFile qbFile) {
        if (blUpload) {
            urlImg = qbFile.getPublicUrl();

            DataHolder.instance.urlImage = urlImg;

            SharedPreferences prefs = main3ActivityDrawer.getSharedPreferences("MIPROPS", 0);
            SharedPreferences.Editor edit = prefs.edit();

            edit.putString("urlPerfil", urlImg);

            edit.commit();

            main3ActivityDrawer.main3ActivityDrawerController.descargarImg();

            Log.v("FotoPerfil", "SHAREPREFERENCE ----------------- " + edit.putString("urlPerfil", urlImg));

            Log.v("FotoPerfil", "FOTTOOOOOOOOOOO" + urlImg);
            DataHolder.instance.qbAdmin.insertarDatosPerfil(urlImg);
        }
    }

    @Override
    public void cambiarContraseñas(boolean cambiada, QBUser user) {
        if (cambiada) {
            Toast.makeText(main3ActivityDrawer, "Contraseña cambiada", Toast.LENGTH_SHORT).show();
            main3ActivityDrawer.perfil.etContraseñaPerfil.setText("");
        } else {
            Toast.makeText(main3ActivityDrawer, "Fallo al cambiar la contraseña", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void insertarDatosPerfil(boolean blInsertado, QBCustomObject object) {
        if (blInsertado) {
            Toast.makeText(main3ActivityDrawer, "Foto de perfil actualizado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(main3ActivityDrawer, "Error al actualizar foto de perfil", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(final View view) {
        //Para cerrar el fragment pin cuando pulsas el imgButton
        if (view.getId() == main3ActivityDrawer.pin.imgBtnClose.getId()) {
            Log.v("Entra", "CERRRRRARRR********** ");
            main3ActivityDrawer.cambiarFragmentDrawer(5);
        }
        //para mandar los datos del pin al fragment de galeria
        if (view.getId() == main3ActivityDrawer.pin.imgBtnFavorites.getId()) {
            Log.v("FAVORITOSSSS     :", main3ActivityDrawer.pin.txt_dificultad.getText().toString());
            DataHolder.instance.contadorFav+=1;
            main3ActivityDrawer.galeriaFrag.data();
            Toast.makeText(view.getContext(), "Spot agregado a favoritos", Toast.LENGTH_SHORT).show();
        }

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
                DataHolder.instance.qbAdmin.cambiarContraseña(DataHolder.instance.pwd, main3ActivityDrawer.perfil.etContraseñaPerfil.getText().toString(), DataHolder.instance.user);
            }
        }

        if (view.getId() == main3ActivityDrawer.perfil.btnGuardarPerfil.getId()) {
            try {
                Log.v("ENTRA_PERFIL", "----------------------- AL PULSAR BOTON -----------------------");
                DataHolder.instance.qbAdmin.subirFotoPerfil(main3ActivityDrawer.perfil.mPath);
            } catch (QBResponseException e) {
                e.printStackTrace();
            }
        }

        if (view.getId() == main3ActivityDrawer.pin.btnEmpezar.getId()) {
            Log.v("ENTRA_MAPA", "ENTRA MAPAAAAAAAAA");
            Toast.makeText(view.getContext(), "Viaje iniciado", Toast.LENGTH_SHORT).show();
            main3ActivityDrawer.sendLatLong();
            main3ActivityDrawer.startDirection(DataHolder.instance.miPin, DataHolder.instance.longitud, DataHolder.instance.latitud);
        }
    }



}
