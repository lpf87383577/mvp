
/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinhoandroid.mvp.model.converter;
import com.google.gson.TypeAdapter;
import java.io.IOException;
import java.io.Reader;
import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, Object> {

    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }


    @Override
    public Object convert(ResponseBody value) throws IOException {
        try {
            Reader reader = value.charStream();
            Object obj = adapter.fromJson(reader);
            return obj;
//            特定错误返回
//            if (obj instanceof BaseResult) {
//
//                BaseResult baseResult = (BaseResult) obj;
//                if (!TextUtils.isEmpty(baseResult.status)) {
//                    if (TextUtils.equals(baseResult.status, StatusCodes.OAUTH_FAILTURE)) {
//                        //认证失败异常 直接去登陆页面
//                        throw new OauthFailtureException(baseResult);
//                    } else if (TextUtils.equals(baseResult.status, StatusCodes.TOKEN_INVALID)) {
//                        //token失效异常 去刷新token
//                        throw new TokenInvalidException(baseResult);
//                    } else if (StringUtils.intValue(baseResult.status) < 0) {
//                        //特定 API 的错误，在相应的 Subscriber 的 onError 的方法中进行处理
//                        throw new ApiException(baseResult);
//                    } else {
//                        return baseResult;
//                    }
//
//                } else {
//                    return baseResult;
//                }
//            }else {
//                return obj;
//            }

        } finally {
            value.close();
        }
    }


}
