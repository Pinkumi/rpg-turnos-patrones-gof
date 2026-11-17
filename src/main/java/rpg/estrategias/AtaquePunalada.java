package rpg.estrategias;
import rpg.modelo.*;
public class AtaquePunalada implements EstrategiaAtaque {
 public ResultadoAtaque atacar(Personaje a, Personaje o){return new ResultadoAtaque(Math.max(1,a.getAtaque()+ (a.getVelocidad()>o.getVelocidad()?8:0)-o.getDefensa()),"Punalada",0);}
}
