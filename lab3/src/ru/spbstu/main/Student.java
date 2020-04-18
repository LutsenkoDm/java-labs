package ru.spbstu.main;

public class Student
{
  int labsCount;
  String subjectName;
  Student(int labsCount, String subjectName)
  {
    this.labsCount = labsCount;
    this.subjectName = subjectName;
  }
  @Override
  public String toString()
  {
    return subjectName + " " + labsCount;
  }
}

