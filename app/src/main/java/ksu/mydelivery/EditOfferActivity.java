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

import java.util.ArrayList;
import java.util.HashMap;

public class EditOfferActivity extends AppCompatActivity {
    private Spinner availableDay , avaFrom , avaTo;
    private String region, day , from , to ,price  ;
    private EditText minPrice;
    private CheckBox east , west , south , north;
    private Offer offer;
    private int indexDay , indexFrom , indexTo , checkRegion;
    private ArrayList<Offer> offerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_offer);

        offerList = (ArrayList<Offer>) getIntent().getSerializableExtra("Offers");
        offer = (Offer) getIntent().getSerializableExtra("Offer");


        price = offer.getPriceInterval();
        day = offer.getAvailableDays();
        from = offer.getAvailableTimeFrom();
        to = offer.getAvailableTimeTo();
        region = offer.getAvailableRegions();
        price = offer.getPriceInterval();



        switch (day) {
            case "Weekdays":
                indexDay = 1;
                break;
            case "Weekend":
                indexDay = 2;
                break;
            case "Sunday":
                indexDay = 3;
                break;
            case "Monday":
                indexDay = 4;
                break;
            case "Tuesday":
                indexDay = 5;
                break;
            case "Wednesday":
                indexDay = 6;
                break;
            case "Thursday":
                indexDay = 7;
                break;
            case "Friday":
                indexDay = 8;
                break;
            case "Saturday":
                indexDay = 9;
                break;
            default: indexDay=0;
        }


                switch (from) {
                    case "00:00:00":
                        indexFrom = 1;
                        break;
                    case "01:00:00":
                        indexFrom = 2;
                        break;
                    case "02:00:00":
                        indexFrom = 3;
                        break;
                    case "03:00:00":
                        indexFrom = 4;
                        break;
                    case "04:00:00":
                        indexFrom = 5;
                        break;
                    case "05:00:00":
                        indexFrom = 6;
                        break;
                    case "06:00:00":
                        indexFrom = 7;
                        break;
                    case "07:00:00":
                        indexFrom = 8;
                        break;
                    case "08:00:00":
                        indexFrom = 9;
                        break;
                    case "09:00:00":
                        indexFrom = 10;
                        break;
                    case "10:00:00":
                        indexFrom = 11;
                        break;
                    case "11:00:00":
                        indexFrom = 12;
                        break;
                    case "12:00:00":
                        indexFrom = 13;
                        break;
                    case "13:00:00":
                        indexFrom = 14;
                        break;
                    case "14:00:00":
                        indexFrom = 15;
                        break;
                    case "15:00:00":
                        indexFrom = 16;
                        break;
                    case "16:00:00":
                        indexFrom = 17;
                        break;
                    case "17:00:00":
                        indexFrom = 18;
                        break;
                    case "18:00:00":
                        indexFrom = 19;
                        break;
                    case "19:00:00":
                        indexFrom = 20;
                        break;
                    case "20:00:00":
                        indexFrom = 21;
                        break;
                    case "21:00:00":
                        indexFrom = 22;
                        break;
                    case "22:00:00":
                        indexFrom = 23;
                        break;
                    case "23:00:00":
                        indexFrom = 24;
                        break;
                    default: indexFrom = 0;
                }


        switch (to) {
            case "00:00:00":
                indexTo = 1;
                break;
            case "01:00:00":
                indexTo = 2;
                break;
            case "02:00:00":
                indexTo = 3;
                break;
            case "03:00:00":
                indexTo = 4;
                break;
            case "04:00:00":
                indexTo = 5;
                break;
            case "05:00:00":
                indexTo = 6;
                break;
            case "06:00:00":
                indexTo = 7;
                break;
            case "07:00:00":
                indexTo = 8;
                break;
            case "08:00:00":
                indexTo = 9;
                break;
            case "09:00:00":
                indexTo = 10;
                break;
            case "10:00:00":
                indexTo = 11;
                break;
            case "11:00:00":
                indexTo = 12;
                break;
            case "12:00:00":
                indexTo = 13;
                break;
            case "13:00:00":
                indexTo = 14;
                break;
            case "14:00:00":
                indexTo = 15;
                break;
            case "15:00:00":
                indexTo = 16;
                break;
            case "16:00:00":
                indexTo = 17;
                break;
            case "17:00:00":
                indexTo = 18;
                break;
            case "18:00:00":
                indexTo = 19;
                break;
            case "19:00:00":
                indexTo = 20;
                break;
            case "20:00:00":
                indexTo = 21;
                break;
            case "21:00:00":
                indexTo = 22;
                break;
            case "22:00:00":
                indexTo = 23;
                break;
            case "23:00:00":
                indexTo = 24;
                break;
            default: indexTo = 0;
        }


      //  availableDay.setSelection(indexDay);
      //  avaFrom.setSelection(indexFrom);
      //  avaTo.setSelection(indexTo);

      //  west.setChecked(true);


        ((Spinner)findViewById(R.id.spnAvailableDays)).setSelection(indexDay);
        ((Spinner)findViewById(R.id.spnFrom)).setSelection(indexFrom);
        ((Spinner)findViewById(R.id.spnTo)).setSelection(indexTo);

        if (offer.getAvailableRegions().contains("North")){
            ((CheckBox)findViewById(R.id.chkNorth)).setChecked(true);
        }
        if (offer.getAvailableRegions().contains("East")){
            ((CheckBox)findViewById(R.id.chkEast)).setChecked(true);
        }
        if (offer.getAvailableRegions().contains("South")){
            ((CheckBox)findViewById(R.id.chkSouth)).setChecked(true);
        }
        if (offer.getAvailableRegions().contains("West")){
            ((CheckBox)findViewById(R.id.chkWest)).setChecked(true);
        }


        ((EditText)findViewById(R.id.txtPrice)).setText(price +" SR");



        ImageButton editOffer = (ImageButton) findViewById(R.id.imgbtnSaveChange);
        editOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOffer();

            }
        });

        ImageButton delete = (ImageButton) findViewById(R.id.imgbtnDeleteOffer);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOffer();

            }
        });
    }



    public void editOffer () {

        Toast.makeText(EditOfferActivity.this , String.valueOf( offer.getID() ),Toast.LENGTH_LONG).show();

        availableDay = (Spinner) findViewById(R.id.spnAvailableDays);
        day= availableDay.getSelectedItem().toString();
        avaFrom = (Spinner) findViewById(R.id.spnFrom);
        from= avaFrom.getSelectedItem().toString();
        String[] split = from.split(":");
        String part1 = split[0]; // The number before ' : '

            if (Integer.valueOf(part1) < 10 ) {

                from = "0"+part1+":00:00";
            }
        else {
                from = part1+"00:00";
            }

        avaTo = (Spinner) findViewById(R.id.spnTo);
        to= avaTo.getSelectedItem().toString();
         split = to.split(":");
         part1 = split[0]; // The number before ' : '

        if (Integer.valueOf(part1) < 10 ) {

            to = "0"+part1+":00:00";
        }
        else {
            to = part1+"00:00";
        }


        north = (CheckBox) findViewById(R.id.chkNorth);
        east = (CheckBox) findViewById(R.id.chkEast);
        south = (CheckBox) findViewById(R.id.chkSouth);
        west = (CheckBox) findViewById(R.id.chkWest);

        minPrice = (EditText) findViewById(R.id.txtPrice);
        price = minPrice.getText().toString();

        if((day.equals("--Available Day(s)--") || from.equals("--From--") || to.equals("--To--"))
                ||  (!north.isChecked() && !east.isChecked() && !south.isChecked() && !west.isChecked())
                || price.equals("") ){
            (findViewById(R.id.lblRegion)).requestFocus();
            ((EditText) (findViewById(R.id.lblRegion))).setError("FIELD CANNOT BE EMPTY");
        }
        else{

            region="";

            if (north.isChecked()) {
                region=region+"north , ";
            }
            if (east.isChecked()) {
                region=region+"east , ";
            }
            if (south.isChecked()) {
                region=region+"south , ";
            }
            if (west.isChecked()) {
                region=region+"west , ";
            }
            region += " " ;

            region = region.replace(" ,  " , "");
            region = region.replace(" , " , " : ");

            HashMap postOfferData = new HashMap();

            offer.setAvailableDays(day);
            offer.setAvailableTimeFrom(from);
            offer.setAvailableTimeTo(to);
            offer.setAvailableRegions(region);
            offer.setPriceInterval(price);


            postOfferData.put("txtOfferID", String.valueOf( offer.getID() ));
            postOfferData.put("txtAvailableDays", day);
            postOfferData.put("txtAvailableTimeFrom", from);
            postOfferData.put("txtAvailableTimeTo", to);
            postOfferData.put("txtRegions", region);
            postOfferData.put("txtPriceLimit", price);

            PostResponseAsyncTask task1 = new PostResponseAsyncTask(EditOfferActivity.this, postOfferData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if (s.contains("success")) {

                        for (int i = 0 ; i < offerList.size() ; i++) {

                            if (offer.getID() == offerList.get(i).getID()) {
                                offerList.set(i , offer) ;
                            }
                        }

                        Toast.makeText(EditOfferActivity.this, "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(EditOfferActivity.this, MyOfferActivity.class);
                        intent.putExtra("Offers",offerList);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(EditOfferActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

            task1.execute("http://10.0.2.2/provider/updateOffer.php");
        }

    }


    public void deleteOffer () {

        HashMap postOfferData = new HashMap();

        postOfferData.put("txtOfferID", String.valueOf( offer.getID() ) );


        PostResponseAsyncTask task1 = new PostResponseAsyncTask(EditOfferActivity.this, postOfferData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if (s.contains("success")) {

                    for (int i = 0 ; i < offerList.size() ; i++) {

                        if (offer.getID() == offerList.get(i).getID()) {
                            offerList.remove(i) ;
                        }
                    }

                    Toast.makeText(EditOfferActivity.this, "Success", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(EditOfferActivity.this, MyOfferActivity.class);
                    intent.putExtra("Offers",offerList);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(EditOfferActivity.this, "Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        task1.execute("http://10.0.2.2/provider/removeOffer.php");
    }

    }
