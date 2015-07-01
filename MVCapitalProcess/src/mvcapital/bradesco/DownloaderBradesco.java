package mvcapital.bradesco;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.File;
import java.util.Calendar;
import java.util.logging.Level;
import static java.awt.event.KeyEvent.*;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

public class DownloaderBradesco 
{
	private WebDriver driver = null;
	private Robot r= null;
	public DownloaderBradesco() 
	{
		FirefoxProfile fxProfile = new FirefoxProfile();

	    fxProfile.setPreference("browser.download.folderList",2);
	    fxProfile.setPreference("browser.download.manager.showWhenStarting",false);
	    
	    System.out.println("OS Name: " + System.getProperty("os.name").toLowerCase());
	    fxProfile.setAcceptUntrustedCertificates( true );

	    fxProfile.setPreference( "security.enable_java", true ); 

	    fxProfile.setPreference( "plugin.state.java", 2 );
	    fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
	    fxProfile.setPreference("browser.download.manager.showWhenStarting",false);
	    fxProfile.setPreference("browser.pdfjs.disabled",true);
	    fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",true);
	    this.driver = new FirefoxDriver(fxProfile);
        this.driver.navigate().to("https://www.ne2.bradesconetempresa.b.br/ibpjlogin/login.jsf?nscnn=3");
        WebElement selectCertificado = this.driver.findElement(By.id("rdoTipoAcesso01"));
        selectCertificado.click();
        timeAndWait(6);
        
        WebElement btnProcurar = this.driver.findElement(By.id("btnProcurar"));        
        WebElement pathCertificado = this.driver.findElement(By.id("textbox"));
        
        btnProcurar.click();
        
        
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		if(System.getProperty("os.name").toLowerCase().contains("windows"))
		{   	    
    	    r.mouseMove(950, 700);//coordinates of save button
		}
		else if(System.getProperty("os.name").toLowerCase().contains("linux"))
		{			
			r.mouseMove(950, 700);//coordinates of save button
		}
		
	    r.mousePress(InputEvent.BUTTON1_MASK);
	    r.mouseRelease(InputEvent.BUTTON1_MASK);
        
	   //r.keyPress();
	    CharSequence charSequence = "W:\\Fontes\\Bradesco\\Certificados\\cert_mi.crt"; 
	    this.type('W');
//	    this.type(':');
//	    this.type('\\');
//	    this.type("Fontes");
//        pathCertificado.sendKeys("W:\\Fontes\\Bradesco\\Certificados\\cert_mi.crt");
	}
	
	public static void main(String[] args)
	{
		DownloaderBradesco downloader = new DownloaderBradesco();		
	}
	
	public static void timeAndWait(int n)
	{
	        Calendar cal = Calendar.getInstance();
	        cal.getTime();
			for(int i=0;i<n;i++)
			{
			    cal = Calendar.getInstance();
			    cal.getTime();
		        try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}
	
    public void type(CharSequence characters) {
        int length = characters.length();
        for (int i = 0; i < length; i++) {
            char character = characters.charAt(i);
            type(character);
        }
    }

    public void type(char character) {
        switch (character) {
        case 'a': doType(VK_A); break;
        case 'b': doType(VK_B); break;
        case 'c': doType(VK_C); break;
        case 'd': doType(VK_D); break;
        case 'e': doType(VK_E); break;
        case 'f': doType(VK_F); break;
        case 'g': doType(VK_G); break;
        case 'h': doType(VK_H); break;
        case 'i': doType(VK_I); break;
        case 'j': doType(VK_J); break;
        case 'k': doType(VK_K); break;
        case 'l': doType(VK_L); break;
        case 'm': doType(VK_M); break;
        case 'n': doType(VK_N); break;
        case 'o': doType(VK_O); break;
        case 'p': doType(VK_P); break;
        case 'q': doType(VK_Q); break;
        case 'r': doType(VK_R); break;
        case 's': doType(VK_S); break;
        case 't': doType(VK_T); break;
        case 'u': doType(VK_U); break;
        case 'v': doType(VK_V); break;
        case 'w': doType(VK_W); break;
        case 'x': doType(VK_X); break;
        case 'y': doType(VK_Y); break;
        case 'z': doType(VK_Z); break;
        case 'A': doType(VK_SHIFT, VK_A); break;
        case 'B': doType(VK_SHIFT, VK_B); break;
        case 'C': doType(VK_SHIFT, VK_C); break;
        case 'D': doType(VK_SHIFT, VK_D); break;
        case 'E': doType(VK_SHIFT, VK_E); break;
        case 'F': doType(VK_SHIFT, VK_F); break;
        case 'G': doType(VK_SHIFT, VK_G); break;
        case 'H': doType(VK_SHIFT, VK_H); break;
        case 'I': doType(VK_SHIFT, VK_I); break;
        case 'J': doType(VK_SHIFT, VK_J); break;
        case 'K': doType(VK_SHIFT, VK_K); break;
        case 'L': doType(VK_SHIFT, VK_L); break;
        case 'M': doType(VK_SHIFT, VK_M); break;
        case 'N': doType(VK_SHIFT, VK_N); break;
        case 'O': doType(VK_SHIFT, VK_O); break;
        case 'P': doType(VK_SHIFT, VK_P); break;
        case 'Q': doType(VK_SHIFT, VK_Q); break;
        case 'R': doType(VK_SHIFT, VK_R); break;
        case 'S': doType(VK_SHIFT, VK_S); break;
        case 'T': doType(VK_SHIFT, VK_T); break;
        case 'U': doType(VK_SHIFT, VK_U); break;
        case 'V': doType(VK_SHIFT, VK_V); break;
        case 'W': doType(VK_SHIFT, VK_W); break;
        case 'X': doType(VK_SHIFT, VK_X); break;
        case 'Y': doType(VK_SHIFT, VK_Y); break;
        case 'Z': doType(VK_SHIFT, VK_Z); break;
        case '`': doType(VK_BACK_QUOTE); break;
        case '0': doType(VK_0); break;
        case '1': doType(VK_1); break;
        case '2': doType(VK_2); break;
        case '3': doType(VK_3); break;
        case '4': doType(VK_4); break;
        case '5': doType(VK_5); break;
        case '6': doType(VK_6); break;
        case '7': doType(VK_7); break;
        case '8': doType(VK_8); break;
        case '9': doType(VK_9); break;
        case '-': doType(VK_MINUS); break;
        case '=': doType(VK_EQUALS); break;
        case '~': doType(VK_SHIFT, VK_BACK_QUOTE); break;
        case '!': doType(VK_EXCLAMATION_MARK); break;
        case '@': doType(VK_AT); break;
        case '#': doType(VK_NUMBER_SIGN); break;
        case '$': doType(VK_DOLLAR); break;
        case '%': doType(VK_SHIFT, VK_5); break;
        case '^': doType(VK_CIRCUMFLEX); break;
        case '&': doType(VK_AMPERSAND); break;
        case '*': doType(VK_ASTERISK); break;
        case '(': doType(VK_LEFT_PARENTHESIS); break;
        case ')': doType(VK_RIGHT_PARENTHESIS); break;
        case '_': doType(VK_UNDERSCORE); break;
        case '+': doType(VK_PLUS); break;
        case '\t': doType(VK_TAB); break;
        case '\n': doType(VK_ENTER); break;
        case '[': doType(VK_OPEN_BRACKET); break;
        case ']': doType(VK_CLOSE_BRACKET); break;
        case '\\': doType(VK_BACK_SLASH); break;
        case '{': doType(VK_SHIFT, VK_OPEN_BRACKET); break;
        case '}': doType(VK_SHIFT, VK_CLOSE_BRACKET); break;
        case '|': doType(VK_SHIFT, VK_BACK_SLASH); break;
        case ';': doType(VK_SEMICOLON); break;
        case ':': doType(VK_COLON); break;
        case '\'': doType(VK_QUOTE); break;
        case '"': doType(VK_QUOTEDBL); break;
        case ',': doType(VK_COMMA); break;
        case '<': doType(VK_SHIFT, VK_COMMA); break;
        case '.': doType(VK_PERIOD); break;
        case '>': doType(VK_SHIFT, VK_PERIOD); break;
        case '/': doType(VK_SLASH); break;
        case '?': doType(VK_SHIFT, VK_SLASH); break;
        case ' ': doType(VK_SPACE); break;
        default:
            throw new IllegalArgumentException("Cannot type character " + character);
        }
    }

    private void doType(int... keyCodes) {
        doType(keyCodes, 0, keyCodes.length);
    }

    private void doType(int[] keyCodes, int offset, int length) {
        if (length == 0) {
            return;
        }

        this.r.keyPress(keyCodes[offset]);
        doType(keyCodes, offset + 1, length - 1);
        this.r.keyRelease(keyCodes[offset]);
    }	
}

