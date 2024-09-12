package Nivel;

import Aliens.Aliens;
import Bloques.Bloques;

/**
 * La clase {@code Nivel} representa los niveles en el juego. Se encarga de inicializar y proporcionar información sobre
 * los enemigos y bloques presentes en el nivel.
 * <p>
 * Dependiendo del número de nivel pasado al constructor, se crean diferentes configuraciones de enemigos y bloques.
 */
public class Nivel {
    private Aliens[] enemigos;
    private Bloques[] bloques;

    /**
     * Crea una nueva instancia de {@code Nivel} según el número del nivel proporcionado.
     * <p>
     * Dependiendo del número del nivel, se inicializan diferentes configuraciones de enemigos y bloques.
     *
     * @param nivel el número del nivel a crear (1, 2 o 3)
     * @throws IllegalArgumentException si el nivel no está definido (número de nivel fuera del rango permitido)
     */
    public Nivel(int nivel) {
        switch (nivel) {
            case 1:
                enemigos = new Aliens[] {
                    new Aliens(100, 50, 3),
                    new Aliens(160, 50, 3),
                    new Aliens(220, 50, 3),
                    new Aliens(280, 50, 3),
                    new Aliens(340, 50, 3),
                    //
                    new Aliens(100, 70, 1),
                    new Aliens(160, 70, 1),
                    new Aliens(220, 70, 1),
                    new Aliens(280, 70, 1),
                    new Aliens(340, 70, 1),
                    new Aliens(100, 100, 1),
                    new Aliens(160, 100, 1),
                    new Aliens(220, 100, 1),
                    new Aliens(280, 100, 1),
                    new Aliens(340, 100, 1),
                    //
                    new Aliens(100, 120, 2),
                    new Aliens(160, 120, 2),
                    new Aliens(220, 120, 2),
                    new Aliens(280, 120, 2),
                    new Aliens(340, 120, 2),
                    new Aliens(100, 150, 2),
                    new Aliens(160, 150, 2),
                    new Aliens(220, 150, 2),
                    new Aliens(280, 150, 2),
                    new Aliens(340, 150, 2),
                };
                bloques = new Bloques[] {
                    new Bloques(230, 500),
                    new Bloques(330, 500),
                    new Bloques(430, 500),
                    new Bloques(530, 500)
                };
                break;
            case 2:
                enemigos = new Aliens[] {
                    new Aliens(100, 50, 1),
                    new Aliens(160, 50, 1),
                    new Aliens(220, 50, 1),
                    new Aliens(280, 50, 1),
                    new Aliens(100, 70, 1),
                    new Aliens(160, 70, 1),
                    new Aliens(220, 70, 1),
                    new Aliens(280, 70, 1),
                    new Aliens(340, 70, 1),
                    new Aliens(100, 100, 1),
                    new Aliens(160, 100, 1),
                    new Aliens(220, 100, 1),
                    new Aliens(280, 100, 1),
                    new Aliens(340, 100, 1),
                    new Aliens(100, 70, 1),
                    new Aliens(160, 70, 1),
                    new Aliens(220, 70, 1),
                    new Aliens(280, 70, 1),
                    new Aliens(340, 70, 1),
                    new Aliens(100, 100, 1),
                    new Aliens(160, 100, 1),
                    new Aliens(220, 100, 1),
                    new Aliens(280, 100, 1),
                    new Aliens(340, 100, 1),
                };
                bloques = new Bloques[] {
                    new Bloques(230, 500),
                    new Bloques(330, 500),
                    new Bloques(430, 500),
                    new Bloques(530, 500)
                };
                break;
            case 3:
                enemigos = new Aliens[] {
                    new Aliens(100, 50, 1),
                    new Aliens(160, 50, 1),
                    new Aliens(220, 50, 1),
                    new Aliens(280, 50, 1),
                    new Aliens(340, 50, 1),
                    new Aliens(100, 70, 1),
                    new Aliens(160, 70, 1),
                    new Aliens(220, 70, 1),
                    new Aliens(280, 70, 1),
                    new Aliens(340, 70, 1),
                    new Aliens(100, 100, 1),
                    new Aliens(160, 100, 1),
                    new Aliens(220, 100, 1),
                    new Aliens(280, 100, 1),
                    new Aliens(340, 100, 1),
                    new Aliens(100, 70, 1),
                    new Aliens(160, 70, 1),
                    new Aliens(220, 70, 1),
                    new Aliens(280, 70, 1),
                    new Aliens(340, 70, 1),
                    new Aliens(100, 100, 1),
                    new Aliens(160, 100, 1),
                    new Aliens(220, 100, 1),
                    new Aliens(280, 100, 1),
                    new Aliens(340, 100, 1),
                };
                bloques = new Bloques[] {
                    new Bloques(230, 500),
                    new Bloques(330, 500),
                    new Bloques(430, 500),
                    new Bloques(530, 500)
                };
                break;
            default:
                throw new IllegalArgumentException("Nivel no definido");
        }
    }

    /**
     * Obtiene el array de enemigos para el nivel actual.
     *
     * @return un array de objetos {@code Aliens} que representan los enemigos en el nivel
     */
    public Aliens[] getEnemigos() {
        return enemigos;
    }

    /**
     * Obtiene el array de bloques para el nivel actual.
     *
     * @return un array de objetos {@code Bloques} que representan los bloques en el nivel
     */
    public Bloques[] getBloques() {
        return bloques;
    }

    /**
     * Obtiene el número total de enemigos en el nivel actual.
     *
     * @return el número de enemigos en el nivel
     */
    public int getNumEnemigos() {
        return enemigos.length;
    }
}
