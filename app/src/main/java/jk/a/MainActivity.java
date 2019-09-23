package jk.a;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.widget.TextView;





public class MainActivity extends AppCompatActivity {
    String a = "10101010101010111100001100101001";
    int time =1200;
    private Handler handler;

    TextView TempShow;
    private double BatteryT;

    TextView tx;
    Button bt;
    TextView tm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TempShow = findViewById(R.id.texttemp);




        bt = findViewById(R.id.button);
        tm = findViewById(R.id.curtime);
        tx = findViewById(R.id.textview);
        registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));


        handler = new Handler() {
            public void handleMessage(Message msg) {
                tm.setText((String) msg.obj);
            }
        };

        Threads thread = new Threads();
        thread.start();




        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tx.setText("开始传输");
                try {
                    start();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tx.setText("传输结束");
            }
        });







    }

     BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver()
    {
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            /*
             * 如果捕捉到的action是ACTION_BATTERY_CHANGED， 就运行onBatteryInfoReceiver()
             */
            if (Intent.ACTION_BATTERY_CHANGED.equals(action))
            {

                BatteryT = intent.getIntExtra("temperature", 0);  //电池温度
                TempShow.setText( "温度为" + (BatteryT*0.1) + "℃");
            }
        }
    };






    class Threads extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy年MM月dd日   HH:mm:ss");
                    String str = sdf.format(new Date());
                    handler.sendMessage(handler.obtainMessage(100, str));
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public void running() {
//        String a = "101011100011";
//        char j;
//
//        for (int i = 0; i < a.length(); i++) {
//            j = a.charAt(i);
//            if (j == '1') {
//                start();
//                Log.v("MainActivity", "start");
//                tx.setText("start");
//            } else {
//
//                try {
//                    Thread.sleep(time);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Log.v("MainActivity", "sleep");
//                tx.setText("sleep");
//            }
//        }
//    }


    public void start() throws InterruptedException {
        Runtime r = Runtime.getRuntime();
        ExecutorService pool = Executors.newFixedThreadPool(r.availableProcessors());
        ExecutorService pool1 = Executors.newFixedThreadPool(r.availableProcessors());
        char j;






        for (int i = 0; i < a.length(); i++) {

            j = a.charAt(i);

            if (j == '1') {
                for (int w = 0; w < r.availableProcessors() - 2; w++) {
                    pool.execute(new Loop());
                }
                Thread.sleep(time);
                Date date=new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateString = formatter.format(date);
                Log.v("MainActivity", "主线程 启动 "+dateString);

            }
            if (j == '0') {
                for (int k = 0; k < r.availableProcessors() - 2; k++) {
                    pool1.execute(new Loop1());
                }
                Thread.sleep(time);
                Date date=new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateString1 = formatter.format(date);
                Log.v("MainActivity", "主线程 休息 "+dateString1);
            }

        }
        pool.shutdown();
        pool1.shutdown();






    }

    class Loop implements Runnable {
        @Override
        public void run() {
            int busyTime = time;//可调节参数，单位为ms。50ms后线程休眠50毫秒，然后再经系统调度。该数值越小，则线程被调度得越频繁，则CPU使用率也就越高（平均）


                long startTime = System.currentTimeMillis();

                while ((System.currentTimeMillis() - startTime) <= busyTime){

               double a =999.778;
               double b = Math.pow(a,3);
               b=b*b;

                }
            Date date=new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateString = formatter.format(date);

                Log.v("Mainactivity","子线程运行"+dateString);
            }
        }

class Loop1 implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date date=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateString = formatter.format(date);

        Log.v("Mainactivity","子线程休息"+dateString);
    }
    }
}

    //    public void start(int ms) {
//
//        long now = System.currentTimeMillis();
//        long end = now;
//        while (now < end + ms) {
//
//            now = System.currentTimeMillis();
//            long primes = calculatePrimes(a, b);
//        }
//    }
//
//    public long calculatePrimes(int a, int b) {
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < a; i++) {
//            double candidate = i * (b * Math.random());
//
//
//        }
//        long end1 = System.currentTimeMillis();
//        end1 = end1 - start;
////        Log.v("MainActivity", "the interval is  " + end1);
//        return start;
//
//    }





