package com.polymer.application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.TimeUnit;

public class TestMain {
    public static void main(String[] args) {
        String description = "<p><img src=\"http://127.0.0.1:9000/polymer/20260625/1-%201-2_1782350029.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20260625%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20260625T011349Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=be3ff522f449c46ccad93a96719db3eb8d42a85f4150a24f3302af4819e8dc4a\" alt=\"1- 1-2.jpg\" data-href=\"20260625/1- 1-2_1782350029.jpg\" style=\"\"/>33424<img src=\"http://127.0.0.1:9000/polymer/20260625/0e587e04316db09f3ce4a604846acfc6_1782350037.jpeg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=minioadmin%2F20260625%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20260625T011357Z&X-Amz-Expires=600&X-Amz-SignedHeaders=host&X-Amz-Signature=84911ee83614e6f3423197a596b6925b1d479ade0b8490f2c42f6cd780c24e9d\" alt=\"0e587e04316db09f3ce4a604846acfc6.jpeg\" data-href=\"20260625/0e587e04316db09f3ce4a604846acfc6_1782350037.jpeg\" style=\"\"/></p>";
        if (description == null || description.isEmpty()) {
            return ;
        }
        // 解析HTML
        Document document = Jsoup.parse(description);
        // 获取所有img标签
        Elements imgElements = document.select("img");
        // 遍历所有img标签
        for (Element img : imgElements) {
            // 获取data-href属性值
            String dataHref = img.attr("data-href");

            // 如果data-href不为空，则重新构建src
            if (dataHref != null && !dataHref.isEmpty()) {

                // 更新src属性
                img.attr("src", dataHref);
            }
        }
        System.out.println(document.body().html());
    }
}
