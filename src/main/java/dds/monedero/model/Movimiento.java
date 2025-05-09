package dds.monedero.model;

import java.time.LocalDate;

public class Movimiento {
  private LocalDate fecha;
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


  public TipoMovimiento getTipo() {
    return tipo;
  }

}
