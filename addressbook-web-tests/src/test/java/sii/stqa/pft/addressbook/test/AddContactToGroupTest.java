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

public class AddContactToGroupTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
      app.goTo().homePage();
    }
    if (app.db().contacts().size() == 0) {
      app.contact().create(new ContactData().withFirstName("Name").withLastName("Surname").withPhoto(new File("src/test/resources/pl.png")));
    } else {
      Contacts contacts = new Contacts(app.db().contacts().stream().filter((c) -> c.getGroups().size() != app.db().groups().size()).collect(Collectors.toSet()));
      if (contacts.size() == 0) {
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test4"));
        app.goTo().homePage();
      }
    }
  }

  @Test
  public void testAddContactToGroup() {
    ContactData contact = app.db().contacts().stream().filter((c) -> (c.getGroups().size() != app.db().groups().size())).iterator().next();
    Groups before = contact.getGroups();
    Groups groups = app.db().groups();
    for(GroupData group : contact.getGroups()){
      groups.remove(group);
    }
    GroupData group = groups.iterator().next();
    app.contact().addToGroup(contact, group);
    Groups after = contact.getGroups();
    before.add(group);
    assertThat(after, equalTo(before));
  }
}
