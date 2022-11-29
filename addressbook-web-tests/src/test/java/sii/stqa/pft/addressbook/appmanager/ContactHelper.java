package sii.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import sii.stqa.pft.addressbook.model.ContactData;
import sii.stqa.pft.addressbook.model.Contacts;
import sii.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase {

  private Contacts contactCache = null;

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToHomePage() {
    click(By.linkText("home"));
  }

  public void submitContactCreation() {
    click(By.xpath("//input[21]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstName());
    type(By.name("lastname"), contactData.getLastName());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomePhone());
    type(By.name("mobile"), contactData.getMobilePhone());
    type(By.name("work"), contactData.getWorkPhone());
    type(By.name("email"), contactData.getEmail());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
    attach(By.name("photo"),contactData.getPhoto());

    if (creation) {
      if(contactData.getGroups().size()>0){
        Assert.assertTrue(contactData.getGroups().size()==1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void initContactModification(int id) {
    wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
  }
  private void goToDetails(int id) {
    wd.findElement(By.cssSelector("a[href='view.php?id=" + id + "']")).click();

  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void confirmDeletion() {
    acceptModal();
  }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCache=null;
    returnToHomePage();
  }

  public void modify(ContactData contact) {
    initContactModification(contact.getId());
    fillContactForm(contact, false);
    submitContactModification();
    contactCache=null;
    returnToHomePage();
  }


  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    confirmDeletion();
    contactCache=null;
    returnToHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }


  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> rows = wd.findElements((By.name("entry")));
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements((By.tagName("td")));
      int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData()
              .withId(id)
              .withFirstName(cells.get(2).getText())
              .withLastName(cells.get(1).getText())
              .withAllPhones(cells.get(5).getText())
              .withAllEmails(cells.get(4).getText())
              .withAddress(cells.get(3).getText());

      contactCache.add(contact);
    }
    return new Contacts(contactCache);
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModification(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId())
            .withFirstName(firstname)
            .withLastName(lastname)
            .withHomePhone(home)
            .withMobilePhone(mobile)
            .withWorkPhone(work)
            .withAddress(address)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3);
  }

  public ContactData infoFromDetails(ContactData contact) {
    goToDetails(contact.getId());
    String[] content = wd.findElement((By.id("content"))).getText().split("\n");

    String fullName = content[0];
    String home = content[4];
    String mobile = content[5];
    String work = content[6];
    String address = content[2];
    String email = content[8];
    String email2 = content[9];
    String email3 = content[10];
    wd.navigate().back();
    return new ContactData()
            .withId(contact.getId())
            .withFullName(fullName)
            .withHomePhone(home)
            .withMobilePhone(mobile)
            .withWorkPhone(work)
            .withAddress(address)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3);
  }

  public void addToGroup(ContactData contact, GroupData group){
    selectContactById(contact.getId());
    wd.findElement(By.name("to_group")).click();
    wd.findElement(By.name("to_group")).findElement(By.cssSelector("option[value='" + group.getId() + "']")).click();
    wd.findElement(By.name("add")).click();
    wd.navigate().back();
  }


  public void removeGroup(ContactData contact, GroupData group) {
    wd.findElement(By.name("group")).click();
    wd.findElement(By.name("group")).findElement(By.cssSelector("option[value='" + group.getId() + "']")).click();
    selectContactById(contact.getId());
    wd.findElement(By.name("remove")).click();
    wd.navigate().back();
  }
}
