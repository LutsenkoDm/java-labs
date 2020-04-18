package ru.spbstu.main;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Main
{
  private static StudentGenerator studentGenerator = new StudentGenerator();
  private static ConcurrentLinkedQueue<Student> queue = new ConcurrentLinkedQueue<>();
  private static Robot robot1 = new Robot("Math", queue);
  private static Robot robot2 = new Robot("OOP", queue);
  private static Robot robot3 = new Robot("Physics", queue);

  public static void main(String[] args)
  {
    studentGenerator.start();
    studentGenerator.setResource(queue);
    robot1.start();
    robot2.start();
    robot3.start();
  }
}
