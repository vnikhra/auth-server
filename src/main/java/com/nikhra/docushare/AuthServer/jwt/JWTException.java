package com.nikhra.docushare.AuthServer.jwt;

public class JWTException extends Exception{
  public JWTException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
