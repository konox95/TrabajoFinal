package konox.actividad3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by konox on 18/01/2017.
 */

public class MapaFragment extends SupportMapFragment {

    public MapaFragment() {
    }

    public static MapaFragment newInstance() {
        return new MapaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);

        return root;
    }
}
