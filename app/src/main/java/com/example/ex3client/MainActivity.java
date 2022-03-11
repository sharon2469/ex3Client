package com.example.ex3client;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private static final String DETAILS_ERROR_TOAST_TXT     = "details error";
    private static final int    REQUEST_PERSON_DETAILS      = 1;

    private EditText edCall;
    private EditText edSurf;
    private EditText edEmail;
    private TextView txtRegister;
    private Toast detailsErrorToast;

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edCall = findViewById(R.id.edCall);
        edSurf = findViewById(R.id.edSurf);
        edEmail = findViewById(R.id.edEmail);
        txtRegister = findViewById(R.id.txtRegister);
        detailsErrorToast   = Toast.makeText(getApplicationContext(), DETAILS_ERROR_TOAST_TXT, Toast.LENGTH_SHORT);
    }

    public void callBtnClicked(View view) {
        try {
            String phoneNumberStr = edCall.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumberStr));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            detailsErrorToast.show();
        }
    }

    public void surfBtnClicked(View view) {
        try {
            String websiteAddressStr = edSurf.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteAddressStr));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            detailsErrorToast.show();
        }
    }

    public void emailBtnClicked(View view) {
        try {
            String emailStr = edEmail.getText().toString();
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailStr, null));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            detailsErrorToast.show();
        }
    }

    public void registerBtnClicked(View view) {
        Intent intent = new Intent("com.action.ex3.register");
        startActivityForResult(intent, REQUEST_PERSON_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PERSON_DETAILS && resultCode == RESULT_OK && data != null) {
            String firstName = data.getStringExtra("first_name");
            String lastName = data.getStringExtra("last_name");
            String genderType = data.getStringExtra("gender");
            String gender = (genderType.equals("Male")) ? "Mr." : "Ms.";

            txtRegister.setText(getString(R.string.register_text_view_txt, gender, firstName, lastName));
        }
    }

}