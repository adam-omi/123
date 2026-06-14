package pl.dmcs.rkotas.springbootjsp_iwa2026.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dmcs.rkotas.springbootjsp_iwa2026.model.Address;
import pl.dmcs.rkotas.springbootjsp_iwa2026.repository.AddressRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/addresses")
public class AddressRESTController {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressRESTController(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public ResponseEntity<Address> getOneAddress(@PathVariable("id") long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            System.out.println("Address not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address.get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Address> addAddress(@RequestBody Address address) {
        addressRepository.save(address);
        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Address> updateAddress(@RequestBody Address address, @PathVariable("id") long id) {
        address.setId(id);
        addressRepository.save(address);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Address> deleteAddress(@PathVariable("id") long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            System.out.println("Address not found!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        addressRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}