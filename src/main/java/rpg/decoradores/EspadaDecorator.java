package rpg.decoradores;
import rpg.modelo.*;
import rpg.estrategias.EstrategiaAtaque;
public class EspadaDecorator extends PersonajeDecorator { public EspadaDecorator(Personaje p){super(p);} public int getAtaque(){return super.getAtaque()+10;} }
