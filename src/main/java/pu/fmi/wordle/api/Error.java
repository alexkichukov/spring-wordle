package pu.fmi.wordle.api;

public class Error {
  String code;
  String value;
  String message;

  public Error(String code, String value, String message) {
    this.code = code;
    this.value = value;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public String getMessage() {
    return message;
  }
}
