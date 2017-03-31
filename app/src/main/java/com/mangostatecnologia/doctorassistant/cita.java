package com.mangostatecnologia.doctorassistant;

/**
 * Created by Daniel on 11/22/14.
 */
public class cita
{
    private String idCita;
    private String desc;
    private String fechaInicio;
    private String fechaFin;
    private paciente paci;
    private procedimiento proc;
    private consultorio consul;
    private entidad enti;

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public paciente getPaci() {
        return paci;
    }

    public void setPaci(paciente paci) {
        this.paci = paci;
    }

    public procedimiento getProc() {
        return proc;
    }

    public void setProc(procedimiento proc) {
        this.proc = proc;
    }

    public consultorio getConsul() {
        return consul;
    }

    public void setConsul(consultorio consul) {
        this.consul = consul;
    }

    public entidad getEnti() {
        return enti;
    }

    public void setEnti(entidad enti) {
        this.enti = enti;
    }
}
