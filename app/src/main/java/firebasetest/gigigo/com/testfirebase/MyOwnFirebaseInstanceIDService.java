package firebasetest.gigigo.com.testfirebase;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by nubor on 20/04/2017.
 */

public class MyOwnFirebaseInstanceIDService extends FirebaseInstanceIdService {
  @Override public void onTokenRefresh() {
    // Get updated InstanceID token.
    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
    Log.d("*******", " \n\n\nRefreshed token: " + refreshedToken +"\n\n\n");

    // If you want to send messages to this application instance or
    // manage this apps subscriptions on the server side, send the
    // Instance ID token to your app server.
    //sendRegistrationToServer(refreshedToken);
  }
}
