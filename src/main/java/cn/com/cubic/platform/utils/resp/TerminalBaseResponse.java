package cn.com.cubic.platform.utils.resp;

import cn.com.flaginfo.platform.api.common.base.BaseResponse;

/**
 * @Author Meng.Liu
 * @Date 2017/12/19 10:44
 */
public class TerminalBaseResponse<T> extends BaseResponse<T> {

    public static <W> BaseResponse<W> error(String message) {
        return BaseResponse.error(-4001L, message);
    }

    public static BaseResponse success(String message) {
        return new BaseResponse(message);
    }

    public static <T> BaseResponse success(String message, T data) {
        return new BaseResponse<T>(message, data);
    }

    public static <T> BaseResponse success(T data) {
        BaseResponse baseResponse = new BaseResponse<T>();
        baseResponse.setData(data);
        return baseResponse;
    }
}
