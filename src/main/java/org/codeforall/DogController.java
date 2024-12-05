package org.codeforall;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DogController {

    private List<Dog> dogs = new ArrayList<>();

    public DogController(){
        Dog toy = new Dog();
        toy.setName("Toy");
        toy.setAge(3);
        toy.setBreed("Poodle");

        Dog bifana = new Dog();
        bifana.setName("Bifana");
        bifana.setAge(5);
        bifana.setBreed("Chihuahua");

        dogs.add(toy);
        dogs.add(bifana);
    }
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<List<Dog>> dogList(){
        return new ResponseEntity<>(dogs, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{name}")
    public ResponseEntity<String> dogBreed(@PathVariable String name) {

        for (Dog dog : dogs) {
            if (dog.getName().equalsIgnoreCase(name)) {
                return new ResponseEntity<>("That dog is a " + dog.getBreed(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Dog not found", HttpStatus.NOT_FOUND);

    }
    @RequestMapping(method = RequestMethod.POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addDog(@RequestBody Dog dog){
        dogs.add(dog);

        return new ResponseEntity<>(dog, HttpStatus.CREATED);
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{name}")
    public ResponseEntity<String> delete(@PathVariable String name){

        for (Dog dog : dogs) {
            if (dog.getName().equalsIgnoreCase(name)) {
                dogs.remove(dog);
                return new ResponseEntity<>(dog.getName() + " is removed", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Dog not found", HttpStatus.NOT_FOUND);
    }
    @RequestMapping(method = RequestMethod.PUT, value = "/edit/{name}")
    public ResponseEntity<String> edit(@PathVariable String name, @RequestBody Dog editedDog){

        for (Dog dog : dogs) {
            if (dog.getName().equalsIgnoreCase(name)) {
                dog.setName(editedDog.getName());
                dog.setAge(editedDog.getAge());
                dog.setBreed(editedDog.getBreed());

                return new ResponseEntity<>("Changes made to dog", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Dog not found", HttpStatus.NOT_FOUND);
    }

}
