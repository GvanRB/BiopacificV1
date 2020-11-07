package ivan.grb.biopacificv1.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import ivan.grb.biopacificv1.R;
import ivan.grb.biopacificv1.ServiciosMascota;
import ivan.grb.biopacificv1.fragments.MascotaServicios;
import ivan.grb.biopacificv1.models.ListadoMascotasPorCliente;

public class Adapterlistmascotasxcliente extends RecyclerView.Adapter<Adapterlistmascotasxcliente.ViewHolder> {
    public static String idmascota;
    private Context context;
    ArrayList<ListadoMascotasPorCliente> listadoMascotasPorClientes;


    public Adapterlistmascotasxcliente(Context context, ArrayList<ListadoMascotasPorCliente> listadoMascotasPorClientes) {
        this.context = context;
        this.listadoMascotasPorClientes = listadoMascotasPorClientes;
    }

    @NonNull
    @Override
    public Adapterlistmascotasxcliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mascotas,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapterlistmascotasxcliente.ViewHolder holder, int position) {
        final ListadoMascotasPorCliente itemsmascotas=listadoMascotasPorClientes.get(position);
        holder.idMascota.setText(itemsmascotas.getIdMascota()+"");
        holder.nombre.setText(itemsmascotas.getNombre()+"");
        holder.genero.setText(itemsmascotas.getGenero()+"");
        holder.raza.setText(itemsmascotas.getRaza()+"");
        holder.especie.setText(itemsmascotas.getEspecie()+"");
        holder.cardMascotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServiciosMascota.class);
                idmascota = itemsmascotas.getIdMascota();
                context.startActivity(intent);
                //Toast.makeText(context, itemsmascotas.getIdMascota(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listadoMascotasPorClientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,idMascota,genero,raza,especie;
        CardView cardMascotas;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idMascota=itemView.findViewById(R.id.idMascota);
            nombre=itemView.findViewById(R.id.nombre);
            genero=itemView.findViewById(R.id.genero);
            raza=itemView.findViewById(R.id.raza);
            especie=itemView.findViewById(R.id.especie);
            cardMascotas=itemView.findViewById(R.id.cardMascotas);
        }
    }
}
