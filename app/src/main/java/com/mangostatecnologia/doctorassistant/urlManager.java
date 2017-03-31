package com.mangostatecnologia.doctorassistant;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 9/10/14.
 */
public class urlManager {
    private static final String TAG = "DoctorAssistant";
    byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0 ){
                out.write(buffer , 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrl(String urlSpec) throws IOException{

        return new String(getUrlBytes(urlSpec));
    }

    public String loginTried(String user, String pass){
        /*

        try{
            String url = Uri.parse("http://www.mangostatecnologia.com/medicos_test/secureLogin/includes/process_login3.php").buildUpon().appendQueryParameter("email",user).appendQueryParameter("p",pass).build().toString();
            Log.i(TAG,url);
            xmlString = getUrl(url);
            Log.i(TAG,xmlString);
        }catch(IOException ioe){
            Log.e(TAG,"Failed to fetch items", ioe);
        }*/
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/secureLogin/includes/process_login2.php");

        try {
            // Add your data

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(pass.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            String password = sb.toString();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("email",user));
            nameValuePairs.add(new BasicNameValuePair("p",password));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return xmlString;
    }

    public String registerTried(String nombre, String apellido, String cedula, String telefono, String direccion, String clave, String email)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/secureLogin/includes/register.inc2_copy.php");

        try
        {
            // Add your data
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(clave.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            String password = sb.toString();
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
            nameValuePairs.add(new BasicNameValuePair("id",cedula));
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("p",password));
            nameValuePairs.add(new BasicNameValuePair("nombre",nombre));
            nameValuePairs.add(new BasicNameValuePair("apellido",apellido));
            nameValuePairs.add(new BasicNameValuePair("telefono",telefono));
            nameValuePairs.add(new BasicNameValuePair("direccion",direccion));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return xmlString;
    }


    public String crearConsultorio( String id,String desc,String direccion,String telefono ){
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/crearConsultorioAndroid.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("desc",desc));
            nameValuePairs.add(new BasicNameValuePair("direccion",direccion));
            nameValuePairs.add(new BasicNameValuePair("telefono",telefono));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String updateConsultorio( String id,String desc,String direccion,String telefono,String idLoc ){
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/actualizarConsultorio.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("desc",desc));
            nameValuePairs.add(new BasicNameValuePair("direccion",direccion));
            nameValuePairs.add(new BasicNameValuePair("telefono",telefono));
            nameValuePairs.add(new BasicNameValuePair("idLoc",idLoc));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String consultorios( String id)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/consultorios.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("idDoctor",id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String pacientes( String id)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/pacientes.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("idDoctor",id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }


    public String crearPaciente( String id,String email, String fechaNacimiento, String nombre,String apellido,String telefono,
                                 String direccion, String idMedico, String idEntidad)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/crearPaciente.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("fechaNacimiento",fechaNacimiento));
            nameValuePairs.add(new BasicNameValuePair("nombre",nombre));
            nameValuePairs.add(new BasicNameValuePair("apellido",apellido));
            nameValuePairs.add(new BasicNameValuePair("telefono",telefono));
            nameValuePairs.add(new BasicNameValuePair("direccion",direccion));
            nameValuePairs.add(new BasicNameValuePair("idMedico",idMedico));
            nameValuePairs.add(new BasicNameValuePair("idEntidad",idEntidad));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String updatePaciente( String id,String email, String fechaNacimiento, String nombre,String apellido,String telefono,
                                 String direccion, String idMedico, String idEntidad)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/actualizarPaciente.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("email",email));
            nameValuePairs.add(new BasicNameValuePair("fechaNacimiento",fechaNacimiento));
            nameValuePairs.add(new BasicNameValuePair("nombre",nombre));
            nameValuePairs.add(new BasicNameValuePair("apellido",apellido));
            nameValuePairs.add(new BasicNameValuePair("telefono",telefono));
            nameValuePairs.add(new BasicNameValuePair("direccion",direccion));
            nameValuePairs.add(new BasicNameValuePair("idMedico",idMedico));
            nameValuePairs.add(new BasicNameValuePair("idEntidad",idEntidad));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String entidades( String id)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/entidad.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("idDoctor",id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String grabarEntidades( String id,String entJson)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/entidadMedico.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("entJson",entJson));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String procedimentos( String id)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/procedimientos.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("idDoctor",id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String crearProcedimento( String id,String nombre)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/crearProcedimiento.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("desc",nombre));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String updateProcedimento( String id,String nombre)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/actualizarProcedimiento.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("desc",nombre));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String preciosXProcedimentos( String id)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/preciosXEntidad.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("idDoctor",id));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String grabarPreciosXProcedimentos( String id,String id_proc, String json)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/precioXEntidad.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("id_proc",id_proc));
            nameValuePairs.add(new BasicNameValuePair("entJson",json));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String getCitas(String idLoc,String fechaIni,String fechaFin)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/citas.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
            nameValuePairs.add(new BasicNameValuePair("idLocacion",idLoc));
            nameValuePairs.add(new BasicNameValuePair("dateIni",fechaIni));
            nameValuePairs.add(new BasicNameValuePair("dateFin",fechaFin));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String grabarCita(String idCita,String idPaciente,String idEntidad, String idMedico, String idLocacion, String idProc, String dateInicio, String dateFinal, String motivo)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/crearCita.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(9);
            nameValuePairs.add(new BasicNameValuePair("id",idCita));
            nameValuePairs.add(new BasicNameValuePair("idPaciente",idPaciente));
            nameValuePairs.add(new BasicNameValuePair("idEntidad",idEntidad));
            nameValuePairs.add(new BasicNameValuePair("idMedico",idMedico));
            nameValuePairs.add(new BasicNameValuePair("idLocacion",idLocacion));
            nameValuePairs.add(new BasicNameValuePair("idProc",idProc));
            nameValuePairs.add(new BasicNameValuePair("dateInicio",dateInicio));
            nameValuePairs.add(new BasicNameValuePair("dateFinal",dateFinal));
            nameValuePairs.add(new BasicNameValuePair("motivo",motivo));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }

    public String getID(String email)
    {
        String xmlString = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.mangostatecnologia.com/medicos_test/services/ws_version/getID_Android.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("email",email));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            xmlString = EntityUtils.toString(entity, "UTF-8");

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        return xmlString;
    }
}