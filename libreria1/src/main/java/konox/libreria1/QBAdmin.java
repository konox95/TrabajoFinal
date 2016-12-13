package konox.libreria1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

/**
 * Created by konox on 22/11/2016.
 */

public class QBAdmin {
    QBAdminListiner adminlistener;

    public QBAdmin(QBAdminListiner qbadminlistener,Activity activity) {
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
        final QBUser user = new QBUser(usr, pwd);
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

    public void registrarse(String usr, String email, String pwd){
        final  QBUser user = new QBUser(usr, email, pwd);
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


}
