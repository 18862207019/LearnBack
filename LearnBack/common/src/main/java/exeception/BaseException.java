package exeception;


import lombok.Getter;

/**
 * @description: 自定义异常类
 */
@Getter
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -3177085408025135330L;
    private String msg;
    private int errcode;
    private String uniqueKey;
    private Throwable exception;

    public BaseException(CodeMsg codeMsg, Throwable e) {
        super(codeMsg.getMsg(), e);
        this.errcode = codeMsg.getStatus();
        this.msg = codeMsg.getMsg();
        this.exception = e;
    }

    public BaseException(CodeMsg codeMsg) {
        this.errcode = codeMsg.getStatus();
        this.msg = codeMsg.getMsg();
    }

    public BaseException(CodeMsg codeMsg, String uniqueKey) {
        this.errcode = codeMsg.getStatus();
        this.msg = codeMsg.getMsg();
        this.uniqueKey = uniqueKey;
    }

}
