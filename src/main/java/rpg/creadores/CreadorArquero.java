package rpg.creadores;
import rpg.modelo.*;
import rpg.estrategias.*;
public class CreadorArquero extends CreadorPersonaje {
 public Personaje crearPersonaje(String nombre) {
 validarNombre(nombre);
 return new PersonajeBase(nombre,"Arquero",100,23,8,20,new AtaqueFlecha());
 }
}
