package demo;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.mustache.StringChunk;

import java.time.Duration;
import java.util.logging.Level;
import java.util.List;

import demo.utils.ExcelDataProvider;
import demo.utils.ExcelReaderUtil;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;
        private Wrappers wrapper;

        @Test(priority = 1)
        public void testCase01() {
                System.out.println("START : testCase01 Started -->");
                driver.get("https://www.youtube.com/");
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

                String actualURL = driver.getCurrentUrl();
                String expectedURL = "https://www.youtube.com/";

                Assert.assertEquals(actualURL, expectedURL, "ERROR : URL doesn't matching");
                System.out.println("Pass : URL verified Successfully");

                WebElement about = driver.findElement(By.xpath("//*[@href='https://www.youtube.com/about/']"));
                wrapper.scroll(about);
                wrapper.click(By.xpath("//*[@href='https://www.youtube.com/about/']"));
                System.out.println("Pass : Clicked on About Successfully");
                wrapper.waitFor(By.xpath("(//*[@href='https://www.youtube.com/creators/'])[1]"));

                System.out.println(wrapper.getText(By.xpath(
                                "//*[@class='lb-font-display-3 lb-font-color-text-primary']/../h1")));

                List<WebElement> list = driver
                                .findElements(By.xpath("//*[@class='lb-font-display-3 lb-font-color-text-primary']"));
                for (WebElement webElement : list) {
                        System.out.println(webElement.getText());
                }
                System.out.println("Pass : Display message printed Successfully");
                System.out.println("END : testCase01 Ended " + "\n");
        }

        @Test(priority = 2)
        public void testCase02() throws InterruptedException {
                System.out.println("START : testCase01 Started -->");
                driver.get("https://www.youtube.com/");
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

                WebElement movies = driver
                                .findElement(By.xpath("//*[text()='Movies' and contains(@class,'renderer')]"));
                try {
                        wrapper.scroll(movies);
                        wrapper.click(By.xpath("//*[text()='Movies' and contains(@class,'renderer')]"));
                } catch (Exception e) {
                        wrapper.click(By.xpath("(//*[@id='guide-icon'])[2]/.."));
                        wrapper.scroll(movies);
                        wrapper.click(By.xpath("//*[text()='Movies' and contains(@class,'renderer')]"));
                }
                wrapper.scroll(movies);
                wrapper.click(By.xpath("//*[text()='Movies' and contains(@class,'renderer')]"));
                System.out.println("Pass : Clicked on Movies Successfully");
                wrapper.waitFor(By.xpath("//*[text()='Purchased']"));

                WebElement next = driver.findElement(By.xpath(
                                "//*[text()='Top selling']/ancestor::div[contains(@class,'grid')]/following-sibling::div/descendant::button[2]"));
                while (next.isEnabled()) {
                        wrapper.click(By.xpath(
                                        "//*[text()='Top selling']/ancestor::div[contains(@class,'grid')]/following-sibling::div/descendant::button[2]"));
                        if (!next.isDisplayed()) {
                                break;
                        }
                }
                System.out.println("Pass : Scrolled to the Extreme right Successfully");

                WebElement lastMovie = driver.findElement(By.xpath("(//*[@id='video-title'])[16]"));
                WebElement rated = lastMovie.findElement(By.xpath(
                                "./parent::h3/parent::a/following-sibling::ytd-badge-supported-renderer/div[2]/p"));
                String actualRated = rated.getText();
                System.out.println(actualRated);
                SoftAssert softAssert = new SoftAssert();
                softAssert.assertTrue("A".equals(actualRated) || "U".equals(actualRated) || "U/A".equals(actualRated),
                                "Movie is not [A],[U],[U/A] rated, its " + actualRated + " rated.");

                System.out.println("Pass : Verified wheather the movie is 'A' rated or not Successfully");

                WebElement genre = lastMovie.findElement(By.xpath("./parent::h3/parent::a/span"));
                String actualGenre = genre.getText();
                System.out.println(actualGenre);

                softAssert.assertTrue(
                                actualGenre.contains("Comedy") || actualGenre.contains("Animation")
                                                || actualGenre.contains("Drama"),
                                "The movie genre should be either 'Comedy' or 'Animation', but was: " + actualGenre);
                System.out.println(
                                "Pass : Verified wheather the movie Genre is either 'Comedy' or 'Animation' or 'Drama' Successfully");
                softAssert.assertAll();

                System.out.println("END : testCase02 Ended " + "\n");
        }

        @Test(priority = 3)
        public void testCase03() throws InterruptedException {

                System.out.println("START : testCase03 Started -->");
                driver.get("https://www.youtube.com/");
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

                try {
                        wrapper.click(By.xpath("//*[text()='Music' and contains(@class,'guide')]"));
                } catch (Exception e) {
                        wrapper.click(By.xpath("(//*[@id='guide-icon'])[2]/.."));
                        wrapper.click(By.xpath("//*[text()='Music' and contains(@class,'guide')]"));
                }
                System.out.println("Pass : Clicked on Music Successfully");

                wrapper.waitFor(By.xpath("(//*[contains(text(),'Biggest Hits')])[1]"));

                Thread.sleep(2000);

                WebElement nextMusic = driver.findElement(By.xpath(
                                "//*[contains(text(),'Biggest Hits')]/../../../../../../following-sibling::div/descendant::div[contains(@class,'touch-response')][2]"));
                // wrapper.scroll(nextMusic);
                while (nextMusic.isEnabled()) {
                        wrapper.click(By.xpath(
                                        "//*[contains(text(),'Biggest Hits')]/../../../../../../following-sibling::div/descendant::div[contains(@class,'touch-response')][2]"));
                        Thread.sleep(200);
                        if (!nextMusic.isDisplayed()) {
                                break;
                        }
                } // span[@id='title' and contains(text(),'Top
                  // selling')]/../../../../../../following-sibling::div//button[@aria-label='Next']
                System.out.println("Pass : Scrolled Successfully to Extream right");

                List<WebElement> playlists = driver.findElements(
                                By.xpath("//*[contains(text(),'Biggest Hits')]/../../../../../../following-sibling::div/descendant::ytd-compact-station-renderer"));
                WebElement lastPlaylistElement = playlists.get(playlists.size() - 1);

                WebElement playlistTitleElement = lastPlaylistElement.findElement(By.xpath("./div/a/h3"));
                String playlistTitle = playlistTitleElement.getText();
                System.out.println("Last Playlist Name : " + playlistTitle);
                System.out.println("Pass : Playlist name printed Successfully");

                WebElement noOfTracksElement = lastPlaylistElement.findElement(By.xpath("./div/a/p"));
                String noOfTracks = noOfTracksElement.getText();
                StringBuilder sb1 = new StringBuilder();
                char[] arr1 = noOfTracks.toCharArray();
                for (int i = 0; i < arr1.length; i++) {
                        if (arr1[i] == ' ') {
                                break;
                        } else {
                                sb1.append(arr1[i]);
                        }
                }
                int tracks = Integer.parseInt(sb1.toString());

                SoftAssert softAssert = new SoftAssert();
                softAssert.assertTrue(tracks <= 50, "Number of Tracks in Playlist are more than 50");
                System.out.println("Pass : Number of tracks in Playlist Verified Successfully");
                softAssert.assertAll();
                System.out.println("END : testCase03 Ended " + "\n");

        }

        @Test(priority = 4)
        public void testCase04() {
                System.out.println("START : testCase04 Started -->");
                driver.get("https://www.youtube.com/");
                driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10)); // before

                wrapper.click(By.xpath("//*[text()='News' and contains(@class,'guide')]"));
                System.out.println("Pass : Clicked on News Successfully");
                wrapper.waitFor(By.xpath("//*[text()='Latest news posts']"));
                // modify xpath in generic way :: DONE
                List<WebElement> topNewsStoriesParentElement = driver.findElements(By.xpath(
                                "//*[text()='Latest news posts']/../../../../../following-sibling::div/ytd-rich-item-renderer"));
                int totalLikeCount = 0;
                // add body of news story :: DONE
                for (int i = 0; i < 3; i++) {
                        System.out.println("Printing Title, Body and liks of " + (i + 1) + " News Story");
                        try {
                                WebElement storyTitleElement = topNewsStoriesParentElement.get(i).findElement(By.xpath(
                                                "./descendant::a[@id='author-text']/span"));
                                WebElement storyBodyElement = topNewsStoriesParentElement.get(i).findElement(By.xpath(
                                                "./descendant::span[@dir='auto'][1]"));
                                WebElement storylikeCountElement = topNewsStoriesParentElement.get(i)
                                                .findElement(By.xpath(
                                                                "./descendant::span[@id='vote-count-middle']"));
                                System.out.println((i + 1) + ". News Title : " + storyTitleElement.getText());
                                System.out.println((i + 1) + ". News Body : " + storyBodyElement.getText());
                                System.out.println((i + 1) + ". News Likes : " + storylikeCountElement.getText());
                                int count = Integer.parseInt(storylikeCountElement.getText());
                                totalLikeCount = totalLikeCount + count;

                        } catch (Exception e) {
                                System.out.println("IN CATCH BLOCK");
                                continue;
                        }

                }
                System.out.println("Pass : Titles and like count of 1st 3 News stories are printed Successfully");
                System.out.println("Pass : Sum of the number of likes on all 3 News Stories are : " + totalLikeCount);

        }

        @Test(priority = 5, dataProvider = "excelData")
        public void testCase05(String searchTerm) {
                driver.get("https://www.youtube.com");
                wrapper.search(By.name("search_query"), searchTerm);

                long totalViews = 0;
                while (totalViews < 100_000_000) { // 10 Crore = 100 million
                        List<WebElement> videoElements = driver.findElements(
                                        By.xpath("//ytd-video-renderer//div[@id='metadata-line']//span[1]"));

                        for (WebElement videoElement : videoElements) {
                                String viewsText = videoElement.getText();
                                long views = parseViews(viewsText);
                                totalViews += views;

                                if (totalViews >= 100_000_000) {
                                        break;
                                }
                        }

                        if (totalViews < 100_000_000) {
                                wrapper.scrollToBottom();
                        }
                }
        }

        private long parseViews(String viewsText) {
                viewsText = viewsText.toUpperCase().replaceAll("[^0-9KM]", "");

                if (viewsText.endsWith("M")) {
                        return (long) (Double.parseDouble(viewsText.replace("M", "")) * 1_000_000);
                } else if (viewsText.endsWith("K")) {
                        return (long) (Double.parseDouble(viewsText.replace("K", "")) * 1_000);
                } else {
                        return Long.parseLong(viewsText);
                }
        }

        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();

                wrapper = new Wrappers(driver, 10);
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}