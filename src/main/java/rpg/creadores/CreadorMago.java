package rpg.creadores;
import rpg.modelo.*;
import rpg.estrategias.*;
public class CreadorMago extends CreadorPersonaje {
 public Personaje crearPersonaje(String nombre) {
 validarNombre(nombre);
 return new PersonajeBase(nombre,"Mago",90,26,6,15,new AtaqueHechizo());
 }
}
