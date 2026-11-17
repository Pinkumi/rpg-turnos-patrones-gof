package rpg.decoradores;
import rpg.modelo.*;
import rpg.estrategias.EstrategiaAtaque;
public class BendicionDecorator extends PersonajeDecorator {
 public BendicionDecorator(Personaje p){super(p); ampliarHp(p.getHpMaximo()*15/100);}
 public int getAtaque(){return super.getAtaque()*115/100;}
 public int getDefensa(){return super.getDefensa()*115/100;}
 public int getVelocidad(){return super.getVelocidad()*115/100;}
}
