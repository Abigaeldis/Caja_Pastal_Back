package com.m2i.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.m2i.entities.Carte;
import com.m2i.services.BLLException;
import com.m2i.services.CarteService;

@RestController
@CrossOrigin
@RequestMapping("/cartes")
public class RecetteController {
	private CarteService carteService;

	@Autowired
	public RecetteController(CarteService carteService) {
		this.carteService = carteService;
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Carte>> getAllCartes() {
		Iterable<Carte> cartes = carteService.getAllCartes();
		return new ResponseEntity<>(cartes, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Carte> getCarteById(@PathVariable int id) {
		return carteService.getCarteById(id).map(carte -> new ResponseEntity<>(carte, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity<?> createCarte(@RequestBody Carte carte) {
		Carte createdCarte;
		try {
			createdCarte = carteService.createCarte(carte);
			return new ResponseEntity<>(createdCarte, HttpStatus.CREATED);
		} catch (BLLException e) {
			return new ResponseEntity<>(e.getErreurs(), HttpStatus.CONFLICT);
		}
}

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCarte(@PathVariable int id, @RequestBody Carte updatedCarte) {
        Carte updated;
		try {
			updated = carteService.updateCarte(id, updatedCarte);
	        if (updated != null) {
	            return new ResponseEntity<>(updated, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
		} catch (BLLException e) {
			return new ResponseEntity<>(e.getErreurs(), HttpStatus.CONFLICT);
		}
 }


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCarte(@PathVariable int id) {
		carteService.deleteCarte(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
