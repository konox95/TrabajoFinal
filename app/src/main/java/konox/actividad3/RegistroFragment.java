package konox.actividad3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistroFragment extends Fragment {
    public Button btnAceptar;
    public Button btnCancelar;
    public EditText ed_usr;
    public EditText ed_email;
    public EditText ed_pwd;


    public RegistroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registro, container, false);
        btnAceptar = (Button) v.findViewById(R.id.btnOkRegistro);
        btnCancelar = (Button) v.findViewById(R.id.buttonLogin);
        ed_usr = (EditText) v.findViewById(R.id.editTextNombreUsuario);
        ed_email= (EditText) v.findViewById(R.id.editTextEmail);
        ed_pwd = (EditText) v.findViewById(R.id.editTextContraseña);
        return v;

    }

}
