package konox.watch;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.quickblox.content.model.QBFile;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import konox.libreria1.MiPin;
import konox.libreria1.QBAdminListiner;

/**
 * Created by konox on 07/06/2017.
 */

public class MapsActivityController implements QBAdminListiner, GoogleMap.OnMarkerClickListener {

    MapsActivity vista;

    public MapsActivityController(MapsActivity vista) {
        this.vista = vista;
        DataHolder.instance.qbAdmin.setListener(this);
    }


    @Override
    public void sesionCreada(boolean creada) {
        if (creada) {
            DataHolder.instance.qbAdmin.login(vista.usr, vista.pwd);
        }
    }

    @Override
    public void logeado(boolean blLogeado, QBUser user) {
        if (blLogeado) {

            Log.v("Logueado", "ME he logueado");

            user.getEmail();

            Toast.makeText(vista, "Auth Success", Toast.LENGTH_SHORT).show();

            DataHolder.instance.qbAdmin.descargDatosPines();

        } else if (!blLogeado) {
            Toast.makeText(vista, "Auth fail", Toast.LENGTH_SHORT).show();
        }
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
            Marker tempmar = vista.mMap.addMarker(new MarkerOptions().position(current).
                    title(pines.get(i).sNombre));
            tempmar.setTag(pines.get(i));

            vista.mMap.setOnMarkerClickListener(this);
        }

    }

    @Override
    public void insertarSpot(boolean blInsertado, QBCustomObject object) {

    }

    @Override
    public void fotosubida(boolean blUpload, QBFile qbFile) {

    }

    @Override
    public void fotosubidaPerfil(boolean blUpload, QBFile qbFile) {

    }

    @Override
    public void cambiarContraseñas(boolean cambiada, QBUser user) {

    }

    @Override
    public void insertarDatosPerfil(boolean blInsertado, QBCustomObject object) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        MiPin pin = (MiPin) marker.getTag();
        Log.v("MAP", "NOMBRE DEL MARKER PINCHADO " + pin.sNombre + pin.sDificultad);
        DataHolder.instance.miPin = pin;


        vista.sendLatLong();

        vista.startDirection(DataHolder.instance.miPin, -3.8939395, 40.540424);

        return false;
    }
}
