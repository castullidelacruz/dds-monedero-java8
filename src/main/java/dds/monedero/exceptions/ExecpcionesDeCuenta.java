package dds.monedero.exceptions;

import dds.monedero.model.TipoErrorCuenta;

public class ExecpcionesDeCuenta extends RuntimeException {
  public ExecpcionesDeCuenta(TipoErrorCuenta tipoError, Double valor) {
    super(generarMensaje(tipoError, valor));
  }

  private static String generarMensaje(TipoErrorCuenta tipoError, Double valor) {
    return switch (tipoError) {
      case MAX_DEPOSITOS -> "Ya excedió los 3 depósitos diarios.";
      case MAX_EXTRACCION_DIARIA -> "No puede extraer más de $1000 diarios.";
      case MONTO_NEGATIVO -> valor + ": el monto debe ser un valor positivo.";
      case SALDO_INSUFICIENTE -> "No puede sacar más de $" + valor;
    };
  }
}
