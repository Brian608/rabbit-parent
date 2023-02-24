package org.feather.rabbit.api.exception;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.api.exception
 * @className: MessageRunTimeException
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-24 18:17
 * @version: 1.0
 */

public class MessageRunTimeException extends  RuntimeException {

  private static final long serialVersionUID = 8651828913888663267L;

  public MessageRunTimeException() {
    super();
  }

  public MessageRunTimeException(String message) {
    super(message);
  }

  public MessageRunTimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public MessageRunTimeException(Throwable cause) {
    super(cause);
  }
  }
