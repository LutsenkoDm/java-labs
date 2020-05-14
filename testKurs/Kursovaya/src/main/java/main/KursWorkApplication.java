package main;

import main.entity.Book;
import main.entity.BookType;
import main.entity.Client;
import main.repository.BookRepository;
import main.repository.BookTypeRepository;
import main.repository.ClientRepository;
import main.repository.JournalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KursWorkApplication {

    private static final Logger log = LoggerFactory.getLogger(KursWorkApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KursWorkApplication.class, args);
    }

    @Bean
    public CommandLineRunner test(/*JournalRepository journalRepository, ClientRepository clientRepository,
                                  BookRepository bookRepository, BookTypeRepository bookTypeRepository*/) {
        return args -> {
            /*bookTypeRepository.save(new BookType("fun", 10L, 22L, 1000L));
            bookTypeRepository.save(new BookType("comedy", 25L, 77L, 313L));

            bookRepository.save(new Book("FirstBook", 1L, 1L));
            bookRepository.save(new Book("SecondBook", 3L, 1L));

            clientRepository.save(new Client("Dmitrii", "Lutsenko", "Urievich", "1111", "333"));
            clientRepository.save(new Client("Oleg", "Fomin", "Alekseivch", "2222", "677"));


            /*for (Book book : bookRepository.findAll()) {
                log.info("The book is: " + book.toString());
            }

            log.info("--------------------------------------");

            for (BookType bookType : bookTypeRepository.findAll()) {
                log.info("The book type is: " + bookType.toString());
            }

            log.info("--------------------------------------");

            for (Client client : clientRepository.findAll()) {
                log.info("The client is: " + client.toString());
            }*/

            log.info("--------------------------------------");
        };
    }
}
