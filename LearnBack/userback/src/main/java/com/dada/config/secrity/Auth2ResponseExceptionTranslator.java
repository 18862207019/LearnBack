package com.dada.config.secrity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import util.result.ResultGenerator;

/**
 * 自定义token失效转换类
 */
@Slf4j
public class Auth2ResponseExceptionTranslator implements WebResponseExceptionTranslator {

  @Override
  public ResponseEntity translate(Exception e) throws Exception {
    log.error("Auth2异常", e);

    Throwable throwable = e.getCause();
    if (throwable instanceof InvalidTokenException) {
      return new ResponseEntity<>(ResultGenerator.genFailResult("token失效"), HttpStatus.UNAUTHORIZED);
    }
    return new ResponseEntity<>(ResultGenerator.genFailResult(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()), HttpStatus.METHOD_NOT_ALLOWED);
  }
}
