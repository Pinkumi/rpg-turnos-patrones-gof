package rpg.modelo;
public record ResultadoAtaque(int dano, String efecto, int turnosVeneno) {
 public ResultadoAtaque { if(dano < 0 || turnosVeneno < 0) throw new IllegalArgumentException(); }
 public int getDano() { return dano; }
 public String getEfecto() { return efecto; }
 public boolean isCritico() { return false; }
}
