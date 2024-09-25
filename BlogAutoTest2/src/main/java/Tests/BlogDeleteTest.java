package Tests;

import common.AutoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

public class BlogDeleteTest extends AutoTestUtils {
    @BeforeAll
    static void baseControl()  {
        webDriver.get("http://localhost:8080/blog_system/blog_list.html");
    }

    @Test
    void DeleteBlog () {
        // 点击查看全文按钮
        webDriver.findElement(By.cssSelector("body > div.container > div.container-right > div:nth-child(1) > a")).click();
        // 点击删除按钮
        webDriver.findElement(By.cssSelector("#delete_button")).click();
        // 博客列表页第一篇博客不是“自动化测试”
        String first_blog_title = webDriver.findElement(By.cssSelector("body > div.container > div.container-right > div:nth-child(1) > div.title")).getText();
        // 校验当前博客标题不等于“自动化测试”
        Assertions.assertNotEquals(first_blog_title,"自动化测试");
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        saveScreenshot(getClass().getName());

        // 尝试删除别人的文章
        // 点击查看全文按钮
        webDriver.findElement(By.cssSelector("body > div.container > div.container-right > div:nth-child(1) > a")).click();
        // 点击删除按钮
        webDriver.findElement(By.cssSelector("#delete_button")).click();
        // 删除失败的检测，获取文本进行比对
        String expectError = "没有权限"; // 没有权限错误字样
        String actual = webDriver.findElement(By.cssSelector("body")).getText();

        if (actual.contains(expectError)) {
            System.out.println("删除失败测试通过");
        } else {
            System.out.println("删除失败测试不通过");
        }
        saveScreenshot(getClass().getName());
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // 导航回列表页
        webDriver.navigate().back();
    }
}
