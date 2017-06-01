package konox.actividad3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by konox on 18/01/2017.
 */

public class PerfilFragment extends Fragment {

    TextView tvNombrePerfil;
    TextView tvEmailPErfil;
    ImageButton btnEdit;
    TextView tvContraseñaPerfil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        tvNombrePerfil= (TextView) v.findViewById(R.id.tvNombreUsuarioPerfil);
        tvEmailPErfil= (TextView) v.findViewById(R.id.tvEmaileUsuarioPerfil);
        tvContraseñaPerfil= (TextView) v.findViewById(R.id.etContraseñaPerfil);

        btnEdit = (ImageButton)v.findViewById(R.id.ibEditar);

        tvNombrePerfil.setText(DataHolder.instance.name);
        tvEmailPErfil.setText(DataHolder.instance.email);

        return v;
    }
}
