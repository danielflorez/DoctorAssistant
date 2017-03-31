package com.mangostatecnologia.doctorassistant;

/**
 * Created by Daniel on 11/12/14.
 */
public class precioProcedimiento
{
    private String idProc;
    private String idEnt;
    private String nombreEnt;
    private String precio;

    public String getIdProc() {
        return idProc;
    }

    public void setIdProc(String idProc) {
        this.idProc = idProc;
    }

    public String getIdEnt() {
        return idEnt;
    }

    public void setIdEnt(String idEnt) {
        this.idEnt = idEnt;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getNombreEnt() {
        return nombreEnt;
    }

    public void setNombreEnt(String nombreEnt) {
        this.nombreEnt = nombreEnt;
    }
}
