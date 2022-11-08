package sii.stqa.pft.addressbook.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import sii.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTest extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList();
    GroupData group = new GroupData("test2", null, null);
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1);

    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    group.setId(after.stream().max(byId).get().getId());
    before.add(group);
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
