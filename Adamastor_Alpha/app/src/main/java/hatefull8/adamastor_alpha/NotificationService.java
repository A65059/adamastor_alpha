package hatefull8.adamastor_alpha;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

public class NotificationService extends NotificationListenerService {

    private String TAG = this.getClass().getSimpleName();
    private NLServiceReceiver nlservicereciver;
    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("carlos.simplelauncher.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
        this.requestListenerHints(this.HINT_HOST_DISABLE_NOTIFICATION_EFFECTS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        Log.i(TAG,"ENTREI\n");
        if(sbn.getPackageName().equals("carlos.simplelauncher")){
            //NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            //nm.cancel(sbn.getId());

            Log.i(TAG,"BLOQUEADO\n");
            Intent i = new  Intent("carlos.simplelauncher.NOTIFICATION_LISTENER_EXAMPLE");
            i.putExtra("notification_event", "BLOQUEADO\n");
            sendBroadcast(i);

            cancelNotification(sbn.getKey());

        }else {
            //NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            Log.i(TAG, "**********  onNotificationPosted");
            Log.i(TAG, "ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText + "\t" + sbn.getPackageName());
            Intent i = new Intent("carlos.simplelauncher.NOTIFICATION_LISTENER_EXAMPLE");
            i.putExtra("notification_event", "onNotificationPosted :" + sbn.getPackageName() + "\n");
            sendBroadcast(i);
            //this.cancelAllNotifications();
            cancelNotification(sbn.getKey());
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG,"********** onNOtificationRemoved");
        Log.i(TAG,"ID :" + sbn.getId() + "\t" + sbn.getNotification().tickerText +"\t" + sbn.getPackageName());
        Intent i = new  Intent("carlos.simplelauncher.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("notification_event","onNotificationRemoved :" + sbn.getPackageName() + "\n");

        sendBroadcast(i);
    }

    class NLServiceReceiver extends BroadcastReceiver{


        @Override
        public void onReceive(Context context, Intent intent) {
            int j = 0;
            if(intent.getStringExtra("command").equals("clearall")){
                NotificationService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                Intent i1 = new  Intent("carlos.simplelauncher.NOTIFICATION_LISTENER_EXAMPLE");
                i1.putExtra("notification_event","=====================");
                sendBroadcast(i1);
                int i=1;
                for (StatusBarNotification sbn : NotificationService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("carlos.simplelauncher.NOTIFICATION_LISTENER_EXAMPLE");
                    Bundle b = sbn.getNotification().extras;
                    PackageManager pm = getApplicationContext().getPackageManager();
                    ApplicationInfo ai;
                    try{
                        ai = pm.getApplicationInfo(sbn.getPackageName(),0);
                    }catch(final PackageManager.NameNotFoundException e){
                        ai = null;
                    }
                    if(pm.getApplicationLabel(ai).equals("SimpleLauncher")){
                        i2.putExtra("notification_event", "BLOQUEADO");
                        sendBroadcast(i2);
                        i++;
                    }else{
                        i2.putExtra("notification_event",i +" ### " + pm.getApplicationLabel(ai) + " ### " + b.getCharSequence(Notification.EXTRA_TITLE) + " ### " + b.getCharSequence(Notification.EXTRA_TEXT) + " ###" + "\n");
                        sendBroadcast(i2);
                        i++;
                    }

                }
                Intent i3 = new  Intent("carlos.simplelauncher.NOTIFICATION_LISTENER_EXAMPLE");
                i3.putExtra("notification_event","===== Notification List ====");
                sendBroadcast(i3);

            }

        }
    }

}
