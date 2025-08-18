package com.zagdev.devicemanager.domain.exceptions;

public class InvalidDataException extends GenericException {

  public InvalidDataException(ErrorCode errorCodes) {
    super(errorCodes);
  }

}
