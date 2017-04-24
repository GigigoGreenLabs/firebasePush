package firebasetest.gigigo.com.testfirebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import modules.gigigo.com.notificationsdk.Builders.NotificationGeneratorBuilder;
import modules.gigigo.com.notificationsdk.NotificationGenerator;
import modules.gigigo.com.notificationsdk.ResBuilders.DrawablesNotGenBuilder;

import static modules.gigigo.com.notificationsdk.NotificationGenerator.mBuilder;

/**
 * Created by nubor on 20/04/2017.
 */
//firebase step5
public class MyOwnFirebaseMessagingService extends FirebaseMessagingService {
  //region firebase NotificationGenerator
  NotificationGenerator mNotificationGenerator = new NotificationGenerator();
  DrawablesNotGenBuilder mDrawablesNotGenBuilder =
      new DrawablesNotGenBuilder().setLarge_color_icon(R.drawable.ox_notification_large_icon)
          .setSmall_alpha_icon(R.drawable.ox_notification_alpha_small_icon)
          .setSmall_color_icon(R.drawable.ox_notification_color_small_icon);

  /*
    LayoutNotGenBuilder mLayoutNotGenBuilder =
        new LayoutNotGenBuilder().setBig_local_notification_layout(
            R.layout.black_big_local_notification)
            .setBig_push_notification_layout(R.layout.black_big_push_notification)
            .setNormal_local_notification_layout(R.layout.black_normal_local_notification)
            .setNormal_push_notification_layout(R.layout.black_normal_push_notification);

    ViewIdNotGenBuilder mViewIdNotGenBuilder =
        new ViewIdNotGenBuilder().setDateTimeView(R.id.app_time_custom_local_notification)
            .setImageViewLargeIcon(R.id.app_notifimage_custom_local_notification)
            .setImageViewSmallColorIcon(R.id.app_notifimage_small_custom_local_notification)
            .setTextViewBody(R.id.app_notiftext_custom_local_notification)
            .setTextViewTitle(R.id.app_notiftitle_custom_local_notification);
            */
  //endregion
  public PendingIntent getPendingIntent(Bundle extras, Class classe, String actionStr,
      int notificationId) {
    if (mBuilder.getmContext() != null) {
      Intent intent = new Intent();
      new Intent(mBuilder.getmContext(), classe).setAction(actionStr)
          .setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
          .putExtras(extras);

      return PendingIntent.getBroadcast(mBuilder.getmContext(), notificationId, intent,
          PendingIntent.FLAG_UPDATE_CURRENT);
    } else {
      return null;
    }
  }

  private void createNotification(NotificationGeneratorBuilder builder, String title, String body,String urlScheme) {
    mNotificationGenerator.initResources(builder);
    //asv we can show 2 kid of notifications tempaltes, one
    boolean isPush = false;
    // isPush = ((CompoundButton) findViewById(R.id.chk_is_push)).isChecked();

    if (isPush) title = "PUSH " + title;
    mNotificationGenerator.createNotification(title, body, isPush,getPendingIntent(urlScheme));
  }
  public static final String EXTRA_NOTIFICATION_PUSH_URL_SCHEME_ACTION =
      "EXTRA_NOTIFICATION_PUSH_URL_SCHEME_ACTION";

  public PendingIntent getPendingIntent(String UrlScheme) {
    Intent i = new Intent(this, MyOwnBroadcastReceiver.class);
    i.putExtra(EXTRA_NOTIFICATION_PUSH_URL_SCHEME_ACTION, UrlScheme);
    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
        | Intent.FLAG_ACTIVITY_NEW_TASK
        | Intent.FLAG_ACTIVITY_CLEAR_TASK);

    int notificationId = 1;
    return PendingIntent.getBroadcast(this, notificationId, i, PendingIntent.FLAG_UPDATE_CURRENT);
  }
  String TAG = "MyOwnService";

  //https://gigigo.atlassian.net/wiki/pages/viewpage.action?pageId=184025132 notification generator

  @Override public void onMessageReceived(RemoteMessage remoteMessage) {

    // super.onMessageReceived(remoteMessage);
    // ...
    // TODO(developer): Handle FCM messages here.
    // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
    Log.d(TAG, "From: " + remoteMessage.getFrom());

    // Check if message contains a data payload.

    // Check if message contains a notification payload.
    if (remoteMessage.getNotification() != null) {
      String urlScheme = "";
      if (remoteMessage.getData().size() > 0) {
        Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        urlScheme = remoteMessage.getData().get("urlscheme");
      }

      Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
      NotificationGeneratorBuilder builder =
          new NotificationGeneratorBuilder(this)
              .setmDrawablesNotGenBuilder(mDrawablesNotGenBuilder);
      createNotification(builder, remoteMessage.getNotification().getTitle(),
          remoteMessage.getNotification().getBody(),urlScheme);
    }
  }

  @Override public void onDeletedMessages() {
    super.onDeletedMessages();
  }

  @Override public void onMessageSent(String s) {
    super.onMessageSent(s);
  }

  @Override public void onSendError(String s, Exception e) {
    super.onSendError(s, e);
  }

  @Override public void handleIntent(Intent intent) {
    super.handleIntent(intent);
  }
}
