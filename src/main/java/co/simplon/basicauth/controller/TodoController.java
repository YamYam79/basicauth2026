package co.simplon.basicauth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.basicauth.entity.TodoEntity;
import co.simplon.basicauth.repository.TodoRepository;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepositoryInjected) {
        this.todoRepository = todoRepositoryInjected;
    }

 //Affiner les droits d'accès en ajoutant des annotations @PreAuthorize directement sur les routes de l'API.
 // Accessible par ROLE_USER et ROLE_ADMIN
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

    @GetMapping("")
    public List<TodoEntity> getAll() {
        return this.todoRepository.findAll();
    }

     // Accessible par ROLE_ADMIN uniquement
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TodoEntity create(@RequestBody TodoEntity entity) {
        return this.todoRepository.save(entity);
    }
}
