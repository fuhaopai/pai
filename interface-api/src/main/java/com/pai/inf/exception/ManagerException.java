package com.pai.inf.exception;


/**
 * 通用业务处理异常
 *
 * @date 2017-06-06
 */
public class ManagerException extends RuntimeException {


    private static final long serialVersionUID = -7568732382779200678L;

    /**
     * 错误编码
     */
    private String code;

    /**
     * 定界符
     */
    private static final String DELIM_STR = "{}";

    public ManagerException(String message) {
        super(message);
    }

    public ManagerException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManagerException(String code, String message, Object... params) {
        super(replaceSymbol(message));
        this.code = code;
    }

    public ManagerException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ManagerException(String code, String message, Throwable cause, Object... params) {
        super(replaceSymbol(message, params), cause);
        this.code = code;
    }

    /**
     * 替换占位符
     *
     * @param format 待格式化的字符串
     * @param params 参数
     * @return
     */
    private static String replaceSymbol(final String format, final Object... params) {
        if (params == null || params.length == 0)
            return format;
        StringBuilder sbuilder = new StringBuilder(format.length() + 50);
        int fromIndex = 0, sercheIndex = 0;
        for (int i = 0, l = params.length; i < l; i++) {
            sercheIndex = format.indexOf(DELIM_STR, fromIndex);
            if (sercheIndex == -1) {
                if (i == 0) {
                    return format;
                } else if (fromIndex < format.length()) {
                    sbuilder.append(format.substring(fromIndex, format.length()));
                    break;
                }
            } else {
                sbuilder.append(format.substring(fromIndex, sercheIndex)).append(params[i]);
                fromIndex = sercheIndex + 2;
            }
        }
        return sbuilder.toString();
    }

    /**
     * 得到错误编码
     *
     * @return
     */
    public String getCode() {

        return code;
    }
}
