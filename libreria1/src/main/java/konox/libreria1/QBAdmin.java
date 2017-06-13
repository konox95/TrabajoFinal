package konox.libreria1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.helper.FileHelper;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.customobjects.QBCustomObjects;
import com.quickblox.customobjects.model.QBCustomObject;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by konox on 22/11/2016.
 */

public class QBAdmin {
    QBAdminListiner adminlistener;
    Activity activity;
    ProgressDialog progressDialog;

    public QBAdmin(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
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

        Log.v("ENTRE_LOG", "USER " + usr + " PWD " + pwd);
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

    public void cambiarContraseña(String oldpsw,String contraseñaPerfil, QBUser user) {

        user.setOldPassword(oldpsw);
        user.setPassword(contraseñaPerfil);

        QBUsers.updateUser(user, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                adminlistener.cambiarContraseñas(true, user);
            }

            @Override
            public void onError(QBResponseException errors) {
                adminlistener.cambiarContraseñas(false, null);
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
        Log.v("ENTRA_PINES" ,"ENTRA EN descargDatosPines QBADMIN -------------------");
        QBCustomObjects.getObjects("Pines", requestBuilder, new QBEntityCallback<ArrayList<QBCustomObject>>() {

            @Override
            public void onSuccess(ArrayList<QBCustomObject> qbCustomObjects, Bundle bundle) {
                Log.v("ENTRA_PINES" ,"AAAAAAAAAAAAA -------------------");

                ArrayList<MiPin> pines = new ArrayList<MiPin>();
                Log.v("QBADMIN", "----->>>>>  " + qbCustomObjects);

                for (int i = 0; i < qbCustomObjects.size(); i++) {
                    Log.v("QBAdmin", "Fila" + i + qbCustomObjects.get(i).getFields());
                    double Longitud = (double) qbCustomObjects.get(i).getFields().get("Longitud");
                    double Latitud = (double) qbCustomObjects.get(i).getFields().get("Latitud");
                    String nombreSpot = qbCustomObjects.get(i).getFields().get("Nombre").toString();
                    String descripSpot = qbCustomObjects.get(i).getFields().get("Descripcion").toString();
                    String tipoSpot =  qbCustomObjects.get(i).getFields().get("Tipo").toString();
                    String dificultadSpot =  qbCustomObjects.get(i).getFields().get("Dificultad").toString();
                    Boolean chBanco = (Boolean) qbCustomObjects.get(i).getFields().get("CBanco");
                    Boolean chBarandilla = (Boolean) qbCustomObjects.get(i).getFields().get("CBarandilla");
                    Boolean chBowl = (Boolean) qbCustomObjects.get(i).getFields().get("CBowl");
                    Boolean chCajon = (Boolean) qbCustomObjects.get(i).getFields().get("CCajon");
                    Boolean chEscalera = (Boolean) qbCustomObjects.get(i).getFields().get("CEscalera");
                    Boolean chRampa = (Boolean) qbCustomObjects.get(i).getFields().get("CRampa");

                    ArrayList<String> imgSpot= new ArrayList<String>();
                    if(qbCustomObjects.get(i).getFields().get("Fotos")!=null){

                        Gson gson = new Gson();

                        String json = gson.toJson(qbCustomObjects.get(i).getFields().get("Fotos"));

                        try {

                            JSONArray jsonArray =new JSONArray(json);
                            for(int j=0;j<jsonArray.length();j++){
                                imgSpot.add(jsonArray.getString(j));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                    pines.add(new MiPin(Latitud, Longitud, nombreSpot,descripSpot, tipoSpot, dificultadSpot, imgSpot, chBanco, chBarandilla, chBowl, chCajon, chEscalera, chRampa));
                }
                adminlistener.descargaPinesFinalizado(pines);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.v("Error", e.toString());
            }
        });
    }

    public void insertarPines(double longitud, double latitud, String nombreSpot, String descripcionSpot, String tipoSpot, String dificultad,
                              Boolean chBanco, Boolean chBarandilla, Boolean chBowl, Boolean chCajon, Boolean chEscalera, Boolean chRampa, ArrayList<String> fotos) {
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
        object.putArray("Fotos", fotos);

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

    public void subirFoto(String path) throws QBResponseException {
        File file = new File(path);
        Log.v("QBADMIN", file + " +++++++++++++++++++++++++++++++++++++++++++++++++++ " + path);

        QBContent.uploadFileTask(file, true, null, new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle bundle) {
                adminlistener.fotosubida(true, qbFile);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.v("Error", e.toString());
                adminlistener.fotosubida(false, null);
            }
        }, new QBProgressCallback() {
            @Override
            public void onProgressUpdate(int progress) {

            }
        });

    }

    public void subirFotoPerfil(String path) throws QBResponseException {
        File file = new File(path);

        Log.v("ENTRA_PERFIL", "----------------------- ENTRA POR SUBIR FOTO PERFIL -----------------------");


        Log.v("QBADMIN", file + " +++++++++++++++++++++++++++++++++++++++++++++++++++ " + path);

        QBContent.uploadFileTask(file, true, null, new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle bundle) {
                adminlistener.fotosubidaPerfil(true, qbFile);
            }

            @Override
            public void onError(QBResponseException e) {
                Log.v("Error", e.toString());
                adminlistener.fotosubidaPerfil(false, null);
            }
        }, new QBProgressCallback() {
            @Override
            public void onProgressUpdate(int progress) {

            }
        });

    }

    public void insertarDatosPerfil(String urlFotoPErfil) {
        QBCustomObject object = new QBCustomObject();

        Log.v("ENTRA_PERFIL", "----------------------- ENTRA POR PERFIL -----------------------");

        object.putString("FotoPerfil", urlFotoPErfil);

        object.setClassName("Perfil");

        QBCustomObjects.createObject(object, new QBEntityCallback<QBCustomObject>() {
            @Override
            public void onSuccess(QBCustomObject createdObject, Bundle params) {
                adminlistener.insertarDatosPerfil(true, createdObject);

            }

            @Override
            public void onError(QBResponseException errors) {
                adminlistener.insertarDatosPerfil(false, null);

            }
        });

    }



}
