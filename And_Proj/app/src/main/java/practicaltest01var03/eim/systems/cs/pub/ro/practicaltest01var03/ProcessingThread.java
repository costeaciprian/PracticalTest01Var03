package practicaltest01var03.eim.systems.cs.pub.ro.practicaltest01var03;

import android.content.Context;
import android.content.Intent;

import java.util.Random;

/**
 * Created by ciprian on 3/29/2018.
 */

public class ProcessingThread extends Thread {
    private boolean isRunning = true;
    private Context context;
    private String text;
    private Random random = new Random();

    public ProcessingThread(Context context, String text) {
        this.context = context;
        this.text = text;
    }

    @Override
    public void run() {
        super.run();

        while(isRunning) {
            sendBroadcastMessage();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interr) {
            interr.printStackTrace();
        }
    }

    private void sendBroadcastMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.action_types[random.nextInt(Constants.action_types.length)]);
        intent.putExtra("msg", text);
        context.sendBroadcast(intent);
    }

    public void stopThread() {
        isRunning = false;
    }
}
