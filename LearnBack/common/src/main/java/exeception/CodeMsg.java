package exeception;

/**
 * @description: 错误码
 */
public class CodeMsg {

    private int status;
    private String msg;

    /**
     * 常见通用错误码
     */
    public static CodeMsg 请求成功 = new CodeMsg(0, "请求成功");
    public static CodeMsg 参数不正确 = new CodeMsg(400, "参数不正确");

    private CodeMsg(int status, String msg ) {
        this.status = status;
        this.msg = msg;
    }

    public static CodeMsg create(int status, String msg) {
        return new CodeMsg(status, msg);
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
