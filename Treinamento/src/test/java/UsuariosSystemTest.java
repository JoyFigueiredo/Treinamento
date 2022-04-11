
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import main.Pagina.UsuariosPage;
import org.openqa.selenium.edge.EdgeDriver;

/**
 *
 * @author Joice
 */
public class UsuariosSystemTest {

    private WebDriver driver;
    private UsuariosPage usuarios;

    @Before
    public void inicializa() {
        this.driver = new EdgeDriver();
        this.usuarios = new UsuariosPage(driver);
    }

    @Test
    public void deveAdicionarUmUsuario() {

        //driver.get("http://localhost:8080/usuarios/new");
        //WebElement nome = driver.findElement(By.name("usuario.nome"));
        //WebElement email = driver.findElement(By.name("usuario.email"));
        //nome.sendKeys("Ronaldo Luiz de Albuquerque");
        //email.sendKeys("ronaldo2009@terra.com.br");
        usuarios.visita();
        //usuarios.novo();
        
        usuarios.novo()
                .cadastra("Ronaldo Luiz de Alburquerque",
                        "ronaldo2009@terra.com.br");
        
        assertTrue(usuarios.existeNaListagem("Ronaldo Luiz de Alburquerque",
                        "ronaldo2009@terra.com.br"));
        /*
        nome.submit();
        
        boolean achouNome = driver.getPageSource().contains("Ronaldo Luiz de Alburqueque");
        boolean achouEmail = driver.getPageSource().contains("ronaldo2009@terra.com.br");        
        
        assertTrue(achouNome);
        assertTrue(achouEmail);
        
        driver.close();
         */
    }

    @After
    public void encerra() {
        driver.close();
    }
}
