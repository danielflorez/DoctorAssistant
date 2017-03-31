package com.mangostatecnologia.doctorassistant;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;


/**
 * Created by Daniel on 9/11/14.
 */
public class loginFragment extends Fragment {
    private static final String TAG = "DoctorAssistant";
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mUserEditText;
    private EditText mPassEditText;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mLoginButton = (Button)v.findViewById(R.id.loginButton);
        mRegisterButton = (Button)v.findViewById(R.id.registerButton);
        mUserEditText = (EditText)v.findViewById(R.id.userName);
        mPassEditText = (EditText)v.findViewById(R.id.password);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mUserEditText.getText().toString();
                String pass = mPassEditText.getText().toString();
                String result = null;
                try {
                    result = new FetchItemsTask().execute(user, pass).get();
                }catch (InterruptedException ioe){
                    Log.e(TAG,"Failed to login",ioe);
                }catch (ExecutionException ioe){
                    Log.e(TAG,"Failed to login",ioe);
                }
                if(result.equals("OK")) {
                    final GlobalClass globalVariable = (GlobalClass) getActivity().getApplicationContext();
                    globalVariable.setEmail(user);
                    globalVariable.getMemberID();
                    Intent i = new Intent(getActivity(), mainMenu.class);
                    startActivity(i);
                }else if(result.equals("ACT"))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("Falta activar la cuenta, por favor sigue las instrucciones del email de activacion que se envio.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getActivity());
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("Usuario o clave errada.")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iR = new Intent(getActivity(),registerFragment.class);
                startActivity(iR);
            }
        });
        return v;
    }

    private class FetchItemsTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().loginTried(params[0],params[1]);
        }
    }
}
