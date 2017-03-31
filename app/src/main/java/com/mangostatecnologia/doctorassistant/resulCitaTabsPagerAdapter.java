package com.mangostatecnologia.doctorassistant;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Daniel on 2/13/15.
 */
public class resulCitaTabsPagerAdapter extends FragmentPagerAdapter
{
    public resulCitaTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new updatePacienteResulCita();
            case 1:
                return new historiaClinica();
            case 2:
                return new examen_fisico();
            case 3:
                return new examen_fisico_2();
            case 4:
                return new control();

        }

        return null;
    }

    @Override
    public int getCount()
    {
        return 5;
    }
}
