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
public class LoginFragment extends Fragment {

    public Button btnIniciar;
    public Button btnRegistrarse;
    public EditText et_usr;
    public EditText et_pwd;

    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_login, container, false);
        btnIniciar=(Button)v.findViewById(R.id.btnOkLogin);
        btnRegistrarse = (Button) v.findViewById(R.id.botonRegistro);
        et_usr= (EditText) v.findViewById(R.id.usuario);
        et_pwd= (EditText) v.findViewById(R.id.contraseña);
        return v;
    }

}
