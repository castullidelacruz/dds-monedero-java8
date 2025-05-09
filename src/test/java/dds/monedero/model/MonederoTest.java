package dds.monedero.model;

import dds.monedero.exceptions.ExcepcionesDeCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  @DisplayName("Es posible poner $1500 en una cuenta vacía")
  void Poner() {
    cuenta.setSaldo(1500.0);
    assertEquals(1500.0, cuenta.getSaldo());
  }

  @Test
  @DisplayName("No es posible poner montos negativos")
  void PonerMontoNegativo() {
    Movimiento movimiento = new Movimiento(LocalDate.now(), -1500.0, TipoMovimiento.DEPOSITO);
    assertThrows(ExcepcionesDeCuenta.class, () -> cuenta.ponerDinero(movimiento));
  }

  @Test
  @DisplayName("Es posible realizar múltiples depósitos consecutivos")
  void TresDepositos() {
    Movimiento movimiento1 = new Movimiento(LocalDate.now(), 1500.0, TipoMovimiento.DEPOSITO);
    Movimiento movimiento2 = new Movimiento(LocalDate.now(), 496.0, TipoMovimiento.DEPOSITO);
    Movimiento movimiento3 = new Movimiento(LocalDate.now(), 1900.0, TipoMovimiento.DEPOSITO);
    cuenta.ponerDinero(movimiento1);
    cuenta.ponerDinero(movimiento2);
    cuenta.ponerDinero(movimiento3);
    assertEquals(3896,0, cuenta.getSaldo());
  }

  @Test
  @DisplayName("No es posible superar la máxima cantidad de depositos diarios")
  void MasDeTresDepositos() {
    Movimiento movimiento1 = new Movimiento(LocalDate.now(), 1500.0, TipoMovimiento.DEPOSITO);
    Movimiento movimiento2 = new Movimiento(LocalDate.now(), 496.0, TipoMovimiento.DEPOSITO);
    Movimiento movimiento3 = new Movimiento(LocalDate.now(), 1900.0, TipoMovimiento.DEPOSITO);
    Movimiento movimiento4 = new Movimiento(LocalDate.now(), 1900.0, TipoMovimiento.DEPOSITO);

    assertThrows(ExcepcionesDeCuenta.class, () -> {
      cuenta.ponerDinero(movimiento1);
      cuenta.ponerDinero(movimiento2);
      cuenta.ponerDinero(movimiento3);
      cuenta.ponerDinero(movimiento4);
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el saldo disponible")
  void ExtraerMasQueElSaldo() {
    Movimiento movimiento1 = new Movimiento(LocalDate.now(), 1500.0, TipoMovimiento.EXTRACCION);
    assertThrows(ExcepcionesDeCuenta.class, () -> {
      cuenta.setSaldo(90.0);
      cuenta.sacarDinero(movimiento1);
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el límite diario")
  void ExtraerMasDe1000() {
    Movimiento movimiento1 = new Movimiento(LocalDate.now(), 1001.0, TipoMovimiento.EXTRACCION);
    assertThrows(ExcepcionesDeCuenta.class, () -> {
      cuenta.setSaldo(5000.0);
      cuenta.sacarDinero(movimiento1);
    });
  }

  @Test
  @DisplayName("No es posible extraer un monto negativo")
  void ExtraerMontoNegativo() {
    Movimiento movimiento1 = new Movimiento(LocalDate.now(), -1500.0, TipoMovimiento.EXTRACCION);
    assertThrows(ExcepcionesDeCuenta.class, () -> cuenta.sacarDinero(movimiento1));
  }

}