package rpg.modelo;
import rpg.estrategias.EstrategiaAtaque;
public interface Personaje {
 String getNombre(); String getDescripcion();
 int getAtaque(); int getDefensa(); int getVelocidad(); int getHp(); int getHpMaximo();
 void setHp(int hp); void ampliarHp(int cantidad);
 EstrategiaAtaque getEstrategia(); void setEstrategia(EstrategiaAtaque e);
 default ResultadoAtaque atacar(Personaje objetivo) { return atacarCon(this, objetivo); }
 default ResultadoAtaque atacarCon(Personaje efectivo, Personaje objetivo) { return getEstrategia().atacar(efectivo, objetivo); }
 default int regeneracion() { return 0; }
}
