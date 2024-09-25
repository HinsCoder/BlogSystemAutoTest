package Tests;

import common.AutoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class BlogDetailTest extends AutoTestUtils {
    @BeforeAll
    static void baseControl()  {
        webDriver.get("http://localhost:8080/blog_system/blog_list.html");
    }

    public static Stream<Arguments> Generator() {
        return Stream.of(Arguments.arguments("http://localhost:8080/blog_system/blog_detail.html",
                "博客详情页","自动化测试"));
    }

    @ParameterizedTest
    @MethodSource("Generator")
    void BlogDetail(String expected_url,String expected_title,String expected_blog_title) {
        // 找到第一个博客对应的查看全文按钮
        webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/a")).click();
        // 获取当前页面的URL、页面title、博客标题
        String cur_url = webDriver.getCurrentUrl();
        String cur_title = webDriver.getTitle();
        String cur_blog_title = webDriver.findElement(By.cssSelector("body > div.container > div.container-right > div > h3")).getText();
        Assertions.assertEquals(expected_title, cur_title);
        Assertions.assertEquals(expected_blog_title, cur_blog_title);
        if (cur_url.contains(expected_url)) {
            System.out.println("测试通过");
        } else {
            System.out.println("测试不通过");
        }
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        saveScreenshot(getClass().getName());
    }
}
