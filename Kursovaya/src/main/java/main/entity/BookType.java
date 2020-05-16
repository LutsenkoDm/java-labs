package main.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "bookTypes")
public class BookType {
    public BookType() {
    }

    public BookType(String name, Long cnt, Long fine, Long dayCount) {
        this.name = name;
        this.cnt = cnt;
        this.fine = fine;
        this.dayCount = dayCount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cnt")
    private Long cnt;

    @Column(name = "fine")
    private Long fine;

    @Column(name = "dayCount")
    private Long dayCount;

    @OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "typeId", referencedColumnName = "id")
    private Set<Book> books = new HashSet<>();
}
