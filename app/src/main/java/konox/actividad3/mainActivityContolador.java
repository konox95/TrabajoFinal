package konox.actividad3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.quickblox.content.model.QBFile;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

import konox.libreria1.MiPin;
import konox.libreria1.QBAdminListiner;

/**
 * Created by konox on 01/12/2016.
 */

public class mainActivityContolador implements View.OnClickListener, QBAdminListiner {
    //QBAdmin qbAdmin;
    MainActivity vista;
    LoginFragment loginFragment;
    String email=null,pwd=null;
    Main3ActivityDrawer main3ActivityDrawer;

    public boolean blAutoLoginCorrect=false;
    public boolean blLoginClickPressed=false;

    public mainActivityContolador(MainActivity vista) {
        this.vista = vista;
        DataHolder.instance.qbAdmin.setListener(this);

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == vista.loginFragment.btnIniciar.getId()) {
            blLoginClickPressed=true;
            DataHolder.instance.qbAdmin.login(vista.loginFragment.et_usr.getText().toString(), vista.loginFragment.et_pwd.getText().toString());

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


    public void autoLogin(){
        //String email,pwd;
        SharedPreferences prefs = vista.getSharedPreferences("MIPROPS",0);
        email= prefs.getString("email", null);
        pwd= prefs.getString("pwd", null);

        if(email != null && pwd != null){
            Log.v("Entra", "xxxxxxxxxxxxxxxxxxxxxxxxx- "+email+"       "+pwd);
            DataHolder.instance.qbAdmin.login(email, pwd);

            DataHolder.instance.email = email;

            Log.v("AAA", "PEFIL ----------------------------------------------------------------------------------------------");
        }
    }


    @Override
    public void sesionCreada(boolean creada) {
        if(creada){
            autoLogin();
        }
    }

    @Override
    public void logeado(boolean blLogeado, QBUser user) {
        if (blLogeado) {
            Log.v("Controlador", "ME HE LOGEADO");

            SharedPreferences prefs = vista.getSharedPreferences("MIPROPS",0);
            SharedPreferences.Editor edit = prefs.edit();


            edit.putString("email",vista.loginFragment.et_usr.getText().toString());
            edit.putString("pwd", vista.loginFragment.et_pwd.getText().toString());

            user.getEmail();

            edit.commit();

            DataHolder.instance.urlImage = prefs.getString("urlPerfil", null);

            blAutoLoginCorrect=true;

            //Cogemos el email y el nombre del qbuser y lo guardamos en el dataHolder
            DataHolder.instance.email = user.getEmail();
            DataHolder.instance.perfilNombre = user.getLogin();
            Log.v("NOMBRE", DataHolder.instance.perfilNombre);
            DataHolder.instance.user = user;

            if(pwd==null){
                DataHolder.instance.pwd=vista.loginFragment.et_pwd.getText().toString();
            }
            else{
                DataHolder.instance.pwd=pwd;
            }

            if(blLoginClickPressed){

                Intent inten = new Intent(vista, Main3ActivityDrawer.class);
                vista.startActivity(inten);
            }

        } else if (!blLogeado){
            Toast.makeText(vista, "Auth fail", Toast.LENGTH_SHORT).show();
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

    @Override
    public void fotosubida(boolean blUpload, QBFile qbFile) {

    }

    @Override
    public void fotosubidaPerfil(boolean blUpload, QBFile qbFile) {

    }

    @Override
    public void cambiarContraseñas(boolean cambiada, QBUser user) {

    }

    @Override
    public void insertarDatosPerfil(boolean blInsertado, QBCustomObject object) {

    }

}
