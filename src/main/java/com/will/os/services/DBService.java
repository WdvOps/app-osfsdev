package com.will.os.services; 

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.will.os.domain.Cliente;
import com.will.os.domain.OS;
import com.will.os.domain.Tecnico;
import com.will.os.domain.enuns.Prioridade;
import com.will.os.domain.enuns.Status;
import com.will.os.repositories.ClienteRepository;
import com.will.os.repositories.OSRepository;
import com.will.os.repositories.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OSRepository osRepository;

	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "William Freittas", "859.556.456-67", "55(31) 988886666");		
		Cliente c1 = new Cliente(null, "Augusta Freitas", "810.611.077-01", "55(31) 988887776");
		OS os1 = new OS(null, null, null, Prioridade.ALTA, "Teste create os", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));
	}

}
