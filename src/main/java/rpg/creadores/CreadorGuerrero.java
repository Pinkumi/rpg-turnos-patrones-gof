package rpg.creadores;
import rpg.modelo.*;
import rpg.estrategias.*;
public class CreadorGuerrero extends CreadorPersonaje {
 public Personaje crearPersonaje(String nombre) {
 validarNombre(nombre);
 return new PersonajeBase(nombre,"Guerrero",120,22,12,12,new AtaqueEspada());
 }
}
