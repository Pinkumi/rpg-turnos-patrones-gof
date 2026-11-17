package rpg.creadores;
import rpg.modelo.*;
import rpg.estrategias.*;
public class CreadorGoblin extends CreadorEnemigo {
 public Personaje crearEnemigo() {
 String nombre="Goblin";
 return new PersonajeBase(nombre,"Goblin",60,16,5,14,new AtaquePunalada());
 }
}
