package com.example.tabservice.datos;

/**
 * Created by choqu_000 on 31/03/2016.
 * Clase datos personales
 */
public class DatosListaActivo {

    //Atributos
    private String Cantidad;
    private String Responsable;
    private String Fecha;
    private String Email;

    //Constructor
    public DatosListaActivo(){

    }

    //Encapsulamiento/Propiedad
    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getResponsable() {
        return Responsable;
    }

    public void setResponsable(String responsable) {
        Responsable = responsable;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
