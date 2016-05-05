package ksu.mydelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class RateAppActivity extends AppCompatActivity {

    private RegularUser user;
    private Provider provider;
    private float rate=0;

    private ArrayList<Request> requestList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_app);


        user = (RegularUser) getIntent().getSerializableExtra("RegularUser");
        provider = (Provider) getIntent().getSerializableExtra("Provider");

        requestList = new ArrayList<>();
        requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");

        final RatingBar rateApp = (RatingBar) findViewById(R.id.rbRateApp);


        if(user != null) {
            rateApp.setRating(Float.parseFloat(String.valueOf(user.getRate())));
        } else if(provider != null){
            rateApp.setRating(Float.parseFloat(String.valueOf(provider.getRate())));
        }


        rateApp.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                rate = rating;
                Toast.makeText(RateAppActivity.this , "Rate = " + String.valueOf(rating) , Toast.LENGTH_LONG).show();
            }
        });


        Button twitter = (Button) findViewById(R.id.btnRate);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRate();
            }
        });


    }


    public  void updateRate(){


        if (user != null ){
        HashMap updateRate = new HashMap();

        updateRate.put("txtUID" , String.valueOf(user.getID()));
        updateRate.put("txtRate" , String.valueOf(rate));


        PostResponseAsyncTask task1 = new PostResponseAsyncTask(RateAppActivity.this, updateRate, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.contains("success")) {
                    user.setRate(rate);

                    Intent intent = new Intent(RateAppActivity.this, HomeActivity.class);
                    intent.putExtra("RegularUser", user);
                    intent.putExtra("Requests" , requestList);
                    startActivity(intent);
                    finish();
                }
            }
            });

            task1.execute("http://10.0.2.2/user/appRate.php");

              }    else {

                    provider.setRate(rate);

                    HashMap updateRate = new HashMap();

                    updateRate.put("txtPID" , String.valueOf(provider.getID()));
                    updateRate.put("txtRate" , String.valueOf(rate));


                    PostResponseAsyncTask task1 = new PostResponseAsyncTask(RateAppActivity.this, updateRate, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if (s.contains("success")) {

                                Intent intent = new Intent(RateAppActivity.this, HomeProviderActivity.class);
                                intent.putExtra("Provider",provider);
                                intent.putExtra("Requests" , requestList);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(RateAppActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    task1.execute("http://10.0.2.2/provider/appRate.php");}
                }


    }

