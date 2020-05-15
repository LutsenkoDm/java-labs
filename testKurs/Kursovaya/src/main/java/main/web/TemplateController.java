package main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {
  @GetMapping("/journal")
  public String journalPage() {
    return "journal";
  }

  @GetMapping("/books")
  public String booksPage() {
    return "books";
  }

  @GetMapping("/clients")
  public String clientsPage() {
    return "clients";
  }

  @GetMapping("/bookTypes")
  public String bookTypesPage() {
    return "bookTypes";
  }

}
