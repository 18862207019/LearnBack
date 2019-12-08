package com.dada.feign.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Oauth2.0服务验证
 */
@FeignClient(value = "oauth-server")
public interface OauthRpc {


	/**
	 * 获取access_token
	 */
	@PostMapping(path = "/oauth/token")
	Map<String, Object> postAccessToken(@RequestParam Map<String, String> parameters);


	/**
	 * 删除access_token和refresh_token
	 */
	@DeleteMapping(path = "/remove_token")
	void removeToken(@RequestParam("access_token") String access_token);

}
