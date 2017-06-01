package konox.libreria1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
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


    public QBAdmin(Activity activity) {
        final String APP_ID = "40279";
        final String AUTH_KEY = "CfDF25-RfZt5XaE";
        final String AUTH_SECRET = "vHwmPW7vbCY79WV";
        final String ACCOUNT_KEY = "zbCFpz9PjsxxxtzV7Em5";


        QBSettings.getInstance().init(activity, APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        QBAuth.createSession(new QBEntityCallback<QBSession>() {

            @Override
            public void onSuccess(QBSession session, Bundle params) {
                // You have successfully created the session
                //
                // Now you can use QuickBlox API!
                adminlistener.sesionCreada(true);
            }

            @Override
            public void onError(QBResponseException errors) {
                adminlistener.sesionCreada(false);
            }
        });
    }

    public void setListener(QBAdminListiner qbadminlistener) {
        this.adminlistener = qbadminlistener;
    }

    public void login(String usr, String pwd) {
        final QBUser user = new QBUser();
        user.setEmail(usr);
        user.setPassword(pwd);
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

    public void registrarse(String usr, String email, String pwd) {
        final QBUser user = new QBUser(usr, email, pwd);
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
    }

    public void descargDatosPines() {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        QBCustomObjects.getObjects("Pines", requestBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {

            @Override
            public void onSuccess(ArrayList<QBCustomObject> qbCustomObjects, Bundle bundle) {
                ArrayList<MiPin> pines = new ArrayList<MiPin>();
                Log.v("QBADMIN", "----->>>>>  " + qbCustomObjects);

                for (int i = 0; i < qbCustomObjects.size(); i++) {
                    Log.v("QBAdmin", "Fila" + i + qbCustomObjects.get(i).getFields());
                    double Longitud = (double) qbCustomObjects.get(i).getFields().get("Longitud");
                    double Latitud = (double) qbCustomObjects.get(i).getFields().get("Latitud");
                    String nombreSpot = qbCustomObjects.get(i).getFields().get("Nombre").toString();
                    pines.add(new MiPin(Latitud, Longitud, nombreSpot));
                }

                adminlistener.descargaPinesFinalizado(pines);

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    public void insertarPines(double longitud, double latitud, String nombreSpot, String descripcionSpot, String tipoSpot, String dificultad ,
                              Boolean chBanco, Boolean chBarandilla, Boolean chBowl, Boolean chCajon, Boolean chEscalera, Boolean chRampa) {
        QBCustomObject object = new QBCustomObject();


        object.putString("Nombre", nombreSpot);
        object.putFloat("Longitud", (float) longitud);
        object.putFloat("Latitud", (float) latitud);
        object.putString("Descripcion", descripcionSpot);
        object.putString("Tipo", tipoSpot);
        object.putString("Dificultad", dificultad);
        object.putBoolean("CBanco", chBanco);
        object.putBoolean("CBarandilla", chBarandilla);
        object.putBoolean("CBowl", chBowl);
        object.putBoolean("CCajon", chCajon);
        object.putBoolean("CEscalera", chEscalera);
        object.putBoolean("CRampa", chRampa);

        object.setClassName("Pines");

        QBCustomObjects.createObject(object, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject createdObject, Bundle params) {
                adminlistener.insertarSpot(true, createdObject);

            }

            @Override
            public void onError(QBResponseException errors) {
                adminlistener.insertarSpot(false, null);

            }
        });

    }


}
