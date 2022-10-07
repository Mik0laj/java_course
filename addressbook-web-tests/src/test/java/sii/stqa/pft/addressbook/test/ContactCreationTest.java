package sii.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoContactCreation();
    app.getContactHelper().fillContactForm(new ContactData("Name", "Surname", "Address", "123456789", "test@test.pl"));
    app.getContactHelper().submitContactCreation();
    app.getContactHelper().returnToHomePage();
  }
}
