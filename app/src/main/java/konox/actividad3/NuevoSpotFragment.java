package konox.actividad3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by konox on 18/01/2017.
 */

public class NuevoSpotFragment extends Fragment {
    Button btnNewSpot;
    EditText editTextSpot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_nuevospot, container, false);
        btnNewSpot=(Button)v.findViewById(R.id.btnOkSpot);
        editTextSpot=(EditText)v.findViewById(R.id.editTextSpot);
        return v;
    }
}
