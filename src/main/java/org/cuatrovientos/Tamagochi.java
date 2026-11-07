package org.cuatrovientos;
import java.util.Random;

public class Tamagochi implements Runnable {

    private static long timesTampInicio = System.currentTimeMillis();
    private static int autoid;

    private  int id;
    private String nombre;
    private int ritmo;
    private Random random;
    private Estado estado;

    public Tamagochi(String nombre) {
        this.nombre = nombre;
        this.estado = Estado.PARADO;
        this.id = autoid++;
        this.random = new Random();
        this.ritmo = tiempoAlimento();
    }

    public static int getAutoid() {
        return autoid;
    }

    public Random getRandom() {
        return random;
    }

    public int getRitmo() {
        return ritmo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Estado isEstado() {
        return estado;
    }

    public int  tiempoAlimento(){
        return random.nextInt(100,1000) + 1;
    }


    /*
    Tendrán que alimentarse, pero cada uno de ellos se alimenta a diferente ritmo. Unos comen más rápido que otros, o sea
    que si les das una manzana a cada uno, tardaran diferente tiempo en comerla. Tendrán que avisar cuando comienzan y
    finalizan de comer
        */
    public void alimentarse(){

    }



    /*
También quieren jugar. Jugar significa plantear al cuidador una suma entre dos números de 1 cifra cuyo resultado es menor
que 10. El cuidador deberá responder. Si el resultado es incorrecto el tamagotchi sigue jugando.
   */
    public void jugar(int num1,int num2){

    }

    /*
También necesita limpiarse. El tamagotchi se va ensuciando según pasa el tiempo. Suciedad es una escala de -0-10. Cada
20 segundos se incrementa 1 unidad y al limpiarse vuelve a 0, el baño son 5 segundos. Cuando llega a 5 el tamagotchi
avisa que está empezando a estar muy sucio. Al llegar a 10 el tamagotchi muere
     */
    public void limpiarse(){

    }


    //5 minutos son 300000 milisegundos.
    public void morir(){
        System.out.println("muerto");
    }

    public void logConTimesTamp(){

    }

    @Override
    public void run() {


    }

    @Override
    public String toString() {
        return "Tamagochi{" +
                "estado=" + estado +
                ", id=" + id +
                ", nombre='" + nombre + '\'' +
                ", ritmo=" + ritmo;

    }


}
