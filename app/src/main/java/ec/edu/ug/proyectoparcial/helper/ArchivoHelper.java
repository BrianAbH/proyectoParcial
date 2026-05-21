package ec.edu.ug.proyectoparcial.helper;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ec.edu.ug.proyectoparcial.data.dao.InventarioDao;

public class ArchivoHelper {

    // Texto del reporte
    public static String generarReporte(String nombreResponsable, ArrayList<InventarioDao> listaItems){

        StringBuilder reporte = new StringBuilder();

        // Fecha actual
        String fechaActual = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm",
                Locale.getDefault()
        ).format(new Date());

        // Encabezado
        reporte.append("REPORTE INVENTARIO UG\n\n");

        reporte.append("Responsable: ")
                .append(nombreResponsable)
                .append("\n");

        reporte.append("Fecha: ")
                .append(fechaActual)
                .append("\n\n");

        reporte.append("Total elementos: ")
                .append(listaItems.size())
                .append("\n\n");

        reporte.append("LISTADO\n\n");

        // Recorrer lista
        for(InventarioDao item : listaItems){

            reporte.append("Nombre: ")
                    .append(item.getNombre())
                    .append("\n");

            reporte.append("Categoría: ")
                    .append(item.getCategoria())
                    .append("\n");

            reporte.append("Cantidad: ")
                    .append(item.getCantidad())
                    .append("\n\n");
        }

        return reporte.toString();
    }

    // Guarda archivo interno
    public static void guardarArchivoInterno(Context context, String contenido){

        try {
            FileOutputStream fos = context.openFileOutput("resumen_interno.txt", Context.MODE_PRIVATE);
            fos.write(contenido.getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Guarda archivo externo
    public static String guardarArchivoExterno(Context context, String contenido){
        try {
            File directorio = context.getExternalFilesDir(null);
            File archivo = new File(directorio, "reporte_inventario.txt");
            FileOutputStream fos = new FileOutputStream(archivo);
            byte[] bytes = contenido.getBytes();
            fos.write(bytes);
            fos.close();
            return archivo.getAbsolutePath();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}