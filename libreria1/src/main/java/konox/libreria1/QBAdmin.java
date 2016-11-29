package konox.libreria1;

import android.app.Activity;
import android.content.Context;

import com.quickblox.core.QBSettings;

/**
 * Created by konox on 22/11/2016.
 */

public class QBAdmin{
    QBAdminListiner adminlistener;
    static final String APP_ID = "40279";
    static final String AUTH_KEY = "CfDF25-RfZt5XaE";
    static final String AUTH_SECRET = "vHwmPW7vbCY79WV";
    static final String ACCOUNT_KEY = "zbCFpz9PjsxxxtzV7Em5";

    public QBAdmin(Activity activity){
        QBSettings.getInstance().init(activity, APP_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
    }

    public void AdminListener(QBAdminListiner adminlistener){
        this.adminlistener = adminlistener;
    }



}
