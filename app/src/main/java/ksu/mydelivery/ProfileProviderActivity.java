package ksu.mydelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;

public class ProfileProviderActivity extends AppCompatActivity {

    private Provider provider;
    private String fName , lName , phone ,email , bDate , nationID , password;
    private EditText EfName , ElName , Ephone ,Eemail , EbDate , EnationID , Epassword ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prfile_provider);


        provider = (Provider) getIntent().getSerializableExtra("Provider");

        fName = provider.getfName();
        lName = provider.getlName();
        phone = provider.getPhoneNumber();
        email = provider.getEmail();
        bDate = provider.getBirthDate();
        nationID = String.valueOf(provider.getNationalID());

        ((EditText)findViewById(R.id.txtFirstN)).setText(fName);
        ((EditText)findViewById(R.id.txtLastN)).setText(lName);
        ((EditText)findViewById(R.id.txtContactInfo)).setText(phone);
        ((EditText)findViewById(R.id.txtEmail)).setText(email);
        ((EditText)findViewById(R.id.txtBDate)).setText(bDate);
        ((EditText)findViewById(R.id.txtNationID)).setText(nationID);



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



    public void changePass () {

        Intent intent = new Intent(ProfileProviderActivity.this, EditPassActivity.class);
        intent.putExtra("Provider",provider);
        startActivity(intent);
        finish();

    }



    public void updateProfile() {

        // EfName = (EditText) findViewById(R.id.txtFirstN);
        // ElName = (EditText) findViewById(R.id.txtLastN);
        Ephone = (EditText) findViewById(R.id.txtContactInfo);
        Eemail = (EditText) findViewById(R.id.txtEmail);
        EbDate = (EditText) findViewById(R.id.txtBDate);
        // EnationID = (EditText) findViewById(R.id.txtNationID);
        phone = String.valueOf(Ephone.getText());
        email = String.valueOf(Eemail.getText());
        bDate = String.valueOf(EbDate.getText());

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
    }
        else {

            provider.setEmail(email);
            provider.setPhoneNumber(phone);
            provider.setBirthDate(bDate);

            HashMap updateProvider = new HashMap();

            updateProvider.put("txtPid" , String.valueOf(provider.getID()));
            updateProvider.put("txtPassword" , String.valueOf(provider.getPassword()));
            updateProvider.put("txtEmail" , email);
            updateProvider.put("txtPhone" , phone);
            updateProvider.put("txtBdate" , bDate);

            PostResponseAsyncTask task1 = new PostResponseAsyncTask(ProfileProviderActivity.this, updateProvider, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if (s.contains("success")) {


                        Toast.makeText(ProfileProviderActivity.this, "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProfileProviderActivity.this, HomeProviderActivity.class);
                        intent.putExtra("Provider",provider);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(ProfileProviderActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });

            task1.execute("http://10.0.2.2/provider/updateProvider.php");
        }
    }


}
