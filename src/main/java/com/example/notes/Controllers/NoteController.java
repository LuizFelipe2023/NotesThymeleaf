package com.example.notes.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.notes.entity.Note;
import com.example.notes.service.NoteService;

import java.util.Optional;

@Controller
@RequestMapping("/notes")
public class NoteController {

     private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String listNotes(Model model) {
        model.addAttribute("notes", noteService.findAll());
        return "notes/list"; 
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("note", new Note());
        return "notes/form"; 
    }

    @PostMapping("/save")
    public String saveNote(@ModelAttribute("note") Note note) {
        noteService.save(note);
        return "redirect:/notes";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Note> noteOpt = noteService.findById(id);
        if (noteOpt.isPresent()) {
            model.addAttribute("note", noteOpt.get());
            return "notes/form"; 
        } else {
            return "redirect:/notes";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Long id) {
        noteService.deleteById(id);
        return "redirect:/notes";
    }
}

