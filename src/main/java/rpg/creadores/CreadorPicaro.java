package rpg.creadores;
import rpg.modelo.*;
import rpg.estrategias.*;
public class CreadorPicaro extends CreadorPersonaje {
 public Personaje crearPersonaje(String nombre) {
 validarNombre(nombre);
 return new PersonajeBase(nombre,"Picaro",95,21,7,25,new AtaquePunalada());
 }
}
