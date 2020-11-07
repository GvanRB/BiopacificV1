package ivan.grb.biopacificv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ivan.grb.biopacificv1.adapters.AdapterServiciosXmascota;
import ivan.grb.biopacificv1.adapters.Adapterlistmascotasxcliente;
import ivan.grb.biopacificv1.models.ServiciosXmascotas;
import ivan.grb.biopacificv1.services.Services;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiciosMascota extends AppCompatActivity {
    private RecyclerView Recycler;
    private AdapterServiciosXmascota adapter;
    ArrayList<ServiciosXmascotas> listServiciosXmasc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_mascota);
        //idmascota = getArguments().getString("MascotasServicios");
        //idmascota = "MA01";
        //Toast.makeText(this, "MENSAGGE"+idmascota, Toast.LENGTH_SHORT).show();
        Recycler = findViewById(R.id.recyMascotas);
        Recycler.setLayoutManager(new LinearLayoutManager(this));
        ListServiciosMascotas(Adapterlistmascotasxcliente.idmascota);
    }

    private void ListServiciosMascotas(final String idmascota) {
        //Toast.makeText(this, "MENSAGGE"+idmascota, Toast.LENGTH_SHORT).show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.14:8090/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Services services =  retrofit.create(Services.class);
        Call<List<ServiciosXmascotas>> call = services.getlistadoserviciosmasc(idmascota);
        call.enqueue(new Callback<List<ServiciosXmascotas>>() {
            @Override
            public void onResponse(Call<List<ServiciosXmascotas>> call, Response<List<ServiciosXmascotas>> response) {
                listServiciosXmasc = new ArrayList<>(response.body());
                Toast.makeText(ServiciosMascota.this, "Revise los estados de sus servicios", Toast.LENGTH_SHORT).show();
                adapter = new AdapterServiciosXmascota(ServiciosMascota.this,listServiciosXmasc);
                adapter.notifyDataSetChanged();
                Recycler.setAdapter(adapter);

            }
            @Override
            public void onFailure(Call<List<ServiciosXmascotas>> call, Throwable t) {
                Toast.makeText(ServiciosMascota.this, "Falied: "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
}