package konox.examen;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.quickblox.users.model.QBUser;

import java.util.HashMap;

import konox.libreria1.QBAdmin;
import konox.libreria1.QBAdminListiner;

/**
 * Created by konox on 20/12/2016.
 */

public class mainActivityController implements View.OnClickListener, QBAdminListiner {
    MainActivity vista;
    QBAdmin qbAdmin;
    String Lang;

    public  mainActivityController(MainActivity vista){
        this.vista = vista;
        qbAdmin = new QBAdmin(this, vista);
        Lang = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == vista.FragLogin.btnLog.getId() ){
            qbAdmin.login(vista.FragLogin.et_USR.getText().toString(), vista.FragLogin.et_PWD.getText().toString());
        }
        if (view.getId() == vista.FragLogin.btnReg.getId()){
            vista.cambiarFragment(2);
        }

        if (view.getId() == vista.FragRegistro.btnAceptar.getId()){
            qbAdmin.registrarse(vista.FragRegistro.ed_usr.getText().toString(), vista.FragRegistro.ed_pwd.getText().toString(), vista.FragRegistro.ed_email.getText().toString());
            vista.cambiarFragment(1);
        }
        if (view.getId() == vista.FragRegistro.btnCancelar.getId()){
            vista.cambiarFragment(1);
        }

        if (view.getId() == vista.FragLogin.btnEsp.getId()){
            Lang = "ES";
            qbAdmin.descargDatos(Lang);
        }
        if (view.getId() == vista.FragLogin.btnIng.getId()){
            Lang = "EN";
            qbAdmin.descargDatos(Lang);
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
    public void datosdescarg(HashMap<Integer, String> datos) {
        vista.FragLogin.txt_USR.setText(datos.get(5));
        vista.FragLogin.txt_PWD.setText(datos.get(6));
    }
}
