package ksu.mydelivery;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AddRequestActivity extends AppCompatActivity {

    private  String title, description , type , src , dest, price , dueTime , contactInfo , userID;
     private RegularUser user ;
    private String requestID;
    private Provider provider;
    private Offer offer;
    private ArrayList<String> jsonList;
    private ArrayList<Offer> offerList;
    private ArrayList<Offer> matchedOfferList;
    private ArrayList<Provider> providers;
    private ArrayList<Request> requestList;
    private EditText dueDate;
    private EditText time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAddRequest) ;
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v) {
                AddRequest();
            }
        });

          requestList = new ArrayList<>();
          requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");
          user = (RegularUser) getIntent().getSerializableExtra("RegularUser");

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH) ;
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int min = calendar.get(Calendar.MINUTE);

          dueDate = (EditText) findViewById(R.id.txtDueDate);
          time = (EditText) findViewById(R.id.txtDueTime);

        if (dueDate != null)
        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePick = new DatePickerDialog(AddRequestActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dueDate.setText(year +"-"+ ++monthOfYear +"-"+ dayOfMonth);
                    }
                },year,month,day);
                datePick.setTitle("Due Date");
                datePick.show();
            }
        });


        if (time != null)
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePick = new TimePickerDialog(AddRequestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay +":"+ minute);
                    }
                },hour,min,true
                );
                timePick.setTitle("Due Time");
                timePick.show();
            }
        });




    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void AddRequest () {

        title = ((EditText) findViewById(R.id.txtTitle)).getText().toString();
        description = ((EditText) findViewById(R.id.txtDescr)).getText().toString();
        Spinner spnType = ((Spinner) findViewById(R.id.spnType));
        type   = spnType.getSelectedItem().toString();
        src = ((EditText) findViewById(R.id.txtSource)).getText().toString();
        dest = ((EditText) findViewById(R.id.txtDest)).getText().toString();
        price = ((EditText) findViewById(R.id.txtPrice)).getText().toString();
        dueTime = dueDate.getText().toString();
        dueTime += " "+ time.getText().toString();
        contactInfo = ((EditText) findViewById(R.id.txtContactInfo)).getText().toString();
        userID = String.valueOf(user.getID());

        if (title.equals("") || type.equals("--Type--") || src.equals("") || dest.equals("") || price.equals("") || dueTime.equals("") || contactInfo.equals("") ) {
            (findViewById(R.id.txtTitle)).requestFocus();
            ((EditText) (findViewById(R.id.txtTitle))).setError("FIELD CANNOT BE EMPTY");

        }

        else {


            HashMap postData = new HashMap();
            postData.put("txtTitle", title);
            postData.put("txtDescription", description);
            postData.put("txtType", type);
            postData.put("txtSrcAddress", src);
            postData.put("txtDestAddress", dest);
            postData.put("txtPrice", price );
            postData.put("txtDueTime", dueTime);
            postData.put("txtContactInfo", contactInfo);
            postData.put("txtUserIDAdded", userID );




            final PostResponseAsyncTask task1 = new PostResponseAsyncTask(AddRequestActivity.this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    Toast.makeText(AddRequestActivity.this,s, Toast.LENGTH_LONG).show();
                    if (s.contains("success")) {

                        s = s.replace("success ", "");

                        try {
                            JSONObject json = new JSONObject(s);
                            requestID = (json.getString("requestID"));

                        } catch (org.json.JSONException e) {
                            Toast.makeText(AddRequestActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(AddRequestActivity.this,"Request Added Successfully", Toast.LENGTH_LONG).show();


                        PostResponseAsyncTask getOffers = new PostResponseAsyncTask(AddRequestActivity.this, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {

                                jsonList = new ArrayList<>();
                                offerList = new ArrayList<>();
                                matchedOfferList = new ArrayList<>();
                                try {
                                    JSONArray jsonArray = new JSONArray(s);
                                    JSONObject jsonObject ;

                                    if (jsonArray != null) {
                                        for (int i=0;i<jsonArray.length();i++){
                                            jsonList.add(jsonArray.get(i).toString());
                                        }
                                    }

                                    for (int i=0;i<jsonList.size();i++){
                                        jsonObject = new JSONObject(jsonList.get(i));
                                        offer = new Offer(jsonObject.getInt("offerID"), jsonObject.getString("available_days"), jsonObject.getString("available_time_from"), jsonObject.getString("available_time_to")
                                                , jsonObject.getString("regions"), jsonObject.getString("price_limit"), jsonObject.getInt("providerID_made"));
                                        offerList.add(offer);
                                    }

                                    String[] split = dueTime.split(" ");
                                    dueTime = split[0]; // Date of request
                                    String time = split[1] ; // Time of request
                                    Date date = new Date();
                                    Time timeVal ;
                                    split = time.split(":");
                                    int hour = Integer.parseInt(split[0]);
                                    int min = Integer.parseInt(split[1]);

                                    try {
                                        date = new SimpleDateFormat("yyyy-M-d").parse(dueTime);
                                    }
                                    catch (ParseException e) {
                                        Log.e("Exception: " , e.toString());
                                    }
                                    String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date);


                                    boolean matchDay = false ;
                                    String from[];
                                    String to[];

                                    for (int i = 0 ; i < offerList.size() ; i++) {

                                        matchDay = false ;

                                        if (offerList.get(i).getAvailableDays().equalsIgnoreCase("weekend") || offerList.get(i).getAvailableDays().equalsIgnoreCase("weekdays")) {

                                            if (offerList.get(i).getAvailableDays().equalsIgnoreCase("weekend")) {
                                                switch (dayOfWeek) {
                                                    case "Friday":
                                                        matchDay = true;
                                                        break;
                                                    case "Saturday":
                                                        matchDay = true;
                                                        break;
                                                    default:
                                                        matchDay = false;
                                                }
                                            }

                                            if (offerList.get(i).getAvailableDays().equalsIgnoreCase("weekdays")) {

                                                switch (dayOfWeek) {
                                                    case "Sunday":
                                                        matchDay = true;
                                                        break;
                                                    case "Monday":
                                                        matchDay = true;
                                                        break;
                                                    case "Tuesday":
                                                        matchDay = true;
                                                        break;
                                                    case "Wednesday":
                                                        matchDay = true;
                                                        break;
                                                    case "Thursday":
                                                        matchDay = true;
                                                        break;
                                                    default:
                                                        matchDay = false;
                                                }
                                            }

                                            if (Double.parseDouble(price) > Double.parseDouble(offerList.get(i).getPriceInterval()) && matchDay) {

                                                from = offerList.get(i).getAvailableTimeFrom().split(":");
                                                to = offerList.get(i).getAvailableTimeTo().split(":");

                                                if ( Integer.parseInt(from[0]) < hour &&  Integer.parseInt(to[0]) >= hour )  {

                                                    matchedOfferList.add(offerList.get(i));

                                                }

                                            }

                                        } else {
                                            if (Double.parseDouble(price) > Double.parseDouble(offerList.get(i).getPriceInterval())
                                                    && offerList.get(i).getAvailableDays().equals(dayOfWeek) ) {

                                               from = offerList.get(i).getAvailableTimeFrom().split(":");
                                               to = offerList.get(i).getAvailableTimeFrom().split(":");

                                               if ( Integer.parseInt(from[0]) < hour && ( Integer.parseInt(to[0]) >= hour && Integer.parseInt(to[1]) > min ))  {

                                                   matchedOfferList.add(offerList.get(i));

                                               }

                                            }
                                        }
                                    }

                                    for (int i = 0 ; i < matchedOfferList.size() ; i++) {

                                        for (int j = i ; i < matchedOfferList.size() ; i++) {
                                            if (matchedOfferList.get(i).getProviderID_made() == matchedOfferList.get(j).getProviderID_made()
                                                    && !matchedOfferList.get(i).equals(matchedOfferList.get(j))) {
                                                matchedOfferList.remove(i);
                                                i--;
                                            }
                                        }

                                    } // Remove Duplication in provider

                                    if (!matchedOfferList.isEmpty()) {

                                        providers = new ArrayList<>();

                                        for (int i = 0 ; i < matchedOfferList.size() ; i++) {

                                            HashMap updateProvider = new HashMap();
                                            updateProvider.put("txtProviderID" , String.valueOf(matchedOfferList.get(i).getProviderID_made()) );
                                            PostResponseAsyncTask task1 = new PostResponseAsyncTask(AddRequestActivity.this, updateProvider, new AsyncResponse() {
                                                @Override
                                                public void processFinish(String s) {
                                                    if (s.contains("success ")) {
                                                        s = s.replace("success ", "");

                                                        try {
                                                            JSONObject json = new JSONObject(s);

                                                            provider = new Provider(json.getString("password"), json.getString("email"), json.getString("first_name"), json.getString("last_name")
                                                                    , json.getString("phone_number"), json.getString("birth_date"), json.getLong("nationalID"), json.getString("is_verified") , 0.0);
                                                            provider.setID(Integer.parseInt(json.getString("providerID")));
                                                        } catch (org.json.JSONException e) {
                                                            Toast.makeText(AddRequestActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                        }

                                                        providers.add(provider);
                                                        Intent intent = new Intent(AddRequestActivity.this, AssignProviderActivity.class);
                                                        intent.putExtra("RequestID",requestID);
                                                        intent.putExtra("MatchedOffers", matchedOfferList);
                                                        intent.putExtra("Providers", providers);
                                                        intent.putExtra("RegularUser", user);
                                                        intent.putExtra("Requests", requestList);
                                                        startActivity(intent);
                                                        finish();
                                                    }

                                                    else {
                                                        Toast.makeText(AddRequestActivity.this, "Failed", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                            task1.execute("http://10.0.2.2/provider/providers.php");


                                        }



                                    }

                                    else {

                                        Toast.makeText(AddRequestActivity.this, "There is no matching offers \n Please wait for offer or change request's conditions.", Toast.LENGTH_LONG).show();
                                        finish();
                                    }


                                } catch (org.json.JSONException e) {
                                    Toast.makeText(AddRequestActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                        getOffers.execute("http://10.0.2.2/provider/offers.php");


                    }
                    else {
                        Toast.makeText(AddRequestActivity.this,"Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

                task1.execute("http://10.0.2.2/user/addRequest.php");

        }

    }
}
