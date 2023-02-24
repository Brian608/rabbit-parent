package org.feather.rabbit.api.exception;

/**
 * @projectName: rabbit-parent
 * @package: org.feather.rabbit.api.exception
 * @className: MessageException
 * @author: feather(杜雪松)
 * @description: TODO
 * @since: 2023-02-24 18:16
 * @version: 1.0
 */

public class MessageException extends Exception {

    private static final long serialVersionUID = 6347951066190728758L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

}
