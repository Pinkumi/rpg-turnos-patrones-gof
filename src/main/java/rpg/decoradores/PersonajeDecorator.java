package rpg.decoradores;
import rpg.modelo.*;
import rpg.estrategias.EstrategiaAtaque;
public abstract class PersonajeDecorator implements Personaje {
 protected final Personaje personaje;
 protected PersonajeDecorator(Personaje p){personaje=java.util.Objects.requireNonNull(p);}
 public String getNombre(){return personaje.getNombre();}
 public String getDescripcion(){return personaje.getDescripcion()+" + "+getClass().getSimpleName().replace("Decorator","");}
 public int getAtaque(){return personaje.getAtaque();} public int getDefensa(){return personaje.getDefensa();}
 public int getVelocidad(){return personaje.getVelocidad();} public int getHp(){return personaje.getHp();}
 public int getHpMaximo(){return personaje.getHpMaximo();} public void setHp(int hp){personaje.setHp(hp);}
 public void ampliarHp(int n){personaje.ampliarHp(n);}
 public EstrategiaAtaque getEstrategia(){return personaje.getEstrategia();}
 public void setEstrategia(EstrategiaAtaque e){personaje.setEstrategia(e);}
 public ResultadoAtaque atacarCon(Personaje efectivo,Personaje objetivo){return personaje.atacarCon(efectivo,objetivo);}
 public int regeneracion(){return personaje.regeneracion();}
}
