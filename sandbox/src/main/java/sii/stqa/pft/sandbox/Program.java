package sii.stqa.pft.sandbox;

public class Program {

  public static void main(String[] args) {

    hello("world");
    hello("user");
    hello("Mikolaj");

    Square s = new Square(5);
    System.out.println("area of square with side " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(4, 6);
    System.out.println("area of rectangle with sides " + r.a + " and " + r.b + " = " + r.area());


  }

  public static void hello(String somebody) {
    System.out.println("Hello, " + somebody + "!");

  }



}