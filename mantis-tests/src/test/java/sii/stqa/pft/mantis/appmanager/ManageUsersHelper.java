package sii.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import sii.stqa.pft.mantis.model.User;

public class ManageUsersHelper extends HelperBase{

  public ManageUsersHelper(ApplicationManager app) {
    super(app);
  }

  public void startResetPassword(User user) {
    wd.get(app.getProperty("web.baseUrl")+"/manage_user_edit_page.php?user_id="+user.getId());
    click(By.xpath("//input[@value='Reset Password']"));
  }

  public void finishResetPassword(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"),password);
    type(By.name("password_confirm"),password);
    click(By.cssSelector("input[value='Update User']"));
  }

  public void login(String administrator, String root) {
    type(By.name("username"), administrator);
    type(By.name("password"), root);
    click(By.xpath("//input[@value='Login']"));
  }
}
