package com.mangostatecnologia.doctorassistant;

import java.util.Calendar;

/**
 * Created by Daniel on 11/22/14.
 */
public class citaCompleta
{
    private Calendar hora;
    private cita cita;

    public Calendar getHora()
    {
        return hora;
    }

    public void setHora(Calendar hora)
    {
        this.hora = hora;
    }

    public cita getCita()
    {
        return cita;
    }

    public void setCita(cita cita)
    {
        this.cita = cita;
    }
}
