package konox.examen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by konox on 20/12/2016.
 */

public class RegFragment extends Fragment {
    public Button btnAceptar;
    public Button btnCancelar;
    public EditText ed_usr;
    public EditText ed_email;
    public EditText ed_pwd;

    public RegFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.registarse_fragment, container, false);
        btnAceptar = (Button) v.findViewById(R.id.button_aceptar);
        btnCancelar = (Button) v.findViewById(R.id.button_cancelar);
        ed_usr = (EditText) v.findViewById(R.id.editText_usuario);
        ed_email = (EditText) v.findViewById(R.id.editText_email);
        ed_pwd = (EditText) v.findViewById(R.id.editText_pwd);
        return v;

    }
}
