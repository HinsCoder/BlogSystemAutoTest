package Tests;

import common.AutoTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.IOException;

public class BlogInfoChecked extends AutoTestUtils {
    @BeforeAll
    static void baseControl()  {
        webDriver.get("http://localhost:8080/blog_system/blog_list.html");
    }

    /*
     * 博客列表页可以正常显示新发布的文章
     */

    @Test
    @Order(1)
    void listPageFirstBlog() {
        // 获取第一篇博客的标题
        String first_blog_title = webDriver.findElement(By.cssSelector("body > div.container > div.container-right > div:nth-child(1) > div.title")).getText();
        // 获取第一篇博客的发布时间
        String first_blog_time = webDriver.findElement(By.xpath("/html/body/div[2]/div[2]/div[1]/div[2]")).getText();
        // 校验博客标题是不是“自动化测试”
        Assertions.assertEquals("自动化测试",first_blog_title);
        // 如果时间是“2024-9-25”则测试通过
        if(first_blog_time.contains("2024-09-25")){
            System.out.println("测试通过");
        }
        else{
            System.out.println("当前时间是：" + first_blog_time);
            System.out.println("测试不通过");
        }
    }
}
