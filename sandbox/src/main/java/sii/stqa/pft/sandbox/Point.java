package sii.stqa.pft.sandbox;

public class Point {

  double x;
  double y;

  public double distance (Point p){
    return Math.sqrt((Math.pow((this.x-p.x),2))+(Math.pow((this.y-p.y),2)));
  }

}
