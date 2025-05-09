package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private Double saldo = 0.0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta(Double montoInicial) {
    saldo = montoInicial;
  }

  public void ponerDinero(Movimiento movimiento) {
    if (movimiento.getMonto() <= 0) {
      throw new MontoNegativoException(movimiento.getMonto()  + ": el monto a ingresar debe ser un valor positivo");
    }

    if (getMovimientos().stream()
        .filter(mov -> mov.fueDepositado(LocalDate.now()))
        .count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }

    this.agregarMontoACuenta(movimiento);
  }

  public void sacarDinero(Movimiento movimiento) {
    if (movimiento.getMonto() <= 0) {
      throw new MontoNegativoException(movimiento.getMonto() + ": el monto a ingresar debe ser un valor positivo");
    }
    if (getSaldo() - movimiento.getMonto() < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
    var montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    var limite = 1000 - montoExtraidoHoy;
    if (movimiento.getMonto() > limite) {
      throw new MaximoExtraccionDiarioException(
          "No puede extraer mas de $ " + 1000 + " diarios, " + "límite: " + limite);
    }
    this.restarMontoExtraido(movimiento);
  }

  public void agregarMovimiento(Movimiento movimiento) {
    movimientos.add(movimiento);
  }

  public Double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> (movimiento.getTipo().equals(TipoMovimiento.DEPOSITO)) && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public Double getSaldo() {
    return saldo;
  }

  public void setSaldo(Double saldo) {
    this.saldo = saldo;
  }

  //---------------------------------------------------------------------------------------

  public void agregarMontoACuenta(Movimiento movimiento) {
    Double nuevoSaldo = this.getSaldo() + movimiento.getMonto();
    this.setSaldo(nuevoSaldo);
    this.agregarMovimiento(movimiento);
  }

  public void restarMontoExtraido(Movimiento movimiento) {
    Double nuevoSaldo = this.getSaldo() - movimiento.getMonto();
    this.setSaldo(nuevoSaldo);
    this.agregarMovimiento(movimiento);
  }

}
