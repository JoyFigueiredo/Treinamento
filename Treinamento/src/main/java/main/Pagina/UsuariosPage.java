package main.Pagina;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/*
 *
 * @author Joice
 */
public class UsuariosPage {

    private WebDriver driver;

    public UsuariosPage(WebDriver driver) {
        this.driver = driver;
    }

    public void visita() {
        driver.get("http://localhost:8080/usuarios/new");

    }

    public void novo() {
        driver
                .findElement(By.linkText("Novo Usuario"))
                .click();
    }

    public boolean existeNaListagem(String nome, String email) {
        //verifique se ambos estao na listagem
        return driver.getPageSource().contains(nome)
                && driver.getPageSource().contains(email);
    }
}
