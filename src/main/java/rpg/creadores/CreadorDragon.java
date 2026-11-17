package rpg.creadores;
import rpg.modelo.*;
import rpg.estrategias.*;
public class CreadorDragon extends CreadorEnemigo {
 public Personaje crearEnemigo() {
 String nombre="Dragon";
 return new PersonajeBase(nombre,"Dragon",220,32,16,18,new AtaqueHechizo());
 }
}
