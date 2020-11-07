package ivan.grb.biopacificv1;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ivan.grb.biopacificv1.models.Agregarmascota;
import ivan.grb.biopacificv1.models.ListadoMascotasPorCliente;
import ivan.grb.biopacificv1.models.Perfiles;
import ivan.grb.biopacificv1.models.Solicitarservicio;
import ivan.grb.biopacificv1.models.Usuarios;
import ivan.grb.biopacificv1.services.Services;
import ivan.grb.biopacificv1.views.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Servicio extends AppCompatActivity {
        private Button btnSolicitar;
        private TextView txtCodPerfil, txtCodMasc,txtCostoPerfil;
        Spinner comboPerfiles, comboMascotas;
        public static String formattedDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio);
        txtCodPerfil=findViewById(R.id.txtCodPerfil);
        txtCodMasc=findViewById(R.id.txtCodMasc);
        txtCostoPerfil=findViewById(R.id.txtCostoPerfil);
        btnSolicitar = findViewById(R.id.btnSolicitar);
        comboPerfiles = findViewById(R.id.ComboPerfiles);
        comboMascotas = findViewById(R.id.comboMascotas);
        Usuarios usuario = new Usuarios();
        usuario.setNombreUsuario(Login.email);
        usuario.setContraseña(Login.pass);
        spinnerMascotas(usuario);
        spinnerPerfiles();
        btnSolicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RegistrarServicio(saveservicio());
            }
        });
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());

    }


    private void spinnerPerfiles() {
        final List<Perfiles> perfilesList = new ArrayList<>();
        final ArrayAdapter<Perfiles> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, perfilesList);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<List<Perfiles>> call = services.getListPerfiles();
        call.enqueue(new Callback<List<Perfiles>>() {
            @Override
            public void onResponse(Call<List<Perfiles>> call, Response<List<Perfiles>> response) {
                if(response.isSuccessful()){
                    for(final Perfiles listPer : response.body()){
                        final String idPerfil = listPer.getIdPerfil();
                        final String nombre = listPer.getNombre();
                        final Integer costo = listPer.getCosto();
                        final Perfiles perfiles = new Perfiles(idPerfil,nombre,costo);
                        perfilesList.add(perfiles);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        comboPerfiles.setAdapter(adapter);

                    comboPerfiles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            txtCostoPerfil.setText(perfilesList.get(position).getCosto().toString());
                            txtCodPerfil.setText(perfilesList.get(position).getIdPerfil());
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    }
                }

            }
            @Override
            public void onFailure(Call<List<Perfiles>> call, Throwable t) {
                Toast.makeText(Servicio.this, "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void spinnerMascotas(final Usuarios usuarios) {
        final List<ListadoMascotasPorCliente> mascotasList = new ArrayList<>();
        final ArrayAdapter<ListadoMascotasPorCliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mascotasList);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<List<ListadoMascotasPorCliente>> call = services.postListMascXCli(usuarios);
        call.enqueue(new Callback<List<ListadoMascotasPorCliente>>() {
            @Override
            public void onResponse(Call<List<ListadoMascotasPorCliente>> call, Response<List<ListadoMascotasPorCliente>> response) {
                if(response.isSuccessful()){
                    for(ListadoMascotasPorCliente listPer : response.body()){
                        String idmascota = listPer.getIdMascota();
                        String nombre = listPer.getNombre();
                        String genero = listPer.getGenero();
                        String raza = listPer.getRaza();
                        String especie = listPer.getEspecie();
                        ListadoMascotasPorCliente mascotas = new ListadoMascotasPorCliente(idmascota,nombre,genero,raza,especie);
                        mascotasList.add(mascotas);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                        comboMascotas.setAdapter(adapter);

                        comboMascotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                          @Override
                          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                             txtCodMasc.setText(mascotasList.get(position).getIdMascota());
                      }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                  });
                 }
                }
            }
            @Override
            public void onFailure(Call<List<ListadoMascotasPorCliente>> call, Throwable t) {
                Toast.makeText(Servicio.this, "Falied: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    private void RegistrarServicio(final Solicitarservicio solicitarservicio) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<Solicitarservicio> call = services.postRegistrarServicio(solicitarservicio);
        call.enqueue(new Callback<Solicitarservicio>() {
            @Override
            public void onResponse(Call<Solicitarservicio> call, Response<Solicitarservicio> response) {
                if(response.isSuccessful()){
                    Toast.makeText(Servicio.this,"¡Solicitado!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Servicio.this,"Siga solicitando servicios", Toast.LENGTH_SHORT).show();
                     /*Intent intent = new Intent(Servicio.this, MenuPrincipal.class);
                     startActivity(intent);*/
                }
            }
            @Override
            public void onFailure(Call<Solicitarservicio> call, Throwable t) {
                Toast.makeText(Servicio.this, "Falied: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public Solicitarservicio saveservicio(){
        Solicitarservicio addServicio = new Solicitarservicio();
        addServicio.setIdPerfil(txtCodPerfil.getText().toString());
        addServicio.setIdCliente(Login.idCliente);
        addServicio.setIdMascota(txtCodMasc.getText().toString());
        //Integer costo = Integer.parseInt(txtCostoPerfil.getText().toString());
        addServicio.setCostoServicio(Integer.parseInt(txtCostoPerfil.getText().toString()));
        addServicio.setFechaRegistro(formattedDate);
        //Toast.makeText(this, txtCostoPerfil.getText().toString(), Toast.LENGTH_SHORT).show();
        return addServicio;

    }
}