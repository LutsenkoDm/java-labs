package ru.spbstu.main;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.spbstu.main.Robot.semaphore;

public class StudentGenerator extends Thread
{
  private int maxQueueSize = 10;
  private ConcurrentLinkedQueue<Student> queue = new ConcurrentLinkedQueue<>();
  private static final String[] subjects = {"Math", "OOP", "Physics"};
  private static final int[] counts = {10, 20, 100};

  void setResource(ConcurrentLinkedQueue<Student> queue)
  {
    this.queue = queue;
  }

  synchronized public void run()
  {
    while (true)
    {
      if (queue.size() < maxQueueSize)
      {
        try
        {
          semaphore.acquire();
          Student student = new Student(counts[new Random().nextInt(counts.length)], subjects[new Random().nextInt(subjects.length)]);
          queue.add(student);
          System.out.println("Generated " + student.subjectName + ", " + student.labsCount + " labs;  ");
          semaphore.release();
        }
        catch (InterruptedException e)
        {
          e.printStackTrace();
        }
      }
    }
  }
}


