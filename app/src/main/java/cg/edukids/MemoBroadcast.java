package cg.edukids;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MemoBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.girlpng)
                .setLargeIcon(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.background), 128, 128,false))
                .setContentTitle("EduKids")
                .setContentText("This is a daily notification, let's play")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());
    }
}
