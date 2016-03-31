package com.example.tabservice.sqlcontrol;

import com.example.tabservice.crud.ConsultaActivo;
import com.example.tabservice.datos.DatosAutenticacion;

/**
 * Created by choqu_000 on 31/03/2016.
 */
public class SqlControlConsultaActividadPersona {

    //Atributos
    private ConsultaActivo consultaActivo;

    //Cosntructor
    public SqlControlConsultaActividadPersona(){

    }

    public boolean ConsultaDatosActividadPersonla(DatosAutenticacion datosAutenticacion){
        //Consulta de activo
        consultaActivo=(ConsultaActivo)new ConsultaActivo(datosAutenticacion).execute();
        return true;
    }
}
