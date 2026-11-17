package rpg.estrategias;
import rpg.modelo.*;
public class AtaqueEspada implements EstrategiaAtaque {
 public ResultadoAtaque atacar(Personaje a, Personaje o){return new ResultadoAtaque(Math.max(1,a.getAtaque()-o.getDefensa()),"Espada",0);}
}
