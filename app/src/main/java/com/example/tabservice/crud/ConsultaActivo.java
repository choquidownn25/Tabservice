package com.example.tabservice.crud;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tabservice.datos.DatosAutenticacion;
import com.example.tabservice.parametros.ServiceHandler;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by choqu_000 on 31/03/2016.
 */
public class ConsultaActivo extends AsyncTask<String, Void, String> {

    //Atributo
    private String strgcm_regid;
    private String stremail;
    private String strnombre;
    private String strfecha;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String strDate = sdf.format(c.getTime());

    Calendar HoraSystem = Calendar.getInstance();
    SimpleDateFormat foratoTiempo = new SimpleDateFormat("hh:mm:ss");
    String strHoradelSistema = foratoTiempo.format(HoraSystem.getTime());

    private Activity activity;
    private DatosAutenticacion datosAutenticacion;
    private String jsonResult;
    private String urls = "http://sigti.co/directv/activos/ordendetrabajoactivo.php";
    HttpResponse response; //sistema de archivos para que puedan ser reutilizados
    //Constructor
    public ConsultaActivo(DatosAutenticacion datosAutenticacion){
        this.datosAutenticacion=datosAutenticacion;
    }

    @Override
    protected String doInBackground(String... params) {

        try{

            //jsonResult = serviceClient.makeServiceCall(url, ServiceHandler.POST, parametro);

            //datos traidos desde la insercion
            strgcm_regid=datosAutenticacion.getGcm_regid();
            stremail = datosAutenticacion.getEmail();
            strnombre = datosAutenticacion.getNombre();
            //strfecha = datosAutenticacion.getFecha_auxlliar_activo();

            List<NameValuePair> parametro = new ArrayList<NameValuePair>();
            parametro.add(new BasicNameValuePair("gcm_regid", strgcm_regid));
            parametro.add(new BasicNameValuePair("nombre", strnombre));
            parametro.add(new BasicNameValuePair("email", stremail));
            ServiceHandler serviceClient = new ServiceHandler();

            jsonResult = serviceClient.makeServiceCall(urls,
                    ServiceHandler.POST, parametro);


            Log.d("Create Prediction Request: ", "> " + jsonResult);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            //Validacion del Internet
            HttpResponse response = httpclient.execute(httppost);






        }catch (Exception e){
            // Log.i("ThreadHandler", "Thread  exception " + e.toString());

        }

        return null;

    }

    //Metodo de la consulta


}
