package spbstu.ru.entities;

public class Item
{
    private static int idValue = 0;
    private int id;
    private int prodid;
    private String title;
    private int cost;

    public Item(String title, int cost)
    {
        id = ++idValue;
        prodid = idValue;
        this.title = title;
        this.cost = cost;
    }

    public int getId()
    {
        return id;
    }

    public int getProdid()
    {
        return prodid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }
}
