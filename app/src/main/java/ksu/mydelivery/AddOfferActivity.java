package ksu.mydelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;


public class AddOfferActivity extends AppCompatActivity /* implements AdapterView.OnItemSelectedListener */{
    private Spinner availableDay , avaFrom , avaTo;
    private CheckBox east , west , south , north;
    private EditText minPrice;
    private String region="" , day , from , to  , price , pid;
    private Provider provider ;
    private Offer offer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);


        ImageButton addOffer = (ImageButton) findViewById(R.id.imgbtnAddOffer);
        addOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOffer();

            }
        });

          provider = (Provider) getIntent().getSerializableExtra("Provider");
            pid = String.valueOf(provider.getID());

    }


    public void addOffer () {
        availableDay = (Spinner) findViewById(R.id.spnAvailableDays);
        day= availableDay.getSelectedItem().toString();
        avaFrom = (Spinner) findViewById(R.id.spnFrom);
        from= avaFrom.getSelectedItem().toString();
        avaTo = (Spinner) findViewById(R.id.spnTo);
        to= avaTo.getSelectedItem().toString();

        north = (CheckBox) findViewById(R.id.chkNorth);
        east = (CheckBox) findViewById(R.id.chkEast);
        south = (CheckBox) findViewById(R.id.chkSouth);
        west = (CheckBox) findViewById(R.id.chkWest);

        minPrice = (EditText) findViewById(R.id.lblRegion);
        price = minPrice.getText().toString();

        if((day.equals("--Available Day(s)--") || from.equals("--From--") || to.equals("--To--"))
                ||  (!north.isChecked() && !east.isChecked() && !south.isChecked() && !west.isChecked())
                || price.equals("") ){
             (findViewById(R.id.lblRegion)).requestFocus();
            ((EditText) (findViewById(R.id.lblRegion))).setError("FIELD CANNOT BE EMPTY");
        }
        else{

            if (north.isChecked()) {
                region=region+"North , ";
            }
            if (east.isChecked()) {
                region=region+"East , ";
            }
            if (south.isChecked()) {
                region=region+"South , ";
            }
            if (west.isChecked()) {
                region=region+"West , ";
            }
            region += " " ;

            region = region.replace(" ,  " , "");
            region = region.replace(" , " , " : ");

            HashMap postOfferData = new HashMap();

            postOfferData.put("txtAvailableDays", day);
            postOfferData.put("txtAvailableTimeFrom", from);
            postOfferData.put("txtAvailableTimeTo", to);
            postOfferData.put("txtRegions", region);
            postOfferData.put("txtPriceLimit", price);
            postOfferData.put("txtPid", pid);
            offer = new Offer(26 , day , from , to , region , price , provider.getID());



            PostResponseAsyncTask task1 = new PostResponseAsyncTask(AddOfferActivity.this, postOfferData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if (s.contains("success")) {
                        Toast.makeText(AddOfferActivity.this, "Success", Toast.LENGTH_LONG).show();

                        finish();
                    }
                    else {
                        Toast.makeText(AddOfferActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

            task1.execute("http://10.0.2.2/provider/addOffer.php");
        }
    }

    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    */

}
