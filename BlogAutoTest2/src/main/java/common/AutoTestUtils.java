package common;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AutoTestUtils {
    public static WebDriver webDriver;

    @BeforeAll
    static void SetUp() {
        if(webDriver == null)
        {
            webDriver = new ChromeDriver();
        }
        // 设置隐式等待时间为3秒
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }


    public static void TearDown() {
        webDriver.quit();
    }

    // 保存截图的方法
    public static void saveScreenshot(String testName) {
        // 获取当前时间并格式化
        String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmssSSS").format(new Date());
        String dateFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String fileName = String.format("%s-%s.png", testName, timestamp);

        // 创建文件夹
        File folder = new File("screenshots/" + dateFolder);
        if (!folder.exists()) {
            folder.mkdirs(); // 创建文件夹
        }

        // 保存截图
        File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(screenshot.toPath(), Paths.get(folder.getPath(), fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
