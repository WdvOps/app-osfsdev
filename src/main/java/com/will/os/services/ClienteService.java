package com.will.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.will.os.domain.Cliente;
import com.will.os.domain.Pessoa;
import com.will.os.dtos.ClienteDTO;
import com.will.os.repositories.ClienteRepository;
import com.will.os.repositories.PessoaRepository;
import com.will.os.services.exceptions.DataIntegratyViolationException;
import com.will.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository2;
	
	//Busca de cliente por ID
	
	public Cliente findById(Integer id ) {
		
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException ("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	/*
	 * Busca todos os Clientes da base de dados
	 */

	public List<Cliente> findAll() {
		
		return repository.findAll();
	}
	
	//Criação de novo cliente com regra de não duplicidade de CPF
	
	public Cliente create(ClienteDTO objDTO) {
		
		if(findByCPF(objDTO) != null) {
			
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}
	
	//Atualização de dados do cliente
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new  DataIntegratyViolationException("CPF já cadastrado na base de dados");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		
		return repository.save(oldObj);
	
	}
	
	// Deleta cliente 
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if(obj.getList().size() > 0) {
			
			throw new DataIntegratyViolationException("Pessoa possui ordens de serviços e não pode ser deletado!");
		}
		
	    repository.deleteById(id);
		
	}
	
	// Busca de cliente pelo CPF
	
	private Pessoa findByCPF(ClienteDTO objDTO) {
		
		Pessoa obj = pessoaRepository2.findByCPF(objDTO.getCpf());
		
		if(obj != null) {
			return obj;
		}
		return null;
	}

	

	
}