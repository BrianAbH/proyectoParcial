package ec.edu.ug.proyectoparcial.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ec.edu.ug.proyectoparcial.DetalleActivity;
import ec.edu.ug.proyectoparcial.R;
import ec.edu.ug.proyectoparcial.data.dao.InventarioDao;

public class listaAdapter extends RecyclerView.Adapter<listaAdapter.Holder> {

    ArrayList<InventarioDao> listInventario;
    ArrayList<InventarioDao> listaFiltrada;

    public listaAdapter(ArrayList<InventarioDao> listInventario){
        this.listInventario = listInventario;
        this.listaFiltrada = new ArrayList<>(listInventario);
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listaAdapter.Holder holder, int position) {
        InventarioDao inventario = listaFiltrada.get(position);
        holder.tvTitulo.setText(inventario.getNombre());
        holder.tvDescripcion.setText(inventario.getCategoria());
        holder.itemView.setOnClickListener(v->{
            Context contexto = v.getContext();
            Intent iDetalle = new Intent(contexto, DetalleActivity.class);
            iDetalle.putExtra("ID",inventario.getId());
            contexto.startActivity(iDetalle);
        });
    }

    @Override
    public int getItemCount() {
        return listaFiltrada.size();
    }


    public void filtrarLista(ArrayList<InventarioDao> nuevaLista) {
        listaFiltrada.clear();
        listaFiltrada.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder{

        TextView tvTitulo,tvDescripcion;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}
