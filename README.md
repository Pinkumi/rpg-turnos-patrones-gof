# RPG por turnos: patrones GoF

Prototipo de consola para Análisis y Diseño de Sistemas. Aplica Strategy a ataques,
Factory Method a creación y Decorator a equipamiento. La consigna original se
conserva en [docs/Instrucciones (1).pdf](docs/Instrucciones%20(1).pdf).

## Entorno y ejecución

Requiere JDK 21 y Maven 3.9 o superior, con JAVA_HOME apuntando al JDK.
Código, documentación y salida usan UTF-8. JUnit 5 es la única dependencia.

```sh
mvn clean verify
mvn test
java -jar target/rpg-turnos-patrones-gof.jar
java -jar target/rpg-turnos-patrones-gof.jar --demo
```

En IntelliJ IDEA: File > Open > seleccionar pom.xml > abrir como proyecto Maven.
En Project Structure elegir JDK 21; en Maven Runner usar el mismo JDK.
Recargar Maven y ejecutar el método main de rpg.consola.Main con el icono verde.
Para demostraciones añadir --demo en Program arguments. Configurar consola y
File Encodings en UTF-8.

Para regenerar la evidencia completa en Windows, ejecutar desde PowerShell:

```powershell
cmd /c "java -jar target/rpg-turnos-patrones-gof.jar --demo > EJECUCION.txt"
```

En bash: `java -jar target/rpg-turnos-patrones-gof.jar --demo > EJECUCION.txt`.
La aplicación escribe UTF-8; cmd conserva sus bytes sin recodificar.

## Controles

Elegir 1 para nueva partida, ingresar nombre, clase (1–4), y equipo (1–5).
Se añade una pieza por selección; 0 termina el equipamiento.
Elegir enemigo (1–3). En cada turno: 1 atacar, 2 defender, 3 poción.
Hay tres pociones de 30 HP. Todas las acciones consumen turno, incluso intentar
usar una poción agotada. Al terminar, 1 inicia otra partida y 0 sale.
Entradas inválidas se vuelven a solicitar; EOF cierra la partida.

Ejemplo: nueva partida, Ana, Guerrero, Espada, Armadura, Listo, Goblin,
y atacar hasta el resultado. Las demos reproducen tres combinaciones completas
sin azar ni entrada humana.

## Organización y entrega

- src/main/java/rpg/modelo: interfaz, estado compartido y resultado de ataque.
- estrategias: Strategy y cuatro algoritmos intercambiables.
- creadores: Factory Method con cuatro creadores de héroes y tres de enemigos.
- decoradores: envoltorios combinables y delegación contextual.
- combate: reglas, iniciativa, efectos y bitácora sin teclado.
- consola: presentación, menús y demostraciones.
- src/test/java: pruebas JUnit de reglas, integración y entradas.
- [Análisis y diseño](docs/analisis-diseno.md): reglas, tablas y trazabilidad.
- [Diagramas UML](docs/diagramas.md): clases y secuencias Mermaid.
- [Evidencia real](EJECUCION.txt): salida íntegra de --demo.

Referencia conceptual consultada: [GoFPattern](https://www.gofpattern.com/index.php).
