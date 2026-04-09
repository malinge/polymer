package com.polymer.framework.web.client.core;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.polymer.framework.common.exception.ServiceException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;

@Service
public class ApiClient {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 执行HTTP请求
     * @param url 请求URL
     * @param method 请求方法
     * @param headers 请求头
     * @param body 请求体
     * @param responseJavaType 响应类型
     * @return 响应数据
     */
    public <T, R> R execute(String url, HttpMethod method,
                            HttpHeaders headers, T body,
                            JavaType responseJavaType) {
        HttpEntity<T> entity = new HttpEntity<>(body, headers);
        ParameterizedTypeReference<ApiResponse<R>> typeRef = createTypeReference(responseJavaType);
        try {
            // 使用 exchange 获取原始 JSON 字符串
            ResponseEntity<ApiResponse<R>> apiResponse = restTemplate.exchange(
                    url, method,
                    entity, typeRef
            );

            // 3. 检查HTTP状态码
            if (apiResponse.getStatusCode() != HttpStatus.OK) {
                throw new ServiceException(apiResponse.getStatusCodeValue(),
                        "HTTP错误: " + apiResponse.getStatusCode());
            }

            return handleResponse(apiResponse.getBody());
        } catch (ServiceException e) {
            throw new ServiceException(e.getCode(), e.getMsg());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // 处理4xx/5xx错误
            throw new ServiceException(e.getRawStatusCode(), e.getResponseBodyAsString());
        } catch (Exception e) {
            // 处理其他异常
            throw new ServiceException("HTTP请求失败: " + e.getMessage());
        }
    }

    // 创建自定义的类型引用
    @NonNull
    private <R> ParameterizedTypeReference<ApiResponse<R>> createTypeReference(JavaType responseJavaType) {
        // 直接构建完整的参数化类型
        JavaType apiResponseType = objectMapper.getTypeFactory().constructParametricType(
                ApiResponse.class,
                responseJavaType
        );

        return new ParameterizedTypeReference<ApiResponse<R>>() {
            @Override
            @NonNull
            public Type getType() {
                return apiResponseType;
            }
        };
    }

    private <R> R handleResponse(ApiResponse<R> apiResponse) {
        if (apiResponse == null) {
            throw new ServiceException(500, "响应体为空");
        }

        if (!apiResponse.isSuccess()) {
            throw new ServiceException(apiResponse.getStatus(), apiResponse.getMessage());
        }

        return apiResponse.getResult();
    }


    // 获取单个对象
    public <R> R getForObject(String url, HttpHeaders headers, Class<R> responseType) {
        return execute(url, HttpMethod.GET, headers, null,
                TypeFactory.defaultInstance().constructType(responseType));
    }

    // 获取对象列表
    public <R> List<R> getForList(String url, HttpHeaders headers, Class<R> elementType) {
        JavaType listType = TypeFactory.defaultInstance().constructCollectionType(
                List.class, elementType
        );
        return execute(url, HttpMethod.GET, headers, null, listType);
    }

    // POST请求（返回对象）
    public <T, R> R postForObject(String url, HttpHeaders headers, T body, Class<R> responseType) {
        return execute(url, HttpMethod.POST, headers, body,
                TypeFactory.defaultInstance().constructType(responseType));
    }

    // POST请求（返回列表）
    public <T, R> List<R> postForList(String url, HttpHeaders headers, T body, Class<R> elementType) {
        JavaType listType = TypeFactory.defaultInstance().constructCollectionType(
                List.class, elementType
        );
        return execute(url, HttpMethod.POST, headers, body, listType);
    }
}