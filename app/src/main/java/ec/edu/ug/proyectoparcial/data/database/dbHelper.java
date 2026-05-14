package ec.edu.ug.proyectoparcial.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

import ec.edu.ug.proyectoparcial.data.dao.InventarioDao;

public class dbHelper extends SQLiteOpenHelper {

    private static String db_name = "inventarioUg.db";
    private static int version = 1;
    private static String table_inventario = "t_inventario";

    public dbHelper(Context context) {
        super(context, db_name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableInventario = "CREATE TABLE " + table_inventario + "("+
                                        " id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                        + " nombre text not null,"
                                        + " categoria text not null," +
                                        " cantidad INTEGER not null," +
                                        " ubicacion text not null," +
                                        " observacion text not null," +
                                        " fecha_registro text not null)";
        db.execSQL(createTableInventario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_inventario);
        onCreate(db);
    }

    public boolean add(InventarioDao item) {
        boolean estado;
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("nombre", item.getNombre());
            values.put("categoria", item.getCategoria());
            values.put("cantidad", item.getCantidad());
            values.put("ubicacion", item.getUbicacion());
            values.put("observacion", item.getObservacion());
            values.put("fecha_registro", item.getFecha_registro());
            db.insert(table_inventario, null, values);
            estado = true;
        } catch (Exception e) {
            Log.d("Errorrr",e.getMessage());
            estado = false;
        } finally {
            db.close();
        }
        return estado;
    }


    public ArrayList<InventarioDao> getAllItem(){
        ArrayList<InventarioDao> items = new ArrayList<>();

        String querySelect = "SELECT * FROM " + table_inventario;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(querySelect, null);

        if (cursor.moveToFirst()){
            do {
                InventarioDao item = new InventarioDao();

                item.setId(cursor.getInt(0));
                item.setNombre(cursor.getString(1));
                item.setCategoria(cursor.getString(2));
                item.setCantidad(cursor.getInt(3));
                item.setUbicacion(cursor.getString(4));
                item.setObservacion(cursor.getString(5));
                item.setFecha_registro(cursor.getString(6));

                items.add(item);
            }while (cursor.moveToNext());
        }
        return items;
    }








}
