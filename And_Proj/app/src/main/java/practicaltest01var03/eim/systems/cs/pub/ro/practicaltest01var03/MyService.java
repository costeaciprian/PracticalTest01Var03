package practicaltest01var03.eim.systems.cs.pub.ro.practicaltest01var03;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ciprian on 3/29/2018.
 */

public class MyService extends Service {

    private ProcessingThread prTh = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        prTh = new ProcessingThread(getApplicationContext(), intent.getStringExtra("text"));

        prTh.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        prTh.stopThread();
    }
}
