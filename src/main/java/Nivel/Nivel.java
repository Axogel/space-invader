package Nivel;

import Aliens.Aliens;
import Bloques.Bloques;

public class Nivel {
    private Aliens[] enemigos;
    private Bloques[] bloques;

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
                    //
  
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
                    new Aliens(280, 50, 1)
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
                    new Aliens(340, 50, 1)
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

    public Aliens[] getEnemigos() {
        return enemigos;
    }

    public Bloques[] getBloques() {
        return bloques;
    }

    public int getNumEnemigos() {
        return enemigos.length;
    }
}
