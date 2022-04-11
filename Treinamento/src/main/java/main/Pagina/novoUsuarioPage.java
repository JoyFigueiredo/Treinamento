package main.Pagina;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author Joice
 */
public class novoUsuarioPage {

    private WebDriver driver;

    public novoUsuarioPage(WebDriver driver) {
        this.driver = driver;
    }

    public void cadastra(String nome, String email) {
        WebElement txtNome = driver.findElement(By.name("usuario.nome"));
        WebElement txtEmail = driver.findElement(By.name("usuario.email"));

        txtNome.sendKeys(nome);
        txtEmail.sendKeys(email);

        txtNome.submit();
    }
}
