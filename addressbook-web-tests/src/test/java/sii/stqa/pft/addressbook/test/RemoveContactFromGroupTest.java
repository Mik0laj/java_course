package sii.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.ContactData;
import sii.stqa.pft.addressbook.model.Contacts;
import sii.stqa.pft.addressbook.model.GroupData;
import sii.stqa.pft.addressbook.model.Groups;

import java.io.File;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroupTest extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
      app.goTo().homePage();
    }
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Name").withLastName("Surname").withPhoto(new File("src/test/resources/pl.png")).inGroup(app.db().groups().iterator().next()));
    } else {
      Contacts contacts = new Contacts(app.db().contacts().stream().filter((c) -> c.getGroups().size() > 0).collect(Collectors.toSet()));
      if (contacts.size() == 0) {
        app.contact().create(new ContactData().withFirstName("Name").withLastName("Surname").withPhoto(new File("src/test/resources/pl.png")).inGroup(app.db().groups().iterator().next()));
      }
    }
  }

  @Test
  public void testRemoveContactFromGroup() {
    ContactData contact = app.db().contacts().stream().filter((c) -> (c.getGroups().size() > 0)).iterator().next();
    Groups before = contact.getGroups();
    GroupData group = before.iterator().next();
    app.contact().removeGroup(contact,group);
    Groups after = contact.getGroups();
    before.remove(group);
    assertThat(after, equalTo(before));
  }
}
