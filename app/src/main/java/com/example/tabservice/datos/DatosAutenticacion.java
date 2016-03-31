package com.example.tabservice.datos;

/**
 * Created by choqu_000 on 31/03/2016.
 */
public class DatosAutenticacion {

    //Atributos
    private String gcm_regid;
    private String email;
    private String nombre;
    private String activo;
    private String fecha_auxlliar_activo;
    private String hora;
    private float latitud;
    private float longitud;

    //Cosntructor
    public DatosAutenticacion(){

    }

    //Propiedad y encapsulamiento
    public String getGcm_regid() {
        return gcm_regid;
    }

    public void setGcm_regid(String gcm_regid) {
        this.gcm_regid = gcm_regid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getFecha_auxlliar_activo(){
        return fecha_auxlliar_activo;
    }

    public void setFecha_auxlliar_activo(String fecha_auxlliar_activo){
        this.fecha_auxlliar_activo=fecha_auxlliar_activo;
    }

    public String getHora(){
        return hora;
    }

    public void setHora(String hora){
        this.hora=hora;
    }


    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }
}
