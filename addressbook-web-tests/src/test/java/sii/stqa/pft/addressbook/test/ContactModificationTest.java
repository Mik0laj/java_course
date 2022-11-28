package sii.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.ContactData;
import sii.stqa.pft.addressbook.model.Contacts;
import sii.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      if (app.db().groups().size() == 0) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1"));
        app.goTo().homePage();
      }
      app.contact().create(new ContactData().withFirstName("Name").withLastName("Surname").withPhoto(new File("src/test/resources/pl.png")));
    }
  }
  @Test
  public void testContactModification() {
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstName("Name")
            .withLastName("Surname")
            .withHomePhone("111")
            .withMobilePhone("222")
            .withWorkPhone("333")
            .withAddress("12345 Poland,123")
            .withEmail("abc@abc.pl")
            .withEmail2("qwe@qwe.pl")
            .withEmail3("zxc@zxc.pl")
            .withPhoto(new File("src/test/resources/pl.png"));
    app.contact().modify(contact);
    assertThat(app.contact().count(),equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after,equalTo(before.withModified(modifiedContact,contact)));
  }


}
