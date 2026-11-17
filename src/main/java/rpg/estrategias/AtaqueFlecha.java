package rpg.estrategias;
import rpg.modelo.*;
public class AtaqueFlecha implements EstrategiaAtaque {
 public ResultadoAtaque atacar(Personaje a, Personaje o){return new ResultadoAtaque(Math.max(1,a.getAtaque()+a.getVelocidad()/3-o.getDefensa()),"Flecha",0);}
}
