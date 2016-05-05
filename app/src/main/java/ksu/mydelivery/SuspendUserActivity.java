package ksu.mydelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

public class SuspendUserActivity extends AppCompatActivity {

    private Admin admin;
    private RegularUser user;
    private String userPhone , fName , lName , suspend;
    private EditText EuserPhone;
    private TextView Tname , Tsuspend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend_user);


        admin = (Admin) getIntent().getSerializableExtra("Admin");
        user = (RegularUser) getIntent().getSerializableExtra("RegularUser");

        ImageButton searchUser = (ImageButton) findViewById(R.id.imgbtnSearchUser);
        searchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUser();

            }
        });


        Button suspendUser = (Button) findViewById(R.id.btnSuspend);
        suspendUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suspendUser();

            }
        });


        Button unSuspendUser = (Button) findViewById(R.id.btnUnSuspend);
        unSuspendUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unSuspendUser();

            }
        });

    }


    public void searchUser(){

        EuserPhone = (EditText) findViewById(R.id.txtUserPhone) ;
        userPhone = String.valueOf(EuserPhone.getText());

            HashMap updateUser = new HashMap();

            updateUser.put("txtPhone" , userPhone);

            PostResponseAsyncTask task1 = new PostResponseAsyncTask(SuspendUserActivity.this, updateUser, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if (s.contains("success ")) {
                        s = s.replace("success ", "");

                        try {
                            JSONObject json = new JSONObject(s);

                            user = new RegularUser(json.getString("password"), json.getString("email"), json.getString("first_name"), json.getString("last_name")
                                    , json.getString("phone_number"), json.getString("birth_date"), json.getString("address"), json.getString("nationality")  , json.getString("is_suspended") , json.getDouble("rate_app") );
                            user.setID(Integer.parseInt(json.getString("userID")));

                        } catch (org.json.JSONException e) {
                            Toast.makeText(SuspendUserActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }

                        Tname = (TextView) findViewById(R.id.txtvName) ;
                        Tname.setText(user.getfName() + " " + user.getlName());
                        Tsuspend = (TextView) findViewById(R.id.txtvIsSuspended);

                        if(user.isSuspended()){
                            Tsuspend.setText("Suspend !");
                        }
                        else if (!user.isSuspended())
                                Tsuspend.setText("Active !");
                        }

                    else {
                        Toast.makeText(SuspendUserActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

            task1.execute("http://10.0.2.2/user/searchUser.php");



    }


    public void suspendUser(){

        if(user != null) {

            Tname = (TextView) findViewById(R.id.txtvName) ;
            Tname.setText(" ");
            Tsuspend = (TextView) findViewById(R.id.txtvIsSuspended);
            Tsuspend.setText(" ");


            Toast.makeText(SuspendUserActivity.this, "user phone  =  " + userPhone, Toast.LENGTH_LONG).show();
            Toast.makeText(SuspendUserActivity.this, " another user phone  =  " + String.valueOf(user.getPhoneNumber()), Toast.LENGTH_LONG).show();

            userPhone=userPhone.replace("0","");
            Toast.makeText(SuspendUserActivity.this, "user phone  =  " + userPhone, Toast.LENGTH_LONG).show();

            if (!userPhone.equals(String.valueOf(user.getPhoneNumber()))) {
                Toast.makeText(SuspendUserActivity.this, " phone not equal  ", Toast.LENGTH_LONG).show();
                (findViewById(R.id.txtUserPhone)).requestFocus();
                ((EditText) (findViewById(R.id.txtUserPhone))).setError("YOU SHOULD SEARCH FOR USER FIRST");
            } else {

                HashMap updateUser = new HashMap();

                updateUser.put("txtPhone", userPhone);
                updateUser.put("txtAdminID", String.valueOf(admin.getID()));

                PostResponseAsyncTask task1 = new PostResponseAsyncTask(SuspendUserActivity.this, updateUser, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (s.contains("success")) {
                            user.setIsSuspended(true);
                            Toast.makeText(SuspendUserActivity.this, "Success", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(SuspendUserActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                task1.execute("http://10.0.2.2/user/suspendUser.php");

            }
        }
        else {
            Toast.makeText(SuspendUserActivity.this, "user null  ==  ", Toast.LENGTH_LONG).show();

            (findViewById(R.id.txtUserPhone)).requestFocus();
            ((EditText) (findViewById(R.id.txtUserPhone))).setError("YOU SHOULD SEARCH FOR USER FIRST");
        }

    }



    public void unSuspendUser(){

        Tname = (TextView) findViewById(R.id.txtvName) ;
        Tname.setText(" ");
        Tsuspend = (TextView) findViewById(R.id.txtvIsSuspended);
        Tsuspend.setText(" ");

        if(user != null) {

            if (!userPhone.equals(String.valueOf(user.getPhoneNumber()))) {
                (findViewById(R.id.txtUserPhone)).requestFocus();
                ((EditText) (findViewById(R.id.txtUserPhone))).setError("YOU SHOULD SEARCH FOR USER FIRST");
            } else {

                HashMap updateUser = new HashMap();

                updateUser.put("txtPhone", userPhone);

                PostResponseAsyncTask task1 = new PostResponseAsyncTask(SuspendUserActivity.this, updateUser, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (s.contains("success")) {
                            user.setIsSuspended(false);

                            Toast.makeText(SuspendUserActivity.this, "Success", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(SuspendUserActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                task1.execute("http://10.0.2.2/user/unSuspendUser.php");

            }
        }
        else {
            (findViewById(R.id.txtUserPhone)).requestFocus();
            ((EditText) (findViewById(R.id.txtUserPhone))).setError("YOU SHOULD SEARCH FOR USER FIRST");
        }

    }



}
