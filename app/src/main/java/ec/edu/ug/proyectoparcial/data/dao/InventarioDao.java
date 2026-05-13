package ec.edu.ug.proyectoparcial.data.dao;

public class InventarioDao {

    private int id;
    private String nombre;
    private String categoria;
    private int cantidad;
    private String ubicacion;
    private String observacion;
    private String fecha_registro;



    public InventarioDao (){};

    public InventarioDao(int id, String nombre, String categoria, int cantidad, String ubicacion, String observacion, String fecha_registro){
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.ubicacion = ubicacion;
        this.observacion = observacion;
        this.fecha_registro = fecha_registro;
    }

    public int getId(){
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCantidad() {
        return cantidad;
    }
    public String getUbicacion() {
        return ubicacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }
}
