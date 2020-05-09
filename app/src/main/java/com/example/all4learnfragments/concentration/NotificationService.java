//package com.example.all4learnfragments.concentration;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//
//import com.example.all4learnfragments.R;
//
//import java.util.concurrent.TimeUnit;
//
//
//public class NotificationService extends Service {
//
//    NotificationManager nm;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//    }
//
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        sendNotif();
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    void sendNotif() {
//        // 1-я часть
//        Notification notif = new Notification(R.drawable.progress_bar, "We are watching you" ,
//                System.currentTimeMillis());

//        // 3-я часть
//        Intent intent = new Intent(this, ConcentrationFragment.class);
//           intent.putExtra(ConcentrationFragment.FILE_NAME, "somefile");
//        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        // 2-я часть
//         notif.setLatestEventInfo(this, "All4Learn", "Don't touch the phone", pIntent);
//
//        // ставим флаг, чтобы уведомление пропало после нажатия
//        notif.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        // отправляем
//        nm.notify(1, notif);
//    }
//
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
