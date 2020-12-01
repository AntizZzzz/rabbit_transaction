package com.xjw.entity.vo;

import com.xjw.entity.dto.SerializableDto;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: Restful API 返回值
 * @author: XIEJIANWEI
 * @create: 2020-07-04 18:31
 **/
@Data
@NoArgsConstructor
public class R<T> extends SerializableDto {

    public static final String SUCCESS_CODE = "0";

    public static final String FAILED_CODE = "-1";

    /**
     * 响应信息
     */
    private String message;

    /**
     * 错误码
     */
    private String code;
    /**
     * 响应数据
     */
    private T data;

    /**
     * 正常这个只有成功能用的上
     *
     * @param code
     * @param message
     * @param data
     */
    private R(String code, String message, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static R success() {
        return success(null, null);
    }

    public static R success(String message, Object data) {
        return new R(SUCCESS_CODE, message, data);
    }

    public static R failed() {
        return failed(FAILED_CODE, "操作失败", null);
    }

    public static R failed(String code, String message, Object data) {
        return new R(code, message, data);
    }

}
