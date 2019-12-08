package util.log;

/**
 * @description: 日志打印工具类
 */
public class Log {

    public static final String api = "api";
    public static final String errcode = "errcode";
    public static final String input = "input";
    public static final String output = "output";
    public static final String msg = "msg";
    public static final String agent = "agent";
    public static final String ip = "ip";
    public static final String userid = "userid";
    public static final String amount = "amount";
    public static final String currency = "currency";
    public static final String error = "error";
    public static final String timeUsed = "timeUsed";
    public static final String traceId = "traceid";
    public static final String step = "step";
    public static final String exception = "exception";
    public static final String businessType = "businesstype";
    public static final String mobile = "mobile";
    public static final String security = "security";
    public static final String source = "source";


    public String key;
    public Object value;

    private Log(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public static Log create(String key, Object value) {
        return new Log(key, value);
    }

    public static Log source(String value) {
        return new Log(Log.source, value);
    }

    public static Log mobile(String value) {
        return new Log(Log.mobile, value);
    }

    public static Log exception(Throwable t) {
        return new Log(Log.exception, t);
    }

    public static Log businessType(String value) {
        return new Log(Log.businessType, value);
    }

    public static Log api(String value) {
        return new Log(Log.api, value);
    }

    public static Log errcode(int value) {
        return new Log(Log.errcode, value);
    }

    public static Log input(String value) {
        return new Log(Log.input, value);
    }

    public static Log traceId(String value) {
        return new Log(Log.traceId, value);
    }

    public static Log step(String value) {
        return new Log(Log.step, value);
    }

    public static Log output(String value) {
        return new Log(Log.output, value);
    }

    public static Log msg(String value) {
        return new Log(Log.msg, value);
    }

    public static Log agent(String value) {
        return new Log(Log.agent, value);
    }

    public static Log ip(String value) {
        return new Log(Log.ip, value);
    }

    public static Log userid(String value) {
        return new Log(Log.userid, value);
    }

    public static Log amount(Double value) {
        return new Log(Log.amount, value);
    }

    public static Log currency(String value) {
        return new Log(Log.currency, value);
    }

    public static Log error() {
        return new Log(Log.error, true);
    }

    public static Log security() {
        return new Log(Log.security, true);
    }

    public static Log timeUsed(long timeUsed) {
        return new Log(Log.timeUsed, timeUsed);
    }

    @Override
    public String toString() {
        if (null == value) {
            return "";
        }
        return value.toString();
    }
}

