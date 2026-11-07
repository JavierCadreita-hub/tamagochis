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
    private String ordenPendiente;

    public Tamagochi(String nombre) {
        this.nombre = nombre;
        this.estado = Estado.PARADO;
        this.id = autoid++;
        this.random = new Random();
        this.ritmo = tiempoAlimento();
        this.ordenPendiente = "";
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

    public String getOrdenPendiente() {
        return ordenPendiente;
    }

    public boolean darOrden(String orden) {
        if (estado == Estado.PARADO && ordenPendiente.isEmpty()) {
            ordenPendiente = orden;
            return true;     // aceptada
        }
        return false;        // está ocupado
    }

    /*
        Tendrán que alimentarse, pero cada uno de ellos se alimenta a diferente ritmo. Unos comen más rápido que otros, o sea
        que si les das una manzana a cada uno, tardaran diferente tiempo en comerla. Tendrán que avisar cuando comienzan y
        finalizan de comer
            */
    public void alimentarse(){
        System.out.println("comiendo....");

    }



    /*
También quieren jugar. Jugar significa plantear al cuidador una suma entre dos números de 1 cifra cuyo resultado es menor
que 10. El cuidador deberá responder. Si el resultado es incorrecto el tamagotchi sigue jugando.
   */
    public void jugar(){
        int num1 = random.nextInt();
        int num2 = random.nextInt();
        System.out.println("jugando....");
    }

    /*
También necesita limpiarse. El tamagotchi se va ensuciando según pasa el tiempo. Suciedad es una escala de -0-10. Cada
20 segundos se incrementa 1 unidad y al limpiarse vuelve a 0, el baño son 5 segundos. Cuando llega a 5 el tamagotchi
avisa que está empezando a estar muy sucio. Al llegar a 10 el tamagotchi muere
     */
    public void limpiarse(){
        System.out.println("limpiando....");
    }


    //5 minutos son 300000 milisegundos.
    public void morir(){
        System.out.println("muerto");
    }

    public void logConTimesTamp(){

    }

    @Override
    public void run() {
        System.out.println(nombre + ": He nacido!!! Tardo " + ritmo + " ms en comer");

        try {
            while (estado != Estado.MUERTO) {

                String orden = ordenPendiente;   // copio la orden que me hayan dejado
                ordenPendiente = "";             // y la vacío

                switch (orden) {
                    case "":
                        break;                   // no hay orden: no hago nada

                    case "comer":
                        estado = Estado.COMIENDO;
                        alimentarse();
                        break;

                    case "jugar":
                        estado = Estado.JUGANDO;
                        jugar();
                        break;

                    case "limpiar":
                        estado = Estado.LIMPIANDO;
                        limpiarse();
                        break;

                    default:
                        System.out.println(nombre + ": orden no encontrada");
                }

                if (estado != Estado.MUERTO) {   // si no ha muerto mientras tanto
                    estado = Estado.PARADO;      // vuelve a estar libre
                }

                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            estado = Estado.MUERTO;              // me han interrumpido: he muerto
        }

        System.out.println(nombre + ": Fin Adiós cuidador");
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
