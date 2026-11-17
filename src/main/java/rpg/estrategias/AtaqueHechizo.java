package rpg.estrategias;
import rpg.modelo.*;
public class AtaqueHechizo implements EstrategiaAtaque {
 public ResultadoAtaque atacar(Personaje a, Personaje o){return new ResultadoAtaque(Math.max(1,a.getAtaque()+6-o.getDefensa()/2),"Hechizo",0);}
}
