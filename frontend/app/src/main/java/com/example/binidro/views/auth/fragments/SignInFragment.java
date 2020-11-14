package com.example.binidro.views.auth.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.binidro.views.auth.AuthActivity;
import com.example.binidro.views.main.MainActivity;
import com.example.binidro.views.welcome.WelcomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SignInFragment extends Fragment implements View.OnClickListener{

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private Button signUpButton;
    private TextView forgotPasswordButton;
    private TextView errorTextViewSignIn;

    private String email;
    private String password;

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        findXmlElements(view);
        setUpListeners();
        initializeAndroidNetworkingLibrary();
        return view;
    }

    private void findXmlElements(View view) {
        emailEditText = view.findViewById(R.id.emailEditTextSignIn);
        passwordEditText = view.findViewById(R.id.passwordEditTextSignIn);
        signInButton = view.findViewById(R.id.signInButtonSignIn);
        signUpButton = view.findViewById(R.id.signUpButtonSignIn);
        forgotPasswordButton = view.findViewById(R.id.forgotPasswordButtonSignIn);
        errorTextViewSignIn = view.findViewById(R.id.errorTextViewSignIn);
    }

    private void setUpListeners(){
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
    }

    public void initializeAndroidNetworkingLibrary() {
        // Adding an Network Interceptor for Debugging purpose :
        AndroidNetworking.initialize(getActivity().getApplicationContext());
    }

    private void handleSignIn(){
        errorTextViewSignIn.setVisibility(View.GONE);
        emailEditText.setError(null);
        passwordEditText.setError(null);
        if(getValues()) {
            if (!isEmailValid(email)) {
                errorTextViewSignIn.setText("Email isn't valid!");
                errorTextViewSignIn.setVisibility(View.VISIBLE);
            }

            else {
                final String email = emailEditText.getText().toString().toLowerCase();
                final String password = passwordEditText.getText().toString();
                signIn(email, password);
            }
        }
    }

    private void signIn(String email, String password) {
        errorTextViewSignIn.setVisibility(View.INVISIBLE);
        JSONObject formData = new JSONObject();
        try {
            formData.put("email", email);
            formData.put("password", password);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        AndroidNetworking.post("http://118.179.145.125:25565/healthworker/login")
                .addJSONObjectBody(formData)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        intent.putExtra("userDetails", response.toString());
                        startActivity(intent);
                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    @Override
                    public void onError(ANError error) {
                        errorTextViewSignIn.setText("Invalid Sign In Attempt! Try Again!");
                        errorTextViewSignIn.setVisibility(View.VISIBLE);
                    }
                });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Return true if gets values successfully
    private boolean getValues(){
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString();

        if(email.isEmpty()){
            emailEditText.setError("Invalid Email!");
            return false;
        }
        else if(password.isEmpty()){
            passwordEditText.setError("Incorrect Password!");
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        if(view == signInButton){
            handleSignIn();
        } else if(view == signUpButton){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.fragmentContainerAuth, new SignUpFragment(), "signUp");
            fragmentTransaction.commit();
        } else if(view == forgotPasswordButton){
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.fragmentContainerAuth, new ForgotPasswordFragment(), "forgotPassword");
            fragmentTransaction.commit();
        }
    }

    public void showToast(String message){
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}