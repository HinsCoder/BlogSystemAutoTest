package Tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({BlogLoginTest.class, BlogListTest.class, BlogEditTest.class, BlogInfoChecked.class, BlogDetailTest.class, BlogDeleteTest.class, BlogLogoutTest.class})
public class runSuite {
}
