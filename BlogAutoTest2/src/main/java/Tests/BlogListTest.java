package Tests;

import common.AutoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BlogListTest extends AutoTestUtils {

    @BeforeAll
    static void baseControl()  {
        webDriver.get("http://localhost:8080/blog_system/blog_list.html");
    }

    /*
     * 博客列表页可以正常显示
     */
    @Test
    void listPageLoadRight() throws IOException {
        // 可以多检查几个，确保正确
        webDriver.findElement(By.cssSelector("body > div.nav > a:nth-child(6)"));
        webDriver.findElement(By.cssSelector("body > div.container > div.container-left > div > img"));
        // 获取页面上所有博客标题对应的元素
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        int title_num = webDriver.findElements(By.cssSelector(".title")).size();
        // 如果元素数量不为0，则测试通过
        Assertions.assertNotEquals(0,title_num);
        saveScreenshot(getClass().getName());
    }
}
