package util.result;



/**
 * 响应结果生成工具
 */
public class ResultGenerator {

    public static Result genSuccessResult() {
        return new Result( ResultCode.SUCCESS );
    }

    public static <T> Result <T> genSuccessResult(T data) {
        return new Result( ResultCode.SUCCESS ).setData( data );
    }

    public static Result genFailResult() {
        return new Result( ResultCode.FAILURE );
    }

    public static Result genFailResult(String message) {
        return new Result( ResultCode.FAILURE ).setMessage( message );
    }

}
