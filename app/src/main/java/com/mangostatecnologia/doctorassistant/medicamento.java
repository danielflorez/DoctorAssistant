package com.mangostatecnologia.doctorassistant;

/**
 * Created by Daniel on 12/1/14.
 */
public class medicamento
{
    private String codigo;
    private String nombre;
    private String compActivo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCompActivo() {
        return compActivo;
    }

    public void setCompActivo(String compActivo) {
        this.compActivo = compActivo;
    }
}
