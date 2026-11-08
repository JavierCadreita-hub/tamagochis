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
    private int suciedad = 0;
    private String pregunta = "";
    private Integer respuesta = null;
    private long nacimiento = System.currentTimeMillis();
    private long ultimaSuciedad = System.currentTimeMillis();
    private Thread hilo;

    public Tamagochi(String nombre) {
        this.nombre = nombre;
        this.estado = Estado.PARADO;
        this.id = autoid++;
        this.random = new Random();
        this.ritmo = tiempoAlimento();
        this.ordenPendiente = "";
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

    public int getSuciedad() {
        return suciedad;
    }

    public long getVidaRestante() {
        long vivido = System.currentTimeMillis() - nacimiento;
        return Math.max(0, (300000 - vivido) / 1000);
    }

    public int  tiempoAlimento(){
        return random.nextInt(2000, 8001);
    }

    public String getOrdenPendiente() {
        return ordenPendiente;
    }

    public boolean darOrden(String orden) {
        if (estado == Estado.PARADO && ordenPendiente.isEmpty()) {
            ordenPendiente = orden;
            return true;
        }
        return false;
    }

    public boolean matar() {
        if (estado != Estado.PARADO || !ordenPendiente.isEmpty()) {
            return false;
        }
        morir("Estaba parado y el cuidador me ha apagado.");
        return true;
    }

    /*
        Tendrán que alimentarse, pero cada uno de ellos se alimenta a diferente ritmo. Unos comen más rápido que otros, o sea
        que si les das una manzana a cada uno, tardaran diferente tiempo en comerla. Tendrán que avisar cuando comienzan y
        finalizan de comer
            */
    public void alimentarse() throws InterruptedException {
        logConTimesTamp("Empiezo a comer la manzana...");
        Thread.sleep(ritmo);
        logConTimesTamp("He terminado de comer. ¡Qué rica!");
    }



    /*
También quieren jugar. Jugar significa plantear al cuidador una suma entre dos números de 1 cifra cuyo resultado es menor
que 10. El cuidador deberá responder. Si el resultado es incorrecto el tamagotchi sigue jugando.
   */
    public void jugar() throws InterruptedException {
        logConTimesTamp("¡Vamos a jugar!");
        boolean acertado = false;

        while (!acertado) {
            int num1 = random.nextInt(1, 9);
            int num2 = random.nextInt(1, 10 - num1);

            respuesta = null;
            pregunta = "¿Cuánto es " + num1 + " + " + num2 +"?";
            logConTimesTamp(pregunta);

            while (respuesta == null) {
                comprobarTiempo();
                Thread.sleep(100);
            }

            int contestado = respuesta;
            if (contestado == num1 + num2) {
                logConTimesTamp("Correcto! Ya me he cansado de jugar");
                acertado = true;
            } else {
                logConTimesTamp("Mal! " + num1 + " + " + num2 + " no es " + contestado + ". sigo jugando...");
            }
        }
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setRespuesta(int respuesta) {
        this.pregunta = "";
        this.respuesta = respuesta;
    }

    /*
También necesita limpiarse. El tamagotchi se va ensuciando según pasa el tiempo. Suciedad es una escala de -0-10. Cada
20 segundos se incrementa 1 unidad y al limpiarse vuelve a 0, el baño son 5 segundos. Cuando llega a 5 el tamagotchi
avisa que está empezando a estar muy sucio. Al llegar a 10 el tamagotchi muere
     */
    public void limpiarse() throws InterruptedException {
        logConTimesTamp("Me meto en la bañera (5 segunos ) suciedad " + suciedad + "/10");
        Thread.sleep(5000);
        suciedad = 0;
        ultimaSuciedad = System.currentTimeMillis();
        logConTimesTamp("Ya estoy limpio! Suciedad 0/10");
    }


    //5 minutos son 300000 milisegundos.
    public void morir(String motivo){
        if (estado == Estado.MUERTO) {
            return;
        }
        estado = Estado.MUERTO;
        logConTimesTamp(motivo);
        if (hilo != null) {
            hilo.interrupt();
        }
    }

    public void ensuciarse() {
        if (estado == Estado.MUERTO) {
            return;
        }
        suciedad++;

        if (suciedad == 5) {
            logConTimesTamp("Empiezo a estar muy sucio! Suciedad 5/10");
        } else if (suciedad >= 10) {
            morir("Suciedad 10/10 Me muero de lo sucio que estoy!");
        }
    }

    public void comprobarTiempo() {
        while (estado != Estado.MUERTO && System.currentTimeMillis() - ultimaSuciedad >= 20000) {
            ultimaSuciedad += 20000;
            ensuciarse();
        }
        if (getVidaRestante() == 0) {
            morir("Se han cumplido mis 5 minutos de vida Adiós, cuidador!");
        }
    }

    public void logConTimesTamp(String mensaje){
        long segundos = (System.currentTimeMillis() - timesTampInicio) / 1000;
        System.out.println("[" + segundos + "s] " + nombre + ": " + mensaje);
    }

    @Override
    public void run() {
        hilo = Thread.currentThread();
        nacimiento = System.currentTimeMillis();
        ultimaSuciedad = nacimiento;
        System.out.println(nombre + ": He nacido!!! Tardo " + ritmo + " ms en comer");

        try {
            while (estado != Estado.MUERTO) {

                String orden = ordenPendiente;
                ordenPendiente = "";

                switch (orden) {
                    case "":
                        break;

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

                if (estado != Estado.MUERTO) {
                    estado = Estado.PARADO;
                }

                comprobarTiempo();
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            estado = Estado.MUERTO;
        }

        System.out.println(nombre + ": fin Adiós cuidador");
    }

    @Override
    public String toString() {
        return "Tamagochi{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", suciedad=" + suciedad + "/10" +
                ", vidaRestante=" + getVidaRestante() + "s" +
                ", ritmo=" + ritmo +
                '}';
    }


}
