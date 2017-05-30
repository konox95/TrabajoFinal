package konox.actividad3;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public Button btnIniciar;
    public TextView btnRegistrarse;
    public EditText et_usr;
    public EditText et_pwd;
    boolean blPrimeraVez=true;

    public LoginFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_login, container, false);
        btnIniciar=(Button)v.findViewById(R.id.btnOkLogin);
        btnRegistrarse = (TextView) v.findViewById(R.id.botonRegistro);
        et_usr= (EditText) v.findViewById(R.id.usuario);
        et_pwd= (EditText) v.findViewById(R.id.contraseña);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //checkPrimeraVez();



    }



    @Override
    public void onStart() {
        super.onStart();
        if(DataHolder.instance.blIntentarLogearse){
            DataHolder.instance.blIntentarLogearse = false;
            String email;
            String pwd;
            SharedPreferences prefs =getActivity().getSharedPreferences("MIPROPS",0);
            email= prefs.getString("email", null);
            pwd= prefs.getString("pwd", null);

            if(email != null && pwd != null){
                Log.v("Entra", "ENTRA------------------------------------------------------"+email+"    "+pwd);
                DataHolder.instance.qbAdmin.login(email, pwd);
            }
        }
    }

    public void checkPrimeraVez(){

        if(blPrimeraVez){
            String email;
            String pwd;
            SharedPreferences prefs =getActivity().getSharedPreferences("MIPROPS",0);
            email= prefs.getString("email", null);
            pwd= prefs.getString("pwd", null);

            if(email != null && pwd != null){
                Log.v("Entra", "ENTRA------------------------------------------------------"+email+"    "+pwd);
                DataHolder.instance.qbAdmin.login(email, pwd);
            }
            blPrimeraVez=false;
        }
        else{

        }


    }

}
