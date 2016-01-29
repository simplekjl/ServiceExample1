package mac.mamigon.serviceexample1.jobs;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * Created by mito6 on 1/28/2016.
 */
public class JobMeh extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, params ) );
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mJobHandler.removeMessages( 1 );
        return false;

    }

    private Handler mJobHandler = new Handler( new Handler.Callback() {

        @Override
        public boolean handleMessage( Message msg ) {
//            Toast.makeText(getApplicationContext(),
//                    "JobService task running", Toast.LENGTH_SHORT)
//                    .show();
            sendSms();
            jobFinished( (JobParameters) msg.obj, false );
            return true;
        }

    } );
    private void sendSms(){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("4704552895", null, "Hellooooooo Brothaaaaaaa!", null, null);
    }
}
