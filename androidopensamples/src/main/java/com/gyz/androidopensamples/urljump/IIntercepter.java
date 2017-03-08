package com.gyz.androidopensamples.urljump;

/**
 * Created by guoyizhe on 2017/3/8.
 * 邮箱:gyzboy@126.com
 */

public interface IIntercepter {
    boolean process();//返回true,继续向下执行,否则拦截
}
