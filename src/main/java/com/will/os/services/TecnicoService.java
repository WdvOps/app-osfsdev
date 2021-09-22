package com.will.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.will.os.domain.Pessoa;
import com.will.os.domain.Tecnico;
import com.will.os.dtos.TecnicoDTO;
import com.will.os.repositories.PessoaRepository;
import com.will.os.repositories.TecnicoRepository;
import com.will.os.services.exceptions.DataIntegratyViolationException;
import com.will.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaRepository pessoarepository;
	
	//Busca de técnico por ID
	
	public Tecnico findById(Integer id ) {
		
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}
	
	/*
	 * Busca todos os tecnicos da base de dados
	 */


	public List<Tecnico> findAll() {
		
		return repository.findAll();
	}
	
	//Criação de novo técnico com regra de não duplicidade de CPF
	
	public Tecnico create(TecnicoDTO objDTO) {
		
		if(findByCPF(objDTO) != null) {
			
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}
	
	//Atualização de dados do técnico
	
	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new  DataIntegratyViolationException("CPF já cadastrado na base de dados");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		
		return repository.save(oldObj);
	
	}
	
	// Deleta técnico 
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getList().size() > 0) {
			
			throw new DataIntegratyViolationException("Técnico possui ordens de serviços e não pode ser deletado!");
		}
		
	    repository.deleteById(id);
		
	}
	
	// Busca de técnico pelo CPF
	
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		
		Pessoa obj = pessoarepository.findByCPF(objDTO.getCpf());
		
		if(obj != null) {
			return obj;
		}
		return null;
	}

	

	
}