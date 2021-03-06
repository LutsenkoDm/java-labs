package ru.spbstu.main;

import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class Robot extends Thread
{
  private boolean working = false;
  private String subject;
  private ConcurrentLinkedQueue<Student> queue;
  private static Semaphore semaphore  = new Semaphore(1, true);

  Robot(String subject, ConcurrentLinkedQueue<Student> queue)
  {
    this.subject = subject;
    this.queue = queue;
  }

  public void run()
  {
    while (true)
    {
      try
      {
        if (isInterrupted() && !queue.isEmpty())
        {
          new Robot(subject, queue).start();
        }
        if (!isInterrupted() && queue.element().subjectName.equals(subject) && !working)
        {
          working = true;
          semaphore.acquire();
          System.out.println("     Take " + queue.remove().toString() + "; ");
          semaphore.release();
          System.out.println("     " + subject + " ends");
          working = false;
        }
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
      catch (NoSuchElementException e)
      {
        interrupt();
      }
    }
  }
}

