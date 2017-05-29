package konox.actividad3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.quickblox.core.model.QBBaseCustomObject;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.HashMap;

import konox.libreria1.MiPin;
import konox.libreria1.QBAdmin;
import konox.libreria1.QBAdminListiner;

/**
 * Created by konox on 01/12/2016.
 */

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.quickblox.core.model.QBBaseCustomObject;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.HashMap;

import konox.libreria1.QBAdmin;
import konox.libreria1.QBAdminListiner;

/**
 * Created by konox on 01/12/2016.
 */

public class mainActivityContolador implements View.OnClickListener, QBAdminListiner {
    //QBAdmin qbAdmin;
    MainActivity vista;
    public String sUser,sPass;

    public mainActivityContolador(MainActivity vista) {
        this.vista = vista;
        //qbAdmin = new QBAdmin(this, vista);
        DataHolder.instance.qbAdmin.setListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == vista.loginFragment.btnIniciar.getId()) {
            DataHolder.instance.qbAdmin.login(vista.loginFragment.et_usr.getText().toString(), vista.loginFragment.et_pwd.getText().toString());
            Intent inten = new Intent(vista, Main3ActivityDrawer.class);
            vista.startActivity(inten);
        } else if (view.getId() == vista.loginFragment.btnRegistrarse.getId()){
            vista.cambiarFragment(2);
        }

        if (view.getId() == vista.registroFragment.btnAceptar.getId()) {
            DataHolder.instance.qbAdmin.registrarse(vista.registroFragment.ed_usr.getText().toString(), vista.registroFragment.ed_pwd.getText().toString(), vista.registroFragment.ed_email.getText().toString());
            vista.cambiarFragment(1);
        }
        if (view.getId() == vista.registroFragment.btnCancelar.getId()){
            vista.cambiarFragment(1);
        }

    }

    @Override
    public void logeado(boolean blLogeado, QBUser user) {
        if (blLogeado) {
            Log.v("Controlador", "ME HE LOGEADO");
        }
    }

    @Override
    public void registrado(boolean blRegistrado, QBUser user) {
        if (blRegistrado) {
            Log.v("Controlador", "ME HE REGISTRADO");
        }
    }

    @Override
    public void descargaPinesFinalizado(ArrayList<MiPin> pines) {

    }

    @Override
    public void insertarSpot(boolean blRegistrado, QBCustomObject object) {

    }

}
