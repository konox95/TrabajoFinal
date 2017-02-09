package konox.actividad3;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import konox.libreria1.MiPin;

/**
 * Created by konox on 17/01/2017.
 */

public class main3ActivityDrawerController implements NavigationView.OnNavigationItemSelectedListener,GoogleMap.OnMarkerClickListener {
    Main3ActivityDrawer main3ActivityDrawer;

    public main3ActivityDrawerController(Main3ActivityDrawer main3ActivityDrawer) {
        this.main3ActivityDrawer = main3ActivityDrawer;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.v("MenuItems",R.id.perfil+" "+R.id.nuevoSpot+" "+R.id.mapaSpots+" "+item.getItemId());

        if(item.getItemId()==main3ActivityDrawer.perfilNav.getItemId()){
            main3ActivityDrawer.cambiarFragmentDrawer(3);
            Log.v("AAA", "PEFIL");
            main3ActivityDrawer.onBackPressed();
        }
        else if(item.getItemId()==main3ActivityDrawer.spotNav.getItemId()){
            main3ActivityDrawer.cambiarFragmentDrawer(2);
            Log.v("BBB", "NUEVOOOO");
            main3ActivityDrawer.onBackPressed();
        }
        else if(item.getItemId()==main3ActivityDrawer.mapaNav.getItemId()){
            main3ActivityDrawer.cambiarFragmentDrawer(1);
            Log.v("CCCC", "MAPAAAA!!!!!!!!!");
            main3ActivityDrawer.onBackPressed();
        }

        return true;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {


        MiPin pin=(MiPin)marker.getTag();
        Log.v("MAP","NOMBRE DEL MARKER PINCHADO "+pin.sNombre);

        return false;
    }
}
