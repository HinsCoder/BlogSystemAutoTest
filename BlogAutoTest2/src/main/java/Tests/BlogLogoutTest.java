package Tests;

import common.AutoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

public class BlogLogoutTest extends AutoTestUtils {
    @BeforeAll
    static void baseControl()  {
        webDriver.get("http://localhost:8080/blog_system/blog_list.html");
    }

    @Test
    void Logout(){
        webDriver.findElement(By.cssSelector("body > div.nav > a:nth-child(6)")).click();
        // 校验当前页面URL是否是“http://localhost:8080/blog_system/login.html”
        String cur_url=webDriver.getCurrentUrl();
        Assertions.assertEquals("http://localhost:8080/blog_system/login.html",cur_url);
        // 校验提交按钮
        WebElement webElement = webDriver.findElement(By.cssSelector("#login-button"));
        Assertions.assertNotNull(webElement);
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        saveScreenshot(getClass().getName());
        //webDriver.quit();
        TearDown();
    }
}
