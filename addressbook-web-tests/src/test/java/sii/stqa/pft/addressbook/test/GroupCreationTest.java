package sii.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTest extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    List<GroupData> before = app.group().list();
    GroupData group = new GroupData().withName("test1");
    app.group().create(group);
    List<GroupData> after = app.group().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    group.withId(after.stream().max(byId).get().getId());
    before.add(group);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
