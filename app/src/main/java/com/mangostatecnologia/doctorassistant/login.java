package com.mangostatecnologia.doctorassistant;


import android.app.Fragment;




public class login extends SingleFragmentActivity {

    @Override
    public Fragment createFragment(){
        final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
        globalVariable.setEmail("");
        return new loginFragment();
    }
}
