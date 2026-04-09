package com.polymer.framework.encrypt.core.filter;

import com.polymer.framework.encrypt.core.utils.EncryptUtils;
import org.springframework.lang.NonNull;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * 加密响应参数包装类
 *
 * @author polymer
 */
public class EncryptResponseBodyWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream byteArrayOutputStream;
    private final ServletOutputStream servletOutputStream;
    private final PrintWriter printWriter;

    public EncryptResponseBodyWrapper(HttpServletResponse response) {
        super(response);
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.servletOutputStream = this.getOutputStream();
        this.printWriter = new PrintWriter(new OutputStreamWriter(byteArrayOutputStream));
    }

    @Override
    public PrintWriter getWriter() {
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (servletOutputStream != null) {
            servletOutputStream.flush();
        }
        if (printWriter != null) {
            printWriter.flush();
        }
    }

    @Override
    public void reset() {
        byteArrayOutputStream.reset();
    }

    public String getContent() throws IOException {
        flushBuffer();
        return byteArrayOutputStream.toString();
    }

    /**
     * 获取加密内容
     *
     * @param servletResponse response
     * @param publicKey       RSA公钥 (用于加密 AES 秘钥)
     * @param headerFlag      请求头标志
     * @return 加密内容
     * @throws IOException io
     */
    public String getEncryptContent(HttpServletResponse servletResponse, String publicKey, String headerFlag) throws IOException {
        // 生成秘钥
        String encryptAes = EncryptUtils.generateAesKey(256);
        // Rsa 公钥加密 Base64 编码
        String encryptPassword = EncryptUtils.encryptByRsa(encryptAes, publicKey);

        // 设置响应头
        servletResponse.addHeader("Access-Control-Expose-Headers", headerFlag);
        servletResponse.setHeader(headerFlag, encryptPassword);
        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        servletResponse.setHeader("Access-Control-Allow-Methods", "*");
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        // 获取原始内容
        String originalBody = this.getContent();
        // 对内容进行加密
        return EncryptUtils.encryptByAes(originalBody, encryptAes);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) {
                byteArrayOutputStream.write(b);
            }

            @Override
            public void write(@NonNull byte[] b) throws IOException {
                byteArrayOutputStream.write(b);
            }

            @Override
            public void write(@NonNull byte[] b, int off, int len) {
                byteArrayOutputStream.write(b, off, len);
            }
        };
    }

}
