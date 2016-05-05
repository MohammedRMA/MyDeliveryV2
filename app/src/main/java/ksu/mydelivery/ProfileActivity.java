package ksu.mydelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {


    private RegularUser user;
    private String fName , lName , phone ,email , bDate , nationality , password , address;
    private EditText EfName , ElName , Ephone ,Eemail , EbDate , Enation , Epassword , Eaddress ;

    private ArrayList<Request> requestList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        user = (RegularUser) getIntent().getSerializableExtra("RegularUser");

        requestList = new ArrayList<>();
        requestList = (ArrayList<Request>) getIntent().getSerializableExtra("Requests");

        Toast.makeText(ProfileActivity.this, "Request list size = "+ requestList.size(), Toast.LENGTH_LONG).show();


        fName = user.getfName();
        lName = user.getlName();
        phone = user.getPhoneNumber();
        email = user.getEmail();
        bDate = user.getBirthDate();
        address = user.getAddress();
        nationality = user.getNationality();

        ((EditText)findViewById(R.id.txtFirstN)).setText(fName);
        ((EditText)findViewById(R.id.txtLastN)).setText(lName);
        ((EditText)findViewById(R.id.txtContactInfo)).setText(phone);
        ((EditText)findViewById(R.id.txtEmail)).setText(email);
        ((EditText)findViewById(R.id.txtBDate)).setText(bDate);
        ((EditText)findViewById(R.id.txtAddress)).setText(address);
        ((EditText)findViewById(R.id.txtNation)).setText(nationality);



        Button Change = (Button) findViewById(R.id.btnChangePass);
        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePass();
            }
        });


        Button update = (Button) findViewById(R.id.btnSaveChange);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });


    }

    public void updateProfile () {


            // EfName = (EditText) findViewById(R.id.txtFirstN);
            // ElName = (EditText) findViewById(R.id.txtLastN);
            Ephone = (EditText) findViewById(R.id.txtContactInfo);
            Eemail = (EditText) findViewById(R.id.txtEmail);
            EbDate = (EditText) findViewById(R.id.txtBDate);
            Eaddress = (EditText) findViewById(R.id.txtAddress);
            // EnationID = (EditText) findViewById(R.id.txtNationID);
            phone = String.valueOf(Ephone.getText());
            email = String.valueOf(Eemail.getText());
            bDate = String.valueOf(EbDate.getText());
            address = String.valueOf(Eaddress.getText());

            if (phone.length() < 10 || phone.length() > 10) {
                (findViewById(R.id.txtContactInfo)).requestFocus();
                ((EditText) (findViewById(R.id.txtContactInfo))).setError("FIELD SHOULD BE 10 NUMBERS");
            }
            if (phone.equals("") || email.equals("") || bDate.equals("")) {
                if(phone.equals("")){
                    (findViewById(R.id.txtContactInfo)).requestFocus();
                    ((EditText) (findViewById(R.id.txtContactInfo))).setError("FIELD CANNOT BE EMPTY");
                }
                if (email.equals("")) {
                    (findViewById(R.id.txtEmail)).requestFocus();
                    ((EditText) (findViewById(R.id.txtEmail))).setError("FIELD CANNOT BE EMPTY");
                }
                if (bDate.equals("")) {
                    (findViewById(R.id.txtBDate)).requestFocus();
                    ((EditText) (findViewById(R.id.txtBDate))).setError("FIELD CANNOT BE EMPTY");
                }
                if (address.equals("")) {
                    (findViewById(R.id.txtBDate)).requestFocus();
                    ((EditText) (findViewById(R.id.txtBDate))).setError("FIELD CANNOT BE EMPTY");
                }
            }
            else {

                user.setEmail(email);
                user.setPhoneNumber(phone);
                user.setBirthDate(bDate);
                user.setAddress(address);

                HashMap updateUser = new HashMap();

                updateUser.put("txtUid" , String.valueOf(user.getID()));
                updateUser.put("txtPassword" , String.valueOf(user.getPassword()));
                updateUser.put("txtEmail" , email);
                updateUser.put("txtPhone" , phone);
                updateUser.put("txtBdate" , bDate);
                updateUser.put("txtAddress" , address);


                PostResponseAsyncTask task1 = new PostResponseAsyncTask(ProfileActivity.this, updateUser, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                        if (s.contains("success")) {


                            Toast.makeText(ProfileActivity.this, "Success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                            intent.putExtra("RegularUser",user);
                            intent.putExtra("Requests", requestList);
                            startActivity(intent);

                            finish();
                        }
                        else {
                            Toast.makeText(ProfileActivity.this, "Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                task1.execute("http://10.0.2.2/user/updateUser.php");
            }
        }



    public void changePass () {

        Intent intent = new Intent(ProfileActivity.this, EditPassActivity.class);
        intent.putExtra("RegularUser",user);
        startActivity(intent);
        finish();

    }


}
