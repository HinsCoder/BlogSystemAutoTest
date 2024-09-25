package Tests;

import common.AutoTestUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class BlogLoginTest extends AutoTestUtils {
    // 所有用例都需要先获取登录页面
    @BeforeAll
    static void baseControl() {
        webDriver.get("http://localhost:8080/blog_system/login.html");
    }

    /**
     * 检查登录页面是否正确
     * 右上角与左上角的显示、登录框等
     */

    @Test
    @Order(1)
    void loginPageLoadRight() throws IOException {
        webDriver.findElement(By.cssSelector("body > div.nav > a:nth-child(4)"));
        webDriver.findElement(By.xpath("/html/body/div[1]/a[2]"));
        webDriver.findElement(By.cssSelector("body > div.login-container > form > div"));
        saveScreenshot(getClass().getName());
    }

    /**
     * 检查登录正常情况：使用多参数测试
     */

    @Order(3)
    @ParameterizedTest
    @CsvFileSource(resources = "LoginSuccess.csv")
    void loginSuccess(String username, String password, String blog_list_url) throws IOException {
        // 在每次登录之后都要进行清空，然后才能重新输入
        webDriver.findElement(By.cssSelector("#username")).clear();
        webDriver.findElement(By.cssSelector("#password")).clear();

        webDriver.findElement(By.cssSelector("#username")).sendKeys(username);
        webDriver.findElement(By.cssSelector("#password")).sendKeys(password);
        webDriver.findElement(By.cssSelector("#login-button")).sendKeys(Keys.ENTER);
        saveScreenshot(getClass().getName());
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        // 以上是登录步骤，但是并不能确保就是登录成功的
        // 获取到当前页面的URL，如果URL匹配则测试通过
        String cur_url = webDriver.getCurrentUrl();
        Assertions.assertEquals(blog_list_url, cur_url);

        // 列表页展示的用户信息是否是登录账号
        String cur_usr = webDriver.findElement(By.cssSelector("body > div.container > div.container-left > div > h3")).getText();
        Assertions.assertEquals(username, cur_usr);
        // 因为要多参数，所以在执行完一遍执行下一遍的时候需要进行页面的回退，否则找不到登录框
        webDriver.navigate().back();
    }

    /**
     * 测试完成后登录以便进行下一阶段测试
     */

    @Order(4)
    @Test
    void loginSuccessAfter()  {
        webDriver.findElement(By.cssSelector("#login-button")).click();
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    /**
     * 检查登录异常情况：使用多参数测试
     */

    @Order(2)
    @ParameterizedTest
    @CsvSource({"admin,123", "lisi,12", "'',''"})       // 第三项为空情况
    void loginFail(String username, String password) throws IOException {
        // 在每次登录之后都要进行清空，然后才能重新输入
        webDriver.findElement(By.cssSelector("#username")).clear();
        webDriver.findElement(By.cssSelector("#password")).clear();

        webDriver.findElement(By.cssSelector("#username")).sendKeys(username);
        webDriver.findElement(By.cssSelector("#password")).sendKeys(password);
        webDriver.findElement(By.cssSelector("#login-button")).click();
        saveScreenshot(getClass().getName());
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        // 登录失败的检测，获取文本进行比对
        String expectNotNull = "为空"; // 为空字样
        String expectError = "错误"; // 错误字样
        String actual = webDriver.findElement(By.cssSelector("body")).getText();

        if (actual.contains(expectNotNull) ||  actual.contains(expectError)) {
            System.out.println("登录失败测试通过");
        } else {
            System.out.println("登录失败测试不通过");
        }

        // 导航回登录页
        webDriver.navigate().back();
    }
}
