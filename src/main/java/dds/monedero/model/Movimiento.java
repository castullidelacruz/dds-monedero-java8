package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
  // Nota: En ningún lenguaje de programación usen jamás doubles (es decir, números con punto flotante) para modelar dinero en el mundo real.
  // En su lugar siempre usen numeros de precision arbitraria o punto fijo, como BigDecimal en Java y similares
  // De todas formas, NO es necesario modificar ésto como parte de este ejercicio. 
  private Double monto;
  private TipoMovimiento tipo;

  public Movimiento(LocalDate fecha, Double monto, TipoMovimiento tipo) {
    this.fecha = fecha;
    this.monto = monto;
    this.tipo = tipo;
  }

  public Double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  public Boolean fueDepositado(LocalDate fecha) {
    return isDeposito() && esDeLaFecha(fecha);
  }

  public Boolean fueExtraido(LocalDate fecha) {
    return isExtraccion() && esDeLaFecha(fecha);
  }

  public Boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public TipoMovimiento getTipo() {
    return tipo;
  }

}
