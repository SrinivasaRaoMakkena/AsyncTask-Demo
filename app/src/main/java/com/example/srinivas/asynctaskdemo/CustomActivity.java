package com.example.srinivas.asynctaskdemo;

/**
 * Created by Srinivas on 6/13/2017.
 */


        import android.app.Activity;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

/**
 * Created by Manjusha on 6/13/2017.
 */
public class CustomActivity extends Activity implements View.OnClickListener {
    private int count=0;
    private TextView tvNumber;
    private LongOperations longOperations;
    private int MAX_INT_VALUE=2147483647;
    private boolean isStopped;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumber = (TextView) findViewById(R.id.count);
        Button btnStart = (Button) findViewById(R.id.start);
        Button btnStop = (Button) findViewById(R.id.stop);


        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.start:
                if(longOperations==null)
                {
//                    count = 0;
                    isStopped = true;
                    longOperations = new LongOperations();
                    longOperations.execute();
                }

                break;
            case R.id.stop:
                isStopped = false;
                if(longOperations!=null) {
                    longOperations.cancel(true);
                    longOperations = null;

                }
                break;
        }
    }


    private class LongOperations extends AsyncTask<Void,Integer,Integer>
    {

        @Override
        protected Integer doInBackground(Void... voids) {
            while(count< MAX_INT_VALUE && isStopped)
            {
                count++;
                publishProgress(new Integer(count));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return count;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvNumber.setText(""+values[0]);
        }
    }
}

