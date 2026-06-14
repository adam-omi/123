package pl.dmcs.rkotas.springbootjsp_iwa2026.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.rkotas.springbootjsp_iwa2026.model.Account;
import pl.dmcs.rkotas.springbootjsp_iwa2026.repository.AccountRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;

@RestController
@RequestMapping("/accounts")
public class AccountRESTController {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountRESTController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Account> getOneAccount(@PathVariable("id") long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            System.out.println("Account not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        accountRepository.save(account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable("id") long id) {
        account.setId(id);
        accountRepository.save(account);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAccount(@PathVariable("id") long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            return new ResponseEntity<>("Account not found!", HttpStatus.NOT_FOUND);
        }
        if (account.get().getStudent() != null) {
            return new ResponseEntity<>("Cannot delete account because it is connected to a student!", HttpStatus.CONFLICT);
        }
        accountRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}