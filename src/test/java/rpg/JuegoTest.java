package rpg;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import rpg.modelo.*;
import rpg.creadores.*;
import rpg.estrategias.*;
import rpg.decoradores.*;
import rpg.combate.*;
import rpg.consola.Main;
import java.util.*;
class JuegoTest {
 Personaje heroe(){return new CreadorGuerrero().crearPersonaje("Ana");}
 Personaje objetivo(){return new CreadorGoblin().crearEnemigo();}
 @Test void creadores(){
  var cs=List.of(new CreadorGuerrero(),new CreadorMago(),new CreadorArquero(),new CreadorPicaro());
  var estrategias=List.of(AtaqueEspada.class,AtaqueHechizo.class,AtaqueFlecha.class,AtaquePunalada.class);
  for(int i=0;i<4;i++){assertEquals(estrategias.get(i),cs.get(i).crearPersonaje("A").getEstrategia().getClass());final var c=cs.get(i);assertThrows(IllegalArgumentException.class,()->c.crearPersonaje(" "));}
  assertInstanceOf(AtaquePunalada.class,new CreadorGoblin().crearEnemigo().getEstrategia());
  assertInstanceOf(AtaqueEspada.class,new CreadorOrco().crearEnemigo().getEstrategia());
  assertInstanceOf(AtaqueHechizo.class,new CreadorDragon().crearEnemigo().getEstrategia());
 }
 @Test void formulasYCambio(){
  var p=heroe();var o=objetivo();
  assertEquals(17,p.atacar(o).dano());
  p.setEstrategia(new AtaqueHechizo());assertEquals(26,p.atacar(o).dano());
  p.setEstrategia(new AtaqueFlecha());assertEquals(21,p.atacar(o).dano());
  p.setEstrategia(new AtaquePunalada());assertEquals(17,p.atacar(o).dano());
  assertEquals(60,o.getHp());assertThrows(NullPointerException.class,()->p.setEstrategia(null));
 }
 @Test void equipoYOrden(){
  var a=new BendicionDecorator(new EspadaDecorator(heroe()));
  var b=new EspadaDecorator(new BendicionDecorator(heroe()));
  assertEquals(36,a.getAtaque());assertEquals(35,b.getAtaque());
  assertEquals(31,a.atacar(objetivo()).dano());
  assertEquals(20,new ArmaduraDecorator(heroe()).getDefensa());
  assertEquals(42,new EspadaDecorator(new EspadaDecorator(heroe())).getAtaque());
 }
 @Test void hpCompartidoYBendicion(){
  var base=heroe();base.setHp(100);var p=new ArmaduraDecorator(new BendicionDecorator(base));
  assertEquals(138,p.getHpMaximo());assertEquals(118,p.getHp());
  p.setHp(999);assertEquals(138,base.getHp());
  for(int i=0;i<10;i++)assertEquals(138,p.getHp());
  base.setHp(-1);assertEquals(0,p.getHp());new BendicionDecorator(p);assertEquals(0,p.getHp());
 }
 @Test void venenoInternoYDuracion(){
  Personaje p=new ArmaduraDecorator(new BendicionDecorator(new VenenoDecorator(heroe())));
  assertEquals(3,p.atacar(objetivo()).turnosVeneno());
  var o=new CreadorDragon().crearEnemigo();o.setEstrategia((a,b)->new ResultadoAtaque(0,"espera",0));
  int[] turno={0};
  var r=new MotorCombate(5,true).iniciarCombate(p,o,a->turno[0]++==0?MotorCombate.Accion.ATACAR:MotorCombate.Accion.DEFENDER,s->{});
  assertEquals(3,r.bitacora().stream().filter(s->s.contains("| veneno |")).count());
  assertEquals(220-p.atacar(o).dano()-12,o.getHp());
 }
 @Test void regeneracionYMuertos(){
  var p=new AmuletoRegeneracionDecorator(heroe());p.setHp(119);
  var o=objetivo();o.setHp(1);o.setEstrategia((a,b)->new ResultadoAtaque(0,"espera",0));
  new MotorCombate().iniciarCombate(p,o,a->MotorCombate.Accion.ATACAR,s->{});
  assertEquals(120,p.getHp());assertEquals(0,o.getHp());
  p.setHp(0);
  var r=new MotorCombate().iniciarCombate(p,objetivo(),a->{fail("Muerto no actúa");return null;},s->{});
  assertEquals(0,r.rondas());assertEquals(0,p.getHp());
 }
 @Test void limiteYDefensaPociones(){
  var p=heroe();var o=objetivo();p.setEstrategia((a,b)->new ResultadoAtaque(0,"espera",0));o.setEstrategia(p.getEstrategia());
  var r=new MotorCombate(5,false).iniciarCombate(p,o,a->MotorCombate.Accion.POCION,s->{});
  assertEquals("Empate",r.ganador());assertEquals(5,r.rondas());
  assertEquals(2,r.bitacora().stream().filter(s->s.contains("sin pociones")).count());
 }
 @Test void integracionYReproducibilidad(){
  List<String> a=new ArrayList<>(),b=new ArrayList<>();Main.demo(a::add);Main.demo(b::add);
  assertEquals(a,b);assertEquals(3,a.stream().filter(s->s.startsWith("Resultado:")).count());
  assertTrue(a.stream().anyMatch(s->s.contains("| veneno |")));
  assertTrue(a.stream().anyMatch(s->s.contains("regeneración")));
 }
}
