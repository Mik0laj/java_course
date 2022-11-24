package sii.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.ContactData;
import sii.stqa.pft.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDetailsTest extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
      app.goTo().homePage();
    }

  }

  @Test
  public void testContactDetails() {
    app.goTo().homePage();
    ContactData contact = new ContactData().withFirstName("Name").withLastName("Surname")
            .withHomePhone("111").withMobilePhone("222").withWorkPhone("333")
            .withAddress("12345 Poland,123")
            .withEmail("abc@abc.pl").withEmail2("qwe@qwe.pl").withEmail3("zxc@zxc.pl");
    app.contact().create(contact);
    contact.withId(app.contact().all().stream().mapToInt((c) -> c.getId()).max().getAsInt());
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    app.goTo().homePage();
    ContactData contactInfoFromDetails = app.contact().infoFromDetails(contact);

    assertThat(contactInfoFromDetails.getFullName(), equalTo(mergeNames(contactInfoFromEditForm)));
    assertThat(mergePhones(contactInfoFromDetails), equalTo(mergePhones(contactInfoFromEditForm)));
    assertThat(mergeEmails(contactInfoFromDetails), equalTo(mergeEmails(contactInfoFromEditForm)));
    assertThat(contactInfoFromDetails.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));

  }

  private String mergeNames(ContactData contact) {
    return Arrays.asList(contact.getFirstName(), contact.getLastName())
            .stream().filter((s) -> !s.equals(""))
            .collect(Collectors.joining(" "));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s) -> !s.equals(""))
            .map(ContactDetailsTest::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> !s.equals(""))
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone
            .replaceAll("H: ", "")
            .replaceAll("M: ", "")
            .replaceAll("W: ", "");
  }

}
