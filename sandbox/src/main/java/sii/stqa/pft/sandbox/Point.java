package sii.stqa.pft.sandbox;

public class Point {

  double x;
  double y;

  public Point() {

  }

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double distance(Point p) {
    return Math.round(Math.sqrt((Math.pow((this.x - p.x), 2)) + (Math.pow((this.y - p.y), 2)))*100)/100.00;
  }

}
