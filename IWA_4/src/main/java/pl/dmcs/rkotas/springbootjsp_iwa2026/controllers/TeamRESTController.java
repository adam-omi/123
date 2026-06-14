package pl.dmcs.rkotas.springbootjsp_iwa2026.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.rkotas.springbootjsp_iwa2026.model.Student;
import pl.dmcs.rkotas.springbootjsp_iwa2026.model.Team;
import pl.dmcs.rkotas.springbootjsp_iwa2026.repository.StudentRepository;
import pl.dmcs.rkotas.springbootjsp_iwa2026.repository.TeamRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamRESTController {

    private final TeamRepository teamRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TeamRESTController(TeamRepository teamRepository, StudentRepository studentRepository) {
        this.teamRepository = teamRepository;
        this.studentRepository = studentRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Team> getOneTeam(@PathVariable("id") long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isEmpty()) {
            System.out.println("Team not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(team.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        teamRepository.save(team);
        return new ResponseEntity<>(team, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Team> updateTeam(@RequestBody Team team, @PathVariable("id") long id) {
        team.setId(id);
        teamRepository.save(team);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Team> deleteTeam(@PathVariable("id") long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isEmpty()) {
            System.out.println("Team not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            if (student.getTeamList() != null) {
                boolean removed = student.getTeamList().removeIf(t -> t.getId() == id);
                if (removed) {
                    studentRepository.save(student);
                }
            }
        }
        teamRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}