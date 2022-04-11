package main.Pagina;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

/**
 *
 * @author Joice
 */
public class EdgerDriverSample {

    public void testSimple() throws Exception {

        EdgeDriver driver = new EdgeDriver();

        try {
            driver.navigate().to("http://google.com.br");

            //digita no campo com nome "q" do google
            WebElement campoDeTexto = driver.findElement(By.name("q"));

            campoDeTexto.sendKeys("Caelum");

            //submete o form
            campoDeTexto.submit();
        } finally {
            driver.quit();
        }
    }

}
