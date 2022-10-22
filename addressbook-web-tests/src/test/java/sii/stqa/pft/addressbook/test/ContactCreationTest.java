package sii.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.ContactData;
import sii.stqa.pft.addressbook.model.GroupData;

public class ContactCreationTest extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    if(! app.getGroupHelper().isThereAGroup()){
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    app.getNavigationHelper().gotoHomePage();
    app.getContactHelper().createContact(new ContactData("Name", "Surname", null, null, null, null));

  }
}
