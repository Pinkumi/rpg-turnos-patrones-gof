# Análisis y diseño

## Requisitos y decisiones

Crear héroes, equiparlos, elegir enemigos, combatir y registrar resultados.
El PDF se conserva sin cambios. Se revisaron sus diagramas de creadores,
clases y secuencia. No contenía datos personales académicos que trasladar.
Se priorizan fórmulas coherentes sobre los números ilustrativos del PDF.

| Tipo | HP máximo | Ataque | Defensa | Velocidad | Estrategia |
|---|---:|---:|---:|---:|---|
| Guerrero |120|22|12|12|Espada|
| Mago |90|26|6|15|Hechizo|
| Arquero |100|23|8|20|Flecha|
| Pícaro |95|21|7|25|Punalada|
| Goblin |60|16|5|14|Punalada|
| Orco |130|25|10|9|Espada|
| Dragón |220|32|16|18|Hechizo|

Las clases se representan mediante tipos de PersonajeBase configurados por
creadores, como en el código base. No necesitan subclases sin comportamiento.

## Reglas

A es ataque efectivo, D defensa efectiva del objetivo y V velocidad efectiva.
Toda división entera trunca hacia abajo; daño directo mínimo 1:

| Estrategia | Daño |
|---|---|
| Espada | max(1, A-D) |
| Hechizo | max(1, A+6-floor(D/2)) |
| Flecha | max(1, A+floor(V/3)-D) |
| Punalada | max(1, A+(8 si supera velocidad del objetivo; 0 si no)-D) |

No hay azar, críticos ni resistencia elemental. Hechizo ignora la mitad de
defensa. Las estrategias calculan sin mutar HP; solo MotorCombate aplica daño.

La velocidad ordena cada ronda; el héroe gana empates. Cada participante vivo
tiene una acción. Al inicio de su turno expira defensa anterior, sufre veneno,
se comprueba muerte y luego regenera. Defender reduce el siguiente ataque
directo a ceil(daño/2), hasta el próximo turno propio. No reduce veneno.
Tres pociones por combate curan 30; sin existencias se consume igualmente turno.
HP se limita a [0,máximo]. Se comprueba muerte tras efectos y acciones.
Tras 100 rondas sin ganador hay empate; el constructor permite otro límite.

Espada añade 10 ataque; Armadura 8 defensa; Veneno 5 ataque y aplica 4 daño
al comienzo de los próximos tres turnos del objetivo. Cada impacto renueva a
tres, sin sumar potencia ni duración. Amuleto añade 3 defensa y 2 regeneración.
Bendición multiplica ataque, defensa y velocidad por 1.15 truncando cada capa.
Al equiparla aumenta máximo y HP actual en floor(máximo anterior*0.15),
conservando el daño sufrido; nunca revive un muerto. El aumento de HP se
realiza una vez en el constructor, en el estado compartido. Equipar es permanente:
no existe desequipamiento y las referencias internas comparten ese máximo.

Se permiten repeticiones: bonos planos y regeneración suman; bendiciones se
componen; venenos conservan un solo DOT. El orden importa: Guerrero con
Espada y luego Bendición tiene 36 ataque; en orden inverso, 35.
Getters nunca mutan. No equipar durante combate es una regla de la consola.

## Patrones y contratos

Strategy resuelve algoritmos variables con EstrategiaAtaque y cuatro clases.
PersonajeBase mantiene estrategia no nula, intercambiable con setEstrategia.
Agregar algoritmo exige implementar atacar(Personaje,Personaje), sin tocar motor.

Factory Method evita que el cliente configure atributos: CreadorPersonaje
declara crearPersonaje(String), CreadorEnemigo declara crearEnemigo().
Sus siete subclases concretas crean el producto Personaje. Para extender,
añadir un creador y una opción de menú.

Decorator evita subclases para cada combinación. PersonajeDecorator implementa
Personaje y envuelve otro Personaje. Los cinco concretos añaden atributos
y/o efectos. Para extender, sobrescribir getters puros o atacarCon, llamando
una vez a super para conservar responsabilidades internas.

La corrección esencial es atacar(objetivo) -> atacarCon(this,objetivo).
Cada capa propaga ese atacante efectivo inalterado. La base ejecuta la
estrategia con el objeto exterior; al regresar, Veneno enriquece ResultadoAtaque
con turnosVeneno. Armadura o Bendición exteriores no eliminan el DOT.
No se llama a atacar de la base perdiendo el contexto.

Se amplió Personaje con getHpMaximo, ampliarHp, atacarCon y regeneracion;
ResultadoAtaque es un record inmutable con duración de veneno tipada.
MotorCombate recibe una función de elección y un consumidor de texto; devuelve
resultado y copia inmutable de bitácora. No lee teclado. La configuración
boolean detalles añade estadísticas; siempre registra equipo, atributos,
ronda, acciones, cambios de HP y resultado. La consola decide presentación.

## Trazabilidad

| Requisito | Clases | Prueba JUnit / evidencia |
|---|---|---|
| Creación y validación | Creador* | creadores |
| Fórmulas y cambio | Ataque*, PersonajeBase | formulasYCambio |
| Bonos y orden | cinco decoradores | equipoYOrden |
| HP consistente | PersonajeBase, Bendicion | hpCompartidoYBendicion |
| Efectos internos, daño único, duración | Veneno, MotorCombate | venenoInternoYDuracion |
| Regeneración, muerte | Amuleto, MotorCombate | regeneracionYMuertos |
| Pociones, límite | MotorCombate | limiteYDefensaPociones |
| Integración determinista | Main.demo | integracionYReproducibilidad, EJECUCION.txt |
| Entradas y EOF | Main | EntradaTest |
