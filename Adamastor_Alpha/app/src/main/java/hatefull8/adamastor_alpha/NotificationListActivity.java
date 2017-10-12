package hatefull8.adamastor_alpha;

import android.app.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class NotificationListActivity extends Activity {


    private NotificationReceiver nReceiver;
    private TextView txtView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);
        nReceiver = new NotificationReceiver();
        txtView = (TextView) findViewById(R.id.textView);
        IntentFilter filter = new IntentFilter();
        filter.addAction("carlos.simplelauncher.NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(nReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }

    protected void loadNotifications(){

    }

    public void sendNotification(View v){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.btn_star_big_on)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, NotificationListActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(NotificationListActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

   /* notificationID allows you to update the notification later on. */
        mNotificationManager.notify(0, mBuilder.build());
        txtView.setText("======"+ "\n"  + txtView.getText());
    }

    public void listNotification(View v){
        Intent i = new Intent("carlos.simplelauncher.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        i.putExtra("command","list");
        sendBroadcast(i);
    }

    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("notification_event") + "\n" + txtView.getText();
            txtView.setText(temp);
            //NotificationListenerService nls = new NotificationService();
            //nls.cancelAllNotifications();
        }
    }


}