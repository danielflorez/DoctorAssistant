package com.mangostatecnologia.doctorassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Daniel on 2/18/15.
 */
public class historiaClinica extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.historia_clinica, container, false);

        return rootView;
    }

}
