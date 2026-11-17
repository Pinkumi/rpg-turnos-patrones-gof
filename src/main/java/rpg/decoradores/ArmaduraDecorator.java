package rpg.decoradores;
import rpg.modelo.*;
import rpg.estrategias.EstrategiaAtaque;
public class ArmaduraDecorator extends PersonajeDecorator { public ArmaduraDecorator(Personaje p){super(p);} public int getDefensa(){return super.getDefensa()+8;} }
