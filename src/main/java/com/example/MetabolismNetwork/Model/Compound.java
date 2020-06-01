/**
 * compound model contain the molecule structure/image (get from chemaxon)
 * other properties, etc.
 */
package com.example.MetabolismNetwork.Model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


// The @Entity annotation specifies that the class is an entity and is mapped to 
// a database table while the @Table annotation specifies the name of the 
// database table to be used for mapping.
@Entity
@Table(name = "compounds")
public class Compound {

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String images;
	
	private String smiles;
	
	private String inchi;
	
	@Column(nullable = false, unique = true)
	private String inchikey;
	
//	private ArrayList<String> belongs_to;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;  // auto increment id (like activerecord

	
	
	// constructor
	
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getSmiles() {
		return smiles;
	}

	public void setSmiles(String smiles) {
		this.smiles = smiles;
	}

	public String getInchi() {
		return inchi;
	}

	public void setInchi(String inchi) {
		this.inchi = inchi;
	}

	public String getInchikey() {
		return inchikey;
	}

	public void setInchikey(String inchikey) {
		this.inchikey = inchikey;
	}

//	public ArrayList<String> getBelongs_to() {
//		return belongs_to;
//	}
//
//	public void setBelongs_to(ArrayList<String> belongs_to) {
//		this.belongs_to = belongs_to;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        if (this.inchikey ==  ((Compound) obj).getInchikey()) {
        	return true;
        }
        
        return Objects.equals(this.id, ((Compound) obj).id);
    }
	
	
	
	
	
}
