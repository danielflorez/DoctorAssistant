package com.mangostatecnologia.doctorassistant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Daniel on 11/24/14.
 */
public class crearCita extends Activity
{
    private static final String TAG = "DoctorAssistant";
    private Button mGrabarButton;
    private Button mSelPacButton;
    private Button mSelProcButton;
    private Button mIniArrButton;
    private Button mIniAbaButton;
    private Button mFinArrButton;
    private Button mFinAbaButton;
    private Button mResultadoCitaButton;
    private EditText mDesc;
    private TextView mHoraInicioText;
    private TextView mHoraFinText;
    private int mHoraIni;
    private int mMinIni;
    private int mHoraFin;
    private int mMinFin;
    private int mPos;
    private Calendar mDay;
    private String mIdConsul;
    private citaCompleta mCitaC;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_cita);
        final Bundle b = getIntent().getExtras();
        mPos = b.getInt("posicion");
        mIdConsul = b.getString("idConsul");
        final GlobalClass globalVariable = (GlobalClass) this.getApplicationContext();
        mCitaC = globalVariable.getmCitasCompletas().get(mPos);
        mDay = mCitaC.getHora();
        mHoraIni = mCitaC.getHora().get(Calendar.HOUR_OF_DAY);
        mMinIni = mCitaC.getHora().get(Calendar.MINUTE);
        if (mMinIni == 0)
        {
            mMinFin = 30;
            mHoraFin = mHoraIni;
        }
        else
        {
            mMinFin = 0;
            mHoraFin = mHoraIni + 1;
        }
        String horaInicio;
        String horaFin;
        if (mHoraIni < 10)
        {
            if (mMinIni < 10)
            {
                horaInicio = "Hora Inicio: 0"+mHoraIni+":0"+mMinIni;
            }
            else
            {
                horaInicio = "Hora Inicio: 0"+mHoraIni+":"+mMinIni;
            }
        }
        else
        {
            if (mMinIni < 10)
            {
                horaInicio = "Hora Inicio: "+mHoraIni+":0"+mMinIni;
            }
            else
            {
                horaInicio = "Hora Inicio: "+mHoraIni+":"+mMinIni;
            }
        }
        if (mHoraFin < 10)
        {
            if (mMinFin < 10)
            {
                horaFin = "Hora Fin: 0"+mHoraFin+":0"+mMinFin;
            }
            else
            {
                horaFin = "Hora Fin: 0"+mHoraFin+":"+mMinFin;
            }
        }
        else
        {
            if (mMinFin < 10)
            {
                horaFin = "Hora Fin: "+mHoraFin+":0"+mMinFin;
            }
            else
            {
                horaFin = "Hora Fin: "+mHoraFin+":"+mMinFin;
            }
        }
        mHoraInicioText = (TextView) findViewById(R.id.cCitaHoraInicioText);
        mHoraInicioText.setText(horaInicio);
        mHoraFinText = (TextView) findViewById(R.id.cCitaHoraFinText);
        mHoraFinText.setText(horaFin);
        mDesc = (EditText) findViewById(R.id.cCitaDesText);
        mResultadoCitaButton = (Button) findViewById(R.id.cCitaResultado);
        mResultadoCitaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iR = new Intent(getBaseContext(),resulCitaTabs.class);
                String n = "";
                String a = "";
                String ced = "";
                String dir = "";
                String tel = "";
                String em = "";
                String fn = "";
                String idE = "";
                if (mCitaC.getCita().getPaci() != null)
                {
                    paciente p = mCitaC.getCita().getPaci();
                    n = p.getNombre();
                    a = p.getApellido();
                    ced = p.getCedula();
                    dir = p.getDireccion();
                    tel = p.getTelefono();
                    em = p.getEmail();
                    fn = p.getFechaNacimiento();
                    idE = p.getIdEntidad();
                }
                iR.putExtra("nombre", n);
                iR.putExtra("apellido",a);
                iR.putExtra("cedula",ced);
                iR.putExtra("direccion",dir);
                iR.putExtra("telefono",tel);
                iR.putExtra("email",em);
                iR.putExtra("fechaNacimiento",fn);
                iR.putExtra("idEntidad",idE);
                startActivity(iR);
            }
        });
        mGrabarButton = (Button) findViewById(R.id.cCitaGrabar);
        mGrabarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final GlobalClass globalVariable = (GlobalClass) getApplicationContext();
                Boolean valid = true;
                String idCita = mCitaC.getCita().getIdCita();
                String idPaciente = null;
                String idEntidad = null;
                String idMedico = globalVariable.getMid();
                String idLocacion = mIdConsul;
                String idProc = null;
                String dateInicio = null;
                String dateFinal = null;
                String motivo = mDesc.getText().toString();
                String result = null;
                Calendar inicio = mDay;
                Calendar fin = mDay;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                inicio.set(Calendar.HOUR_OF_DAY, mHoraIni);
                inicio.set(Calendar.MINUTE, mMinIni);
                Date iniDate = inicio.getTime();
                dateInicio = sdf.format(iniDate);
                fin.set(Calendar.HOUR_OF_DAY, mHoraFin);
                fin.set(Calendar.MINUTE, mMinFin);
                Date finDate = fin.getTime();
                dateFinal = sdf1.format(finDate);
                mCitaC.getCita().setDesc(motivo);
                if (mCitaC.getCita().getPaci() != null)
                {
                    idPaciente = mCitaC.getCita().getPaci().getCedula();
                    idEntidad = mCitaC.getCita().getPaci().getIdEntidad();
                }
                if (mCitaC.getCita().getProc() != null)
                {
                    idProc = mCitaC.getCita().getProc().getIdProc();
                }
                if ((motivo.equals(""))&&(idPaciente ==null))
                {
                    valid = false;
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearCita.this);
                    alertDialogBuilder.setTitle("Alerta")
                            .setMessage("Debe llenar el campo descripcion o seleccionar un paciente")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                ArrayList<cita> citas = globalVariable.getmCitas();
                for (int k = 0;k < citas.size();k ++)
                {
                    if (idCita == null)
                    {
                        idCita = "NC";
                    }
                    if (!(idCita.equals(citas.get(k).getIdCita())))
                    {
                        String c = citas.get(k).getFechaInicio();
                        String m = citas.get(k).getFechaFin();
                        Calendar caleInicio = Calendar.getInstance();
                        Calendar caleFin = Calendar.getInstance();
                        Date inicioCita = new Date();
                        Date finCita = new Date();
                        SimpleDateFormat formatCita = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            inicioCita = formatCita.parse(c);
                            finCita = formatCita.parse(m);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (inicioCita.before(iniDate) || inicioCita.equals(iniDate)) {
                            if ((iniDate.before(finCita)) && !(iniDate.equals(finCita))) {
                                valid = false;
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearCita.this);
                                alertDialogBuilder.setTitle("Alerta")
                                        .setMessage("La hora de la cita se cruza con otra cita.")
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                break;
                            }
                        }
                        if ((finDate.after(inicioCita)) && !(finDate.equals(inicioCita))) {
                            if ((finDate.before(finCita)) || finDate.equals(finCita)) {
                                valid = false;
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearCita.this);
                                alertDialogBuilder.setTitle("Alerta")
                                        .setMessage("La hora de la cita se cruza con otra cita.")
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                break;
                            }
                        }
                        if (iniDate.before(inicioCita) || iniDate.equals(inicioCita)) {
                            if ((finDate.after(finCita)) || finDate.equals(finCita)) {
                                valid = false;
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(crearCita.this);
                                alertDialogBuilder.setTitle("Alerta")
                                        .setMessage("La hora de la cita se cruza con otra cita.")
                                        .setCancelable(false)
                                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                break;
                            }
                        }
                    }

                }

                if (valid)
                {
                    try {
                        result = new CrearCitaTask().execute(idCita, idPaciente, idEntidad, idMedico, idLocacion, idProc, dateInicio, dateFinal, motivo).get();
                    } catch (InterruptedException ioe) {
                        Log.e(TAG, "Failed to login", ioe);
                    } catch (ExecutionException ioe) {
                        Log.e(TAG, "Failed to login", ioe);
                    }
                    if (result.equals("OK"))
                    {

                        String fechaInicoCitas;
                        int month = mDay.get(Calendar.MONTH)+1;
                        int year = mDay.get(Calendar.YEAR);
                        int nextMonth = month+1;
                        int nextYear = year;
                        int prevMonth = month-1;
                        int prevYear = year;
                        if (prevMonth < 1)
                        {
                            prevMonth = 12;
                            prevYear = prevYear-1;
                        }
                        if (nextMonth < 12)
                        {
                            nextMonth = 1;
                            nextYear = nextYear+1;
                        }
                        if (prevMonth < 10)
                        {
                            fechaInicoCitas = String.valueOf(prevYear)+"0"+String.valueOf(prevMonth)+"23";
                        }
                        else
                        {
                            fechaInicoCitas = String.valueOf(prevYear)+String.valueOf(prevMonth)+"23";
                        }
                        String fechaFinCitas;
                        if (nextMonth < 10)
                        {
                            fechaFinCitas = String.valueOf(nextYear)+"0"+String.valueOf(nextMonth)+"06";
                        }
                        else
                        {
                            fechaFinCitas = String.valueOf(nextYear)+String.valueOf(nextMonth)+"06";
                        }
                        globalVariable.getCitas(mIdConsul,fechaInicoCitas,fechaFinCitas);
                        onBackPressed();
                    }
                }
            }
        });
        mSelPacButton = (Button) findViewById(R.id.cCitaSelPacButton);
        mSelPacButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent iR = new Intent(getBaseContext(),pacienteList.class);
                iR.putExtra("cita",true);
                startActivityForResult(iR, 1);
            }
        });
        mSelProcButton = (Button) findViewById(R.id.cCitaSelProcButton);
        mSelProcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iR = new Intent(getBaseContext(),procedimientoList.class);
                iR.putExtra("cita",true);
                startActivityForResult(iR, 2);
            }
        });
        mIniArrButton = (Button) findViewById(R.id.horaIniArrButton);
        mIniArrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (mMinIni == 30)
                {
                    if (mHoraIni+1 < 21)
                    {
                        mMinIni = 0;
                        mHoraIni = mHoraIni + 1;
                    }
                }
                else
                {
                    mMinIni = 30;
                }
                String horaInicio;
                if (mHoraIni < 10)
                {
                    if (mMinIni < 10)
                    {
                        horaInicio = "Hora Inicio: 0"+mHoraIni+":0"+mMinIni;
                    }
                    else
                    {
                        horaInicio = "Hora Inicio: 0"+mHoraIni+":"+mMinIni;
                    }
                }
                else
                {
                    if (mMinIni < 10)
                    {
                        horaInicio = "Hora Inicio: "+mHoraIni+":0"+mMinIni;
                    }
                    else
                    {
                        horaInicio = "Hora Inicio: "+mHoraIni+":"+mMinIni;
                    }
                }
                mHoraInicioText.setText(horaInicio);
            }
        });
        mIniAbaButton = (Button) findViewById(R.id.horaIniAbjButton);
        mIniAbaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMinIni == 0)
                {
                    if (mHoraIni-1 > 5)
                    {
                        mMinIni = 30;
                        mHoraIni = mHoraIni - 1;
                    }
                }
                else
                {
                    mMinIni = 0;
                }
                String horaInicio;
                if (mHoraIni < 10)
                {
                    if (mMinIni < 10)
                    {
                        horaInicio = "Hora Inicio: 0"+mHoraIni+":0"+mMinIni;
                    }
                    else
                    {
                        horaInicio = "Hora Inicio: 0"+mHoraIni+":"+mMinIni;
                    }
                }
                else
                {
                    if (mMinIni < 10)
                    {
                        horaInicio = "Hora Inicio: "+mHoraIni+":0"+mMinIni;
                    }
                    else
                    {
                        horaInicio = "Hora Inicio: "+mHoraIni+":"+mMinIni;
                    }
                }
                mHoraInicioText.setText(horaInicio);
            }
        });
        mFinArrButton = (Button) findViewById(R.id.horaFinArrButton);
        mFinArrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMinFin == 30)
                {
                    if (mHoraFin+1 < 21)
                    {
                        mMinFin = 0;
                        mHoraFin = mHoraFin + 1;
                    }
                }
                else
                {
                    mMinFin = 30;
                }
                String horaFin;
                if (mHoraFin < 10)
                {
                    if (mMinFin < 10)
                    {
                        horaFin = "Hora Fin: 0"+mHoraFin+":0"+mMinFin;
                    }
                    else
                    {
                        horaFin = "Hora Fin: 0"+mHoraFin+":"+mMinFin;
                    }
                }
                else
                {
                    if (mMinFin < 10)
                    {
                        horaFin = "Hora Fin: "+mHoraFin+":0"+mMinFin;
                    }
                    else
                    {
                        horaFin = "Hora Fin: "+mHoraFin+":"+mMinFin;
                    }
                }
                mHoraFinText.setText(horaFin);
            }
        });
        mFinAbaButton = (Button) findViewById(R.id.horaFinAbjButton);
        mFinAbaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMinFin == 0)
                {
                    if (mHoraFin-1 > 5)
                    {
                        mMinFin = 30;
                        mHoraFin = mHoraIni - 1;
                    }
                }
                else
                {
                    mMinFin = 0;
                }
                String horaFin;
                if (mHoraFin < 10)
                {
                    if (mMinFin < 10)
                    {
                        horaFin = "Hora Fin: 0"+mHoraFin+":0"+mMinFin;
                    }
                    else
                    {
                        horaFin = "Hora Fin: 0"+mHoraFin+":"+mMinFin;
                    }
                }
                else
                {
                    if (mMinFin < 10)
                    {
                        horaFin = "Hora Fin: "+mHoraFin+":0"+mMinFin;
                    }
                    else
                    {
                        horaFin = "Hora Fin: "+mHoraFin+":"+mMinFin;
                    }
                }
                mHoraFinText.setText(horaFin);
            }
        });
        if (mCitaC.getCita() != null)
        {
            String c = mCitaC.getCita().getFechaInicio();
            String m = mCitaC.getCita().getFechaFin();
            Calendar caleInicio = Calendar.getInstance();
            Calendar caleFin = Calendar.getInstance();
            Date inicioCita = new Date();
            Date finCita = new Date();
            SimpleDateFormat formatCita = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                inicioCita = formatCita.parse(c);
                finCita = formatCita.parse(m);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            caleInicio.setTime(inicioCita);
            caleFin.setTime(finCita);
            mHoraIni = caleInicio.get(Calendar.HOUR_OF_DAY);
            mMinIni = caleInicio.get(Calendar.MINUTE);
            mHoraFin = caleFin.get(Calendar.HOUR_OF_DAY);
            mMinFin = caleFin.get(Calendar.MINUTE);
            if (mHoraIni < 10)
            {
                if (mMinIni < 10)
                {
                    horaInicio = "Hora Inicio: 0"+mHoraIni+":0"+mMinIni;
                }
                else
                {
                    horaInicio = "Hora Inicio: 0"+mHoraIni+":"+mMinIni;
                }
            }
            else
            {
                if (mMinIni < 10)
                {
                    horaInicio = "Hora Inicio: "+mHoraIni+":0"+mMinIni;
                }
                else
                {
                    horaInicio = "Hora Inicio: "+mHoraIni+":"+mMinIni;
                }
            }
            if (mHoraFin < 10)
            {
                if (mMinFin < 10)
                {
                    horaFin = "Hora Fin: 0"+mHoraFin+":0"+mMinFin;
                }
                else
                {
                    horaFin = "Hora Fin: 0"+mHoraFin+":"+mMinFin;
                }
            }
            else
            {
                if (mMinFin < 10)
                {
                    horaFin = "Hora Fin: "+mHoraFin+":0"+mMinFin;
                }
                else
                {
                    horaFin = "Hora Fin: "+mHoraFin+":"+mMinFin;
                }
            }
            mHoraInicioText.setText(horaInicio);
            mHoraFinText.setText(horaFin);
            if (mCitaC.getCita().getDesc() != null)
            {
                mDesc.setText(mCitaC.getCita().getDesc());
            }
            if (mCitaC.getCita().getPaci() != null)
            {
                mSelPacButton.setText(String.format("%s %s", mCitaC.getCita().getPaci().getNombre(), mCitaC.getCita().getPaci().getApellido()));
            }
            if (mCitaC.getCita().getProc() != null)
            {
                mSelProcButton.setText(mCitaC.getCita().getProc().getNombre());
            }
        }
        else
        {
            mCitaC.setCita(new cita());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        final GlobalClass globalVariable = (GlobalClass) this.getApplicationContext();
        if (requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                String pacId = data.getStringExtra("idPac");
                ArrayList<paciente> pacArray = globalVariable.getmPacientes();
                for (int i = 0;i < pacArray.size();i ++)
                {
                    paciente pac = pacArray.get(i);
                    if (pac.getCedula().equals(pacId))
                    {
                        mCitaC.getCita().setPaci(pac);
                        mSelPacButton.setText(String.format("%s %s", mCitaC.getCita().getPaci().getNombre(), mCitaC.getCita().getPaci().getApellido()));
                        break;
                    }
                }
            }
        }
        else if (requestCode == 2)
        {
            if(resultCode == RESULT_OK)
            {
                String procId = data.getStringExtra("idProc");
                ArrayList<procedimiento> procArray = globalVariable.getmProcedimientos();
                for (int i = 0;i < procArray.size();i ++)
                {
                    procedimiento pro = procArray.get(i);
                    if (pro.getIdProc().equals(procId))
                    {
                        mCitaC.getCita().setProc(pro);
                        mSelProcButton.setText(mCitaC.getCita().getProc().getNombre());
                        break;
                    }
                }
            }
        }
    }

    private class CrearCitaTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... params){
            return new urlManager().grabarCita(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]);
        }
    }
}
