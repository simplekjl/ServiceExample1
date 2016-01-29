package mac.mamigon.serviceexample1.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by mito6 on 1/28/2016.
 */
public class ServiceMeh  extends Service{
    private final int MAX_ITERATION = 10;
    private final IBinder mBinder = new LocalBinder();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public Double doMathIntensive(){
        Double meh = 0.0;
        for(int x=0; x < MAX_ITERATION; x++){
            meh+=.001;
        }
        return meh;
    }
    public class LocalBinder extends Binder {
        public ServiceMeh getService() {
            // Return this instance of LocalService so clients can call public methods
            return ServiceMeh.this;
        }
    }
}
