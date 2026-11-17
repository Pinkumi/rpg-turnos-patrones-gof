package rpg.decoradores;
import rpg.modelo.*;
import rpg.estrategias.EstrategiaAtaque;
public class VenenoDecorator extends PersonajeDecorator {
 public VenenoDecorator(Personaje p){super(p);} public int getAtaque(){return super.getAtaque()+5;}
 public ResultadoAtaque atacarCon(Personaje efectivo,Personaje objetivo){
 var r=super.atacarCon(efectivo,objetivo); return new ResultadoAtaque(r.dano(),r.efecto()+" + veneno",3);
 }
}
