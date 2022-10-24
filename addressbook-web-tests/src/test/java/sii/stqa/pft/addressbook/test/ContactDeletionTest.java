package sii.stqa.pft.addressbook.test;

import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.ContactData;
import sii.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTest extends TestBase{

  @Test
  public void testContactDeletion(){
    app.getNavigationHelper().gotoHomePage();
    if(! app.getContactHelper().isThereAContact()){
      app.getNavigationHelper().gotoGroupPage();
      if(! app.getGroupHelper().isThereAGroup()){
        app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        app.getNavigationHelper().gotoHomePage();
      }
      app.getContactHelper().createContact(new ContactData("Name", "Surname", null, null, null, null));
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().confirmDeletion();
  }
}
