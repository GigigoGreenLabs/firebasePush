package firebasetest.gigigo.com.testfirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * Created by nubor on 21/04/2017.
 */

public class MyOwnBroadcastReceiver extends BroadcastReceiver {

  @Override public void onReceive(Context context, Intent intent) {
    initActivity(context, intent);
    Toast.makeText(context,"MyBroadCastReceiver.onReceive->initActivity(checkDeeplink)",Toast.LENGTH_LONG).show();
  }

  private void initActivity(Context context, Intent intent) {
    if (intent != null && intent.getExtras() != null ) {
      PackageManager pm = context.getPackageManager();
      Intent i = pm.getLaunchIntentForPackage(context.getPackageName());
      i.putExtras(intent);
      i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
          | Intent.FLAG_ACTIVITY_NEW_TASK
          | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      context.startActivity(i);
    }
  }
}
