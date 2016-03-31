package com.example.tabservice;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.tabservice.datos.DatosAutenticacion;
import com.example.tabservice.datos.DatosListaActivo;
import com.example.tabservice.datos.Util;
import com.example.tabservice.parametros.ServiceHandler;
import com.example.tabservice.sqlcontrol.SqlControlConsultaActividadPersona;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by choqu_000 on 31/03/2016.
 */

public class TabLayoutService extends ActionBarActivity{

    //Atributos
    private String url = "Su URL";
    private TextView Cantidad;
    private TextView Responsable;
    private TextView Fecha;
    private TextView Email;
    //tab 2
    private TextView NombreActivoPersonal;
    private TextView Estado;
    private Button Salir;
    private List<DatosListaActivo> listDatosActivo;
    private DatosListaActivo personas;
    private DatosAutenticacion datosAutenticacion;
    private List<DatosAutenticacion> listDatosAutenticacion;
    private String jsonResult;
    private String jsonResultado;
    private Context context;
    private String User_name;
    static final String TAG = "pavan";
    private String gcm_regid;
    private String Usuario_Email;
    public String id_gcm_Google_Messeging;

    private int posicion=0;
    private int posicionesActivo = 0;
    private static final int ALARM_REQUEST_CODE = 1;
    private int cont=0;

    private SqlControlConsultaActividadPersona sqlControlConsultaActividadPersona;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1);


        listDatosActivo = new ArrayList<DatosListaActivo>();
        listDatosAutenticacion = new ArrayList<DatosAutenticacion>();
        //Mensaje
        //new Insertar(GridActiviti.this).execute();
        //context = getApplicationContext();
        estaRegistrado(context);
        Resources resources = Resources.getSystem();

        TabHost tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Servicios",
                resources.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        Cantidad = (TextView)findViewById(R.id.textViewCantidad);
        Responsable=(TextView)findViewById(R.id.textViewResponsable);
        Fecha = (TextView)findViewById(R.id.textViewFecha);
        Email = (TextView)findViewById(R.id.textViewEmail);
        context = getApplicationContext();
        Salir = (Button)findViewById(R.id.btnsalir);

        //metodo de acceso del wb service
        accessWebService();


        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Actividad",
                resources.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);

        AccessWebServices();

        NombreActivoPersonal = (TextView)findViewById(R.id.textViewNombreActivoPersonal);
        Estado = (TextView)findViewById(R.id.textView2);

        tabs.setCurrentTab(0);

        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.i("AndroidTabsDemo", "Pulsada pestaña: " + tabId);
            }
        });

    }

    //El acceso de web service
    public void AccessWebServices(){
        ConsultaActivos task = new ConsultaActivos();
        task.execute(new String[]{getResources().getString(R.string.urlpersonalactivo)});
    }

    //Metodo para el Web service
    public void accessWebService() {

        Consultar task = new Consultar();
        // passes values for the urls string array
        task.execute(new String[]{getResources().getString(R.string.urlactivo)});
        //task.execute(new String[]{url});
    }

    //Metodo en la cual  esta o no registrado
    public boolean estaRegistrado(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        User_name = prefs.getString(Util.USER_NAME, "");
        String DatonombreTecCelular = "Jose";
        String datoRegistrogmc_id_google_messeging = "APA91bHSdgAJ9m1PvSAutUwXD5GbbsGG-UyrYxRQ_t8akgvdPR5Q0OJkCJMJNukcanGFSTMqVZ2mDNKgupD9t2cDr-x2tZhGDqmlKuWYCQw7_brIryMgQ0zEd6NA7zZGQTyd-BDCCT-G";

        id_gcm_Google_Messeging = prefs.getString(Util.PROPERTY_REG_ID, "");
        String ValidacionTelefono = id_gcm_Google_Messeging;
        String gcm_id_elec = id_gcm_Google_Messeging;
        gcm_regid=id_gcm_Google_Messeging;

        if (id_gcm_Google_Messeging.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return true;
        }
        if(id_gcm_Google_Messeging.equals(ValidacionTelefono)){
            Log.i(TAG, "Registration not found.");
            return true;
        }


        return false;

    }

    //Metoo de preferencias para el Google Messeging
    private SharedPreferences getGCMPreferences(Context context) {
        // Esta aplicacion muestra persiste el ID de registro en las preferencias compartidas
        // Como se almacena el ID de registro en su aplicacion depende de usted.
        return getSharedPreferences(TabLayoutService.class.getSimpleName(),Context.MODE_PRIVATE);
        //return getSharedPreferences(VistaFormularioEjecuciones.class.getSimpleName(), Context.MODE_PRIVATE);
    }


    //Creamos la clase para consultar a la foto a subir
    private class Consultar extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {

                final SharedPreferences prefs = getGCMPreferences(context);
                User_name = prefs.getString(Util.USER_NAME, "");
                id_gcm_Google_Messeging = prefs.getString(Util.PROPERTY_REG_ID, "");
                Usuario_Email = prefs.getString(Util.EMAIL, "");
                gcm_regid=id_gcm_Google_Messeging;
                List<NameValuePair> parametro = new ArrayList<NameValuePair>();
                parametro.add(new BasicNameValuePair("gcm_regid", gcm_regid));
                parametro.add(new BasicNameValuePair("nombre", User_name));
                parametro.add(new BasicNameValuePair("email", Usuario_Email));
                ServiceHandler serviceClient = new ServiceHandler();

                jsonResult = serviceClient.makeServiceCall(getResources().getString(R.string.urlactivo),
                        ServiceHandler.POST, parametro);

                //jsonResult = serviceClient.makeServiceCall(url, ServiceHandler.POST, parametro);

                Log.d("Create Prediction Request: ", "> " + jsonResult);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(params[0]);
                //Validacion del Internet
                HttpResponse response = httpclient.execute(httppost);
                //HttpResponse response = httpclient.execute(httppost);
                //jsonResult = inputStreamToString(
                //        response.getEntity().getContent()).toString();

                //aca esta si es del telefono o no
                if (estaRegistrado(context)) {
                    //if(ListDrwaer()){
                    //aca esta si es del telefono o no
                    if(ListDrwaer(jsonResult)){

                        mostrarPersona(posicion);
                    }

                }else{

                    //
                    finish();
                }



            }catch (ClientProtocolException e) {
                Log.d( "Error Internet", e.toString() );
                e.printStackTrace();
            } catch (IOException e) {
                Log.d( "Error Internet", e.toString() );
                e.printStackTrace();
            }

            return null;


        }
    }


    // construir conjunto hash para la vista de lista
    public boolean ListDrwaer(String json) {
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

        try {
            String datoJsonnull="null\n";
            if(json.equals(datoJsonnull)){


                finish();
                if(cont < 1) {
                    //Contadoe de null
                    cont += 1;
                    Log.i(TAG, "Registration not found.");
                }
                // showErrorVista();
            }else {

                JSONObject jsonResponse = new JSONObject(json);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("sigti_instalaciones");
                //JSONArray jsonMainNode = jsonResponse.optJSONArray(" ");

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    personas = new DatosListaActivo();
                    JSONObject jsonArrayChild = jsonMainNode.getJSONObject(i);
                    personas.setCantidad(jsonArrayChild.optString("Cant"));
                    //personas.setNumero_wo(jsonArrayChild.optInt("numero_wo"));
                    personas.setResponsable(jsonArrayChild.optString("responsable"));
                    personas.setFecha(jsonArrayChild.getString("fecha"));
                    personas.setEmail(jsonArrayChild.optString("email"));
                    listDatosActivo.add(personas);
                    //enviar.setEnabled(true);
                }


            }


        } catch (JSONException e) {


            Log.e("JSON Parser", "Error parsing data " + e.toString());
            //showErrorVistaInternet();
            //

            //enviar.setEnabled(false);
            return false;
        }

        return true;


    }

    //Metodo para mostrar datos de json
    //Metodo para mostrar datos de jason
    private void mostrarPersona(final int posicion) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                String datoNulldelJson = "null\n";
                if (jsonResult.equals(datoNulldelJson)) {
                    // showErrorVista();
                    Log.i(TAG, "Registration not found.");
                    //pasa una vez
                    NombreActivoPersonal.setText(User_name.toString());
                    //finish();
                    //startActivity(new Intent(VistaFormularioEjecuciones.this, ShowpopupWindowsVistaFormulario.class));
                } else {

                    if (estaRegistrado(context)) {

                        DatosListaActivo personas = listDatosActivo.get(posicion);
                        //datoOrdenservicio = personas.getId_orden_servicio();

                        //id_orden_servicio.setText(String.valueOf(datoOrdenservicio));
                        //int Dato_Estado = personas.getEstado();

                        Cantidad.setText(personas.getCantidad());
                        Responsable.setText(personas.getResponsable());
                        Fecha.setText(personas.getFecha());
                        Email.setText(personas.getEmail());

                    } else {
                        //noRegistrado();
                        // showNotieneOrdenSalir();

                        finish();
                        //Log.i(TAG, "Registration not found.");
                    }

                }

            }
        });
    }


    //Consulta Activos
    public class ConsultaActivos extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {



            final SharedPreferences prefs = getGCMPreferences(context);
            User_name = prefs.getString(Util.USER_NAME, "");
            id_gcm_Google_Messeging = prefs.getString(Util.PROPERTY_REG_ID, "");
            Usuario_Email = prefs.getString(Util.EMAIL, "");
            gcm_regid=id_gcm_Google_Messeging;
            List<NameValuePair> parametro = new ArrayList<NameValuePair>();
            parametro.add(new BasicNameValuePair("gcm_regid", gcm_regid));
            parametro.add(new BasicNameValuePair("nombre", User_name));
            parametro.add(new BasicNameValuePair("email", Usuario_Email));
            ServiceHandler serviceClient = new ServiceHandler();

            jsonResultado = serviceClient.makeServiceCall(getResources().getString(R.string.urlpersonalactivo),
                    ServiceHandler.POST, parametro);

            //jsonResult = serviceClient.makeServiceCall(url, ServiceHandler.POST, parametro);

            Log.d("Create Prediction Request: ", "> " + jsonResultado);

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            //Validacion del Internet
            try {
                HttpResponse response = httpclient.execute(httppost);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(ListDrawers(jsonResultado)){
                mostrarPersonaActiva(posicionesActivo);
            }

            return null;
        }
    }


    // construir conjunto hash para la vista de lista
    public boolean ListDrawers(String json) {
        List<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

        try {
            String datoJsonnull="null\n";
            if(json.equals(datoJsonnull)){


                // finish();
                if(cont < 1) {
                    //startActivity(new Intent(TabLayoutService.this, ShowpopupWindowsVistaFormulario.class));
                    cont += 1;
                    Log.i(TAG, "Registration not found.");
                }
                // showErrorVista();
            }else {

                JSONObject jsonResponse = new JSONObject(json);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("sigti_instalaciones");
                //JSONArray jsonMainNode = jsonResponse.optJSONArray(" ");

                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                    datosAutenticacion = new DatosAutenticacion();
                    JSONObject jsonArrayChild = jsonMainNode.getJSONObject(i);
                    datosAutenticacion.setGcm_regid(jsonArrayChild.optString("gcm_regid"));
                    //personas.setNumero_wo(jsonArrayChild.optInt("numero_wo"));
                    datosAutenticacion.setNombre(jsonArrayChild.optString("nombre"));
                    datosAutenticacion.setEmail(jsonArrayChild.optString("email"));
                    listDatosAutenticacion.add(datosAutenticacion);
                    //enviar.setEnabled(true);
                }


            }


        } catch (JSONException e) {


            Log.e("JSON Parser", "Error parsing data " + e.toString());
            //showErrorVistaInternet();
            //

            //enviar.setEnabled(false);
            return false;
        }

        return true;


    }

    //Metodo para mostrar datos de json
    //Metodo para mostrar datos de jason
    private void mostrarPersonaActiva(final int posicionesActivo) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                String datoNulldelJson = "null\n";
                if (jsonResultado.equals(datoNulldelJson)) {
                    // showErrorVista();
                    Log.i(TAG, "Registration not found.");
                    NombreActivoPersonal.setText(User_name.toString());
                    Estado.setText("Personal Laborando !");
                    //pasa una vez
                    //finish();
                    //startActivity(new Intent(VistaFormularioEjecuciones.this, ShowpopupWindowsVistaFormulario.class));
                } else {

                    if (estaRegistrado(context)) {

                        //DatosListaActivo personas = listDatosActivo.get(posicion);
                        //datoOrdenservicio = personas.getId_orden_servicio();
                        DatosAutenticacion personasActivas = listDatosAutenticacion.get(posicionesActivo);
                        //id_orden_servicio.setText(String.valueOf(datoOrdenservicio));
                        //int Dato_Estado = personas.getEstado();

                        // Cantidad.setText(personas.getCantidad());
                        //Responsable.setText(personas.getResponsable());
                        //Fecha.setText(personas.getFecha());
                        //Email.setText(personas.getEmail());
                        NombreActivoPersonal.setText(personasActivas.getNombre());
                        Estado.setText("Personal Activo");
                    } else {

                        //finish();
                        NombreActivoPersonal.setText(User_name.toString());
                        Estado.setText("Personal Laboransdo");
                        //Log.i(TAG, "Registration not found.");
                    }

                }

            }
        });
    }


}


