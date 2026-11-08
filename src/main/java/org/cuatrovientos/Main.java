package org.cuatrovientos;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner entrada = new Scanner(System.in);

/*
El programa principal es el “cuidador” de X Tamagotchis. Por lo tanto al arrancarse deberá ponerles las pilas y lanzarlos al
mundo.
*/
        ArrayList<Tamagochi> tamagochis = new ArrayList<>();
        ArrayList<Thread> hilos = new ArrayList<>();

        int opcion = 0;

        //Este bucle crea los tamagochis y arranca los hilos.
        for (int i = 0; i < 4; i++) {
            Tamagochi tama = new Tamagochi("tamagochi" + i);
            Thread hilo = new Thread(tama);

            tamagochis.add(tama);
            hilos.add(hilo);

            hilo.start();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        do {
            System.out.println();
            System.out.println("========= CUIDADOR =========");
            System.out.println("1. Ver estado");
            System.out.println("2. Dar de comer");
            System.out.println("3. Jugar");
            System.out.println("4. Bañar");
            System.out.println("5. Matar");
            System.out.println("0. Salir");
            System.out.print("Introduce la opción que quieres: ");

            opcion = entrada.nextInt();

            int id;
            Tamagochi elegido;

            switch (opcion) {
                case 1:
                    verEstado(tamagochis);
                    break;
                case 2:
                    System.out.print("¿A qué tamagotchi (0-" + (tamagochis.size() - 1) + ")? ");
                    id = entrada.nextInt();
                    elegido = tamagochis.get(id);

                    if (elegido.darOrden("comer")) {
                        System.out.println("orden 'comer' enviada a " + elegido.getNombre());
                    } else {
                        System.out.println(elegido.getNombre() + " no puede, está " + elegido.isEstado());
                    }
                    break;
                case 3:
                    System.out.print("¿A qué tamagotchi (0-" + (tamagochis.size() - 1) + ")? ");
                    id = entrada.nextInt();
                    elegido = tamagochis.get(id);

                    if (!elegido.darOrden("jugar")) {
                        System.out.println(elegido.getNombre() + " no puede está " + elegido.isEstado());
                    } else {
                        while (elegido.isEstado() != Estado.JUGANDO && elegido.isEstado() != Estado.MUERTO) {
                            Thread.sleep(100);
                        }

                        while (elegido.isEstado() == Estado.JUGANDO) {
                            if (!elegido.getPregunta().isEmpty()) {
                                System.out.print("Tu respuesta: ");
                                elegido.setRespuesta(entrada.nextInt());
                            }
                            Thread.sleep(100);
                        }
                    }
                    break;
                case 4:
                    System.out.print("¿A qué tamagotchi (0-" + (tamagochis.size() - 1) + ")? ");
                    id = entrada.nextInt();
                    elegido = tamagochis.get(id);

                    if (elegido.darOrden("limpiar")) {
                        System.out.println("Orden 'limpiar' enviada a " + elegido.getNombre());
                    } else {
                        System.out.println(elegido.getNombre() + " no puede, está " + elegido.isEstado());
                    }
                    break;
                case 5:
                    matarTamagochi(entrada, tamagochis);
                    break;
                case 0:
                    for (Thread hilo : hilos) {
                        hilo.interrupt();
                    }
                    System.out.println("adiós");
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while (opcion != 0);
    }

    public static void verEstado(ArrayList<Tamagochi> tamagochis) {
        for (Tamagochi t : tamagochis) {
            System.out.println(t);
        }
    }

    public static void matarTamagochi(Scanner entrada, ArrayList<Tamagochi> tamagochis) {
        System.out.print("¿A qué tamagotchi (0-" + (tamagochis.size() - 1) + ")? ");
        int id = entrada.nextInt();
        Tamagochi elegido = tamagochis.get(id);

        if (!elegido.matar()) {
            System.out.println(elegido.getNombre() + " está " + elegido.isEstado() + ", no atiende a la destrucción");
        }
    }

}

