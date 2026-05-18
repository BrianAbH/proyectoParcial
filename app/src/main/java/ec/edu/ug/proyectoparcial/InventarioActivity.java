package ec.edu.ug.proyectoparcial;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ec.edu.ug.proyectoparcial.adaptadores.listaAdapter;
import ec.edu.ug.proyectoparcial.data.dao.InventarioDao;
import ec.edu.ug.proyectoparcial.data.database.dbHelper;

public class InventarioActivity extends AppCompatActivity {

    private dbHelper db;
    private RecyclerView recyclerElementos;
    private Button btnAgregar;
    private ArrayList<InventarioDao> lista;
    private listaAdapter adaptador;
    private EditText etBuscar;
    private ImageButton ivVolver;
    private TextView tvElementos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_inventario), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new dbHelper(this);

        iniciarViews();
        configurarAdaptador();
        eventos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarAdaptador();
    }

    private void iniciarViews(){
        recyclerElementos = findViewById(R.id.recyclerElementos);
        btnAgregar = findViewById(R.id.btnAgregar);
        etBuscar = findViewById(R.id.etBuscar);
        ivVolver = findViewById(R.id.ivVolver);
        tvElementos = findViewById(R.id.tvElementos);

    }

    private void configurarAdaptador(){
        lista =  db.getAllItem();
        tvElementos.setText(getString(R.string.txt_elementos, lista.size()));
        recyclerElementos.setLayoutManager(
                new LinearLayoutManager(this)
        );
        adaptador = new listaAdapter(lista);
        recyclerElementos.setAdapter(adaptador);
    }

    private void eventos(){
        btnAgregar.setOnClickListener(v->{
            Intent iAgregar = new Intent(this, RegistrarActivity.class);
            startActivity(iAgregar);
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        ivVolver.setOnClickListener(v->{
            Intent iVolver = new Intent(this, MainActivity.class);
            startActivity(iVolver);
        });

    }

    private void filtrar(String texto) {
        ArrayList<InventarioDao> listaFiltrada = new ArrayList<>();
        for (InventarioDao item : lista) {
            if (item.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(item);
            }
        }
        adaptador.filtrarLista(listaFiltrada);

    }
}
