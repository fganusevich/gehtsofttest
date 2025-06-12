package ceasarCipher;

/**
 * User: f.ganusevich
 * Date: 6/12/25
 */

public enum CaesarCipherAction {
  ENCRYPT("encrypt"),
  DECRYPT("decrypt");

  private final String action;

  CaesarCipherAction(String action) {
    this.action = action;
  }

  public String getAction() {
    return action;
  }
}
