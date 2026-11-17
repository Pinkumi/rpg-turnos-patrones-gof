package rpg.decoradores;
import rpg.modelo.*;
import rpg.estrategias.EstrategiaAtaque;
public class AmuletoRegeneracionDecorator extends PersonajeDecorator {
 public AmuletoRegeneracionDecorator(Personaje p){super(p);}
 public int getDefensa(){return super.getDefensa()+3;} public int regeneracion(){return super.regeneracion()+2;}
}
