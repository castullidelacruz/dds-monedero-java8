package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  @DisplayName("Es posible poner $1500 en una cuenta vacía")
  void Poner() {
    cuenta.poner(1500.0);
  }

  @Test
  @DisplayName("No es posible poner montos negativos")
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500.0));
  }

  @Test
  @DisplayName("Es posible realizar múltiples depósitos consecutivos")
  void TresDepositos() {
    cuenta.poner(1500.0);
    cuenta.poner(456.0);
    cuenta.poner(1900.0);
  }

  @Test
  @DisplayName("No es posible superar la máxima cantidad de depositos diarios")
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
      cuenta.poner(1500.0);
      cuenta.poner(456.0);
      cuenta.poner(1900.0);
      cuenta.poner(245.0);
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el saldo disponible")
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
      cuenta.setSaldo(90.0);
      cuenta.sacar(1001.0);
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el límite diario")
  void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000.0);
      cuenta.sacar(1001.0);
    });
  }

  @Test
  @DisplayName("No es posible extraer un monto negativo")
  void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500.0));
  }

}