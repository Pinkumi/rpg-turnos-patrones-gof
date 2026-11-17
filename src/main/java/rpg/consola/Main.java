package rpg.consola;
import rpg.modelo.*;
import rpg.creadores.*;
import rpg.decoradores.*;
import rpg.combate.MotorCombate;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;

public class Main {
 public static void main(String[] args) throws Exception {
  System.setOut(new PrintStream(System.out,true,StandardCharsets.UTF_8));
  if(Arrays.asList(args).contains("--demo")){demo(System.out::println);return;}
  var entrada=new Scanner(System.in,StandardCharsets.UTF_8);
  try {
   while(numero(entrada,"1 Nueva partida | 0 Salir",0,1)==1){
    String nombre;
    do {System.out.println("Nombre:");nombre=linea(entrada).strip();}while(nombre.isEmpty());
    var creador=switch(numero(entrada,"Clase: 1 Guerrero 2 Mago 3 Arquero 4 Pícaro",1,4)){
     case 1 -> new CreadorGuerrero();case 2 -> new CreadorMago();case 3 -> new CreadorArquero();default -> new CreadorPicaro();
    };
    Personaje p=creador.crearPersonaje(nombre);
    int equipo;
    while((equipo=numero(entrada,"Equipo: 1 Espada 2 Armadura 3 Veneno 4 Amuleto 5 Bendición 0 Listo",0,5))!=0){
     p=switch(equipo){case 1 -> new EspadaDecorator(p);case 2 -> new ArmaduraDecorator(p);case 3 -> new VenenoDecorator(p);case 4 -> new AmuletoRegeneracionDecorator(p);default -> new BendicionDecorator(p);};
     System.out.println(MotorCombate.ficha(p));
    }
    var enemigo=switch(numero(entrada,"Enemigo: 1 Goblin 2 Orco 3 Dragón",1,3)){case 1 -> new CreadorGoblin();case 2 -> new CreadorOrco();default -> new CreadorDragon();};
    new MotorCombate().iniciarCombate(p,enemigo.crearEnemigo(),actor -> {
     System.out.println(MotorCombate.ficha(actor));
     return MotorCombate.Accion.values()[numero(entrada,"1 Atacar 2 Defender 3 Poción",1,3)-1];
    },System.out::println);
   }
  } catch(NoSuchElementException fin){System.out.println("Fin de entrada. Partida cerrada.");}
 }
 static String linea(Scanner entrada){if(!entrada.hasNextLine())throw new NoSuchElementException();return entrada.nextLine();}
 static int numero(Scanner entrada,String mensaje,int min,int max){
  while(true){System.out.println(mensaje);try{int n=Integer.parseInt(linea(entrada).strip());if(n>=min && n<=max)return n;}catch(NumberFormatException e){}System.out.println("Entrada inválida.");}
 }
 public static void demo(Consumer<String> salida){
  Personaje[] heroes={
   new ArmaduraDecorator(new EspadaDecorator(new CreadorGuerrero().crearPersonaje("Guerrero demo"))),
   new BendicionDecorator(new VenenoDecorator(new EspadaDecorator(new CreadorMago().crearPersonaje("Mago demo")))),
   new VenenoDecorator(new AmuletoRegeneracionDecorator(new ArmaduraDecorator(new CreadorArquero().crearPersonaje("Arquero demo"))))
  };
  Personaje[] enemigos={new CreadorGoblin().crearEnemigo(),new CreadorOrco().crearEnemigo(),new CreadorDragon().crearEnemigo()};
  for(int i=0;i<3;i++){salida.accept("=== ESCENARIO "+(i+1)+" ===");new MotorCombate().iniciarCombate(heroes[i],enemigos[i],p->MotorCombate.Accion.ATACAR,salida);}
 }
}
