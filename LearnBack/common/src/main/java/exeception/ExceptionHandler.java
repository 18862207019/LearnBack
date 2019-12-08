package exeception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import util.result.Result;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.List;

/**
 * @description: 统一异常处理器
 */
@ControllerAdvice
public class ExceptionHandler {

  Logger logger = LoggerFactory.getLogger(getClass());

  @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.HttpMediaTypeNotAcceptableException.class)
  @ResponseBody
  public Result handleHttpMediaTypeNotAcceptableException(HttpServletRequest request, Exception ex) {
    logger.error("HttpMediaTypeNotAcceptableException uri:{}, code:{}, msg:{}", request.getRequestURI(), 405, ex.getMessage());
    logger.error("HttpMediaTypeNotAcceptableException", ex);
    Result exceptionResponse = new Result();
    exceptionResponse.setCode(405);
    exceptionResponse.setMessage(ex.getMessage());
    return exceptionResponse;
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.HttpRequestMethodNotSupportedException.class)
  @ResponseBody
  public Result handleHttpRequestMethodNotSupportedException(HttpServletRequest request, Exception ex) {
    logger.error("HttpRequestMethodNotSupportedException uri:{}, code:{}, msg:{}", request.getRequestURI(), 405, ex.getMessage());
    Result exceptionResponse = new Result();
    exceptionResponse.setCode(405);
    exceptionResponse.setMessage(ex.getMessage());
    return exceptionResponse;
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
  @ResponseBody
  public Result handleMissingServletRequestParameterException(HttpServletRequest request, Exception ex) {
    logger.error("MissingServletRequestParameterException uri:{}, code:{}, msg:{}", request.getRequestURI(), 400, ex.getMessage());
    Result exceptionResponse = new Result();
    exceptionResponse.setCode(400);
    exceptionResponse.setMessage(ex.getMessage());
    return exceptionResponse;
  }


  @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
  @ResponseBody
  public Result handleBusinessException(HttpServletRequest request, Exception ex) {

    Result exceptionResponse;
    String uri = request.getRequestURI();
    BaseException e = (BaseException) ex;
    int code = e.getErrcode();
    String msg = e.getMsg();
    logger.error("caught error api:{}, errcode:{}, msg:{}", uri, code, msg);
    if (e.getException() != null) {
      logger.error("caught error api:{}, error info", e.getException());
    }
    exceptionResponse = new Result(code, msg);
    return exceptionResponse;
  }


  @org.springframework.web.bind.annotation.ExceptionHandler(SecurityException.class)
  @ResponseBody
  public Result handleSecurityException(HttpServletRequest request, Exception ex) {
    Result exceptionResponse;
    BaseException e = (BaseException) ex;
    int code = e.getErrcode();
    String msg = e.getMsg();
    exceptionResponse = new Result(code, msg);
    return exceptionResponse;
  }

  /**
   * beanValidator 参数校验异常
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
  @ResponseBody
  public Result handleValidationException(HttpServletRequest request, Exception ex) {
    Result exceptionResponse = new Result();
    javax.validation.ValidationException validationException = (javax.validation.ValidationException) ex;
    validationException.getMessage();
    logger.error(" beanValidator 参数校验未通过 api:{}, errcode:{}, msg:{}", request.getRequestURI(), 500, validationException.getMessage());
    exceptionResponse.setCode(500);
    exceptionResponse.setMessage(validationException.getMessage());
    return exceptionResponse;
  }

  /**
   * 参数绑定异常
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
  @ResponseBody
  public Result handleBindException(HttpServletRequest request, Exception ex) {
    Result exceptionResponse = new Result();
    BindException c = (BindException) ex;
    List<ObjectError> errors = c.getBindingResult().getAllErrors();
    StringBuffer errorMsg = new StringBuffer();
    errors.stream().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(";"));
    logger.error(" beanValidator 校验未通过 api:{}, errcode:{}, msg:{}", request.getRequestURI(), 500, errorMsg.toString());
    exceptionResponse.setCode(500);
    exceptionResponse.setMessage(errorMsg.toString());
    return exceptionResponse;
  }

  /**
   * 未知运行时异常
   */
  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  @ResponseBody
  public Result handleException(HttpServletRequest request, Exception ex) {
    logger.error("uncaught error api:{}, errcode:{}, msg:{}", request.getRequestURI(), 500, "内部错误");
    logger.error("uncaught error ", ex, request.getRequestURI());
    logger.error("uncaught error ", ex);
    Result exceptionResponse = new Result();
    exceptionResponse.setCode(500);
    exceptionResponse.setMessage("内部错误");
    return exceptionResponse;
  }
}

