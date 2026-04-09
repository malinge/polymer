package com.polymer.framework.web.sensitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.polymer.framework.common.utils.DesensitizedUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author polymer
 * <p>
 * 脱敏序列化
 */
public class SensitiveSerialize extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveTypeEnum type;

    private Integer prefixNoMaskLen;

    private Integer suffixNoMaskLen;

    private String maskStr;

    public SensitiveSerialize() {
    }

    public SensitiveSerialize(SensitiveTypeEnum type, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
        this.type = type;
        this.prefixNoMaskLen = prefixNoMaskLen;
        this.suffixNoMaskLen = suffixNoMaskLen;
        this.maskStr = maskStr;
    }

    public SensitiveTypeEnum getType() {
        return type;
    }

    public void setType(SensitiveTypeEnum type) {
        this.type = type;
    }

    public Integer getPrefixNoMaskLen() {
        return prefixNoMaskLen;
    }

    public void setPrefixNoMaskLen(Integer prefixNoMaskLen) {
        this.prefixNoMaskLen = prefixNoMaskLen;
    }

    public Integer getSuffixNoMaskLen() {
        return suffixNoMaskLen;
    }

    public void setSuffixNoMaskLen(Integer suffixNoMaskLen) {
        this.suffixNoMaskLen = suffixNoMaskLen;
    }

    public String getMaskStr() {
        return maskStr;
    }

    public void setMaskStr(String maskStr) {
        this.maskStr = maskStr;
    }

    @Override
    public void serialize(final String origin, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        switch (type) {
            case CHINESE_NAME:
                jsonGenerator.writeString(DesensitizedUtils.chineseName(origin));
                break;
            case ID_CARD:
                jsonGenerator.writeString(DesensitizedUtils.idCardNum(origin));
                break;
            case FIXED_PHONE:
                jsonGenerator.writeString(DesensitizedUtils.fixedPhone(origin));
                break;
            case MOBILE_PHONE:
                jsonGenerator.writeString(DesensitizedUtils.mobilePhone(origin));
                break;
            case ADDRESS:
                jsonGenerator.writeString(DesensitizedUtils.address(origin));
                break;
            case EMAIL:
                jsonGenerator.writeString(DesensitizedUtils.email(origin));
                break;
            case BANK_CARD:
                jsonGenerator.writeString(DesensitizedUtils.bankCard(origin));
                break;
            case PASSWORD:
                jsonGenerator.writeString(DesensitizedUtils.password(origin));
                break;
            case KEY:
                jsonGenerator.writeString(DesensitizedUtils.key(origin));
                break;
            case CUSTOMER:
                jsonGenerator.writeString(DesensitizedUtils.desValue(origin, prefixNoMaskLen, suffixNoMaskLen, maskStr));
                break;
            default:
                throw new IllegalArgumentException("Unknow sensitive type enum " + type);
        }
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
                                              final BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                Sensitive sensitive = beanProperty.getAnnotation(Sensitive.class);
                if (sensitive == null) {
                    sensitive = beanProperty.getContextAnnotation(Sensitive.class);
                }
                if (sensitive != null) {
                    return new SensitiveSerialize(sensitive.type(), sensitive.prefixNoMaskLen(),
                            sensitive.suffixNoMaskLen(), sensitive.maskStr());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }

}
