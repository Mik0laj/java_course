package sii.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.ContactData;
import sii.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTest extends TestBase {

  @Test
  public void testContactModification() {
    app.getNavigationHelper().gotoHomePage();
    if (!app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoGroupPage();
      if (!app.getGroupHelper().isThereAGroup()) {
        app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        app.getNavigationHelper().gotoHomePage();
      }
      app.getContactHelper().createContact(new ContactData("Name", "Surname", null, null, null, null));
    }
    int before = app.getContactHelper().getContactCount();
    app.getContactHelper().initContactModification(before - 1);
    app.getContactHelper().fillContactForm(new ContactData("Name", "Surname", "Address", "123456789", "test@test.pl", null), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToHomePage();
    int after = app.getContactHelper().getContactCount();
    Assert.assertEquals(after, before);
  }
}
