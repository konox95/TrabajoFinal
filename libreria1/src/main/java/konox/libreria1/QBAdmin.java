package konox.libreria1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.Consts;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.model.QBBaseCustomObject;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by konox on 22/11/2016.
 */

public class QBAdmin {
    QBAdminListiner adminlistener;
    public  HashMap<Integer, String> palabra;
    public String sValor;
    public int iPal;

    public QBAdmin(QBAdminListiner qbadminlistener, Activity activity) {
        final String APP_ID = "40279";
        final String AUTH_KEY = "CfDF25-RfZt5XaE";
        final String AUTH_SECRET = "vHwmPW7vbCY79WV";
        final String ACCOUNT_KEY = "zbCFpz9PjsxxxtzV7Em5";

        this.adminlistener = qbadminlistener;

        QBSettings.getInstance().init(activity, APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);

        QBAuth.createSession(new QBEntityCallback<QBSession>() {

            @Override
            public void onSuccess(QBSession session, Bundle params) {
                // You have successfully created the session
                //
                // Now you can use QuickBlox API!
            }

            @Override
            public void onError(QBResponseException errors) {

            }
        });
    }

    public void login(String usr, String pwd) {
        QBUser user = new QBUser(usr, pwd);

        QBUsers.signIn(user, new QBEntityCallback<QBUser>() {
            public void onSuccess(QBUser user, Bundle args) {
                // success
                adminlistener.logeado(true, user);
            }

            public void onError(QBResponseException error) {
                // error
                adminlistener.logeado(false, null);
            }
        });
    }

    public void registrarse(String usr, String email, String pwd,  String repetirContraseña) {
        final QBUser user = new QBUser(usr,pwd,repetirContraseña);

        user.setEmail(email);

        if(pwd.equals(repetirContraseña)) {
        QBUsers.signUp(user, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                adminlistener.registrado(true, qbUser);
            }

            @Override
            public void onError(QBResponseException e) {
                adminlistener.registrado(false, null);
            }
        });
        }else{

            Log.v("Controller", "Las contraseñas no son iguales");
        }
    }

    public void descargDatos(String idIdioma) {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();

        requestBuilder.eq("IdIdioma", idIdioma);

        QBCustomObjects.getObjects("Idiomas", requestBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {

            @Override
            public void onSuccess(ArrayList<QBCustomObject> qbCustomObjects, Bundle bundle) {
                palabra = new HashMap<Integer, String>();
                for (int i = 0; i < qbCustomObjects.size(); i++) {
                    Log.v("QBAdmin", "Fila" + i + qbCustomObjects.get(i).getFields());
                    sValor = qbCustomObjects.get(i).getFields().get("Valor").toString();
                    iPal = (int) qbCustomObjects.get(i).getFields().get("IdPalabra");
                    palabra.put(iPal, sValor);

                }
                adminlistener.datosdescarg(palabra);
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }
}
