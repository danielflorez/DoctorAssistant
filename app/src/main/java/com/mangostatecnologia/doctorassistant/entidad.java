package com.mangostatecnologia.doctorassistant;

/**
 * Created by Daniel on 10/21/14.
 */
public class entidad
{
    private String idEnt;
    private String codigo;
    private String nombre;
    private String idMedico;
    private Boolean check;

    public String getIdEnt() {
        return idEnt;
    }

    public void setIdEnt(String idEnt) {
        this.idEnt = idEnt;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }
}
