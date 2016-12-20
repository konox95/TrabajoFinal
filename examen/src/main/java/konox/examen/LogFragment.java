package konox.examen;

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
 * Created by konox on 20/12/2016.
 */

public class LogFragment extends Fragment {
    public Button btnLog;
    public Button btnReg;
    public Button btnEsp;
    public Button btnIng;
    public EditText et_USR;
    public EditText et_PWD;
    public TextView txt_USR;
    public TextView txt_PWD;

    public LogFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        btnLog = (Button) v.findViewById(R.id.btnLog);
        btnReg = (Button) v.findViewById(R.id.btnRegistrarse);
        btnEsp = (Button) v.findViewById(R.id.btnESP);
        btnIng = (Button) v.findViewById(R.id.btnING);
        et_USR = (EditText) v.findViewById(R.id.et_USR);
        et_PWD = (EditText) v.findViewById(R.id.et_PWD);
        txt_USR = (TextView) v.findViewById(R.id.txtUSR);
        txt_PWD = (TextView) v.findViewById(R.id.txtPWD);
        Log.v("LogFragment", btnLog + "");
        return v;
    }
}
