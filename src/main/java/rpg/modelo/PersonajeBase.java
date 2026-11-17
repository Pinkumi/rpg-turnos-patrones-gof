package rpg.modelo;
import java.util.Objects;
import rpg.estrategias.EstrategiaAtaque;
public class PersonajeBase implements Personaje {
 private final String nombre, tipo;
 private final int ataque, defensa, velocidad;
 private int hp, maximo;
 private EstrategiaAtaque estrategia;
 public PersonajeBase(String nombre, String tipo, int hp, int ataque, int defensa, int velocidad, EstrategiaAtaque e) {
  if(nombre == null || nombre.isBlank() || hp <= 0 || ataque < 0 || defensa < 0 || velocidad < 0) throw new IllegalArgumentException("Nombre o atributos inválidos");
  this.nombre=nombre.strip(); this.tipo=Objects.requireNonNull(tipo); this.hp=hp; maximo=hp;
  this.ataque=ataque; this.defensa=defensa; this.velocidad=velocidad; setEstrategia(e);
 }
 public String getNombre(){return nombre;} public String getDescripcion(){return nombre+" ("+tipo+")";}
 public int getAtaque(){return ataque;} public int getDefensa(){return defensa;} public int getVelocidad(){return velocidad;}
 public int getHp(){return hp;} public int getHpMaximo(){return maximo;}
 public void setHp(int hp){this.hp=Math.clamp(hp,0,maximo);}
 public void ampliarHp(int cantidad){if(cantidad<0)throw new IllegalArgumentException(); maximo+=cantidad; if(hp>0)hp+=cantidad;}
 public EstrategiaAtaque getEstrategia(){return estrategia;}
 public void setEstrategia(EstrategiaAtaque e){estrategia=Objects.requireNonNull(e);}
}
