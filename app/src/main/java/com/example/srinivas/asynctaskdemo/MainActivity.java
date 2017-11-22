package com.example.srinivas.asynctaskdemo;

import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity {
TextView count;
    MyBackGroundTask myTask;
    //Thread thread;
    ImageView imageView;

    private boolean threadStop = false;
   // CountDownLatch latch;

    ThreadControl tControl = new ThreadControl();


    public void startTask(View view){
        tControl.resume();

        Toast.makeText(getApplicationContext(),"Started",Toast.LENGTH_SHORT).show();
        myTask = new MyBackGroundTask();

         myTask.execute();
         imageView.setVisibility(View.INVISIBLE);

    }

    public void stopTask(View view){

        Toast.makeText(getApplicationContext(),"Stopped",Toast.LENGTH_SHORT).show();
        tControl.pause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        count = (TextView) findViewById(R.id.count);
         //latch = new CountDownLatch(1);
        imageView = (ImageView) findViewById(R.id.imagefunny);
        imageView.setVisibility(View.INVISIBLE);

    }



    private class MyBackGroundTask extends AsyncTask<Void,Integer,Void>{



        @Override
        protected Void doInBackground(Void... params) {

                    for(int i=1;i<20;i++){
                        try {
                            tControl.waitIfPaused();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //Stop work if control is cancelled.
                        if (tControl.isCancelled()) {
                            break;
                        }

                        publishProgress(i);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                }
//            });

            //int j=1;

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            count.setText(""+values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            count.setText("Hurray!!!..Have a great Day!!!!!!!!!!!!");


           imageView.setImageResource(R.drawable.good);

            imageView.setVisibility(View.VISIBLE);
            myTask.cancel(true);

        }
    }
    protected void onDestroy() {
        super.onDestroy();
        //Cancel control
        tControl.cancel();
    }

    protected void onPause() {
        super.onPause();

        //No need to pause if we are getting destroyed
        //and will cancel thread control anyway.
        if (!isFinishing()) {
            //Pause control.
            tControl.pause();
        }
    }
    protected void onResume() {
        super.onResume();

        tControl.resume();
    }




    //  blink();
//            final Handler handler = new Handler();
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    int timeToBlink = 1000;    //in milissegunds
//                    try{Thread.sleep(timeToBlink);}catch (Exception e) {}
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                           // TextView txt = (TextView) findViewById(R.id.usage);
//                            if(count.getVisibility() == View.VISIBLE){
//                                count.setVisibility(View.INVISIBLE);
//                            }else{
//                                count.setVisibility(View.VISIBLE);
//                            }
//                            //blink();
//                        }
//                    });
//                }
//            }).start();

    // tControl.cancel();



    // Movie movie = Movie.decodeFile()
//            Glide.with(getApplicationContext())
//                    .load("http://www.dumpaday.com/wp-content/uploads/2016/12/hair-styles.jpg")
//
//                    .into(imageView);
}
