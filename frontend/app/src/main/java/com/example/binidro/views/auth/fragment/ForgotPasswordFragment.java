package com.example.binidro.views.auth.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.binidro.R;

public class ForgotPasswordFragment extends Fragment implements View.OnClickListener{

    private Button sendLinkButton;
    private EditText emailEditText;
    private String email;

    public ForgotPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        findXmlElements(view);
        setUpListeners();
        return view;
    }

    private void findXmlElements(View view){
        sendLinkButton = view.findViewById(R.id.sendLinkButtonForgotPassword);
        emailEditText = view.findViewById(R.id.emailEditTextForgotPassword);
    }

    private void setUpListeners(){
        sendLinkButton.setOnClickListener(this);
    }

    private void handleLinkSend(){
        email = emailEditText.getText().toString().trim();
        if(email.isEmpty()){
            emailEditText.setError("Invalid Email");
        }
        else{
            sendLinkButton.setText("Sending Link...");

            // TODO
        }
    }

    private void goToSignIn(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentContainerAuth, new SignInFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(view==sendLinkButton){
            handleLinkSend();
        }
    }
}