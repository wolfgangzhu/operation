package com.github.wolfgang.operation.core.http;

import com.github.wolfgang.operation.core.OperationContext;

/**
 *
 * TODO 扩展，目前暂时不支持扩展
 * @author wolfgang
 * @date 2020-02-16 15:38:28
 * @version $ Id: CallbackUrlEncryptor.java, v 0.1  wolfgang Exp $
 */
public class CallbackUrlEncryptHelper {

    /**
     * encrypt with
     * @param url url
     * @return authenticator url
     */
    public static String encrypt(String url) {
        Integer operation = OperationContext.getContext().getOperationId();
        if (operation == null) {
            throw new IllegalStateException("oper id can't be null, please check the encrypt call in caller");
        }
        ApiAuthenticator apiAuthenticator = new ApiAuthenticator(url);
        return url + "?operId=" + operation + "&" + apiAuthenticator.toParameters();
    }

}
