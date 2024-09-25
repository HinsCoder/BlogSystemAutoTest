package Tests;

import common.AutoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BlogEditTest extends AutoTestUtils {
    @BeforeAll
    static void baseControl()  {
        webDriver.get("http://localhost:8080/blog_system/blog_list.html");
    }

    /*
     * 博客编辑页可以正常显示
     */

    @Test
    @Order(1)
    void editPageLoadRight() throws IOException {
        // 找到写博客按钮，点击
        webDriver.findElement(By.cssSelector("body > div.nav > a:nth-child(5)")).click();
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // 可以多检查几个，确保正确
        webDriver.findElement(By.cssSelector("#submit"));
        webDriver.findElement(By.cssSelector("#blog-title"));
        saveScreenshot(getClass().getName());
    }

    /*
     * 正确编辑并发布博客测试
     */

    @Test
    @Order(2)
    void editAndSubmitBlog() throws IOException {
        // 通过JS输入标题
        ((JavascriptExecutor) webDriver).executeScript("document.getElementById(\"blog-title\").value=\"自动化测试\"");
        // 编辑页的md是第三方插件，所以不可以直接使用sendKeys向编辑模块写入内容，但是可以通过点击上方按钮进行内容的插入
        webDriver.findElement(By.cssSelector("#editor > div.editormd-toolbar > div > ul > li:nth-child(30) > a > i")).click();
        webDriver.findElement(By.cssSelector("#editor > div.editormd-toolbar > div > ul > li:nth-child(5) > a > i")).click();
        saveScreenshot(getClass().getName());
        // 点击发布
        webDriver.findElement(By.cssSelector("#submit")).click();
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // 获取当前页面的URL
        String cur_url = webDriver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8080/blog_system/blog_list.html", cur_url);
        saveScreenshot(getClass().getName());
    }
}
