package no.uka.findmyapp.android.rest.library;

import java.util.concurrent.PriorityBlockingQueue;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class PriorityIntentService extends Service {

    private final class QueueItem implements Comparable<QueueItem> {
        Intent intent;
        int priority;
        int startId;

        @Override
        public int compareTo(QueueItem another) {
            if (this.priority < another.priority) {
                return -1;
            } else if (this.priority > another.priority) {
                return 1;
            } else {
                return (this.startId < another.startId) ? -1 : 1;
            }
        }
    }
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                final QueueItem item = mQueue.take();
                onHandleIntent(item.intent);
                if (mQueue.isEmpty()) {
                    PriorityIntentService.this.stopSelf();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static final String EXTRA_PRIORITY = "priority";
    private String mName;
    private PriorityBlockingQueue<QueueItem> mQueue;
    private boolean mRedelivery;
    private volatile ServiceHandler mServiceHandler;
    private volatile Looper mServiceLooper;

    public PriorityIntentService(String name) {
        super();
        mName = name;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("PriorityIntentService[" + mName + "]");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        mQueue = new PriorityBlockingQueue<QueueItem>();

    	Log.v("INFO", "SERVICE_CREATED");
    }

    @Override
    public void onDestroy() {
        mServiceLooper.quit();
    }

    protected void onHandleIntent(Intent intent) {
    	Log.v("TEST", intent.toString());
    }

    @Override
    public void onStart(Intent intent, int startId) {
    	Log.v("INFO", "START_SERVICE");
        final QueueItem item = new QueueItem();
        item.intent = intent;
        item.startId = startId;
        final int priority = intent.getIntExtra(EXTRA_PRIORITY, 0);
        item.priority = priority;
        mQueue.add(item);
        mServiceHandler.sendEmptyMessage(0);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onStart(intent, startId);
        return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
    }

    public void setIntentRedelivery(boolean enabled) {
        mRedelivery = enabled;
    }
}
