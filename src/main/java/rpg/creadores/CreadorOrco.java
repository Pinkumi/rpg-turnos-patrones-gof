package rpg.creadores;
import rpg.modelo.*;
import rpg.estrategias.*;
public class CreadorOrco extends CreadorEnemigo {
 public Personaje crearEnemigo() {
 String nombre="Orco";
 return new PersonajeBase(nombre,"Orco",130,25,10,9,new AtaqueEspada());
 }
}
