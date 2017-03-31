package com.mangostatecnologia.doctorassistant;

import android.app.Application;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 10/15/14.
 */
public class GlobalClass extends Application {
    private static final String TAG = "DoctorAssistant";
    private String email;
    private String mid;
    private consultorioBaseAdapter mcAdap = null;
    private pacienteBaseAdapter mpAdap = null;
    private entidadBaseAdapter meAdap = null;
    private procedimientoBaseAdapter mprAdap = null;
    private precioProcBaseAdapter mPPAdap = null;
    private dayCustomGrid mDAdap = null;
    private ArrayList<consultorio> mConsultorios  = new ArrayList<consultorio>();
    private ArrayList<entidad> mEntidades  = new ArrayList<entidad>();
    private ArrayList<paciente> mPacientes  = new ArrayList<paciente>();
    private ArrayList<precioProcedimiento> mPrecioProcedmiento = new ArrayList<precioProcedimiento>();
    private ArrayList<procedimiento> mProcedimientos = new ArrayList<procedimiento>();
    private ArrayList<cita> mCitas = new ArrayList<cita>();
    private ArrayList<citaCompleta> mCitasCompletas = new ArrayList<citaCompleta>();
    private ArrayList<diagnostico> mDiagnosticos = new ArrayList<diagnostico>();
    private ArrayList<medicamento> mMedicamentos = new ArrayList<medicamento>();

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String aEmail)
    {
        email = aEmail;
    }

    public entidadBaseAdapter getMeAdap() {
        return meAdap;
    }

    public void setMeAdap(entidadBaseAdapter meAdap) {
        this.meAdap = meAdap;
    }

    public ArrayList<entidad> getmEntidades() {
        return mEntidades;
    }

    public void setmEntidades(ArrayList<entidad> mEntidades) {
        this.mEntidades = mEntidades;
    }

    public ArrayList<paciente> getmPacientes() {
        return mPacientes;
    }

    public void setmPacientes(ArrayList<paciente> mPacientes) {
        this.mPacientes = mPacientes;
    }

    public pacienteBaseAdapter getMpAdap() {
        return mpAdap;
    }

    public void setMpAdap(pacienteBaseAdapter mpAdap) {
        this.mpAdap = mpAdap;
    }

    public dayCustomGrid getmDAdap() {
        return mDAdap;
    }

    public void setmDAdap(dayCustomGrid mDAdap) {
        this.mDAdap = mDAdap;
    }

    public consultorioBaseAdapter getMcAdap() {
        return mcAdap;
    }

    public void setMcAdap(consultorioBaseAdapter mcAdap) {
        this.mcAdap = mcAdap;
    }

    public procedimientoBaseAdapter getMprAdap() {
        return mprAdap;
    }

    public void setMprAdap(procedimientoBaseAdapter mprAdap) {
        this.mprAdap = mprAdap;
    }

    public precioProcBaseAdapter getmPPAdap() {
        return mPPAdap;
    }

    public void setmPPAdap(precioProcBaseAdapter mPPAdap) {
        this.mPPAdap = mPPAdap;
    }

    public String getMid()
    {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public ArrayList<consultorio> getmConsultorios()
    {
        return mConsultorios;
    }

    public void setmConsultorios(ArrayList<consultorio> mConsultorios)
    {
        this.mConsultorios = mConsultorios;
    }

    public ArrayList<procedimiento> getmProcedimientos() {
        return mProcedimientos;
    }

    public void setmProcedimientos(ArrayList<procedimiento> mProcedimientos) {
        this.mProcedimientos = mProcedimientos;
    }

    public ArrayList<precioProcedimiento> getmPrecioProcedmiento() {
        return mPrecioProcedmiento;
    }

    public void setmPrecioProcedmiento(ArrayList<precioProcedimiento> mPrecioProcedmiento) {
        this.mPrecioProcedmiento = mPrecioProcedmiento;
    }

    public ArrayList<cita> getmCitas() {
        return mCitas;
    }

    public void setmCitas(ArrayList<cita> mCitas) {
        this.mCitas = mCitas;
    }

    public ArrayList<citaCompleta> getmCitasCompletas()
    {
        return mCitasCompletas;
    }

    public void setmCitasCompletas(ArrayList<citaCompleta> mCitasCompletas)
    {
        this.mCitasCompletas = mCitasCompletas;
    }

    public ArrayList<diagnostico> getmDiagnosticos() {
        return mDiagnosticos;
    }

    public void setmDiagnosticos(ArrayList<diagnostico> mDiagnosticos) {
        this.mDiagnosticos = mDiagnosticos;
    }

    public ArrayList<medicamento> getmMedicamentos() {
        return mMedicamentos;
    }

    public void setmMedicamentos(ArrayList<medicamento> mMedicamentos) {
        this.mMedicamentos = mMedicamentos;
    }

    public void getConsultorios()
    {
        String result = null;
        try {
            result = new jsonConsultorios().execute(mid).get();
        } catch (InterruptedException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        } catch (ExecutionException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        }
        if (result != null)
        {
            mConsultorios = new ArrayList<consultorio>();
            try {
                JSONObject consul = new JSONObject(result);
                JSONArray consultorios = consul.getJSONArray("Consultorios");
                for (int i =0;i < consultorios.length();i++)
                {
                    JSONObject c = consultorios.getJSONObject(i);
                    consultorio con = new consultorio();
                    con.setIdLoc(c.getString("idLoc"));
                    con.setDescr(c.getString("nombre"));
                    con.setDireccion(c.getString("direccion"));
                    con.setTelefono(c.getString("telefono"));
                    mConsultorios.add(con);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getPacientes()
    {
        String result = null;
        try {
            result = new jsonPacientes().execute(mid).get();
        } catch (InterruptedException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        } catch (ExecutionException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        }
        if (result != null)
        {
            mPacientes = new ArrayList<paciente>();
            try {
                JSONObject paci = new JSONObject(result);
                JSONArray pacientes = paci.getJSONArray("Pacientes");
                for (int i =0;i < pacientes.length();i++)
                {
                    JSONObject p = pacientes.getJSONObject(i);
                    paciente pac = new paciente();

                    pac.setNombre(p.getString("nombre"));
                    pac.setApellido(p.getString("apellidos"));
                    pac.setFechaNacimiento(p.getString("nacimiento"));
                    pac.setIdEntidad(p.getString("entidad"));
                    pac.setCedula(p.getString("cedula"));
                    pac.setEmail(p.getString("email"));
                    pac.setDireccion(p.getString("direccion"));
                    pac.setTelefono(p.getString("telefono"));
                    mPacientes.add(pac);
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getEntidades()
    {
        String result = null;
        try {
            result = new entidadesTask().execute(mid).get();
        } catch (InterruptedException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        } catch (ExecutionException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        }
        if (result != null)
        {
            mEntidades = new ArrayList<entidad>();
            try {
                JSONObject ent = new JSONObject(result);
                JSONArray entidades = ent.getJSONArray("Entidades");
                for (int i =0;i < entidades.length();i++)
                {
                    JSONObject c = entidades.getJSONObject(i);
                    entidad e = new entidad();
                    e.setIdEnt(c.getString("id"));
                    e.setCodigo(c.getString("codigo"));
                    e.setNombre(c.getString("nombre"));
                    e.setIdMedico(c.getString("idMedico"));
                    if (e.getIdMedico().equals(mid))
                    {
                        e.setCheck(true);
                    }else {
                        e.setCheck(false);
                    }
                    mEntidades.add(e);
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getProcedmientos()
    {
        String result = null;
        try {
            result = new procedimientosTask().execute(mid).get();
        } catch (InterruptedException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        } catch (ExecutionException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        }
        if (result != null)
        {
            mProcedimientos = new ArrayList<procedimiento>();
            try {
                JSONObject pro = new JSONObject(result);
                JSONArray procedimientos = pro.getJSONArray("Procedimientos");
                for (int i =0;i < procedimientos.length();i++)
                {
                    JSONObject c = procedimientos.getJSONObject(i);
                    procedimiento p = new procedimiento();
                    p.setIdProc(c.getString("id"));
                    p.setNombre(c.getString("nombre"));
                    mProcedimientos.add(p);
                }
                Log.i("","");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getPreciosXProcedmiento()
    {
        String result = null;
        try {
            result = new preciosXProcedimientosTask().execute(mid).get();
        } catch (InterruptedException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        } catch (ExecutionException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        }
        if (result != null)
        {
            mPrecioProcedmiento = new ArrayList<precioProcedimiento>();
            try {
                JSONObject pro = new JSONObject(result);
                JSONArray procedimientos = pro.getJSONArray("Procedimientos");
                for (int i =0;i < procedimientos.length();i++)
                {
                    JSONObject c = procedimientos.getJSONObject(i);
                    precioProcedimiento p = new precioProcedimiento();
                    p.setIdProc(c.getString("id"));
                    p.setIdEnt(c.getString("idEnt"));
                    p.setPrecio(c.getString("precio"));
                    mPrecioProcedmiento.add(p);
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getCitas(String idLoc, String fechaIni, String fechafin)
    {
        String result = null;
        try {
            result = new citasTask().execute(idLoc, fechaIni, fechafin).get();
        } catch (InterruptedException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        } catch (ExecutionException ioe) {
            Log.e(TAG, "Failed to login", ioe);
        }
        if (result != null)
        {
            mCitas = new ArrayList<cita>();
            try {
                JSONObject cit = new JSONObject(result);
                JSONArray citas = cit.getJSONArray("Citas");
                for (int i =0;i < citas.length();i++)
                {
                    JSONObject c = citas.getJSONObject(i);
                    cita ci = new cita();
                    ci.setIdCita(c.getString("id"));
                    ci.setDesc(c.getString("Motivo"));
                    ci.setFechaInicio(c.getString("datetime"));
                    ci.setFechaFin(c.getString("datetimefin"));
                    String idPac = c.getString("idPaciente");
                    for (int j = 0;j < mPacientes.size();j++)
                    {
                        paciente p = mPacientes.get(j);
                        if (p.getCedula().equals(idPac))
                        {
                            ci.setPaci(p);
                            break;
                        }
                    }
                    String idEnt = c.getString("idEntidad");
                    for (int k = 0;k < mEntidades.size();k ++)
                    {
                        entidad e = mEntidades.get(k);
                        if (e.getIdEnt().equals(idEnt))
                        {
                            ci.setEnti(e);
                            break;
                        }
                    }
                    String idCon = c.getString("idLocacionXMedico");
                    for (int l = 0;l < mConsultorios.size();l++)
                    {
                        consultorio con = mConsultorios.get(l);
                        if (con.getIdLoc().equals(idCon))
                        {
                            ci.setConsul(con);
                            break;
                        }
                    }
                    String idProc = c.getString("idTipoProcedimiento");
                    for (int m = 0;m < mProcedimientos.size();m ++)
                    {
                        procedimiento pro = mProcedimientos.get(m);
                        if (pro.getIdProc().equals(idProc))
                        {
                            ci.setProc(pro);
                            break;
                        }
                    }
                    mCitas.add(ci);
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void getDianosticos()
    {
        String result;
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.diagnosticos);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            result = new String(b);
        } catch (Exception e) {
            // e.printStackTrace();
            result = "Error: can't show file.";
        }
        try {
            JSONObject diag = new JSONObject(result);
            JSONArray diagnosticos = diag.getJSONArray("Diagnosticos");
            for (int i = 0; i < diagnosticos.length(); i++) {
                JSONObject c = diagnosticos.getJSONObject(i);
                diagnostico d = new diagnostico();
                d.setCodigo(c.getString("cod"));
                d.setDesc(c.getString("desc"));
                mDiagnosticos.add(d);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void getMedicamentos()
    {
        String result;
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.medicamentos);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            result = new String(b);
        } catch (Exception e) {
            // e.printStackTrace();
            result = "Error: can't show file.";
        }
        try {
            JSONObject medi = new JSONObject(result);
            JSONArray medicamentos = medi.getJSONArray("Medicamentos");
            for (int i = 0; i < medicamentos.length(); i++) {
                JSONObject m = medicamentos.getJSONObject(i);
                medicamento med = new medicamento();
                med.setCodigo(m.getString("Codigo"));
                med.setNombre(m.getString("Nombre"));
                med.setCompActivo(m.getString("DescAtc"));
                mMedicamentos.add(med);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Log.e(TAG,"");
    }

    public void getMemberID()
    {
        String result = null;
        try {
            result = new getMemberID().execute(email).get();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        if (result != null)
        {
            mid = result;
        }
    }

    private class jsonConsultorios extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return new urlManager().consultorios(params[0]);
        }
    }

    private class jsonPacientes extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return new urlManager().pacientes(params[0]);
        }
    }

    private class entidadesTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return new urlManager().entidades(params[0]);
        }
    }

    private class procedimientosTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return new urlManager().procedimentos(params[0]);
        }
    }

    private class preciosXProcedimientosTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return new urlManager().preciosXProcedimentos(params[0]);
        }
    }

    private class citasTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return new urlManager().getCitas(params[0],params[1],params[2]);
        }
    }

    private class getMemberID extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            return new urlManager().getID(params[0]);
        }
    }
}
