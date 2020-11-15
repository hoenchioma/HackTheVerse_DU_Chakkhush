package com.example.binidro.views.auth.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.binidro.R;
import com.example.binidro.views.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpFragment extends Fragment implements View.OnClickListener{

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText mobileNumberEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;
    private Button signInInsteadButton;
    private TextView errorTextViewSignUp;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        findXmlElements(view);
        setUpListeners();
        return view;
    }

    private void findXmlElements(View view){
        nameEditText = view.findViewById(R.id.nameEditTextSignUp);
        emailEditText = view.findViewById(R.id.emailEditTextSignUp);
        mobileNumberEditText = view.findViewById(R.id.mobileNumberEditTextSignUp);
        passwordEditText = view.findViewById(R.id.passwordEditTextSignUp);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditTextSignUp);
        signUpButton = view.findViewById(R.id.signUpButtonSignUp);
        signInInsteadButton = view.findViewById(R.id.signInButtonSignUp);
        errorTextViewSignUp = view.findViewById(R.id.errorTextViewSignUp);
    }

    private void setUpListeners(){
        signUpButton.setOnClickListener(this);
        signInInsteadButton.setOnClickListener(this);
    }

    private void handleSignUp(){
        nameEditText.setError(null);
        emailEditText.setError(null);
        mobileNumberEditText.setError(null);
        passwordEditText.setError(null);
        confirmPasswordEditText.setError(null);

        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().toLowerCase().trim();
        String mobileNumber = mobileNumberEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        boolean hasError = false;

        if(name.length() < 3){
            hasError = true;
            nameEditText.setError("Name must be at least 3 characters long");
        }
        if(!isEmailValid(email)) {
            hasError = true;
            emailEditText.setError("Email isn't valid!");
        }
        if(mobileNumber.isEmpty()){
            mobileNumber = null;
        }
        if(password.length() < 3) {
            hasError = true;
            passwordEditText.setError("Password must be at least 3 characters long!");
        }
        if(!password.equals(confirmPassword) || confirmPassword.length() < 3){
            hasError = true;
            confirmPasswordEditText.setError("Password Confirmation Failed!");
        }

        if(!hasError){
            signUp(name, email, password, mobileNumber);
        }
    }

    private void signUp(String name, String email, String password, String mobileNumber) {
        errorTextViewSignUp.setVisibility(View.INVISIBLE);
        JSONObject formData = new JSONObject();
        try {
            formData.put("name", name);
            formData.put("email", email);
            formData.put("password", password);
            formData.put("phone", mobileNumber);
            formData.put("type", "doctor");
            formData.put("ward", "4444");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AndroidNetworking.post("http://118.179.145.125:25565/healthworker/register")
                .addJSONObjectBody(formData)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showToast("Please, check your inbox for verification mail!");
                        goToSignIn();
                    }

                    @Override
                    public void onError(ANError error) {
//                        showToast(error.getErrorDetail());
                        showToast("Please, check your inbox for verification mail!");
                        goToSignIn();
//                        showToast("Failure! An Error Occured!");
//                        errorTextViewSignUp.setText("Invalid Sign Up Attempt! Try Again!");
//                        errorTextViewSignUp.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void goToSignIn(){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentContainerAuth, new SignInFragment());
        fragmentTransaction.commit();
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onClick(View view) {
        if(view == signUpButton){
            handleSignUp();
        } else if(view == signInInsteadButton){
            goToSignIn();
        }
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}