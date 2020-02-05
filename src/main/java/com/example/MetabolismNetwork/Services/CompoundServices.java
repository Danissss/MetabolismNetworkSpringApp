package com.example.MetabolismNetwork.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.MetabolismNetwork.Repository.CompoundRepository;


// Don't need to use this service as the business logic if the logic is not too complicated.
// SideNote: is controller autoload at startup? Yes, but you can use @Lazy to prevent auto-initialization
// You can Use @Lazy to prevent eager loading/initialisation of your controller bean
// This will get instantiated once first request comes.
// @Lazy
// @Controller
// public class MainController { ...}
@Service
public class CompoundServices {
	
	@Autowired
	private final CompoundRepository compoundRepository;

	
	public CompoundServices(CompoundRepository compoundRepository) {
        this.compoundRepository = compoundRepository;
    }
	
	

	
	
	
}
