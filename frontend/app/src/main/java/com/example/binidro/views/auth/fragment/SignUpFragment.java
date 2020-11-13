package com.example.binidro.views.auth.fragment;

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

public class SignUpFragment extends Fragment implements View.OnClickListener{

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText mobileNumberEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpButton;
    private Button signInInsteadButton;

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
            signUpButton.setText("Signing Up...");
            // TODO


        }
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
}