package rpg.consola;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
class EntradaTest {
 @Test void entradasInvalidas(){assertEquals(2,Main.numero(new Scanner("hola\n9\n2\n"),"acción",1,3));}
 @Test void finDeEntrada(){assertThrows(NoSuchElementException.class,()->Main.numero(new Scanner(""),"acción",1,3));}
}
