package mac.mamigon.serviceexample1;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import mac.mamigon.serviceexample1.jobs.JobMeh;
import mac.mamigon.serviceexample1.services.ServiceMeh;

public class MainActivity extends AppCompatActivity {
    private ServiceMeh mService;
    boolean mBound = false;
    private JobScheduler js;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    private void initJobScheduler(){
        js = (JobScheduler)
                getSystemService( Context.JOB_SCHEDULER_SERVICE );
        JobInfo.Builder builder = new JobInfo.Builder( 1,
                new ComponentName( getPackageName(),
                        JobMeh.class.getName() ) );

        builder.setPeriodic( 3000 );
        if( js.schedule( builder.build() ) <= 0 ) {
            Log.e("XXX", "ERROR WHILE SCHEDULING ");
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent i = new Intent(this, ServiceMeh.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ServiceMeh.LocalBinder binder = (ServiceMeh.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;

        }
    };

    public void doMath(View view) {
        if (mBound) {
            // Call a method from the LocalService.
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance.
            Double num = mService.doMathIntensive();
            Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
        }
    }

    public void scheduleJob(View view) {
        initJobScheduler();
    }

    public void cancelScheduleJob(View view) {
        if(js != null)
            js.cancelAll();
    }
}
