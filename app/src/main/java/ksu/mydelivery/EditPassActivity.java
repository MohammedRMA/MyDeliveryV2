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

public class EditPassActivity extends AppCompatActivity {

    private User user;
    private  Provider provider;


    private String  oldPassword , newPassword , newPassword2;
    private EditText  EoldPassword , EnewPassword , EnewPassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);


        provider = (Provider) getIntent().getSerializableExtra("Provider");
        user = (RegularUser) getIntent().getSerializableExtra("User");


        Button editPass = (Button) findViewById(R.id.btnSaveChange);
        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                    changePasswordUser();
                }
                if (provider != null) {
                    changePasswordProvider();
                }
            }
        });
    }


    public void changePasswordUser() {


        EoldPassword = (EditText) findViewById(R.id.txtOldPass);
        EnewPassword = (EditText) findViewById(R.id.txtNewPass) ;
        EnewPassword2 = (EditText) findViewById(R.id.txtConfirmPass) ;

        oldPassword = String.valueOf(EoldPassword.getText());
        newPassword= String.valueOf(EnewPassword.getText());
        newPassword2= String.valueOf(EnewPassword2.getText());


        if( oldPassword.equals( String.valueOf( user.getPassword() ) ) ){

            if(newPassword.equals(newPassword2)){


                if (newPassword.length() < 6 ) {
                    (findViewById(R.id.txtNewPass)).requestFocus();
                    ((EditText) (findViewById(R.id.txtNewPass))).setError("FIELD SHOULD AT LEAST 6 VARIABLES");
                }

                if (newPassword.equals("")) {

                    (findViewById(R.id.txtNewPass)).requestFocus();
                    ((EditText) (findViewById(R.id.txtNewPass))).setError("FIELD CANNOT BE EMPTY");

                }

                else {

                    provider.setPassword(newPassword);

                    HashMap updateUser = new HashMap();

                    updateUser.put("txtPid" , String.valueOf(user.getID()));
                    updateUser.put("txtPassword" , newPassword);
                    updateUser.put("txtEmail" , String.valueOf(user.getEmail()));
                    updateUser.put("txtPhone" , String.valueOf(user.getPhoneNumber()));
                    updateUser.put("txtBdate" , String.valueOf(user.getBirthDate()));

                    PostResponseAsyncTask task1 = new PostResponseAsyncTask(EditPassActivity.this, updateUser, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if (s.contains("success")) {

                                Toast.makeText(EditPassActivity.this, "Success", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(EditPassActivity.this, HomeActivity.class);
                                intent.putExtra("User",user);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(EditPassActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    task1.execute("http://10.0.2.2/user/updateUser.php");}

            }

            else {

                (findViewById(R.id.txtOldPass)).requestFocus();
                ((EditText) (findViewById(R.id.txtOldPass))).setError("PLEASE ENTER A CORRECT PASSWORD");

            }

        }




    }


    public void changePasswordProvider() {

        EoldPassword = (EditText) findViewById(R.id.txtOldPass);
        EnewPassword = (EditText) findViewById(R.id.txtNewPass) ;
        EnewPassword2 = (EditText) findViewById(R.id.txtConfirmPass) ;

        oldPassword = String.valueOf(EoldPassword.getText());
        newPassword= String.valueOf(EnewPassword.getText());
        newPassword2= String.valueOf(EnewPassword2.getText());


        if( oldPassword.equals( String.valueOf( provider.getPassword() ) ) ){


            if (newPassword.equals("") || newPassword.length() < 6 ) {
                if (newPassword.length() < 6) {
                    (findViewById(R.id.txtNewPass)).requestFocus();
                    ((EditText) (findViewById(R.id.txtNewPass))).setError("FIELD SHOULD AT LEAST 6 VARIABLES");
                    return;
                }

                if (newPassword.equals("")) {
                    (findViewById(R.id.txtNewPass)).requestFocus();
                    ((EditText) (findViewById(R.id.txtNewPass))).setError("FIELD CANNOT BE EMPTY");
                    return;
                }
            }

            if(!newPassword.equals(newPassword2)) {
                (findViewById(R.id.txtNewPass)).requestFocus();
                ((EditText) (findViewById(R.id.txtNewPass))).setError("MISMATCH PASSWORD");
            }

                else {

                    provider.setPassword(newPassword);

                    HashMap updateProvider = new HashMap();

                    updateProvider.put("txtPid" , String.valueOf(provider.getID()));
                    updateProvider.put("txtPassword" , newPassword);
                    updateProvider.put("txtEmail" , String.valueOf(provider.getEmail()));
                    updateProvider.put("txtPhone" , String.valueOf(provider.getPhoneNumber()));
                    updateProvider.put("txtBdate" , String.valueOf(provider.getBirthDate()));

                    PostResponseAsyncTask task1 = new PostResponseAsyncTask(EditPassActivity.this, updateProvider, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if (s.contains("success")) {

                                Toast.makeText(EditPassActivity.this, "Success", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(EditPassActivity.this, HomeProviderActivity.class);
                                intent.putExtra("Provider",provider);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(EditPassActivity.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    task1.execute("http://10.0.2.2/provider/updateProvider.php");}
        }
            else {

                (findViewById(R.id.txtOldPass)).requestFocus();
                ((EditText) (findViewById(R.id.txtOldPass))).setError("PLEASE ENTER A CORRECT PASSWORD");

            }






    }



    }

