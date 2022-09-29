package sii.stqa.pft.sandbox;

public class Program {

  public static void main(String[] args) {

    Point p1 = new Point();
    Point p2 = new Point();
    p1.x = 1;
    p1.y = 2;
    p2.x = 1;
    p2.y = 4;
    System.out.println("Distance between p1 and p2 calculated from function = " + distance(p1, p2));

    System.out.println("Distance between p1 and p2 calculated from method = " + p1.distance(p2));

  }

  public static double distance(Point p1, Point p2) {
    return Math.sqrt((Math.pow((p2.x - p1.x), 2)) + (Math.pow((p2.y - p1.y), 2)));
  }

}