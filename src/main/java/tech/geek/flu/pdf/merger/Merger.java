package tech.geek.flu.pdf.merger;

public class Merger {
    private String name;
    private int size;
    private double token;

    public Merger(String nombre, int tamanio, double llave) {
        this.name = nombre;
        this.size = tamanio;
        this.token = llave;
    }

    public static void main(String[] args) {
        Merger merger = new Merger("m1", 23, 23.34); // esto es una instancia
        Merger merger01 = new Merger("m2", 23, 23.34); // instancia dos
    }

}
