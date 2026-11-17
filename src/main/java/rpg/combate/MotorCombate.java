package rpg.combate;
import rpg.modelo.*;
import java.util.*;
import java.util.function.*;

/** El motor es el único que aplica daño; las estrategias solo calculan. */
public class MotorCombate {
 public enum Accion { ATACAR, DEFENDER, POCION }
 public record Resultado(String ganador, int rondas, List<String> bitacora) {}
 private static class Estado { int veneno, pociones=3; boolean defensa; }
 private final int limite;
 private final boolean detalles;
 public MotorCombate(){this(100,true);}
 public MotorCombate(int limite, boolean detalles){if(limite<1)throw new IllegalArgumentException();this.limite=limite;this.detalles=detalles;}
 public Resultado iniciarCombate(Personaje heroe, Personaje enemigo, Function<Personaje,Accion> elegir, Consumer<String> salida){
  if(heroe==enemigo)throw new IllegalArgumentException("Participantes distintos");
  List<String> registro=new ArrayList<>();
  Consumer<String> log=s->{registro.add(s);salida.accept(s);};
  Map<Personaje,Estado> estados=new IdentityHashMap<>();
  estados.put(heroe,new Estado());estados.put(enemigo,new Estado());
  log.accept("=== COMBATE ===");
  for(var p:List.of(heroe,enemigo))log.accept(ficha(p));
  var primero=heroe.getVelocidad()>=enemigo.getVelocidad()?heroe:enemigo;
  var segundo=primero==heroe?enemigo:heroe;
  int ronda=0;
  while(heroe.getHp()>0 && enemigo.getHp()>0 && ronda<limite){
   ronda++;
   for(var actor:List.of(primero,segundo)){
    var objetivo=actor==heroe?enemigo:heroe;
    if(actor.getHp()==0 || objetivo.getHp()==0)break;
    var estado=estados.get(actor);var receptor=estados.get(objetivo);
    String prefijo="Ronda "+ronda+" | "+actor.getNombre()+" -> "+objetivo.getNombre()+" | ";
    estado.defensa=false;
    if(estado.veneno>0){cambiar(actor,-4,prefijo+"veneno",log);estado.veneno--;}
    if(actor.getHp()==0)break;
    if(actor.regeneracion()>0)cambiar(actor,actor.regeneracion(),prefijo+"regeneración",log);
    var accion=actor==heroe?Objects.requireNonNull(elegir.apply(actor)):Accion.ATACAR;
    switch(accion){
     case DEFENDER -> {estado.defensa=true;log.accept(prefijo+"defensa: reduce a la mitad el siguiente daño directo");}
     case POCION -> {
      if(estado.pociones>0){estado.pociones--;cambiar(actor,30,prefijo+"poción (quedan "+estado.pociones+")",log);}
      else log.accept(prefijo+"sin pociones: turno consumido");
     }
     case ATACAR -> {
      var ataque=actor.atacar(objetivo);
      int dano=receptor.defensa?(ataque.dano()+1)/2:ataque.dano();
      receptor.defensa=false;
      cambiar(objetivo,-dano,prefijo+ataque.efecto(),log);
      if(objetivo.getHp()>0 && ataque.turnosVeneno()>0){receptor.veneno=ataque.turnosVeneno();log.accept(prefijo+"veneno renovado: 3 turnos");}
     }
    }
   }
  }
  String ganador=heroe.getHp()==0?enemigo.getNombre():enemigo.getHp()==0?heroe.getNombre():"Empate";
  log.accept("Resultado: "+ganador+" | HP "+heroe.getNombre()+"="+heroe.getHp()+", "+enemigo.getNombre()+"="+enemigo.getHp());
  if(detalles)log.accept("Estadísticas: "+ronda+" rondas; críticos y resistencia elemental: no utilizados.");
  return new Resultado(ganador,ronda,List.copyOf(registro));
 }
 private void cambiar(Personaje p,int delta,String accion,Consumer<String> log){
  int antes=p.getHp();p.setHp(antes+delta);
  log.accept(accion+" | "+p.getNombre()+" HP "+antes+" -> "+p.getHp()+" | daño="+Math.max(0,antes-p.getHp())+" curación="+Math.max(0,p.getHp()-antes));
 }
 public static String ficha(Personaje p){return p.getDescripcion()+" | HP "+p.getHp()+"/"+p.getHpMaximo()+" ATK "+p.getAtaque()+" DEF "+p.getDefensa()+" VEL "+p.getVelocidad();}
}
