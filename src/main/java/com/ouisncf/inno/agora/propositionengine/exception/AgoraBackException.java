package com.ouisncf.inno.agora.propositionengine.exception;

public class AgoraBackException extends RuntimeException{

  private int codeException;

  public int getCodeException() {
    return codeException;
  }

  public void setCodeException(int codeException) {
    this.codeException = codeException;
  }

  public AgoraBackException(String message, int codeException) {
    super(message);
    this.codeException = codeException;
  }
}
