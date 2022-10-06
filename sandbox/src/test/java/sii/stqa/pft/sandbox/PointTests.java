package sii.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

  @Test
  public void testDistance1() {
    Point p1 = new Point(0,0);
    Point p2 = new Point(10,0);
    Assert.assertEquals(p1.distance(p2),10.0);
  }
  @Test
  public void testDistance2() {
    Point p1 = new Point(0,0);
    Point p2 = new Point(1,1);
    Assert.assertEquals(p1.distance(p2),1.41);
  }
  @Test
  public void testDistance3() {
    Point p1 = new Point(-5,-2);
    Point p2 = new Point(5,2);
    Assert.assertEquals(p1.distance(p2),10.77);
  }
}
